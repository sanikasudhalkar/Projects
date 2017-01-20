
public class QuickSort implements SortingAlgos {

	int[] input = null;

	public int[] sort(int[] ip) {
		input = ip;
		quickSort(input, 0, input.length - 1);
		return input;

	}

	public void quickSort(int[] input, int low, int high) {
		if (low < high) {
			int pivot = partition(input, low, high);
			quickSort(input, low, pivot - 1);
			quickSort(input, pivot + 1, high);
		}
	}

	public int partition(int[] input, int low, int high) {
		int pivot = input[high];
		int i = low;
		for (int j = low; j < high; j++) {
			if (input[j] <= pivot) {
				input = SortAndMedianMain.swap(input, i, j);
				i++;
			}
		}
		input = SortAndMedianMain.swap(input, i, high);
		return i;

	}

}
