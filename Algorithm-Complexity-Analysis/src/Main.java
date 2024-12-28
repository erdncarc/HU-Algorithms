import java.util.Arrays;

public class Main {
	public static void main(String[] args) {
		int[] range = {500, 1_000, 2_000, 4_000, 8_000, 16_000, 32_000, 64_000, 128_000, 250_000};
		int[] data = DataFile.getData("TrafficFlowDataset.csv");

		long[][] avgInsert = new long[3][range.length];
		long[][] avgMerge = new long[3][range.length];
		long[][] avgCount = new long[3][range.length];
		long[][] avgLinear = new long[2][range.length];
		long[] avgBinary = new long[range.length];

		FunctionTimer timer = new FunctionTimer();
		for (int n = 0; n < range.length; n++) {
			int[] tempSort = Arrays.copyOf(data, range[n]);
			int[] tempSearch = Arrays.copyOf(data, range[n]);

			SortingAlgorithms.getSortTime(avgInsert[0], avgMerge[0], avgCount[0], timer, n, tempSort);

			Arrays.sort(tempSort);
			SortingAlgorithms.getSortTime(avgInsert[1], avgMerge[1], avgCount[1], timer, n, tempSort);
			SearchingAlgorithms.getSearchTime(avgLinear[0], avgLinear[1], avgBinary, timer, n, tempSearch, tempSort);

			Utilities.reverseArray(tempSort);
			SortingAlgorithms.getSortTime(avgInsert[2], avgMerge[2], avgCount[2], timer, n, tempSort);
		}

		new Graphics("Random Input Data Timing Results", range, avgInsert[0], avgMerge[0], avgCount[0], true);
		new Graphics("Sorted Input Data Timing Results", range, avgInsert[1], avgMerge[1], avgCount[1], true);
		new Graphics("Reverse Sorted Input Data Timing Results", range, avgInsert[2], avgMerge[2], avgCount[2], true);
		new Graphics("Search Timing Results", range, avgLinear[0], avgLinear[1], avgBinary, false);
	}
}