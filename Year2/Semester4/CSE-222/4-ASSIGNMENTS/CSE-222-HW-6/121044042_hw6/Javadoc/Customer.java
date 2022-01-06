import java.util.*;

/**
 * Customer class operates customer operations. This class extends Account
 * class.
 *
 * @author seyda
 */
public class Customer extends Account {

	/**
	 * This method lists products by searching, filtering and sorting.
	 *
	 * @param products LinkedList
	 */
	public void listProducts(LinkedList<String> products) {
		String searchText;
		String filterTrader;
		String filterCategory;
		String filterMinPrice;
		String filterMaxPrice;
		String sortOrder;

		Scanner sc = new Scanner(System.in);

		System.out.println("List Products (Search, Filter, Sort)");
		System.out.println("(Note : You can enter \"none\" to choose default settings.)");

		System.out.println("Search : You can search products by entering text");
		searchText = sc.nextLine();
		if (!searchText.equals("none")) {
			System.out.println("Products searching by search text \"" + searchText + "\"");
		}

		System.out.println("Filter : You can filter products by entering trader name");
		filterTrader = sc.nextLine();
		if (!filterTrader.equals("none")) {
			System.out.println("Products filtering by trader name \"" + filterTrader + "\"");
		}

		System.out.println("Filter : You can filter products by entering category");
		filterCategory = sc.nextLine();
		if (!filterCategory.equals("none")) {
			System.out.println("Products filtering by category \"" + filterCategory + "\"");
		}

		System.out.println("Filter : You can filter products by entering min price");
		filterMinPrice = sc.nextLine();
		if (!filterMinPrice.equals("none")) {
			System.out.println("Products filtering by min price \"" + filterMinPrice + "\"");
		}

		System.out.println("Filter : You can filter products by entering max price");
		filterMaxPrice = sc.nextLine();
		if (!filterMaxPrice.equals("none")) {
			System.out.println("Products filtering by max price \"" + filterMaxPrice + "\"");
		}

		System.out.println("Sort : You can sort products by entering order");
		sortOrder = sc.nextLine();
		if (!filterMaxPrice.equals("none")) {
			System.out.println("Products sorting by order \"" + sortOrder + "\"");
		}

		System.out.println("Displayed All Products As You Entered");
	}
	
	/**
	 * Creates order
	 * 
	 * @param orders ArrayDeque
	 */
	public void createOrder (ArrayDeque<String> orders) {
		System.out.println("Created order ...");
	}
}
