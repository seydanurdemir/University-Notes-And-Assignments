import java.util.*;

/**
 * Branch Class
 *
 * @author Seyda Nur DEMIR
 */
public class Branch {

	/**
	 * Branch Id Counter
	 */
	private static int branchIdCounter = 0;

	/**
	 * Branch Id is unique key for each branch
	 */
	private int branchId;

	/**
	 * Branch name
	 */
	private String name;

	/**
	 * Branch address
	 */
	private String address;

	/**
	 * No parameter constructor
	 */
	public Branch() {
		this.branchId = branchIdCounter++;
		this.name = "unknown";
		this.address = "unknown";
	}

	/**
	 * All parameter constructor
	 *
	 * @param name String
	 * @param address StringString
	 */
	public Branch(String name, String address) {
		this.branchId = branchIdCounter++;
		this.name = name;
		this.address = address;
	}

	/**
	 * Copy constructor
	 *
	 * @param other Branch
	 */
	public Branch(Branch other) {
		this.branchId = other.branchId;
		this.name = other.name;
		this.address = other.address;
	}

	/**
	 * Getter for branch id
	 *
	 * @return branch id int
	 */
	public int getBranchId() {
		return branchId;
	}

	/**
	 * Setter for branch id
	 *
	 * @param branchId int
	 */
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	/**
	 * Getter for name
	 *
	 * @return name String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for name
	 *
	 * @param name String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for address
	 *
	 * @return address String
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Setter for address
	 *
	 * @param address String
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Adds branch
	 *
	 * @param branches KWLinkedList
	 * @param branch Branch
	 */
	public void addBranch(KWLinkedList<Branch> branches, Branch branch) {
		branches.addLast(branch);
	}

	/**
	 * Removes branch
	 *
	 * @param branches KWLinkedList
	 * @param branch Branch
	 */
	public void removeBranch(KWLinkedList<Branch> branches, Branch branch) {
		int index = branches.indexOf(branch);
		if (index != -1) {
			branches.remove(index);
		} else {
			throw new NoSuchElementException();
		}
	}

	/**
	 * Prints branches
	 *
	 * @param branches KWLinkedList
	 */
	public void printBranches(KWLinkedList<Branch> branches) {
		branches.print();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj.getClass() == this.getClass()) {
			Branch other = (Branch) obj;
			return ((this.branchId == other.branchId) && (this.name.equals(other.name)) && (this.address.equals(other.address)));
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 11 * hash + this.branchId;
		hash = 11 * hash + Objects.hashCode(this.name);
		hash = 11 * hash + Objects.hashCode(this.address);
		return hash;
	}

	@Override
	public String toString() {
		return "\nBranch Id   : " + branchId
			+ "\nName        : " + name
			+ "\nAddress     : " + address;
	}
}
