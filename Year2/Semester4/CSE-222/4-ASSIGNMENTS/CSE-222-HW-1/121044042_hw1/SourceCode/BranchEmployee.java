import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class is about branch employee operations
 * Branch employee extends customer, so it has all authorities of customer
 * @author seydanurdemir
 */
public class BranchEmployee extends Customer {

    /**
     * Branch employee can lists products out of stock
     * Stock is determined by requested amount parameter
     * For all system, its default value is 3
     * @param products ArrayList
     * @param requestedAmount int
     */
    public void listProductsOutStock (ArrayList<Product> products, int requestedAmount) {
        System.out.print("List of Products  out of Stock (Requested Amount " + requestedAmount + ")");
        products.get(0).listProductsOutStock(products, requestedAmount);
    }

    /**
     * Branch employee can inform manager about the products out of stock
     * @param products ArrayList
     * @param productIdsOutStock ArrayList
     * @param requestedAmount int
     */
    public void informManager (ArrayList<Product> products, ArrayList<Integer> productIdsOutStock, int requestedAmount) {
        productIdsOutStock.clear();
        for (int i=0; i < products.size(); i++) {
            if (products.get(i).isOutStock(requestedAmount)) {
                productIdsOutStock.add(products.get(i).getProductId());
            }
        }
        System.out.println("Administrators are informed about products out of stock.");
    }

    /**
     * Branch employee can list products
     * @param products ArrayList
     */
    public void listProducts (ArrayList<Product> products) {
        System.out.print("List of Products");
        products.get(0).listProducts(products);
    }

    /**
     * Branch employee can add product by entering category, model, color
     * If the entered category name is same with an existing category, branch employee can not add product
     * When product is added, system assigned a product id to the product automatically
     * @param products ArrayList
     */
    public void addProduct (ArrayList<Product> products) {
        Product product = new Product();
        Scanner sc = new Scanner(System.in);
        System.out.println("Category    : "); product.setCategory(sc.nextLine());
        System.out.println("Model       : "); product.setModel(sc.nextLine());
        System.out.println("Color       : "); product.setColor(sc.nextLine());
        product.addProduct(products,product);
    }

    /**
     * Branch employee can remove product by entering product id
     * If the entered product id is wrong, branch employee can not remove the product
     * @param products ArrayList
     */
    public void removeProduct (ArrayList<Product> products) {
        Product product = new Product();
        Scanner sc = new Scanner(System.in);
        int productId;
        System.out.println("Which Product Do You Want to Remove?");
        try {
            productId = sc.nextInt();
            product.removeProduct(products,productId);
        } catch(InputMismatchException e) {
            System.err.println("Wrong input, product id selected as -1 automatically.");
            productId = -1;
        }
    }

    /**
     * Branch employee can increase amount of products by entering category, model, color
     * If the branch employee enters a product does not exist, can not increase its amount
     * @param products ArrayList
     */
    public void increaseProductAmount (ArrayList<Product> products) {
        Product product = new Product();
        Scanner sc = new Scanner(System.in);
        System.out.println("Category    : "); product.setCategory(sc.nextLine());
        System.out.println("Model       : "); product.setModel(sc.nextLine());
        System.out.println("Color       : "); product.setColor(sc.nextLine());
        product.increaseProductAmount(products,product);
    }

    /**
     * Branch employee can decrease amount of products by entering category, model, color
     * If the branch employee enters a product does not exist, can not decrease its amount
     * @param products ArrayList
     */
    public void decreaseProductAmount (ArrayList<Product> products) {
        Product product = new Product();
        Scanner sc = new Scanner(System.in);
        System.out.println("Category    : "); product.setCategory(sc.nextLine());
        System.out.println("Model       : "); product.setModel(sc.nextLine());
        System.out.println("Color       : "); product.setColor(sc.nextLine());
        product.decreaseProductAmount(products,product);
    }

    /**
     * Branch employee can list customers
     * @param accounts ArrayList
     */
    public void listCustomers (ArrayList<User> accounts) {
        System.out.println("List of Customers");
        accounts.get(0).listByRole(accounts,3);
    }

    /**
     * Branch employee can add customer by entering name, surname, email, password, address, phonenumber
     * If the entered email address is wrong, branch employee can not add customer
     * When the customer is added, system assigned a customer id to the customer automatically
     * @param accounts ArrayList
     * @param branches ArrayList branches
     */
    public void addCustomer (ArrayList<User> accounts, ArrayList<Branch> branches) {
        User user = new User();
        Scanner sc = new Scanner(System.in);
        user.setRole(3);
        user.setBranch(branches.get(0));
        System.out.println("Name        : "); user.setName(sc.nextLine());
        System.out.println("Surname     : "); user.setSurname(sc.nextLine());
        System.out.println("E-mail      : "); user.setEmail(sc.nextLine());
        System.out.println("Password    : "); user.setPassword(sc.nextLine());
        System.out.println("Address     : "); user.setAddress(sc.nextLine());
        System.out.println("Phonenumber : "); user.setPhonenumber(sc.nextLine());
        user.addUser(accounts,user);
    }

    /**
     * Branch employee can remove customer by entering customer id
     * If the entered customer id is wrong, branch employee can not remove customer
     * @param accounts ArrayList
     */
    public void removeCustomer (ArrayList<User> accounts) {
        User user = new User();
        Scanner sc = new Scanner(System.in);
        int userId = -1;
        System.out.println("Which Customer Do You Want to Remove?");
        try {
            userId = sc.nextInt();
            user.removeUser(accounts,userId);
        } catch(InputMismatchException e) {
            System.err.println("Wrong input, user id selected as -1 automatically.");
            userId = -1;
        }
    }

    /**
     * Branch employee can make sale in store or online by entering customer id and product id to sell
     * If the entered customer id or product id is wrong, branch employee can not make sale
     * When sale is done, system creates an order automatically and assigns an order id to this order
     * Orders keeps informations of sale, customer and product ids
     * @param accounts ArrayList
     * @param products ArrayList
     * @param orders ArrayList
     */
    public void makeSale (ArrayList<User> accounts, ArrayList<Product> products, ArrayList<Order> orders) {
        User user = new User();
        Scanner sc = new Scanner(System.in);
        int userId = -1; int productId = -1;
        System.out.println("Which Customer Do You Want to Make Sale to?");
        try {
            userId = sc.nextInt();
            ArrayList<Integer> indexes1 = accounts.get(0).findByProperty(accounts, 0, userId);
            if (indexes1.isEmpty()) {
                System.out.println("There is no any customer with this user id, please add customer.");
            } else {
                //accounts.get(indexes1.get(0));
                System.out.println("Which Product Do You Want to Sell?");
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
                        System.err.println("You sold this product.");
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
     * Branch employee can view all previous orders
     * @param orders ArrayList
     */
    public void viewPreviousOrders (ArrayList<Order> orders) {
        Scanner sc = new Scanner(System.in);
        int userId = -1; String answer = "unknown";
        System.out.println("Which Customer Do You Want to View Previous Orders of?");
        try {
            userId = sc.nextInt();
            for (int i=0; i < orders.size(); i++) {
                if (orders.get(i).getUser().getUserId() == userId) {
                    System.err.println(orders.get(i)+"\n-");
                    System.out.println("Do You Want to Edit This Order?");
                    System.out.println("[1] Yes");
                    System.out.println("[2] No");
                    answer = sc.nextLine();
                    if (answer.equals("1")) {
                        orders.get(i).editOrder(orders);
                    }
                }
            }
        } catch(InputMismatchException e) {
            System.err.println("Wrong input, user id selected as -1 automatically.");
            userId = -1;
        }
    }
}
