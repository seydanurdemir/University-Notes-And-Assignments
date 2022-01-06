import KW.*;

import java.util.*;

/**
 * Company class initializes the system, and keeps datas.
 * 
 * @author seyda
 */
public class Company {
	/**
	 * Initial file name
	 */
	public static final String filename = "e-commerce-samples.csv";
	
	/**
	 * Traders of the company
	 */
	private HashMap<String, String> traders = new HashMap<>();
	
	/**
	 * Customers of the company
	 */
	private HashMap<String, String> customers = new HashMap<>();
	
	/**
	 * Products
	 */
	private LinkedList<String> products = new LinkedList<>();
	
	/**
	 * Products categories
	 */
	private BinarySearchTree<String> category = new BinarySearchTree<>();
	
	/**
	 * Customers orders
	 */
	private ArrayDeque<String> orders = new ArrayDeque<>();
	
	/**
	 * No parameter constructor
	 * Initializes the system
	 */
	public Company() {
		System.out.println("System is initializing files ...");
		Initialize initCompany = new Initialize(filename);
		System.out.println("System initialized files ...");
	}
	
	/**
	 * Retrieves traders
	 * 
	 * @return HashMap
	 */
	public HashMap<String, String> getTraders() {
		return traders;
	}
	
	/**
	 * Retrieves customers
	 * 
	 * @return HashMap
	 */
	public HashMap<String, String> getCustomers() {
		return customers;
	}
	
	/**
	 * Retrieves products
	 * 
	 * @return LinkedList
	 */
	public LinkedList<String> getProducts() {
		return products;
	}
	
	/**
	 * Retrieves categories
	 * 
	 * @return BinarySearchTree
	 */
	public BinarySearchTree<String> getCategory() {
		return category;
	}
	
	/**
	 * Retrieves orders
	 * 
	 * @return ArrayDeque
	 */
	public ArrayDeque<String> getOrders() {
		return orders;
	}

	
}
