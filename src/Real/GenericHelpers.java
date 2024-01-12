package Real;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GenericHelpers {

	public static List<Integer> reconstructPath(int[] predecessors, int end) {
		List<Integer> path = new ArrayList<>();

		for (int at = end; at != -1; at = predecessors[at]) {
			path.add(at);
		}

		Collections.reverse(path);
		return path;
	}

	// Node class to hold the index and cumulative distance
	static class Node {
		int index;
		int cumulativeDistance;

		public Node(int index, int cumulativeDistance) {
			this.index = index;
			this.cumulativeDistance = cumulativeDistance;
		}
	}

	// Result class to hold the path and the total distance
	static class Result {
		List<Integer> path;
		int distance;

		public Result(List<Integer> path, int distance) {
			this.path = path;
			this.distance = distance;
		}
	}
}