import java.util.*;

/**
 * User Abstract Class
 *
 * @author Seyda Nur DEMIR
 */
public abstract class User {
    
    /**
     * User Id is unique key for each product
     */
    private int userId;
    
    /**
     * Role supplies an authority for any user
     * System has three types of roles, such as administrator, branch employee and customer
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
