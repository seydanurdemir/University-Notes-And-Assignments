import java.util.ArrayList;

/**
 * Public Class Main Contains Main Method
 *
 * @author Seyda Nur DEMIR
 * @version 2.0
 * @since 2021-05-13
 */
public class Main {

	/**
	 * Main method
	 * Main method tests the performance of different implementations of KWHashMap interface.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("------------------------------------------------------------------------");
		System.out.println("HASHING PERFORMANCE TEST STARTED                                        ");
		System.out.println("------------------------------------------------------------------------");

		Test testHashing = new Test();
		testHashing.testHashing();

		System.out.println("------------------------------------------------------------------------");
		System.out.println("HASHING PERFORMANCE TEST ENDED                                          ");
		System.out.println("------------------------------------------------------------------------");
		
		System.out.println("------------------------------------------------------------------------");
		System.out.println("HASHING TEST WITH GIVEN INPUT STARTES                                   ");
		System.out.println("------------------------------------------------------------------------");
		
		Test testHashingInput = new Test();
		ArrayList<Integer> array = new ArrayList<Integer>();
		array.add(3);
		array.add(12);
		array.add(13);
		array.add(25);
		array.add(23);
		array.add(51);
		array.add(42);
		testHashingInput.testWithGivenInput(array);
		
		System.out.println("------------------------------------------------------------------------");
		System.out.println("HASHING TEST WITH GIVEN INPUT ENDED                                     ");
		System.out.println("------------------------------------------------------------------------");
	}
}
