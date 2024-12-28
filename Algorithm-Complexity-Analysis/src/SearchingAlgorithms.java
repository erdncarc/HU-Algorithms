import java.util.Random;

public class SearchingAlgorithms {
	int linearSearch(int[] array, int x) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == x) return i;
		}

		return -1;
	}

	int binarySearch(int[] array, int x) {
		int low = 0;
		int high = array.length - 1;

		while (high - low > 1) {
			int mid = (high + low) / 2;

			if (array[mid] < x) low = mid + 1;
			else high = mid;
		}

		if (array[low] == x) return low;
		else if (array[high] == x) return high;

		return -1;
	}

	static void getSearchTime(long[] linearR, long[] linearS, long[] binary, FunctionTimer timer, int n, int[] randomData, int[] sortData) {
		SearchingAlgorithms search = new SearchingAlgorithms();
		Random random = new Random();
		long avgLinearR = 0, avgLinearS = 0, avgBinary = 0;

		for (int i = 0; i < 1000; i++) {
			int searchValue = randomData[random.nextInt(randomData.length)];
			avgLinearR += timer.getTime(() -> search.linearSearch(randomData, searchValue), false);
			avgLinearS += timer.getTime(() -> search.linearSearch(sortData, searchValue), false);
			avgBinary += timer.getTime(() -> search.binarySearch(sortData, searchValue), false);
		}

		linearR[n] = avgLinearR / 1000;
		linearS[n] = avgLinearS / 1000;
		binary[n] = avgBinary / 1000;
	}
}