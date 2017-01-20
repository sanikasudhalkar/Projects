
public class SelectionSort implements SortingAlgos{

	public int[] sort(int[] input) {
		for (int i = 0; i < input.length - 1; i++) {
			int minNum = i;
			for (int j = i+1; j < input.length; j++) {
				if (input[j] < input[minNum])
					minNum = j;
			}

			if (minNum != i) {
				input = SortAndMedianMain.swap(input, minNum, i);
			}
		}
		return input;

	}
}
