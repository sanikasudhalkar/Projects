import java.util.ArrayList;

public class Median {

	public void findKthSmallestValue(int[] inputArr, int k) {
		long start_time = System.currentTimeMillis();
		Integer[] newArray = new Integer[inputArr.length];
		int i = 0;
		for (int value : inputArr) {
			newArray[i++] = Integer.valueOf(value);
		}
		int kthElement = select(newArray, inputArr.length/2);
		long end_time = System.currentTimeMillis();
		if (kthElement == -1) {
			System.out.println("Invalid K Value entered!!");
		} else {
			System.out.println("K-th smallest element = " + kthElement);
			System.out.println("Time taken: " + (end_time - start_time));
		}
	}

	int select(Integer[] inputArr, int k) {
		if (inputArr.length == 1) {
			return inputArr[0];
		}
		int totalElements = inputArr.length;
		// divide inputArr into parts of 5 elements each and find median for
		// each part.
		int numOfIter = (totalElements % 5 == 0) ? totalElements / 5 : ((totalElements / 5) + 1);
		Integer[] medians = new Integer[numOfIter];
		int i;
		for (i = 0; i < numOfIter; i++) {
			int startPos = i * 5;
			int length = Math.min(inputArr.length - startPos, 5);
			// Split array into parts
			Integer[] partArr = new Integer[length];
			System.arraycopy(inputArr, startPos, partArr, 0, length);
			medians[i] = findMedian(partArr, length);
		}
		int medianOfMedians = select(medians, numOfIter / 2);

		ArrayList<Integer> leftArr = new ArrayList<Integer>();
		ArrayList<Integer> rightArr = new ArrayList<Integer>();
		for (i = 0; i < inputArr.length; i++) {
			if (inputArr[i] < medianOfMedians) {
				leftArr.add(inputArr[i]);
			} else if (inputArr[i] > medianOfMedians) {
				rightArr.add(inputArr[i]);
			}
		}
		if (leftArr.size() >= k) {
			return select(leftArr.toArray(new Integer[0]), k);
		} else if (leftArr.size() == k - 1) {
			return medianOfMedians;
		} else {
			return select(rightArr.toArray(new Integer[0]), k - leftArr.size() - 1);
		}
	}

	private int findMedian(Integer[] partArr, int length) {
		insertionSort(partArr);
		return partArr[length / 2];
	}

	private void insertionSort(Integer[] input) {
		for (int i = 1; i < input.length; i++) {
			for (int j = i; j > 0; j--) {
				if (input[j] < input[j - 1]) {
					int temp = input[j];
					input[j] = input[j - 1];
					input[j - 1] = temp;
				}
			}
		}
	}
}