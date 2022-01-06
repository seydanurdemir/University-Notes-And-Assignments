import java.io.*;
import java.util.*;

/**
 * Menu class operates menu operations.
 *
 * @author seyda
 */
public class Menu {

	/**
	 * Scans file or screen
	 */
	private Scanner sc;

	/**
	 * Main menu choice
	 */
	private String mainChoice;

	/**
	 * Interactive menu choice
	 */
	private String interactiveChoice;

	/**
	 * Trader menu choice
	 */
	private String traderChoice;

	/**
	 * Customer menu choice
	 */
	private String customerChoice;

	/**
	 * Test menu choice
	 */
	private String testChoice;

	/**
	 * Company datas
	 */
	private Company com = null;

	/**
	 * Open sessions
	 */
	public ArrayDeque<String> sessions = new ArrayDeque<>();

	/**
	 * Users of the system, trader or customer
	 */
	private HashMap<String, String> users = new HashMap<>();

	/**
	 * One parameter constructor Takes company datas Initializes current datas
	 *
	 * @param com Company
	 */
	public Menu(Company com) {
		this.com = com;
		System.out.println("System is initializing structures ...");
		init();
		System.out.println("System initialized structures ...");
		sc = new Scanner(System.in);
		mainChoice = "M";
		interactiveChoice = "M";
		testChoice = "M";
		traderChoice = "M";
		customerChoice = "M";
	}

	/**
	 * Initializes current datas
	 */
	public void init() {
		String username;
		String password;

		File fpr = new File("e-commerce-traders.csv");

		Scanner sc = null;
		try {
			sc = new Scanner(fpr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		sc.useDelimiter("\n");
		//sc.next();

		while (sc.hasNext()) {
			String tempLine = sc.next();
			//System.out.println(tempLine);
			
			String substring = tempLine.substring(0, tempLine.length());
			String line = substring + ";x";

			ArrayList<String> row = new ArrayList<String>(Arrays.asList(line.split(";")));
			//System.out.println(row.toString());
			username = row.get(0);
			password = row.get(1);
			
			if (users.get(username) == null) {
				users.put(username, password);
				//System.out.println(username+";"+password);
			}
		}

		sc.close();
	}

	/**
	 * Operates main menu
	 */
	public void menu() {
		System.out.println("Start of Menu");
		System.out.println("Welcome to Product Company Automation System");
		while (!(mainChoice.equals("E") || mainChoice.equals("e"))) {
			mainQuestion();
			mainMenu();
			scannerSign();
			mainChoice = sc.nextLine();
			mainChose();
		}
		System.out.println("End of Menu");
	}

	/**
	 * Prints scan sign only
	 */
	public void scannerSign() {
		System.out.println("> ");
	}

	/**
	 * Prints main menu question
	 */
	public void mainQuestion() {
		System.out.println("What Do You Want to Do?");
	}

	/**
	 * Prints main menu options
	 */
	public void mainMenu() {
		System.out.println("[1] Menu");
		System.out.println("[2] Test");
		System.out.println("[3] Driver");
		System.out.println("[E] Exit");
	}

	/**
	 * Operates main menu chose
	 */
	public void mainChose() {
		switch (mainChoice) {
			case "1":
				while (!(interactiveChoice.equals("E") || interactiveChoice.equals("e"))) {
					interactiveQuestion();
					interactiveMenu();
					scannerSign();
					interactiveChoice = sc.nextLine();
					interactiveChose();
				}
				break;
			case "2":
				while (!(testChoice.equals("E") || testChoice.equals("e"))) {
					testQuestion();
					testMenu();
					scannerSign();
					testChoice = sc.nextLine();
					testChose();
				}
				break;
			case "3":
				driverQuestion();
				driverMenu();
				driverChose();
				break;
			default:
				break;
		}
		interactiveChoice = "M";
		testChoice = "M";
	}

	/**
	 * Prints interactive menu question
	 */
	public void interactiveQuestion() {
		System.out.println("Make a Choise");
	}

	/**
	 * Prints interactive menu options
	 */
	public void interactiveMenu() {
		System.out.println("[1] Sign In");
		System.out.println("[2] Sign Up");
		System.out.println("[E] Exit");
	}

	/**
	 * Operates interactive menu chose
	 */
	public void interactiveChose() {
		Account account = new Account();
		String username;
		String password;
		String role;
		switch (interactiveChoice) {
			case "1": {
				System.out.println("Role     : ");
				scannerSign();
				role = sc.nextLine();
				System.out.println("Username : ");
				scannerSign();
				username = sc.nextLine();
				System.out.println("Password : ");
				scannerSign();
				password = sc.nextLine();

				account.signIn(sessions, users, username, password);
				if (!sessions.isEmpty()) {
					if (role.equals("Trader")) {
						while (!(traderChoice.equals("E") || traderChoice.equals("e"))) {
							interactiveQuestion();
							traderMenu();
							scannerSign();
							traderChoice = sc.nextLine();
							traderChose();
						}
					} else {
						while (!(customerChoice.equals("E") || customerChoice.equals("e"))) {
							interactiveQuestion();
							customerMenu();
							scannerSign();
							customerChoice = sc.nextLine();
							customerChose();
						}
					}
				}
			}
			break;
			case "2": {
				System.out.println("Role     : ");
				scannerSign();
				role = sc.nextLine();
				System.out.println("Username : ");
				scannerSign();
				username = sc.nextLine();
				System.out.println("Password : ");
				scannerSign();
				password = sc.nextLine();

				account.signUp(users, username, password);
			}
			break;
			case "e":
				System.out.println("System is returning to main menu ...");
				break;
			case "E":
				System.out.println("System is returning to main menu ...");
				break;
			default:
			/* Left Blank */
		}
	}

	/**
	 * Prints trader menu options
	 */
	public void traderMenu() {
		System.out.println("[1] List Products (Search, Filter, Sort)");
		System.out.println("[2] Create Order");
		System.out.println("[3] Add Product");
		System.out.println("[4] Edit Product");
		System.out.println("[5] Remove Product");
		System.out.println("[6] List Order");
		System.out.println("[7] Meet Order");
		System.out.println("[8] Cancel Order");
		System.out.println("[E] Exit");
	}

	/**
	 * Operates trader menu chose
	 */
	public void traderChose() {
		Trader traderObject = new Trader();
		String tempId = "AAA";
		switch (traderChoice) {
			case "1":
				traderObject.listProducts(com.getProducts());
				break;
			case "2":
				traderObject.createOrder(com.getOrders());
				break;
			case "3":
				traderObject.addProduct(com.getProducts());
				break;
			case "4":
				traderObject.editProduct(com.getProducts(), tempId);
				break;
			case "5":
				traderObject.removeProduct(com.getProducts(), tempId);
				break;
			case "6":
				traderObject.listOrder(com.getOrders());
				break;
			case "7":
				traderObject.meetOrder(com.getOrders(), tempId);
				break;
			case "8":
				traderObject.cancelOrder(com.getOrders(), tempId);
				break;
			case "e": {
				traderObject.signOut(sessions);
				System.out.println("Trader signed out ...");
				System.out.println("System is returning to interactive menu ...");
			}
			break;
			case "E": {
				traderObject.signOut(sessions);
				System.out.println("Trader signed out ...");
				System.out.println("System is returning to interactive menu ...");
			}
			break;
			default:
			/* Left Blank */
		}
	}

	/**
	 * Prints customer menu options
	 */
	public void customerMenu() {
		System.out.println("[1] List Products (Search, Filter, Sort)");
		System.out.println("[2] Create Order");
		System.out.println("[E] Exit");
	}

	/**
	 * Operates customer menu chose
	 */
	public void customerChose() {
		Customer customerObject = new Customer();
		switch (customerChoice) {
			case "1":
				customerObject.listProducts(com.getProducts());
				break;
			case "2":
				customerObject.createOrder(com.getOrders());
				break;
			case "e": {
				customerObject.signOut(sessions);
				System.out.println("Customer signed out ...");
				System.out.println("System is returning to interactive menu ...");
			}
			break;
			case "E": {
				customerObject.signOut(sessions);
				System.out.println("Customer signed out ...");
				System.out.println("System is returning to interactive menu ...");
			}
			break;
			default:
			/* Left Blank */
		}
	}

	/**
	 * Prints test menu question
	 */
	public void testQuestion() {
		System.out.println("Which Operation Do You Want to Test?");
	}

	/**
	 * Prints test menu options
	 */
	public void testMenu() {
		System.out.println("[1]  T1  : Test Sign Up / In / Out");
		System.out.println("[2]  T2  : Test List Products / Search");
		System.out.println("[3]  T3  : Test List Products / Filter");
		System.out.println("[4]  T4  : Test List Products / Sort");
		System.out.println("[E] Exit");
	}

	/**
	 * Operates test menu chose
	 */
	public void testChose() {
		Test testObject = new Test();
		switch (testChoice) {
			case "1":
				testObject.test1();
				break;
			case "2":
				testObject.test2();
				break;
			case "3":
				testObject.test3();
				break;
			case "e":
				System.out.println("System is returning to main menu ...");
				break;
			case "E":
				System.out.println("System is returning to main menu ...");
				break;
			default:
			/* Left Blank */
		}
	}

	/**
	 * Prints driver menu question
	 */
	public void driverQuestion() {
		System.out.println("Driver Program is Running...");
	}

	/**
	 * Prints driver menu options
	 */
	public void driverMenu() {
		System.out.println("[1]  D1  : Driving Sign In / Out");
		System.out.println("[2]  D2  : Driving Sign Up");
		System.out.println("[3]  D3  : Driving Forgot Password");
	}

	/**
	 * Operates driver menu chose
	 */
	public void driverChose() {
		Driver driverObject = new Driver();
		driverObject.drive();
		System.out.println("System is returning to main menu ...");
	}
}
