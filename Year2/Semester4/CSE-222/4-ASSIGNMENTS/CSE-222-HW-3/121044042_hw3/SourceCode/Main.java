/**
 * Main Class : Main method is in the main class.
 * 
 * @author Seyda Nur DEMIR
 * @version 1.0
 * @since 2021-04-16
 */
public class Main {
	/**
	 * Main method
	 * @param args String []
	 */
	public static void main(String [] args)
	{
		System.out.println("\n------------------------------------------------------------------ START");   
		
		Driver hw3Driver = new Driver();
		hw3Driver.driverForBranch();
		hw3Driver.driverForProduct();
		hw3Driver.driverForOrder();
		
		System.out.println("\n-------------------------------------------------------------------- END\n");
	}
}
