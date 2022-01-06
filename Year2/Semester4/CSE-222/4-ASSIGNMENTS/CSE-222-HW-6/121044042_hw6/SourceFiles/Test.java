import java.util.*;

/**
 * Test class tests the system.
 *
 * @author seyda
 */
public class Test {

	/**
	 * User role
	 */
	String role;

	/**
	 * Username
	 */
	String username;

	/**
	 * Password
	 */
	String password;

	/**
	 * Open sessions
	 */
	public ArrayDeque<String> sessions = new ArrayDeque<>();

	/**
	 * Traders of the company
	 */
	public HashMap<String, String> traders = new HashMap<>();

	/**
	 * Customers of the company
	 */
	public HashMap<String, String> customers = new HashMap<>();

	/**
	 * No parameter constructor Initializes the program before test
	 */
	public Test() {
		initTest();
	}

	/**
	 * Initializes the program
	 */
	public void initTest() {
		traders.put("Trader1", "123456");
		customers.put("Customer1", "123456");
	}

	/**
	 * Test1 : Tests Sign Up / Sign In / Sign Out
	 */
	public void test1() {
		String role = "Customer";
		String username = "Customer1";
		String password = "123456";
		Customer customerObject = new Customer();
		/* here */
	}

	/**
	 * Test2 : List Products / Search
	 */
	public void test2() {
	}

	/**
	 * Test3 : List Products / Filter
	 */
	public void test3() {
	}

	/**
	 * Test4 : List Products / Sort
	 */
	public void test4() {
	}
}
