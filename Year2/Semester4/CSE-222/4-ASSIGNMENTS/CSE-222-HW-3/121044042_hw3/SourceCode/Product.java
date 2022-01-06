import java.util.*;

/**
 * Product Class
 *
 * @author Seyda Nur DEMIR
 */
public class Product {

	/**
	 * Product Id Counter
	 */
	private static int productIdCounter = 0;

	/**
	 * Product Id is unique key for each product
	 */
	public int productId;

	/**
	 * Product amount keeps each products amount
	 */
	public int amount;

	/**
	 * Product category or product type, like chair, table etc Product category
	 * must be unique
	 */
	public String category;

	/**
	 * Product model, it changes with product category
	 */
	public String model;

	/**
	 * Product color, it changes with product category or model
	 */
	public String color;

	/**
	 * Requested amount of any product
	 */
	public static final int REQUESTED_AMOUNT = 3;

	/**
	 * No parameter constructor
	 */
	public Product() {
		this.productId = productIdCounter++;
		this.amount = 0;
		this.category = "unknown";
		this.model = "unknown";
		this.color = "unknown";
	}

	/**
	 * All parameter constructor
	 *
	 * @param category String
	 * @param model String
	 * @param color
	 */
	public Product(String category, String model, String color) {
		this.productId = productIdCounter++;
		this.amount = 0;
		this.category = category;
		this.model = model;
		this.color = color;
	}

	/**
	 * Copy constructor
	 *
	 * @param other Product
	 */
	public Product(Product other) {
		this.productId = other.productId;
		this.amount = other.amount;
		this.category = other.category;
		this.model = other.model;
		this.color = other.color;
	}

	/**
	 * Getter for product id
	 *
	 * @return int product id
	 */
	public int getProductId() {
		return productId;
	}

	/**
	 * Setter for product id
	 *
	 * @param productId int
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}

	/**
	 * Getter for amount
	 *
	 * @return int amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Setter for amount
	 *
	 * @param amount int
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * Getter for category
	 *
	 * @return String category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Setter for category
	 *
	 * @param category String
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Getter for model
	 *
	 * @return String model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * Setter for model
	 *
	 * @param model String
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * Getter for color
	 *
	 * @return String color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Setter for color
	 *
	 * @param color String
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Adds product
	 *
	 * @param products
	 * @param product
	 */
	public void addProduct(KWArrayList<Product> products, Product product) {
		products.add(product);
	}

	/**
	 * Removes product
	 *
	 * @param products
	 * @param product
	 */
	public void removeProduct(KWArrayList<Product> products, Product product) {
		int index = products.indexOf(product);
		if (index != -1) {
			products.remove(index);
		} else {
			throw new NoSuchElementException();
		}
	}

	/**
	 * Prints products
	 *
	 * @param products
	 */
	public void printProducts(KWArrayList<Product> products) {
		products.print();
	}

	/**
	 * Increases existing product amount
	 */
	public void increaseAmount() {
		amount++;
	}

	/**
	 * Decreases existing product amount
	 */
	public void decreaseAmount() {
		amount--;
	}

	/**
	 * Checks a product is out of stock or not If the product amount is equal or
	 * less than requested amount, we called it out of stock Requested amount
	 * value is 3 as default for this system
	 *
	 * @return boolean out of stock or not
	 */
	public boolean isOutStock() {
		return (amount <= REQUESTED_AMOUNT);
	}

	/**
	 * Lists products but only out of stock
	 *
	 * @param products KWArrayList
	 */
	public void printOutStock(KWArrayList<Product> products) {
		for (int i = 0; i < products.size(); i++) {
			if (products.get(i).isOutStock()) {
				System.out.println(products.get(i).toString());
			}
		}
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
			Product other = (Product) obj;
			return ((this.category.equals(other.category)) && (this.model.equals(other.model)) && (this.color.equals(other.color)));
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 11 * hash + this.productId;
		hash = 11 * hash + this.amount;
		hash = 11 * hash + Objects.hashCode(this.category);
		hash = 11 * hash + Objects.hashCode(this.model);
		hash = 11 * hash + Objects.hashCode(this.color);
		return hash;
	}

	@Override
	public String toString() {
		return "\nProduct Id  : " + productId
			+ "\nAmount      : " + amount
			+ "\nCategory    : " + category
			+ "\nModel       : " + model
			+ "\nColor       : " + color;
	}
}
