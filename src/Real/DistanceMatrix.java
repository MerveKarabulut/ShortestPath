package Real;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class DistanceMatrix {

	private List<String> cities;

	private List < List<Integer>> matrix;

	public DistanceMatrix(String csvFile) {
		this.cities = getCities(csvFile);
		this.matrix = readCsvIntoMatrix(csvFile);
	}

	// Add getters for cities and matrix if needed
	public List<String> getCities() {
		return cities;
	}

	private static List<String> getCities(String csvFile) {
		List<String> cities = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			String line = br.readLine();

			if (line != null) {
				String[] headers = line.split(",");

				for (int i = 1; i < headers.length; i++) {
					// Replace the � character with an empty string
					String city = headers[i].trim().replaceAll("\uFFFD", "");
					cities.add(city);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return cities;
	}

	private static List < List<Integer>> readCsvIntoMatrix(String csvFile) {
		List < List<Integer>> matrix = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			String line;
			br.readLine(); // Skip header line
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				List<Integer> distances = new ArrayList<>();

				for (int col = 1; col < values.length; col++) {
					String value = values[col].trim()
						.replaceAll("[^0-9-]", "") // Remove all non-digit characters except minus
						.replaceAll("\u00a0", "") // Remove non-breaking space
						.replaceAll("\uFFFD", ""); // Remove the replacement character
					try {
						distances.add(Integer.parseInt(value.isEmpty() ? "-1" : value));
					} catch (NumberFormatException e) {
						distances.add(-1); // Default to -1 if parsing fails
					}
				}

				matrix.add(distances);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return matrix;
	}

	public GenericHelpers.Result findPath(String startCity, String endCity, String algorithm) {
		int startIndex = cities.indexOf(startCity);
		int endIndex = cities.indexOf(endCity);

		if (startIndex == -1 || endIndex == -1) {
			return null;
		}

		switch (algorithm) {
			case "BFS":
				return BFS.bfs(matrix, startIndex, endIndex);
			case "DFS":
				return DFS.dfs(matrix, startIndex, endIndex);
			default:
				return null;
		}
	}
}