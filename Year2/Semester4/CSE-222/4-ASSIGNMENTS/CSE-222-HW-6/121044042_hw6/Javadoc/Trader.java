import java.util.*;

/**
 * Trader class operates trader operations. This class extends Customer class.
 *
 * @author seyda
 */
public class Trader extends Customer {
	/**
	 * Adds new product
	 * 
	 * @param products LinkedList
	 */
	public void addProduct (LinkedList<String> products) {
		System.out.println("Added product ...");
	}
	
	/**
	 * Edit existing product
	 * 
	 * @param products LinkedList
	 * @param productId String
	 */
	public void editProduct (LinkedList<String> products, String productId) {
		System.out.println("Edited product ...");
	}
	
	/**
	 * Removes existing product
	 * 
	 * @param products LinkedList
	 * @param productId String
	 */
	public void removeProduct (LinkedList<String> products, String productId) {
		System.out.println("Removed product ...");
	}
	
	/**
	 * Lists orders
	 * 
	 * @param orders ArrayDeque
	 */
	public void listOrder (ArrayDeque<String> orders) {
		System.out.println("Listed orders ...");
	}
	
	/**
	 * Meets existing order
	 * 
	 * @param orders ArrayDeque
	 * @param orderId String
	 */
	public void meetOrder (ArrayDeque<String> orders, String orderId) {
		System.out.println("Met order ...");
	}
	
	/**
	 * Cancels existing order
	 * 
	 * @param orders ArrayDeque
	 * @param orderId String
	 */
	public void cancelOrder (ArrayDeque<String> orders, String orderId) {
		System.out.println("Canceled order ...");
	}
}
