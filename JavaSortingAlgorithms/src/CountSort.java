public class CountSort implements SortingAlgos {
	public int[] sort(int[] input) {

		// Create an array that contains the number of elements same as the
		// maximum value the input array can hold. Using + 1 because the value
		// range is 0-VAL_RANGE, both inclusive.
		int[] countArr = new int[SortAndMedianMain.VAL_RANGE + 1];

		int i = 0;
		// For each element in the input array, increment the value of countArr
		// at that index.
		for (i = 0; i < input.length; i++) {
			countArr[input[i]]++;
		}

		for (i = 1; i <= SortAndMedianMain.VAL_RANGE; i++) {
			countArr[i] += countArr[i - 1];
		}
		int[] output = new int[input.length];
		for (i = input.length - 1; i >= 0; i--) {
			output[countArr[input[i]]-1] = input[i];
			countArr[input[i]]--;

		}
		return output;
	}

}
