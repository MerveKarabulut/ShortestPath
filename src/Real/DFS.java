package Real;

import java.util.Arrays;
import java.util.List;


public class DFS {

	public static GenericHelpers.Result dfs(List < List<Integer>> matrix, int start, int end) {
		boolean[] visited = new boolean[matrix.size()];
		int[] distances = new int[matrix.size()];
		int[] predecessors = new int[matrix.size()];
		Arrays.fill(distances, Integer.MAX_VALUE);
		Arrays.fill(predecessors, -1);
		distances[start] = 0;

		dfsVisit(matrix, start, end, 0, distances, predecessors, visited);

		List<Integer> path = GenericHelpers.reconstructPath(predecessors, end);
		return new GenericHelpers.Result(path, distances[end]);
	}

	private static void dfsVisit(List < List<Integer>> matrix, int current, int end, int currentDistance, int[] distances, int[] predecessors, boolean[] visited) {
		visited[current] = true;

		for (int i = 0; i < matrix.get(current).size(); i++) {
			int edgeWeight = matrix.get(current).get(i);

			if (edgeWeight != -1 && (currentDistance + edgeWeight  <  distances[i])) {
				distances[i] = currentDistance + edgeWeight;
				predecessors[i] = current;
				dfsVisit(matrix, i, end, distances[i], distances, predecessors, visited);
			}
		}

		visited[current] = false; // Allow revisiting for other paths
	}
}