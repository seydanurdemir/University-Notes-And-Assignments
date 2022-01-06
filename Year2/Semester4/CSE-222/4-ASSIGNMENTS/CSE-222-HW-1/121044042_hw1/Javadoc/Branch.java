import java.util.ArrayList;
import java.util.Objects;

/**
 * This class about the branch operations
 * @author seydanurdemir
 */
public class Branch {
    private int branchId;
    private String name;
    private String address;
    
    /**
     * No parameter constructor
     * Branch id -1, name unknown, address unknown
     */
    public Branch () {
        branchId = -1;
        name = "unknown";
        address = "unknown";
    }
    
    /**
     * Two parameter constructor
     * Branch id -1, name is first parameter, address is second parameter
     * @param name String
     * @param address StringString
     */
    public Branch (String name, String address) {
        branchId = -1;
        this.name = name;
        this.address = address;
    }
    
    /**
     * Three parameter constructor
     * Branch id, name and address comes from parameters
     * @param branchId int
     * @param name String
     * @param address String
     */
    public Branch (int branchId, String name, String address) {
        this.branchId = branchId;
        this.name = name;
        this.address = address;
    }
    
    /**
     * Getter for branch id
     * @return branch id int
     */
    public int getBranchId () { return branchId; }

    /**
     * Setter for branch id
     * @param branchId int
     */
    public void setBranchId (int branchId) { this.branchId = branchId; }

    /**
     * Getter for name
     * @return name String
     */
    public String getName () { return name; }

    /**
     * Setter for name
     * @param name String
     */
    public void setName (String name) { this.name = name; }

    /**
     * Getter for address
     * @return address String
     */
    public String getAddress () { return address; }

    /**
     * Setter for address
     * @param address String
     */
    public void setAddress (String address) { this.address = address; }
    
    /**
     * Lists branches
     * @param branches ArrayList
     */
    public void listBranches (ArrayList<Branch> branches) {
        for (int i=0; i < branches.size(); i++) {
            System.out.println(branches.get(i).toString()+"\n-");
        }
    }
    
    /**
     * Adds branch
     * @param branches ArrayList
     * @param branch Branch
     */
    public void addBranch (ArrayList<Branch> branches, Branch branch) {
        ArrayList<Integer> indexes = findByProperty(branches, 1, branch.getName());
        if (indexes.isEmpty()) {
            branches.add(branch);
            branch.setBranchId(branches.size()-1);
            System.out.println("Branch added with branch id " + branch.getBranchId() + " .");
        } else {
            System.out.println("A branch has already exists with this name, please try again with another name.");
        }
    }
    
    /**
     * Remove branch
     * @param branches ArrayList
     * @param branchId int
     */
    public void removeBranch (ArrayList<Branch> branches, int branchId) {
        ArrayList<Integer> indexes = findByProperty(branches, 0, branchId);
        if (indexes.isEmpty()) {
            System.out.println("Wrong branch id, please try again.");
        } else {
            branches.remove(branches.get(indexes.get(0)));
            System.out.println("Branch " + branchId + " removed.");
        }
    }
    
    /**
     * Finds any object integer attribute property
     * @param branches ArrayList
     * @param propertyNo int
     * @param property int
     * @return ArrayList indexes
     */
    public ArrayList<Integer> findByProperty (ArrayList<Branch> branches, int propertyNo, int property) {
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        for (int i = 0 ; i < branches.size() ; i++) {
            switch (propertyNo) {
                case 0 : { if (branches.get(i).getBranchId() == property) indexes.add(i); break; }
            }
        }
        return indexes;
    }
    
    /**
     * Finds any object string attribute property
     * @param branches ArrayList
     * @param propertyNo int
     * @param property String
     * @return ArrayList indexes
     */
    public ArrayList<Integer> findByProperty (ArrayList<Branch> branches, int propertyNo, String property) {
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        for (int i = 0 ; i < branches.size() ; i++) {
            switch (propertyNo) {
                case 1 : { if (branches.get(i).getName().equals(property)) indexes.add(i); break; }
                case 2 : { if (branches.get(i).getAddress().equals(property)) indexes.add(i); break; }
            }
        }
        return indexes;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) { return true; }
        if (obj == null) { return false; }
        if (obj.getClass() == this.getClass()) {
            Branch other = (Branch) obj;
            return ((this.branchId == other.branchId) && (this.name.equals(other.name)) && (this.address.equals(other.address)));
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode () {
        int hash = 5;
        hash = 11 * hash + this.branchId;
        hash = 11 * hash + Objects.hashCode(this.name);
        hash = 11 * hash + Objects.hashCode(this.address);
        return hash;
    }
    
    @Override
    public String toString() {
        return  "\nBranch Id   : " + branchId + 
                "\nName        : " + name + 
                "\nAddress     : " + address;
    }
}
