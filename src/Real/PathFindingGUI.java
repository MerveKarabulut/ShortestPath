package Real;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

class PathFindingGUI {

	private JFrame frame;

	private JComboBox<String> startCityDropdown, endCityDropdown, algorithmDropdown;

	private JTextArea resultArea;

	private DistanceMatrix distanceMatrix;

	private JButton searchButton;

	private JButton selectFileButton;

	private JFileChooser fileChooser;

	public PathFindingGUI() {
		initializeComponents();
		addEventHandlers();
	}

	private void initializeComponents() {
		frame = new JFrame("Path Finding GUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		// Initialize components
		startCityDropdown = new JComboBox<>();
		endCityDropdown = new JComboBox<>();
		algorithmDropdown = new JComboBox<>(new String[] {
			"BFS", "DFS"
		});
		searchButton = new JButton("Find Path");
		resultArea = new JTextArea(5, 20);
		resultArea.setEditable(false);

		selectFileButton = new JButton("Select CSV File");
		fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
		fileChooser.setFileFilter(filter);
		selectFileButton.addActionListener(new ActionListener() {
			@Override

			public void actionPerformed(ActionEvent e) {
				int returnValue = fileChooser.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					String selectedFile = fileChooser.getSelectedFile().getAbsolutePath();
					// Update the DistanceMatrix with the selected CSV file
					distanceMatrix = new DistanceMatrix(selectedFile);
					populateCities();
				}
			}
		});

		Color oldColor = startCityDropdown.getBackground();
		Color newColor = Color.LIGHT_GRAY;

		MouseAdapter colorChangeAdapter = new MouseAdapter() {
			@Override

			public void mouseEntered(MouseEvent e) {
				((Component) e.getSource()).setBackground(newColor);
			}

			@Override

			public void mouseExited(MouseEvent e) {
				((Component) e.getSource()).setBackground(oldColor);
			}
		};

		searchButton.addMouseListener(colorChangeAdapter);
		selectFileButton.addMouseListener(colorChangeAdapter);

		JPanel topPanel = new JPanel();
		topPanel.add(new JLabel("Start City:"));
		topPanel.add(startCityDropdown);
		topPanel.add(new JLabel("End City:"));
		topPanel.add(endCityDropdown);
		topPanel.add(new JLabel("Algorithm:"));
		topPanel.add(algorithmDropdown);
		topPanel.add(selectFileButton);
		topPanel.add(searchButton);

		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(new JScrollPane(resultArea), BorderLayout.CENTER);

		frame.setSize(800, 200);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); // Center the window
		frame.setVisible(true);
	}

	private void populateCities() {
		List<String> cities = distanceMatrix.getCities();

		for (String city: cities) {
			startCityDropdown.addItem(city);
			endCityDropdown.addItem(city);
		}
	}

	private void addEventHandlers() {
		searchButton.addActionListener(new ActionListener() {
			@Override

			public void actionPerformed(ActionEvent e) {
				performSearch();
			}
		});
	}

	public void performSearch() {
		String startCity = (String) startCityDropdown.getSelectedItem();
		String endCity = (String) endCityDropdown.getSelectedItem();
		String algorithm = (String) algorithmDropdown.getSelectedItem();
		GenericHelpers.Result result = distanceMatrix.findPath(startCity, endCity, algorithm);

		if (result != null) {
			List<String> cityPath = result.path.stream()
				.map(index -> distanceMatrix.getCities().get(index)).toList();
			resultArea.setText("Path: " + cityPath + "\nDistance: " + result.distance);
		} else {
			resultArea.setText("Invalid input or algorithm selection.");
		}
	}

	public void display() {
		if (frame != null) {
			frame.setVisible(true);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				new PathFindingGUI().display();
			}
		});
	}
}