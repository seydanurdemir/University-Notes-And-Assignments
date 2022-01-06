import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class is about administrator operations
 * Administrator extends branch employee, so it has all authorities of branch employee
 * @author seydanurdemir
 */
public class Administrator extends BranchEmployee  implements Company{
    
    /**
     * Administrator can view notifications about products out of stock coming from branch employee
     * @param productIdsOutStock ArrayList
     * @param requestedAmount int
     */
    public void viewNotifications (ArrayList<Integer> productIdsOutStock, int requestedAmount){
        System.out.println("List of Products  out of Stock (Requested Amount " + requestedAmount + ")");
        for (int i=0; i < productIdsOutStock.size(); i++) {
            System.out.println(productIdsOutStock.get(i));
        } 
        System.out.println("Please update stocks.");
    }
    
    /**
     * Administrator can list branches
     * @param branches ArrayList
     */
    @Override
    public void listBranches (ArrayList<Branch> branches) {
        System.out.println("List of Branches");
        branches.get(0).listBranches(branches);
    }
    
    /**
     * Administrator can add new branches by entering branch name and address
     * If there is any other branch with same name, administrator can not add branch
     * System assigns a branch id to the branch automatically
     * @param branches ArrayList
     */
    public void addBranch (ArrayList<Branch> branches) {
        Branch branch = new Branch();
        Scanner sc = new Scanner(System.in);
        System.out.println("Name        : "); branch.setName(sc.nextLine());
        System.out.println("Address     : "); branch.setAddress(sc.nextLine());
        branch.addBranch(branches,branch);
    }
    
    /**
     * Administrator can remove existing branches by entering branch id
     * If the branch id is wrong, administrator can not remove the branch
     * @param branches ArrayList
     */
    public void removeBranch (ArrayList<Branch> branches) {
        Branch branch = new Branch();
        Scanner sc = new Scanner(System.in);
        int branchId;
        System.out.println("Which Branch Do You Want to Remove?");
        try {
            branchId = sc.nextInt();
        } catch(InputMismatchException e) {
            System.err.println("Wrong input, branch id selected as -1 automatically.");
            branchId = -1;
        }
        branch.removeBranch(branches,branchId);
    }
    
    /**
     * Administrator can list branch employees
     * @param accounts ArrayList
     */
    @Override
    public void listBranchEmployees (ArrayList<User> accounts) {
        System.out.println("List of Branch Employees");
        accounts.get(0).listByRole(accounts,2);
    }
    
    /**
     * Administrator can add new branch employee by entering branch name and branch employee name, surname, email, password, address and phone number
     * If there is any other user with same email, administrator can not add branch employee
     * System assigns a branch employee id to the branch employee automatically
     * @param accounts ArrayList
     * @param branches ArrayList
     */
    public void addBranchEmployee (ArrayList<User> accounts, ArrayList<Branch> branches) {
        User user = new User();
        Scanner sc = new Scanner(System.in);
        user.setRole(2);
        System.out.println("Branch Name : ");
        ArrayList<Integer> indexes = branches.get(0).findByProperty(branches, 1, sc.nextLine());
        if (indexes.isEmpty()) {
            System.out.println("Wrong branch name, branch employee added to online branch automatically.");
            user.setBranch(branches.get(0));
        } else {
            user.setBranch(branches.get(indexes.get(0)));
        }
        System.out.println("Name        : "); user.setName(sc.nextLine());
        System.out.println("Surname     : "); user.setSurname(sc.nextLine());
        System.out.println("E-mail      : "); user.setEmail(sc.nextLine());
        System.out.println("Password    : "); user.setPassword(sc.nextLine());
        System.out.println("Address     : "); user.setAddress(sc.nextLine());
        System.out.println("Phonenumber : "); user.setPhonenumber(sc.nextLine());
        user.addUser(accounts,user);
    }
    
    /**
     * Administrator can remove existing branch employee by entering branch employee id
     * If the branch employee id is wrong, administrator can not remove the branch employee
     * @param accounts ArrayList
     */
    public void removeBranchEmployee (ArrayList<User> accounts) {
        User user = new User();
        int userId;
        Scanner sc = new Scanner(System.in);
        System.out.println("Which Branch Employee Do You Want to Remove?");
        try {
            userId = sc.nextInt();
        } catch(InputMismatchException e) {
            System.err.println("Wrong input, user id selected as -1 automatically.");
            userId = -1;
        }
        user.removeUser(accounts,userId);
    }
    
    /**
     * Administrator can change the user role by entering user id
     * If the entered user id is wrong, administrator can not change the user role
     * @param accounts ArrayList
     */
    public void changeUserRole (ArrayList<User> accounts) {
        User user = new User();
        Scanner sc = new Scanner(System.in);
        int userId = -1; int role = 3;
        System.out.println("Which Customer Do You Want to Change Role?");
        try {
            userId = sc.nextInt();
        } catch(InputMismatchException e) {
            System.err.println("Wrong input, user id selected as -1 automatically.");
            userId = -1;
        }
        ArrayList<Integer> indexes = user.findByProperty(accounts, 0, userId);
        if (indexes.isEmpty()) {
            System.out.println("Wrong user id, please try again.");
        } else {
            System.out.println("Which Role You Want to Change User?");
            System.out.println("[1] Administrator");
            System.out.println("[2] Branch Employee");
            System.out.println("[3] Customer");
            try {
                role = sc.nextInt();
            } catch(InputMismatchException e) {
                System.err.println("Wrong input, user role selected customer automatically.");
                role = 3;
            }
            if (role == 1) {
                accounts.get(indexes.get(0)).setRole(role);
                System.out.println("User role changed to \"Administrator\"");
            } else if (role == 2) {
                accounts.get(indexes.get(0)).setRole(role);
                System.out.println("User role changed to \"Branch Employee\"");
            } else if (role == 3) {
                accounts.get(indexes.get(0)).setRole(role);
                System.out.println("User role changed to \"Customer\"");
            } else
                System.out.println("Wrong role, please try again.");
        }
    }
}
