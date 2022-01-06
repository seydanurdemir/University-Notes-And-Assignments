import java.io.*;
import java.util.*;

/**
 * Initialize class initializes the system.
 *
 * @author seyda
 */
public class Initialize {

	/**
	 * Initial file name
	 */
	private String filename;

	/**
	 * Initial products
	 */
	private LinkedList<String> products = new LinkedList<>();

	/**
	 * Initial trader names
	 */
	private ArrayList<String> tradernames = new ArrayList<>();

	/**
	 * One parameter constructor Reads datas from the given file Writes data to
	 * traders and products files
	 *
	 * @param filename String
	 */
	public Initialize(String filename) {
		this.filename = filename;
		System.out.println("System is reading file ...");
		readData();
		System.out.println("System read file ...");
		System.out.println("System is writing files ...");
		writeData();
		System.out.println("System wrote files ...");
	}

	/**
	 * Reads datas from the file
	 */
	public void readData() {
		String id;
		String product_name;
		String product_category_tree;
		String price;
		String discounted_price;
		String description;
		String trader;

		File fpr = new File(filename);

		Scanner sc = null;
		try {
			sc = new Scanner(fpr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		sc.useDelimiter("\n");
		sc.next();

		while (sc.hasNext()) {
			String tempLine = sc.next();

			String substring = tempLine.substring(0, tempLine.length() - 1);
			String line = substring + ";x";

			ArrayList<String> row = new ArrayList<String>(Arrays.asList(line.split(";")));
			id = row.get(0);
			product_name = row.get(1);
			product_category_tree = row.get(2).substring(4, row.get(2).length() - 4);
			price = row.get(3);
			discounted_price = row.get(4);
			description = row.get(5);
			trader = row.get(6);
			if (!tradernames.contains(trader)) {
				tradernames.add(trader);
				//System.out.println(trader);
			}

			StringBuilder eachLine = new StringBuilder(trader + ";" + id + ";" + product_name + ";" + product_category_tree + ";" + price + ";" + discounted_price + ";" + description + "\n");
			products.addLast(eachLine.toString());
			//System.out.println(eachLine.toString());
		}

		sc.close();
	}

	/**
	 * Writes datas to traders and products file
	 */
	public void writeData() {
		File fpt = new File("e-commerce-traders.csv");
		StringBuilder eachLine;

		FileWriter prt = null;
		try {
			prt = new FileWriter(fpt);
			for (int i = 0; i < tradernames.size(); i++) {
				eachLine = new StringBuilder(tradernames.get(i) + ";" + "123456" + "\n");
				prt.append(eachLine.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		File fpp = new File("e-commerce-products.csv");

		FileWriter prp = null;
		try {
			prp = new FileWriter(fpp);
			ListIterator<String> iter = products.listIterator();
			while (iter.hasNext()) {
				prp.append(iter.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
