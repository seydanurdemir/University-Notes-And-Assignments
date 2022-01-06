import java.util.*;

/**
 * Customer Class
 *
 * @author Seyda Nur DEMIR
 */
public class Customer extends User {
	/**
	 * Customer Id Counter
	 */
	private static int customerIdCounter = 0;
    
	/**
	 * No parameter constructor
	 */
	public Customer () {
		this.setUserId(customerIdCounter++);
		this.setRole(2);
		this.setBranch((new Branch()));
		this.setName("unknown");
		this.setSurname("unknown");
		this.setEmail("unknown");
		this.setPassword("unknown");
		this.setAddress("unknown");
		this.setPhonenumber("unknown");
	}
	
	/**
	 * All parameter constructor
	 * @param branch
	 * @param name
	 * @param surname
	 * @param email
	 * @param password
	 * @param address
	 * @param phonenumber 
	 */
	public Customer (Branch branch, String name, String surname, String email, String password, String address, String phonenumber) {
		this.setUserId(customerIdCounter++);
		this.setRole(2);
		this.setBranch(branch);
		this.setName(name);
		this.setSurname(surname);
		this.setEmail(email);
		this.setPassword(password);
		this.setAddress(address);
		this.setPhonenumber(phonenumber);
	}
	
	/**
	 * Copy constructor
	 * @param other 
	 */
	public Customer (Customer other) {
		this.setUserId(other.getUserId());
		this.setRole(other.getRole());
		this.setBranch(other.getBranch());
		this.setName(other.getName());
		this.setSurname(other.getSurname());
		this.setEmail(other.getEmail());
		this.setPassword(other.getPassword());
		this.setAddress(other.getAddress());
		this.setPhonenumber(other.getPhonenumber());
	}
	
}
