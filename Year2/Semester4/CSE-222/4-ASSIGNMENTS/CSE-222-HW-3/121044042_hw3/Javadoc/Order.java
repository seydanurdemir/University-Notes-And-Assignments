import java.util.*;

/**
 * Order Class
 *
 * @author Seyda Nur DEMIR
 */
public class Order {

	/**
	 * Order Id Counter
	 */
	private static int orderIdCounter = 0;

	/**
	 * Order Id is unique key for each sale
	 */
	private int orderId;

	/**
	 * User is who bought any product
	 */
	private User user;

	/**
	 * Product is what bought by any user
	 */
	private Product product;

	/**
	 * No parameter constructor
	 */
	public Order() {
		this.orderId = orderIdCounter++;
		this.user = new Customer();
		this.product = new Product();
	}

	/**
	 * All parameter constructor
	 *
	 * @param user User
	 * @param product Product
	 */
	public Order(User user, Product product) {
		this.orderId = orderIdCounter++;
		this.user = user;
		this.product = product;
	}

	/**
	 * Copy constructor
	 *
	 * @param other Order
	 */
	public Order(Order other) {
		this.orderId = other.orderId;
		this.user = other.user;
		this.product = other.product;
	}

	/**
	 * Getter for order id
	 *
	 * @return int order id
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * Setter for order id
	 *
	 * @param orderId int
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	/**
	 * Getter for user
	 *
	 * @return User user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Setter for user
	 *
	 * @param user User
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Getter for product
	 *
	 * @return Product product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * Setter for product
	 *
	 * @param product Product
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * Adds order
	 *
	 * @param orders
	 * @param order
	 */
	public void addOrder(KWArrayList<Order> orders, Order order) {
		orders.add(order);
	}

	/**
	 * Removes order
	 *
	 * @param orders
	 * @param order
	 */
	public void removeOrder(KWArrayList<Order> orders, Order order) {
		int index = orders.indexOf(order);
		if (index != -1) {
			orders.remove(index);
		} else {
			throw new NoSuchElementException();
		}
	}

	/**
	 * Prints orders
	 *
	 * @param orders
	 */
	public void printOrders(KWArrayList<Order> orders) {
		orders.print();
	}

	/**
	 * Edit method is for edit any order when branch employee looking orders
	 * list It will be edited
	 */
	public void editOrder() {
		System.out.print("Order information is changed.");
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj.getClass() == this.getClass()) {
			Order other = (Order) obj;
			return ((this.orderId == other.orderId) && (this.user.equals(other.user)) && (this.product.equals(other.product)));
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 11 * hash + this.orderId;
		hash = 11 * hash + Objects.hashCode(this.user);
		hash = 11 * hash + Objects.hashCode(this.product);
		return hash;
	}

	@Override
	public String toString() {
		return "\nOrder Id :" + orderId
			+ "\nUser     :" + user
			+ "\nProduct  :" + product;
	}
}
