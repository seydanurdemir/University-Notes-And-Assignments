import java.util.ArrayList;

/**
 * Public Class Test Contains Test Methods
 *
 * @author Seyda Nur DEMIR
 * @version 2.0
 * @since 2021-05-13
 */
public class Test {

	/**
	 * Calculates time difference from start time to end time to put values to
	 * map
	 *
	 * @param n number of entries
	 * @param map KWHashMap
	 * @return time difference of start time and end time
	 */
	public static long putTime(int n, KWHashMap map) {
		long startTime = System.nanoTime();
		for (int i = 0; i < n; i++) {
			map.put(i, i);
		}
		long endTime = System.nanoTime();
		return endTime - startTime;
	}

	/**
	 * Calculates time difference from start time to end time to get values from
	 * map
	 *
	 * @param n number of entries
	 * @param map KWHashMap
	 * @return time difference of start time and end time
	 */
	public static long getTime(int n, KWHashMap map) {
		long startTime = System.nanoTime();
		for (int i = 0; i < n; i++) {
			map.get(i);
		}
		long endTime = System.nanoTime();
		return endTime - startTime;
	}

	/**
	 * Calculates time difference from start time to end time to remove values
	 * from map
	 *
	 * @param n number of entries
	 * @param map KWHashMap
	 * @return time difference of start time and end time
	 */
	public static long removeTime(int n, KWHashMap map) {
		long startTime = System.nanoTime();
		for (int i = 0; i < n; i++) {
			map.remove(i);
		}
		long endTime = System.nanoTime();
		return endTime - startTime;
	}

	/**
	 * Prints the best one of different implementations. Takes three time
	 * difference values from three different implementations. The one that has
	 * smallest difference value has the best performance.
	 *
	 * @param diff1 difference of map 1
	 * @param diff2 difference of map 2
	 * @param diff3 difference of map 3
	 */
	public static void printBestOne(long diff1, long diff2, long diff3) {
		if ((diff1 <= diff2) && (diff1 <= diff3)) {
			System.out.printf("%13s\n", "LinkedList");
		} else if ((diff2 <= diff1) && (diff2 <= diff3)) {
			System.out.printf("%13s\n", "TreeSet");
		} else {
			System.out.printf("%13s\n", "EntryTable");
		}
	}

	/**
	 * Prints time differences of different implementations as n values. Puts,
	 * gets and removes values n times, and prints calculated time differences.
	 *
	 * @param n number of values
	 */
	public static void printEachOne(int n) {
		HashingUsingLinkedList<Integer, Integer> map1 = new HashingUsingLinkedList<>();
		HashingUsingTreeSet<Integer, Integer> map2 = new HashingUsingTreeSet<>();
		HashingUsingEntryTable<Integer, Integer> map3 = new HashingUsingEntryTable<>();

		long diff1, diff2, diff3;

		diff1 = (putTime(n, map1) / 1000);
		diff2 = (putTime(n, map2) / 1000);
		diff3 = (putTime(n, map3) / 1000);
		System.out.printf("%6s %9d %10d ms %10d ms %10d ms ", "Put", n, diff1, diff2, diff3);
		printBestOne(diff1, diff2, diff3);

		diff1 = (getTime(n, map1) / 1000);
		diff2 = (getTime(n, map2) / 1000);
		diff3 = (getTime(n, map3) / 1000);
		System.out.printf("%6s %9d %10d ms %10d ms %10d ms ", "Get", n, diff1, diff2, diff3);
		printBestOne(diff1, diff2, diff3);

		diff1 = (removeTime(n, map1) / 1000);
		diff2 = (removeTime(n, map2) / 1000);
		diff3 = (removeTime(n, map3) / 1000);
		System.out.printf("%6s %9d %10d ms %10d ms %10d ms ", "Remove", n, diff1, diff2, diff3);
		printBestOne(diff1, diff2, diff3);
	}

	/**
	 * Prints all performance test results of different implementations of hash
	 * map.
	 */
	public static void printAllOnes() {
		System.out.printf("%6s %9s %13s %13s %13s %13s\n", "Test", "Num Of", "Chain", "Chain", "OpenAddress", "Performance");
		System.out.printf("%6s %9s %13s %13s %13s %13s\n", "Method", "Value", "LinkedList", "TreeSet", "EntryTable", "Best One");

		System.out.println("------------------------------------------------------------------------");
		printEachOne(10);
		System.out.println("------------------------------------------------------------------------");
		printEachOne(100);
		System.out.println("------------------------------------------------------------------------");
		printEachOne(1000);
		System.out.println("------------------------------------------------------------------------");
		printEachOne(10000);
		// I took these lines to comment, because of execution time rises
		//System.out.println("------------------------------------------------------------------------");
		//printEachOne(100000);
		// I took these lines to comment, because of execution time rises
		//System.out.println("------------------------------------------------------------------------");
		//printEachOne(1000000);
	}

	/**
	 * Prints time test results of hash map performance.
	 */
	public static void printTime() {
		printAllOnes();
	}

	/**
	 * Tests hashing performance results.
	 */
	public void testHashing() {
		printTime();
	}

	/**
	 * Tests hashing performance results.
	 *
	 * @param array
	 */
	public void testWithGivenInput(ArrayList<Integer> array) {
		HashingUsingEntryTable<Integer, Integer> map3 = new HashingUsingEntryTable<>();
		for (Integer item1 : array) {
			map3.put(item1, item1);
			System.out.printf("Method : Put and Item : %d\n", item1);
			System.out.printf("%10s %10s %10s\n", "Hash Value", "Key", "Next");
			for (Integer item2 : array) {
				System.out.printf("%10d %10d %10s\n", map3.find(item2), map3.get(item2), "null");
			}
			System.out.println("------------------------------------------------------------------------");
		}

		map3.remove(array.get(4));
		System.out.printf("Method : Remove and Item : %d\n", array.get(4));
		System.out.printf("%10s %10s %10s\n", "Hash Value", "Key", "Next");
		for (Integer item3 : array) {
			System.out.printf("%10d %10d %10s\n", map3.find(item3), map3.get(item3), "null");
		}
	}
}
