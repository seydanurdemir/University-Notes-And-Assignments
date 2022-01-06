import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class keeps menus
 * @author seydanurdemir
 */
public class Menu {
    private Scanner sc;
    private String mainChoice;
    private String interactiveChoice;
    private String testChoice;
    /*private String driverChoice;*/
    private String administratorChoice;
    private String branchEmployeeChoice;
    private String customerChoice;
    
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
     * No parameter constructor for menu
     */
    public Menu () {
        sc = new Scanner(System.in);
        mainChoice = "M";
        interactiveChoice = "M";
        testChoice = "M";
        administratorChoice = "M";
        branchEmployeeChoice = "M";
        customerChoice = "M";
        accounts.get(0).setUserId(0);
    }
    
    /**
     * Starts level 1 menu
     */
    public void menu () {
        System.out.println("Start of Menu"); System.out.println("Welcome to Office Furniture Company Automation System");
        while (!(mainChoice.equals("E") || mainChoice.equals("e"))) {
            mainQuestion(); mainMenu(); scannerSign(); mainChoice = sc.nextLine(); mainChose();
        }
        System.out.println("System is Closing"); System.out.println("End of Menu");
    }
    
    /**
     * Makes easier to enter any input from the screen
     */
    public void scannerSign () {
        System.out.println("> ");
    }
    
    /**
     * Prints main menu question
     */
    public void mainQuestion () {
        System.out.println("What Do You Want to Do?");
    }
    
    /**
     * Prints main menu choices
     */
    public void mainMenu () {
        System.out.println("[1] Menu");
        System.out.println("[2] Test");
        System.out.println("[3] Driver");
        System.out.println("[E] Exit");
    }
    
    /**
     * Operates main choice
     */
    public void mainChose () {
        if (mainChoice.equals("1")) {
            while (!(interactiveChoice.equals("E") || interactiveChoice.equals("e"))) {
                interactiveQuestion(); interactiveMenu(); scannerSign(); interactiveChoice = sc.nextLine(); interactiveChose();
            }
        } else if (mainChoice.equals("2")) {
            while (!(testChoice.equals("E") || testChoice.equals("e"))) {
                testQuestion(); testMenu(); scannerSign(); testChoice = sc.nextLine(); testChose();
            }
        } else if (mainChoice.equals("3")) {
            driverQuestion(); driverMenu(); driverChose();
        }
        interactiveChoice = "M"; testChoice = "M";
    }
    
    /**
     * Prints interactive menu questions
     */
    public void interactiveQuestion () {
        System.out.println("Make a Choise");
    }
    
    /**
     * Prints interactive menu, starts this menu
     */
    public void interactiveMenu () {
        System.out.println("[1] Sign Up");
        System.out.println("[2] Sign In");
        System.out.println("[3] Forgot Password");
        System.out.println("[E] Exit");
    }
    
    /**
     * Operates interactive menu choice
     */
    public void interactiveChose () {
        Account account = new Account();
        switch(interactiveChoice) {
            case "1": account.signUp(accounts);  break;
            case "2": {
                activeUserRole = account.signIn(accounts);
                if (activeUserRole == 1) {
                    while (!(administratorChoice.equals("E") || administratorChoice.equals("e"))) {
                        administratorMenu(); administratorChoice = sc.nextLine(); administratorChose();
                    } administratorChoice = "M";
                } else if (activeUserRole == 2) {
                    while (!(branchEmployeeChoice.equals("E") || branchEmployeeChoice.equals("e"))) {
                        branchEmployeeMenu(); branchEmployeeChoice = sc.nextLine(); branchEmployeeChose();
                    } branchEmployeeChoice = "M";
                } else if (activeUserRole == 3) {
                    while (!(customerChoice.equals("E") || customerChoice.equals("e"))) {
                        customerMenu(); customerChoice = sc.nextLine(); customerChose();
                    } customerChoice = "M";
                } else
                    System.out.println("Unknown user role!");
                activeUserRole = 0;
                break;
            }
            case "3": account.forgotPassword(accounts);  break;
            default: /* Left Blank */
        }
    }
    
    /**
     * Prints test menu question
     */
    public void testQuestion () {
        System.out.println("Which Operation Do You Want to Test?");
    }
    
    /**
     * Prints test menu choices
     */
    public void testMenu () {
        System.out.println("[1]  T1  : Test Sign Up");
        System.out.println("[2]  T2  : Test Sign In");
        System.out.println("[3]  T3  : Test List Customers");
        System.out.println("[4]  T4  : Test Add Customer");
        System.out.println("[5]  T5  : Test Remove Customer");
        System.out.println("[6]  T6  : Test List Branch Employees");
        System.out.println("[7]  T7  : Test Add Branch Employee");
        System.out.println("[8]  T8  : Test Remove Branch Employee");
        System.out.println("[9]  T9  : Test List Branches");
        System.out.println("[10] T10 : Test Add Branch");
        System.out.println("[11] T11 : Test Remove Branch");
        System.out.println("[12] T12 : Test List All Products");
        System.out.println("[13] T13 : Test List Products out of Stock");
        System.out.println("[14] T14 : Test Add Product");
        System.out.println("[15] T15 : Test Remove Product");
        System.out.println("[16] T16 : Test Increase Product Amount");
        System.out.println("[17] T17 : Test Decrease Product Amount");
        System.out.println("[18] T18 : Test Make Sale");
        System.out.println("[19] T19 : Test Shop Online");
        System.out.println("[20] T20 : Test View Previous Orders");
        System.out.println("[21] T21 : Test Inform Manager");
        System.out.println("[22] T22 : Test View Notifications");
        System.out.println("[E] Exit");
    }
    
    /**
     * Operates test choice
     */
    public void testChose () {
        Test testObject = new Test();
        switch(testChoice) {
            case "1":  testObject.test1();  break;
            case "2":  testObject.test2();  break;
            case "3":  testObject.test3();  break;
            case "4":  testObject.test4();  break;
            case "5":  testObject.test5();  break;
            case "6":  testObject.test6();  break;
            case "7":  testObject.test7();  break;
            case "8":  testObject.test8();  break;
            case "9":  testObject.test9();  break;
            case "10": testObject.test10(); break;
            case "11": testObject.test11(); break;
            case "12": testObject.test12(); break;
            case "13": testObject.test13(); break;
            case "14": testObject.test14(); break;
            case "15": testObject.test15(); break;
            case "16": testObject.test16(); break;
            case "17": testObject.test17(); break;
            case "18": testObject.test18(); break;
            case "19": testObject.test19(); break;
            case "20": testObject.test20(); break;
            case "21": testObject.test21(); break;
            case "22": testObject.test22(); break;
            default: /* Left Blank */
        }
    }
    
    /**
     * Prints driver menu title
     */
    public void driverQuestion () {
        System.out.println("Driver Program is Running...");
    }
    
    /**
     * Prints driver menu steps
     */
    public void driverMenu () {
        System.out.println("[1]  D1  : Driving Sign Up");
        System.out.println("[2]  D2  : Driving Sign In");
        System.out.println("[3]  D3  : Driving List Customers");
        System.out.println("[4]  D4  : Driving Add Customer");
        System.out.println("[5]  D5  : Driving Remove Customer");
        System.out.println("[6]  D6  : Driving List Branch Employees");
        System.out.println("[7]  D7  : Driving Add Branch Employee");
        System.out.println("[8]  D8  : Driving Remove Branch Employee");
        System.out.println("[9]  D9  : Driving List Branches");
        System.out.println("[10] D10 : Driving Add Branch");
        System.out.println("[11] D11 : Driving Remove Branch");
        System.out.println("[12] D12 : Driving List All Products");
        System.out.println("[13] D13 : Driving List Products out of Stock");
        System.out.println("[14] D14 : Driving Add Product");
        System.out.println("[15] D15 : Driving Remove Product");
        System.out.println("[16] D16 : Driving Increase Product Amount");
        System.out.println("[17] D17 : Driving Decrease Product Amount");
        System.out.println("[18] D18 : Driving Make Sale");
        System.out.println("[19] D19 : Driving Shop Online");
        System.out.println("[20] D20 : Driving View Previous Orders");
        System.out.println("[21] D21 : Driving Inform Manager");
        System.out.println("[22] D22 : Driving View Notifications");
    }
    
    /**
     * Operates driver menu
     */
    public void driverChose () {
        Driver driverObject = new Driver();
        driverObject.drive();
    }
    
    /**
     * Prints administrator user menu
     */
    public void administratorMenu () {
        System.out.println("     -----------------------------------------------");
        System.out.println("[1]  List All Products");
        System.out.println("[2]  Search Product");
        System.out.println("[3]  Shop Online");
        System.out.println("[4]  View Previous Orders");
        System.out.println("     -----------------------------------------------");
        System.out.println("[5]  List Only Products in Stock");
        System.out.println("[6]  Inform Manager about Products out of Stock");
        System.out.println("[7]  List Products");
        System.out.println("[8]  Add Product");
        System.out.println("[9]  Remove Product");
        System.out.println("[10] Increase Product Amount");
        System.out.println("[11] Decrease Product Amount");
        System.out.println("[12] List Customers");
        System.out.println("[13] Add Customer");
        System.out.println("[14] Remove Customer");
        System.out.println("[15] Make Sale");
        System.out.println("[16] View Customers' Previous Orders");
        System.out.println("     -----------------------------------------------");
        System.out.println("[17] View Notifications from Branch Employee");
        System.out.println("[18] List Branches");
        System.out.println("[19] Add Branch");
        System.out.println("[20] Remove Branch");
        System.out.println("[21] List Branch Employees");
        System.out.println("[22] Add Branch Employee");
        System.out.println("[23] Remove Branch Employee");
        System.out.println("[24] Change User Role");
        System.out.println("     -----------------------------------------------");
        System.out.println("[E]  Exit");
    }
    
    /**
     * Operates administrators choice
     */
    public void administratorChose () {
        Administrator administratorObject = new Administrator();
        switch(administratorChoice) {
            case "1":  administratorObject.listAllProducts(products); break;
            case "2":  administratorObject.searchProduct(products); break;
            case "3":  administratorObject.shopOnline(accounts, products, orders); break;
            case "4":  administratorObject.viewMyOrders(orders); break;
            case "5":  administratorObject.listProductsOutStock(products, requestedAmount); break;
            case "6":  administratorObject.informManager(products, productIdsOutStock, requestedAmount); break;
            case "7":  administratorObject.listProducts(products); break;
            case "8":  administratorObject.addProduct(products); break;
            case "9":  administratorObject.removeProduct(products); break;
            case "10": administratorObject.increaseProductAmount(products); break;
            case "11": administratorObject.decreaseProductAmount(products); break;
            case "12": administratorObject.listCustomers(accounts); break;
            case "13": administratorObject.addCustomer(accounts, branches); break;
            case "14": administratorObject.removeCustomer(accounts); break;
            case "15": administratorObject.makeSale(accounts, products, orders); break;
            case "16": administratorObject.viewPreviousOrders(orders); break;
            case "17": administratorObject.viewNotifications(productIdsOutStock, requestedAmount); break;
            case "18": administratorObject.listBranches(branches); break;
            case "19": administratorObject.addBranch(branches); break;
            case "20": administratorObject.removeBranch(branches); break;
            case "21": administratorObject.listBranchEmployees(accounts); break;
            case "22": administratorObject.addBranchEmployee(accounts,branches); break;
            case "23": administratorObject.removeBranchEmployee(accounts); break;
            case "24": administratorObject.changeUserRole(accounts); break;
            default: /* Left Blank */
        }
    }
    
    /**
     * Prints branch employee menu
     */
    public void branchEmployeeMenu () {
        System.out.println("     -----------------------------------------------");
        System.out.println("[1]  List All Products");
        System.out.println("[2]  Search Product");
        System.out.println("[3]  Shop Online");
        System.out.println("[4]  View Previous Orders");
        System.out.println("     -----------------------------------------------");
        System.out.println("[5]  List Only Products in Stock");
        System.out.println("[6]  Inform Manager about Products out of Stock");
        System.out.println("[7]  List Products");
        System.out.println("[8]  Add Product");
        System.out.println("[9]  Remove Product");
        System.out.println("[10] Increase Product Amount");
        System.out.println("[11] Decrease Product Amount");
        System.out.println("[12] List Customers");
        System.out.println("[13] Add Customer");
        System.out.println("[14] Remove Customer");
        System.out.println("[15] Make Sale");
        System.out.println("[16] View Customers' Previous Orders");
        System.out.println("     -----------------------------------------------");
        System.out.println("[E]  Exit");
    }
    
    /**
     * Operates branch employee choice
     */
    public void branchEmployeeChose () {
        BranchEmployee branchEmployeeObject = new BranchEmployee();
        switch(branchEmployeeChoice) {
            case "1":  branchEmployeeObject.listAllProducts(products); break;
            case "2":  branchEmployeeObject.searchProduct(products); break;
            case "3":  branchEmployeeObject.shopOnline(accounts, products, orders); break;
            case "4":  branchEmployeeObject.viewMyOrders(orders); break;
            case "5":  branchEmployeeObject.listProductsOutStock(products, requestedAmount); break;
            case "6":  branchEmployeeObject.informManager(products, productIdsOutStock, requestedAmount); break;
            case "7":  branchEmployeeObject.listProducts(products); break;
            case "8":  branchEmployeeObject.addProduct(products); break;
            case "9":  branchEmployeeObject.removeProduct(products); break;
            case "10": branchEmployeeObject.increaseProductAmount(products); break;
            case "11": branchEmployeeObject.decreaseProductAmount(products); break;
            case "12": branchEmployeeObject.listCustomers(accounts); break;
            case "13": branchEmployeeObject.addCustomer(accounts, branches); break;
            case "14": branchEmployeeObject.removeCustomer(accounts); break;
            case "15": branchEmployeeObject.makeSale(accounts, products, orders); break;
            case "16": branchEmployeeObject.viewPreviousOrders(orders); break;
            default: /* Left Blank */
        }
    }
    
    /**
     * Prints customer menu
     */
    public void customerMenu () {
        System.out.println("     -----------------------------------------------");
        System.out.println("[1]  List Products");
        System.out.println("[2]  Search Product");
        System.out.println("[3]  Shop Online");
        System.out.println("[4]  View Previous Orders");
        System.out.println("     -----------------------------------------------");
        System.out.println("[E]  Exit");
    }
    
    /**
     * Operates customer choice
     */
    public void customerChose () {
        Customer customerObject = new Customer();
        switch(customerChoice) {
            case "1": customerObject.listAllProducts(products); break;
            case "2": customerObject.searchProduct(products); break;
            case "3": customerObject.shopOnline(accounts, products, orders); break;
            case "4": customerObject.viewMyOrders(orders); break;
            default: /* Left Blank */
        }
    }
}
