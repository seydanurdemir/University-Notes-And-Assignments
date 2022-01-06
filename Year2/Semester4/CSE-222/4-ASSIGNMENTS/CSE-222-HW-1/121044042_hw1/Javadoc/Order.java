import java.util.ArrayList;
import java.util.Objects;

/**
 * This class is about order operations
 * @author seydanurdemir
 */
public class Order {
    
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
     * Order id -1, user new user, product new product
     */
    public Order () {
        this.orderId = -1;
        this.user = new User();
        this.product = new Product();
    }
    
    /**
     * Two parameter constructor
     * Order id -1, user is first parameter, product is second parameter
     * @param user User
     * @param product Product
     */
    public Order (User user, Product product) {
        this.orderId = -1;
        this.user = user;
        this.product = product;
    }
    
    /**
     * Three parameter constructor
     * Order id -1, user is first parameter, product is second parameter
     * @param orderId int
     * @param user User
     * @param product Product
     */
    public Order (int orderId, User user, Product product) {
        this.orderId = orderId;
        this.user = user;
        this.product = product;
    }
    
    /**
     * Getter for order id
     * @return int order id
     */
    public int getOrderId () { return orderId; }

    /**
     * Setter for order id
     * @param orderId int
     */
    public void setOrderId (int orderId) { this.orderId = orderId; }

    /**
     * Getter for user
     * @return User user
     */
    public User getUser () { return user; }

    /**
     * Setter for user
     * @param user User
     */
    public void setUser (User user) { this.user = user; }

    /**
     * Getter for product
     * @return Product product
     */
    public Product getProduct () { return product; }

    /**
     * Setter for product
     * @param product Product
     */
    public void setProduct (Product product) { this.product = product; }
    
    /**
     * Edit method is for edit any order when branch employee looking orders list
     * It will be edited
     * @param orders ArrayList
     */
    public void editOrder (ArrayList<Order> orders) {
        System.out.print("Order information is changed.");
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) { return true; }
        if (obj == null) { return false; }
        if (obj.getClass() == this.getClass()) {
            Order other = (Order) obj;
            return ((this.orderId == other.orderId) && (this.user.equals(other.user)) && (this.product.equals(other.product)));
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode () {
        int hash = 5;
        hash = 11 * hash + this.orderId;
        hash = 11 * hash + Objects.hashCode(this.user);
        hash = 11 * hash + Objects.hashCode(this.product);
        return hash;
    }
    
    @Override
    public String toString() {
        return  "\nOrder Id :" + orderId + 
                "\nUser     :" + user + 
                "\nProduct  :" + product;
    }
}
