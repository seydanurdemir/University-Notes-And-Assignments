import java.util.ArrayList;

/**
 * This interface is about general company operations
 * @author seydanurdemir
 */
public interface Company {

    /**
     * List branches
     * @param branches ArrayList
     */
    public void listBranches (ArrayList<Branch> branches);

    /**
     * List branch employees
     * @param accounts ArrayList
     */
    public void listBranchEmployees (ArrayList<User> accounts);

    /**
     * List customers
     * @param accounts ArrayList
     */
    public void listCustomers (ArrayList<User> accounts);

    /**
     * List products
     * @param products ArrayList
     */
    public void listProducts (ArrayList<Product> products);
}
