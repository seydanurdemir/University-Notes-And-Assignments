/**
 * Main class contains main method.
 *
 * @author seyda
 */
public class Main {

	/**
	 * Main method
	 *
	 * @param args String
	 */
	public static void main(String[] args) {
		Company com = new Company();

		Menu m = new Menu(com);
		m.menu();
	}
}
