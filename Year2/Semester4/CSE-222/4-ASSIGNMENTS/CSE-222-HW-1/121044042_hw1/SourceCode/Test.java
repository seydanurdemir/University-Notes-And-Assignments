import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Test class is about testing operations
 * @author seydanurdemir
 */
public class Test {
    
    private Scanner sc;
    
    Account account = new Account();
    Administrator administratorObject = new Administrator();
    BranchEmployee branchEmployeeObject = new BranchEmployee();
    Customer customerObject = new Customer();
    
    /**
     * Keeps active user role
     */
    private int activeUserRole;
    
    /**
     * Keeps a Branch array which includes all branches
     */
    private ArrayList<Branch> branches = new ArrayList<Branch>(Arrays.asList(
            new Branch(0,"Online","WEB"), new Branch(1,"Baglarbasi","Darica/KOCAELI"), new Branch(2,"Nenehatun","Darica/KOCAELI"), new Branch(3,"Emek","Darica/KOCAELI")));
    
    /**
     * Keeps a Product array which includes all products
     */
    private ArrayList<Product> products = new ArrayList<Product>(Arrays.asList(
            new Product(0, 12, "Office Chair", "A Model", "Black"),  new Product(1, "Office Chair", "B Model", "Gray"),  new Product(2, "Office Chair", "C Model", "White"),  new Product(3, "Office Chair", "D Model", "Red"),  new Product(4, "Office Chair", "E Model", "Green"),  new Product(5, "Office Chair", "F Model", "Black"),  new Product(6, "Office Chair", "G Model", "Gray"), 
            new Product(7, "Office Desk", "A Model", "Black"), new Product(8, "Office Desk", "B Model", "Gray"), new Product(9, "Office Desk", "C Model", "White"), new Product(10, "Office Desk", "D Model", "Red"), new Product(11, "Office Desk", "E Model", "Red"), 
            new Product(12, "Meeting Table", "A Model", "Black"), new Product(13, "Meeting Table", "B Model", "Gray"), new Product(14, "Meeting Table", "C Model", "White"), new Product(15, "Meeting Table", "D Model", "Red"), new Product(16, "Meeting Table", "E Model", "Black"), new Product(17, "Meeting Table", "F Model", "Gray"), new Product(18, "Meeting Table", "G Model", "White"), new Product(19, "Meeting Table", "H Model", "Red"), new Product(20, "Meeting Table", "I Model", "Black"), new Product(21, "Meeting Table", "J Model", "Gray"), 
            new Product(22, "Bookcases", "A Model"), new Product(23, "Bookcases", "B Model"), new Product(24, "Bookcases", "C Model"), new Product(25, "Bookcases", "D Model"), new Product(26, "Bookcases", "E Model"), new Product(27, "Bookcases", "F Model"), new Product(28, "Bookcase", "G Model"), new Product(29, "Bookcase", "H Model"), new Product(30, "Bookcase", "I Model"), new Product(31, "Bookcase", "J Model"), new Product(32, "Bookcase", "K Model"), new Product(33, "Bookcase", "L Model"), 
            new Product(34, "Bookcases", "A Model"), new Product(35, "Bookcases", "B Model"), new Product(36, "Bookcases", "C Model"), new Product(37, "Bookcases", "D Model"), new Product(38, "Bookcases", "E Model"), new Product(39, "Bookcases", "F Model"), new Product(40, "Bookcase", "G Model"), new Product(41, "Bookcase", "H Model"), new Product(42, "Bookcase", "I Model"), new Product(43, "Bookcase", "J Model"), new Product(44, "Bookcase", "K Model"), new Product(45, "Bookcase", "L Model") 
        ));
    
    /**
     * Keeps Integer array which includes product ids of out of stocks
     */
    private ArrayList<Integer> productIdsOutStock = new ArrayList<Integer>(); int requestedAmount = 3;
    
    /**
     * Keeps User array which includes all user have different roles
     */
    private ArrayList<User> accounts = new ArrayList<User>(Arrays.asList(
            new User(0,1,branches.get(0),"Seyda Nur","DEMIR","seyda@seyda.com","123456","Darica/KOCAELI","5000000000"),
            new User(1,1,branches.get(0),"Sefer","DEMIR","sefer@sefer.com","123456","Darica/KOCAELI","5000000000"),
            new User(2,2,branches.get(0),"Name1","Surname1","be1","1234","Gebze/KOCAELI","500"),
            new User(3,2,branches.get(0),"Name2","Surname2","be3","1234","Gebze/KOCAELI","500"),
            new User(4,3,branches.get(0),"Name3","Surname3","c1","1234","Gebze/KOCAELI","500"),
            new User(5,3,branches.get(0),"Name4","Surname4","c2","1234","Gebze/KOCAELI","500")
        ));
    
    /**
     * Keeps Order array which includes all previous orders
     */
    private ArrayList<Order> orders = new ArrayList<Order>(Arrays.asList(new Order(0, accounts.get(4), products.get(0)), new Order(1, accounts.get(4), products.get(1))));
    
    /**
     * Test 1 method, tests sign up action
     */
    public void test1 () {
        System.out.println("T1  : Test Sign Up");
        account.signUp(accounts);
        System.out.println("T1  : Test Ended");
    }
    
    /**
     * Test 2 method, tests sign in action
     */
    public void test2 () {
        System.out.println("T2  : Test Sign In");
        account.signIn(accounts);
        System.out.println("T2  : Test Ended");
    }
    
    /**
     * Test 3 method, tests listing customers
     */
    public void test3 () {
        System.out.println("T3  : Test List Customers");
        branchEmployeeObject.listCustomers(accounts);
        System.out.println("T3  : Test Ended");
    }
    
    /**
     * Test 4 method, tests adding customers
     */
    public void test4 () {
        System.out.println("T4  : Test Add Customer");
        branchEmployeeObject.addCustomer(accounts, branches);
        System.out.println("T4  : Test Ended");
    }
    
    /**
     * Test 5 method, tests removing customers
     */
    public void test5 () {
        System.out.println("T5  : Test Remove Customer");
        branchEmployeeObject.removeCustomer(accounts);
        System.out.println("T5  : Test Ended");
    }
    
    /**
     * Test 6 method, tests listing branch employees
     */
    public void test6 () {
        System.out.println("T6  : Test List Branch Employees");
        administratorObject.listBranchEmployees(accounts);
        System.out.println("T6  : Test Ended");
    }
    
    /**
     * Test 7 method, tests adding branch employee
     */
    public void test7 () {
        System.out.println("T7  : Test Add Branch Employee");
        administratorObject.addBranchEmployee(accounts, branches);
        System.out.println("T7  : Test Ended");
    }
    
    /**
     * Test 8 method, tests removing branch employee
     */
    public void test8 () {
        System.out.println("T8  : Test Remove Branch Employee");
        administratorObject.removeBranchEmployee(accounts);
        System.out.println("T8  : Test Ended");
    }
    
    /**
     * Test 9 method, tests listing branches
     */
    public void test9 () {
        System.out.println("T9  : Test List Branches");
        administratorObject.listBranches(branches);
        System.out.println("T9  : Test Ended");
    }
    
    /**
     * Test 10 method, tests adding branch
     */
    public void test10 () {
        System.out.println("T10 : Test Add Branch");
        administratorObject.addBranch(branches);
        System.out.println("T10 : Test Ended");
    }
    
    /**
     * Test 11 method, tests removing branch
     */
    public void test11 () {
        System.out.println("T11 : Test Remove Branch");
        administratorObject.removeBranch(branches);
        System.out.println("T11 : Test Ended");
    }
    
    /**
     * Test 12 method, tests listing all products
     */
    public void test12 () {
        System.out.println("T12 : Test List All Products");
        branchEmployeeObject.listAllProducts(products);
        System.out.println("T12 : Test Ended");
    }
    
    /**
     * Test 13 method, tests listing products out of stock
     */
    public void test13 () {
        System.out.println("T13 : Test List Products out of Stock");
        branchEmployeeObject.listProductsOutStock(products, requestedAmount);
        System.out.println("T13 : Test Ended");
    }
    
    /**
     * Test 14 method, tests adding product
     */
    public void test14 () {
        System.out.println("T14 : Test Add Product");
        branchEmployeeObject.addProduct(products);
        System.out.println("T14 : Test Ended");
    }
    
    /**
     * Test 15 method, tests removing productt
     */
    public void test15 () {
        System.out.println("T15 : Test Remove Product");
        branchEmployeeObject.removeProduct(products);
        System.out.println("T15 : Test Ended");
    }
    
    /**
     * Test 16 method, tests increasing product amount
     */
    public void test16 () {
        System.out.println("T16 : Test Increase Product Amount");
        branchEmployeeObject.increaseProductAmount(products);
        System.out.println("T16 : Test Ended");
    }
    
    /**
     * Test 17 method, tests decreasing product amount
     */
    public void test17 () {
        System.out.println("T17 : Test Decrease Product Amount");
        branchEmployeeObject.decreaseProductAmount(products);
        System.out.println("T17 : Test Ended");
    }
    
    /**
     * Test 18 method, tests making sale
     */
    public void test18 () {
        System.out.println("T18 : Test Make Sale");
        branchEmployeeObject.makeSale(accounts, products, orders);
        System.out.println("T18 : Test Ended");
    }
    
    /**
     * Test 19 method, tests shopping online
     */
    public void test19 () {
        System.out.println("T19 : Test Shop Online");
        customerObject.shopOnline(accounts, products, orders);
        System.out.println("T19 : Test Ended");
    }
    
    /**
     * Test 20 method, tests viewing previous orders
     */
    public void test20 () {
        System.out.println("T20 : Test View Previous Orders");
        customerObject.viewMyOrders(orders);
        System.out.println("T20 : Test Ended");
    }
    
    /**
     * Test 21 method, tests informing manager
     */
    public void test21 () {
        System.out.println("T21 : Test Inform Manager");
        branchEmployeeObject.informManager(products, productIdsOutStock, requestedAmount);
        System.out.println("T21 : Test Ended");
    }
    
    /**
     * Test 22 method, tests viewing notifications
     */
    public void test22 () {
        System.out.println("T22 : Test View Notifications");
        administratorObject.viewNotifications(productIdsOutStock, requestedAmount);
        System.out.println("T22 : Test Ended");
    }
}
