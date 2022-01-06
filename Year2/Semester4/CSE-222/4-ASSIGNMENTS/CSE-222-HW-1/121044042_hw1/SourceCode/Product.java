import java.util.ArrayList;
import java.util.Objects;

/**
 * This class is about product operations
 * @author seydanurdemir
 */
public class Product {

    /**
     * Product Id is unique key for each product
     */
    public int productId;

    /**
     * Product amount keeps each products amount
     */
    public int amount;

    /**
     * Product category or product type, like chair, table etc
     * Product category must be unique
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
     * No parameter constructor
     * Order id -1, amount 0, category, model and color are unknown as default
     */
    public Product () {
        productId = -1;
        amount = 0;
        category = "unknown";
        model = "unknown";
        color = "unknown";
    }
    
    /**
     * Three parameter constructor
     * Product id is first, category is second, model is third parameter, and amount 0, color no color as default
     * @param productId int
     * @param category String
     * @param model String
     */
    public Product (int productId, String category, String model) {
        this.productId = productId;
        amount = 0;
        this.category = category;
        this.model = model;
        this.color = "no color";
    }
    
    /**
     * Four parameter constructor
     * Product id is first, category is second, model is third, color is fourth parameter
     * @param productId int
     * @param category String
     * @param model String
     * @param color String
     */
    public Product (int productId, String category, String model, String color) {
        this.productId = productId;
        amount = 0;
        this.category = category;
        this.model = model;
        this.color = color;
    }
    
    /**
     * Five parameter constructor
     * Product id is first, amount is second, category is third, model is fourth, color is fifth parameter
     * @param productId int
     * @param amount int
     * @param category String
     * @param model String
     * @param color String
     */
    public Product (int productId, int amount, String category, String model, String color) {
        this.productId = productId;
        this.amount = amount;
        this.category = category;
        this.model = model;
        this.color = color;
    }
    
    /**
     * Getter for product id
     * @return int product id
     */
    public int getProductId () { return productId; }

    /**
     * Setter for product id
     * @param productId int
     */
    public void setProductId (int productId) { this.productId = productId; }

    /**
     * Getter for amount
     * @return int amount
     */
    public int getAmount () { return amount; }

    /**
     * Setter for amount
     * @param amount int
     */
    public void setAmount (int amount) { this.amount = amount; }

    /**
     * Getter for category
     * @return String category
     */
    public String getCategory () { return category; }

    /**
     * Setter for category
     * @param category String
     */
    public void setCategory (String category) { this.category = category; }

    /**
     * Getter for model
     * @return String model
     */
    public String getModel () { return model; }

    /**
     * Setter for model
     * @param model String
     */
    public void setModel (String model) { this.model = model; }

    /**
     * Getter for color
     * @return String color
     */
    public String getColor () { return color; }

    /**
     * Setter for color
     * @param color String
     */
    public void setColor (String color) { this.color = color; }
    
    /**
     * Checks a product is out of stock or not
     * If the product amount is equal or less than requested amount, we called it out of stock
     * Requested amount value is 3 as default for this system
     * @param requestedAmount int (default 3)
     * @return boolean out of stock or not
     */
    public boolean isOutStock (int requestedAmount) {
        if (amount <= requestedAmount)
            return true;
        else
            return false;
    }
    
    /**
     * Lists products but only out of stock
     * @param products ArrayList
     * @param requestedAmount int
     */
    public void listProductsOutStock (ArrayList<Product> products, int requestedAmount) {
        for (int i=0; i < products.size(); i++) {
            if (products.get(i).isOutStock(requestedAmount)) {
                System.out.println(products.get(i).toString()+"\n-");
            }
        }
    }
    
    /**
     * Lists all products
     * @param products ArrayList
     */
    public void listProducts (ArrayList<Product> products) {
        for (int i=0; i < products.size(); i++) {
            System.out.println(products.get(i).toString()+"\n-");
        }
    }
    
    /**
     * Adds product by taking product category, model and color
     * If the entered category name is same with anyu other product, product can not be added
     * @param products ArrayList
     * @param product Product
     */
    public void addProduct (ArrayList<Product> products, Product product) {
        ArrayList<Integer> indexes = findByProperty(products, 2, product.getCategory());
        if (indexes.isEmpty()) {
            products.add(product);
            product.setProductId(products.size()-1);
            System.out.println("Product added with product id " + product.getProductId() + " .");
        } else {
            System.out.println("A product has already exists with this category name, please try again with another category name.");
        }
    }
    
    /**
     * Removes product by taking product id
     * If the entered product id is wrong, product can not be removed
     * @param products ArrayList
     * @param productId int
     */
    public void removeProduct (ArrayList<Product> products, int productId) {
        ArrayList<Integer> indexes = findByProperty(products, 0, productId);
        if (indexes.isEmpty()) {
            System.out.println("Wrong product id, please try again.");
        } else {
            products.remove(products.get(indexes.get(0)));
            System.out.println("Product " + productId + " removed.");
        }
    }
    
    /**
     * Increases existing product amount
     * If the entered product id is wrong, product amount can not be increased
     * If user want to increase amount of not existing product, it must add before this product
     * @param products ArrayList
     * @param product Product
     */
    public void increaseProductAmount (ArrayList<Product> products, Product product) {
        int indexOf = find(products, product);
        if (indexOf == -1) {
            System.out.println("The product does not exist, please add this product before increase amount.");
        } else {
            products.get(indexOf).setAmount(products.get(indexOf).getAmount()+1);
            System.out.println("Product amount increased to " + products.get(indexOf).getAmount() + " .");
        }
    }
    
    /**
     * Decreases existing product amount
     * If the entered product id is wrong, product amount can not be decreased
     * If user want to decrease amount of not existing product, it must add before this product
     * @param products ArrayList
     * @param product Product
     */
    public void decreaseProductAmount (ArrayList<Product> products, Product product) {
        int indexOf = find(products, product);
        if (indexOf == -1) {
            System.out.println("The product does not exist, please add this product before increase amount.");
        } else {
            if (products.get(indexOf).getAmount() > 0) {
                products.get(indexOf).setAmount(products.get(indexOf).getAmount()-1);
                System.out.println("Product amount decreased to " + products.get(indexOf).getAmount() + " .");
            } else {
                System.out.println("The product is already out of stock, please inform the manager.");
            }
        }
    }
    
    /**
     * Finds any existing products index in the given array
     * If the product does not exist, gives -1
     * @param products ArrayList
     * @param product Product
     * @return int index
     */
    public int find (ArrayList<Product> products, Product product) {
        int indexOf = -1;
        for (int i=0; i < products.size(); i++) {
            if (products.get(i).equals(product)) {
                indexOf = i;
            }
        }
        return indexOf;
    }
    
    /**
     * Finds by integer property any product
     * @param products ArrayList
     * @param propertyNo int
     * @param property int
     * @return ArrayList indexes
     */
    public ArrayList<Integer> findByProperty (ArrayList<Product> products, int propertyNo, int property) {
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        for (int i = 0 ; i < products.size() ; i++) {
            switch (propertyNo) {
                case 0 : { if (products.get(i).getProductId() == property) indexes.add(i); break; }
                case 1 : { if (products.get(i).getAmount() == property) indexes.add(i); break; }
            }
        }
        return indexes;
    }
    
    /**
     * Finds by string property any product
     * @param products ArrayList
     * @param propertyNo int
     * @param property String
     * @return ArrayList indexes
     */
    public ArrayList<Integer> findByProperty (ArrayList<Product> products, int propertyNo, String property) {
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        for (int i = 0 ; i < products.size() ; i++) {
            switch (propertyNo) {
                case 2 : { if (products.get(i).getCategory().equals(property)) indexes.add(i); break; }
                case 3 : { if (products.get(i).getModel().equals(property)) indexes.add(i); break; }
                case 4 : { if (products.get(i).getColor().equals(property)) indexes.add(i); break; }
            }
        }
        return indexes;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) { return true; }
        if (obj == null) { return false; }
        if (obj.getClass() == this.getClass()) {
            Product other = (Product) obj;
            return ((this.category.equals(other.category)) && (this.model.equals(other.model)) && (this.color.equals(other.color)));
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode () {
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
        return  "\nProduct Id  : " + productId + 
                "\nAmount      : " + amount + 
                "\nCategory    : " + category + 
                "\nModel       : " + model + 
                "\nColor       : " + color;
    }
}
