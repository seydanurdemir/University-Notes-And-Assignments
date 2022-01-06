import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

/**
 * This class is about user operations
 * This class is base for customer, branch employee and administrator
 * @author seydanurdemir
 */
public class User {
    
    /**
     * User Id is unique key for each product
     */
    private int userId;
    
    /**
     * Role supplies an authority for any user
     * System has three types of roles, such as administrator, branhc employee and customer
     */
    private int role;
    
    /**
     * Branch keeps user's branch where it registered
     * Branch employees registered for branch where they work
     * Customers registered for web branch for shopping online
     * Customers also registered for any branch by branch employee when they shopping in store
     */
    private Branch branch;
    
    /**
     * Name of the user
     */
    private String name;
    
    /**
     * Surname of the user
     */
    private String surname;
    
    /**
     * Email of the user
     * Email must be unique
     */
    private String email;
    
    /**
     * Password of the user
     * User can change its password
     */
    private String password;
    
    /**
     * Address of the user
     * It is necessary for shopping
     */
    private String address;
    
    /**
     * Phone number of the user
     * It is necessary for shopping
     */
    private String phonenumber;
    
    /**
     * No parameter constructor
     * User id -1, role 3, branch new branch, name surname email password address phonenumber are unknown as default
     */
    public User () {
        userId = -1;
        role = 3;
        branch = new Branch(0,"Online","WEB");
        name = "unknown";
        surname = "unknown";
        email = "unknown";
        password = "unknown";
        address = "unknown";
        phonenumber = "unknown";
    }
    
    /**
     * Four parameter constructor
     * User id -1, role 3 as default, and name is first, surname is second, email is third, password is fourth parameters
     * @param name String
     * @param surname String
     * @param email String
     * @param password String
     */
    public User (String name, String surname, String email, String password) {
        userId = -1;
        role = 3;
        branch = new Branch(0,"Online","WEB");
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        address = "unknown";
        phonenumber = "unknown";
    }
    
    /**
     * Seven parameter constructor
     * User id -1 as default, and role is first, name is second, surname is third, email is fourth, password is fifth, address is sixth, phonenumber is seventh parameters
     * @param role int
     * @param branch Branch
     * @param name String
     * @param surname String
     * @param email String
     * @param password String
     * @param address String
     * @param phonenumber String
     */
    public User (int role, Branch branch, String name, String surname, String email, String password, String address, String phonenumber) {
        userId = -1;
        this.role = role;
        this.branch = branch;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phonenumber = phonenumber;
    }
    
    /**
     * Nine parameter constructor
     * User id is first, role is second, branch is third, name is fourth, surname is fifth, email is sixth, password is seventh, address is eigthth, phonenumber is ninth parameter
     * @param userId int
     * @param role int
     * @param branch Branch
     * @param name String
     * @param surname String
     * @param email String
     * @param password String
     * @param address String
     * @param phonenumber String
     */
    public User (int userId, int role, Branch branch, String name, String surname, String email, String password, String address, String phonenumber) {
        this.userId = userId;
        this.role = role;
        this.branch = branch;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phonenumber = phonenumber;
    }
    
    /**
     * Getter for user id
     * @return int user id
     */
    public int getUserId () { return userId; }

    /**
     * Setter for user id
     * @param userId int
     */
    public void setUserId (int userId) { this.userId = userId; }

    /**
     * Getter for role
     * @return int role
     */
    public int getRole () { return role; }

    /**
     * Setter for role
     * @param role int
     */
    public void setRole (int role) { this.role = role; }

    /**
     * Getter for branch
     * @return Branch branch
     */
    public Branch getBranch () { return branch; }

    /**
     * Setter for branch
     * @param branch Branch
     */
    public void setBranch (Branch branch) { this.branch = branch; }

    /**
     * Getter for name
     * @return String name
     */
    public String getName () { return name; }

    /**
     * Setter for name
     * @param name String
     */
    public void setName (String name) { this.name = name; }

    /**
     * Getter for surname
     * @return String surname
     */
    public String getSurname () { return surname; }

    /**
     * Setter for surname
     * @param surname String
     */
    public void setSurname (String surname) { this.surname = surname; }

    /**
     * Getter for email
     * @return String email
     */
    public String getEmail () { return email; }

    /**
     * Setter for email
     * @param email String
     */
    public void setEmail (String email) { this.email = email; }

    /**
     * Getter for password
     * @return String password
     */
    public String getPassword () { return password; }

    /**
     * Setter for password
     * @param password String
     */
    public void setPassword (String password) { this.password = password; }

    /**
     * Getter for address
     * @return String address
     */
    public String getAddress () { return address; }

    /**
     * Setter for address
     * @param address String
     */
    public void setAddress (String address) { this.address = address; }

    /**
     * Getter for phonenumber
     * @return String phone number
     */
    public String getPhonenumber () { return phonenumber; }

    /**
     * Setter for phonenumber
     * @param phonenumber String
     */
    public void setPhonenumber (String phonenumber) { this.phonenumber = phonenumber; }
    
    /**
     * Lists customer by their role
     * @param accounts ArrayList
     * @param role int
     */
    public void listByRole (ArrayList<User> accounts, int role) {
        for (int i=0; i < accounts.size(); i++) {
            if (accounts.get(i).getRole() == role) {
                System.out.println(accounts.get(i).toString()+"\n-");
            }
        }
    }
    
    /**
     * Adds user to a user array
     * If array consist any user has same email with entered users' email, user did not added
     * @param accounts ArrayList
     * @param user User
     */
    public void addUser (ArrayList<User> accounts, User user) {
        ArrayList<Integer> indexes = findByProperty(accounts, 4, user.getEmail());
        if (indexes.isEmpty()) {
            accounts.add(user);
            user.setUserId(accounts.size()-1);
            System.out.println("User added with user id " + user.getUserId() + " .");
        } else {
            System.out.println("Someone has already signed up with this email, please try again with another email.");
        }
    }
    
    /**
     * Removes user from a user array
     * If entered user id is wrong, user did not removed
     * @param accounts ArrayList
     * @param userId int
     */
    public void removeUser (ArrayList<User> accounts, int userId) {
        ArrayList<Integer> indexes = findByProperty(accounts, 0, userId);
        if (indexes.isEmpty()) {
            System.out.println("Wrong user id, please try again.");
        } else {
            accounts.remove(accounts.get(indexes.get(0)));
            System.out.println("User " + userId + " removed.");
        }
    }
    
    /**
     * Finds by integer property any user
     * @param users ArrayList
     * @param propertyNo int
     * @param property int
     * @return ArrayList indexes
     */
    public ArrayList<Integer> findByProperty (ArrayList<User> users, int propertyNo, int property) {
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        for (int i = 0 ; i < users.size() ; i++) {
            switch (propertyNo) {
                case 0 : { if (users.get(i).getUserId() == property) indexes.add(i); break; }
                case 1 : { if (users.get(i).getRole() == property) indexes.add(i); break; }
            }
        }
        return indexes;
    }
    
    /**
     * Finds by string property any user
     * @param users ArrayList
     * @param propertyNo int
     * @param property String
     * @return ArrayList indexes
     */
    public ArrayList<Integer> findByProperty (ArrayList<User> users, int propertyNo, String property) {
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        for (int i = 0 ; i < users.size() ; i++) {
            switch (propertyNo) {
                case 2 : { if (users.get(i).getName().equals(property)) indexes.add(i); break; }
                case 3 : { if (users.get(i).getSurname().equals(property)) indexes.add(i); break; }
                case 4 : { if (users.get(i).getEmail().equals(property)) indexes.add(i); break; }
                case 5 : { if (users.get(i).getPassword().equals(property)) indexes.add(i); break; }
                case 6 : { if (users.get(i).getAddress().equals(property)) indexes.add(i); break; }
                case 7 : { if (users.get(i).getPhonenumber().equals(property)) indexes.add(i); break; }
            }
        }
        return indexes;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) { return true; }
        if (obj == null) { return false; }
        if (obj.getClass() == this.getClass()) {
            User other = (User) obj;
            return ((this.userId == other.userId) && (this.role == other.role) && (this.name.equals(other.name)) && (this.surname.equals(other.surname)) && (this.email.equals(other.email)) && (this.password.equals(other.password)) && (this.address.equals(other.address)) && (this.phonenumber.equals(other.phonenumber)));
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode () {
        int hash = 5;
        hash = 11 * hash + this.userId;
        hash = 11 * hash + this.role;
        hash = 11 * hash + Objects.hashCode(this.name);
        hash = 11 * hash + Objects.hashCode(this.surname);
        hash = 11 * hash + Objects.hashCode(this.email);
        hash = 11 * hash + Objects.hashCode(this.password);
        hash = 11 * hash + Objects.hashCode(this.address);
        hash = 11 * hash + Objects.hashCode(this.phonenumber);
        return hash;
    }
    
    @Override
    public String toString() {
        return  "\nUser Id     : " + userId + 
                "\nRole        : " + role + 
                "\nBranch      : " + branch.getName() + 
                "\nName        : " + name + 
                "\nSurname     : " + surname + 
                "\nEmail       : " + email + 
                "\nPassword    : " + "***" + 
                "\nAddress     : " + address + 
                "\nPhonenumber : " + phonenumber;
    }
}
