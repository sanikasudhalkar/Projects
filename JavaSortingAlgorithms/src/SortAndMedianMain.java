import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class SortAndMedianMain {

	public static int VAL_RANGE = 0;

	public static void main(String args[]) {
		Scanner s = new Scanner(System.in);
		System.out.println("Please enter the number of elements:\n");
		System.out.println(" 1 - Selection Sort\n 2 - Quick Sort\n 3 - Count Sort\n 4 - Median Finding");
		int numOfElements = s.nextInt();
		int sortingAlgo = s.nextInt();

		int[] inputArr = new int[numOfElements];

		SortingAlgos sortAlgo;
		switch (sortingAlgo) {
		case 1:
			generateRandomForSort(numOfElements, inputArr);
			sortAlgo = new SelectionSort();
			sortAndPrint(inputArr, sortAlgo);
			break;
		case 2:
			generateRandomForSort(numOfElements, inputArr);
			sortAlgo = new QuickSort();
			sortAndPrint(inputArr, sortAlgo);
			break;
		case 3:
			generateRandomForSort(numOfElements, inputArr);
			sortAlgo = new CountSort();
			sortAndPrint(inputArr, sortAlgo);
			break;
		case 4:
			for (int i = 0; i < 10; i++) {
				generateRandomForMedian(numOfElements, inputArr);
				new Median().findKthSmallestValue(inputArr, 0);
			}
			break;
		default:
			System.out.println("Invalid option selected. Please start again!");
		}

	}

	// Using an array list and performing shuffle function. This is done to
	// avoid duplicate elements,for median generation
	private static void generateRandomForMedian(int numOfElements, int[] inputArr) {
		ArrayList<Integer> inputArrList = new ArrayList<Integer>(numOfElements);
		for (int i = 0; i < numOfElements; i++) {
			inputArrList.add(new Integer(i));
		}
		Collections.shuffle(inputArrList);
		int in = 0;
		for (Iterator<Integer> it = inputArrList.iterator(); it.hasNext(); inputArr[in++] = it.next())
			;
	}

	private static void generateRandomForSort(int numOfElements, int[] inputArr) {
		VAL_RANGE = numOfElements * 3;
		Random randomNumGen = new Random();
		for (int i = 0; i < numOfElements; i++) {
			inputArr[i] = randomNumGen.nextInt(VAL_RANGE);
		}
	}

	private static void sortAndPrint(int[] inputArr, SortingAlgos sortAlgo) {
		long startTime = System.currentTimeMillis();
		sortAlgo.sort(inputArr);
		long endTime = System.currentTimeMillis();
		long timeTaken = endTime - startTime;
		System.out.println("Time Taken: " + timeTaken);
	}

	public static int[] swap(int[] input, int a, int b) {

		int temp = input[a];
		input[a] = input[b];
		input[b] = temp;
		return input;
	}

}
