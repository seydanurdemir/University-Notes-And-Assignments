import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class is about customer operations
 * Customer extends user, so it has all authorities of general user
 * @author seydanurdemir
 */
public class Customer extends User {

    /**
     * Customer can list all products
     * @param products ArrayList
     */
    public void listAllProducts (ArrayList<Product> products) {
        System.out.print("List of Products");
        products.get(0).listProducts(products);
    }

    /**
     * Customer can search any product by filtering category, model or color
     * If the category, model or color is wrong, customer can not search product
     * @param products ArrayList
     */
    public void searchProduct (ArrayList<Product> products) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        String property = "unknown";
        System.out.println("Which Property Do You Want to Search for Products?");
        System.out.println("[1] By Category");
        System.out.println("[2] By Model");
        System.out.println("[3] By Color");
        property = sc.nextLine();
        if (property == "1") {
            System.out.println("Which Category Do You Want to Search for Products?");
            property = sc.nextLine();
            indexes = products.get(0).findByProperty(products, 2, property);
            for (int i=0; i < indexes.size(); i++) {
                System.out.println(products.get(indexes.get(i)) + "\n-");
            }
        } else if (property == "2") {
            System.out.println("Which Model Do You Want to Search for Products?");
            property = sc.nextLine();
            indexes = products.get(0).findByProperty(products, 3, property);
            for (int i=0; i < indexes.size(); i++) {
                System.out.println(products.get(indexes.get(i)) + "\n-");
            }
        } else if (property == "3") {
            System.out.println("Which Color Do You Want to Search for Products?");
            property = sc.nextLine();
            indexes = products.get(0).findByProperty(products, 4, property);
            for (int i=0; i < indexes.size(); i++) {
                System.out.println(products.get(indexes.get(i)) + "\n-");
            }
        } else
            System.out.println("Wrong input, please try again.");
    }

    /**
     * Customer can shop online by entering its own customer id and product id to buy
     * If the entered customer id or product id is wrong, customer can not shop
     * When the shopping is done, system creates an order automatically and assigns it order id
     * @param accounts ArrayList
     * @param products ArrayList
     * @param orders ArrayList
     */
    public void shopOnline (ArrayList<User> accounts, ArrayList<Product> products, ArrayList<Order> orders) {
        User user = new User();
        Scanner sc = new Scanner(System.in);
        int userId = -1; int productId = -1;
        System.out.println("What is Your Customer Id?");
        try {
            userId = sc.nextInt();
            ArrayList<Integer> indexes1 = accounts.get(0).findByProperty(accounts, 0, userId);
            if (indexes1.isEmpty()) {
                System.out.println("There is no any customer with this user id, please add customer.");
            } else {
                //accounts.get(indexes1.get(0));
                System.out.println("Which Product Do You Want to Shop?");
                try {
                    productId = sc.nextInt();
                    ArrayList<Integer> indexes2 = products.get(0).findByProperty(products, 0, productId);
                    if (indexes2.isEmpty()) {
                        System.out.println("There is no any product with product user id, please try again.");
                    } else {
                        // products.get(indexes2.get(0))
                        Order newOrder = new Order(accounts.get(indexes1.get(0)),products.get(indexes2.get(0)));
                        newOrder.setOrderId(orders.size()-1);
                        products.get(indexes2.get(0)).decreaseProductAmount(products, products.get(indexes2.get(0)));
                        System.err.println("You bought this product.");
                    }
                } catch(InputMismatchException e) {
                    System.err.println("Wrong input, product id selected as -1 automatically.");
                    productId = -1;
                }
            }
        } catch(InputMismatchException e) {
            System.err.println("Wrong input, user id selected as -1 automatically.");
            userId = -1;
        }
    }

    /**
     * Customer can view its own orders by entering its user id
     * If the entered user id is wrong, user can not view its orders
     * @param orders ArrayList
     */
    public void viewMyOrders (ArrayList<Order> orders) {
        Scanner sc = new Scanner(System.in);
        int userId = -1;
        System.out.println("What is Your Customer Id?");
        try {
            userId = sc.nextInt();
            for (int i=0; i < orders.size(); i++) {
                if (orders.get(i).getUser().getUserId() == userId) {
                    System.err.println(orders.get(i)+"\n-");
                }
            }
        } catch(InputMismatchException e) {
            System.err.println("Wrong input, user id selected as -1 automatically.");
            userId = -1;
        }
    }
}
