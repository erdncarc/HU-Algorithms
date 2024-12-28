import java.util.Arrays;

public class Utilities {
	static void reverseArray(int[] array) {
		int left = 0;
		int right = array.length - 1;

		while (left < right) {
			int temp = array[left];
			array[left] = array[right];
			array[right] = temp;

			left++;
			right--;
		}
	}

	static void print(String name, long[] arr1, long[] arr2, long[] arr3, boolean isMillis) {
		System.out.println(name);
		System.out.println((isMillis ? "Insertion Sort -> " : "Linear Search (Random Input Data) -> ") + String.format(Arrays.toString(arr1)));
		System.out.println((isMillis ? "Merge Sort -> " : "Linear Search (Sorted Input Data) -> ") + String.format(Arrays.toString(arr2)));
		System.out.println((isMillis ? "Counting Sort -> " : "Binary Search (Sorted Input Data) -> ") + String.format(Arrays.toString(arr3)));
	}

	static int findMax(int[] array) {
		int max = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] > max) max = array[i];
		}

		return max;
	}
}
