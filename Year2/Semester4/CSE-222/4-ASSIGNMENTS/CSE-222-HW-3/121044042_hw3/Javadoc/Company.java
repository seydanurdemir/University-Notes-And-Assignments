/**
 * Company Interface
 *
 * This interface is about general company operations
 *
 * @author Seyda Nur DEMIR
 */
public interface Company {

    /**
     * List branches
     * @param branches KWLinkedList
     */
    public void listBranches (KWLinkedList <Branch> branches);

    /**
     * List branch employees
     * @param accounts KWArrayList
     */
    public void listBranchEmployees (KWArrayList <Customer> users);

    /**
     * List customers
     * @param accounts KWArrayList
     */
    public void listCustomers (KWArrayList <Customer> users);

    /**
     * List products
     * @param products KWArrayList
     */
    public void listProducts (HybridList <Product> products);
}
