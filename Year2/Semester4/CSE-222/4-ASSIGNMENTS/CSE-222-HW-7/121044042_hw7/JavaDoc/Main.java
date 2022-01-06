import java.util.ArrayList;

/**
 * Public Class Main Contains Main Method
 *
 * @author Seyda Nur DEMIR
 * @version 1.0
 * @since 2021-06-12
 */
public class Main {

    /**
     * Main method Main method tests the performance of different balanced tree
     * implementations of the book.
     *
     * @param args
     */
    public static void main(String[] args) {
		/* Test Part 1 */
		Test1 test1 = new Test1();
        test1.test1();
		
		/* Test Part 2 */
		Test2 test2 = new Test2();
        test2.test2();
		
		/* Test Part 3 */
		Test3 test3 = new Test3();
        test3.test3();
    }
}
