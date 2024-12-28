import java.util.Arrays;

public class SortingAlgorithms {
	int[] insertionSort(int[] array) {
		for (int i = 1; i < array.length; i++) {
			int key = array[i];
			int j = i - 1;

			while (j >= 0 && array[j] > key) {
				array[j + 1] = array[j];
				j--;
			}

			array[j + 1] = key;
		}

		return array;
	}

	int[] mergeSort(int[] array) {
		if (array.length <= 1) {
			return array;
		}

		int mid = array.length / 2;
		int[] left = Arrays.copyOfRange(array, 0, mid);
		int[] right = Arrays.copyOfRange(array, mid, array.length);

		return merge(mergeSort(left), mergeSort(right));
	}

	private int[] merge(int[] left, int[] right) {
		int[] temp = new int[left.length + right.length];
		int l = 0, r = 0, t = 0;

		while (l < left.length && r < right.length) {
			if (left[l] <= right[r]) temp[t++] = left[l++];
			else temp[t++] = right[r++];

		}

		System.arraycopy(left, l, temp, t, left.length - l);
		System.arraycopy(right, r, temp, t, right.length - r);

		return temp;
	}

	int[] countingSort(int[] array) {
		int max = Utilities.findMax(array);

		int[] count = new int[max + 1];
		int[] output = new int[array.length];

		for (int i : array) {
			count[i]++;
		}

		for (int i = 1; i <= max; i++) {
			count[i] += count[i - 1];
		}

		for (int i = array.length - 1; i >= 0; i--) {
			output[count[array[i]] - 1] = array[i];
			count[array[i]]--;
		}

		return output;
	}

	static void getSortTime(long[] insert, long[] merge, long[] count, FunctionTimer timer, int n, int[] data) {
		SortingAlgorithms sort = new SortingAlgorithms();
		long avgInsert = 0, avgMerge = 0, avgCount = 0;

		for (int i = 0; i < 10; i++) {
			int[] temp = Arrays.copyOf(data, data.length);
			avgInsert += timer.getTime(() -> sort.insertionSort(temp), true);
			avgMerge += timer.getTime(() -> sort.mergeSort(data), true);
			avgCount += timer.getTime(() -> sort.countingSort(data), true);
		}

		insert[n] = avgInsert / 10;
		merge[n] = avgMerge / 10;
		count[n] = avgCount / 10;
	}
}