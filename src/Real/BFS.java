package Real;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class BFS {

	public static GenericHelpers.Result bfs(List < List<Integer>> matrix, int start, int end) {
		Queue < GenericHelpers.Node > queue = new LinkedList<>();
		int[] distances = new int[matrix.size()];
		int[] predecessors = new int[matrix.size()];
		Arrays.fill(distances, Integer.MAX_VALUE);
		Arrays.fill(predecessors, -1);
		distances[start] = 0;
		queue.add(new GenericHelpers.Node(start, 0));

		while (!queue.isEmpty()) {
			GenericHelpers.Node current = queue.poll();
			int currentIndex = current.index;

			for (int i = 0; i < matrix.get(currentIndex).size(); i++) {
				int edgeWeight = matrix.get(currentIndex).get(i);

				if (edgeWeight >= 0) {
					int newDistance = current.cumulativeDistance + edgeWeight;

					if (newDistance < distances[i]) {
						distances[i] = newDistance;
						predecessors[i] = currentIndex;
						queue.add(new GenericHelpers.Node(i, newDistance));
					}
				}
			}
		}

		List<Integer> path = GenericHelpers.reconstructPath(predecessors, end);
		return new GenericHelpers.Result(path, distances[end]);
	}
}