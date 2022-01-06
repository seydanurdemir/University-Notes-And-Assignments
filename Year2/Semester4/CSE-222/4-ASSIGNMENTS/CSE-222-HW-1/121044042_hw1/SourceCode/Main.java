/**
 * Main Class : Main method is in the main class.
 * 
 * @author seydanurdemir
 * @version 1.0
 * @since 2021-03-08
 */
public class Main {
    
    /**
     * Main method
     * @param args String []
     */
    public static void main(String [] args)
    {
        System.out.println("-------------------------------------------------------------------- START");
        
        Menu menuObject = new Menu();
        menuObject.menu();
        
        System.out.println("-------------------------------------------------------------------- END");
    }
}
