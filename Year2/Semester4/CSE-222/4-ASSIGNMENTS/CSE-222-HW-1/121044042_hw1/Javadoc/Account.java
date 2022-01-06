import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class is about accounting operations
 * @author seydanurdemir
 */
public class Account {
    
    /**
     * User can sign up by entering its name, surname, email and password
     * User must enter a unique email
     * If there is another user with entered email, user can not sign up
     * When user signed up, system assigned a user id to user automatically
     * User id is also unique
     * @param accounts ArrayList
     */
    public void signUp (ArrayList<User> accounts) {
        User user = new User();
        Scanner sc = new Scanner(System.in);
        String name; String surname; String email; String password;
        System.out.println("Name     : "); name = sc.nextLine();
        System.out.println("Surname  : "); surname = sc.nextLine();
        System.out.println("E-mail   : "); email = sc.nextLine();
        System.out.println("Password : "); password = sc.nextLine();
        ArrayList<Integer> indexes = user.findByProperty(accounts, 4, email);
        if (indexes.isEmpty()) {
            user.setName(name); user.setSurname(surname); user.setEmail(email); user.setPassword(password);
            accounts.add(user);
            user.setUserId(accounts.size()-1);
            System.out.println("User signed up with user id " + user.getUserId() + " .");
        } else {
            System.out.println("Someone has already signed up with this email, please try again with another email.");
        }
    }
    
    /**
     * User can sign in by entering its email and password
     * If user enter wrong email or password can not sign in
     * When user sign in a menu meets it
     * @param accounts ArrayList accounts
     * @return int active user role
     */
    public int signIn (ArrayList<User> accounts) {
        User user = new User();
        Scanner sc = new Scanner(System.in);
        String email; String password;
        System.out.println("E-mail   : "); email = sc.nextLine();
        System.out.println("Password : "); password = sc.nextLine();
        ArrayList<Integer> indexes = user.findByProperty(accounts, 4, email);
        if (indexes.isEmpty()) {
            System.out.println("Wrong email, please try again.");
            return 0;
        } else {
            if (accounts.get(indexes.get(0)).getPassword().equals(password)) {
                System.out.println("User signed in.");
                return accounts.get(indexes.get(0)).getRole();
            } else {
                System.out.println("Wrong password, please try again.");
                return 0;
            }
        }
    }
    
    /**
     * When an existing user forgot password, it can change its password to sign in
     * User must enter its email to change password
     * If the entered email wrong, user can not change password
     * @param accounts ArrayList accounts
     */
    public void forgotPassword (ArrayList<User> accounts) {
        User user = new User();
        Scanner sc = new Scanner(System.in);
        String email; String password;
        System.out.println("E-mail   : "); email = sc.nextLine();
        ArrayList<Integer> indexes = user.findByProperty(accounts, 4, email);
        if (indexes.isEmpty()) {
            System.out.println("Wrong email, please try again.");
        } else {
                System.out.println("New Password : "); password = sc.nextLine();
                accounts.get(indexes.get(0)).setPassword(password);
                System.out.println("User changed password.");
        }
    }
    
    /**
     * This method did not implemented
     * This method is not also in the menu
     */
    public void editProfile () {
        
    }
    
    /**
     * This method did not implemented
     * This method is not also in the menu
     * @return int signed out user
     */
    public int signOut () {
        return 0;
    }
    
    /**
     * User can delete its account by entering its user id
     * If the user enter wrong user id, user can not delete account
     * This method implemented but is not in the menu
     * @param accounts ArrayList accounts
     * @param userId int
     */
    public void deleteAccount (ArrayList<User> accounts, int userId) {
        User user = new User();
        ArrayList<Integer> indexes = user.findByProperty(accounts, 0, userId);
        if (indexes.isEmpty()) {
            System.out.println("Wrong user id, please try again.");
        } else {
            accounts.remove(accounts.get(indexes.get(0)));
            System.out.println("User " + userId + " removed.");
        }
    }
}
