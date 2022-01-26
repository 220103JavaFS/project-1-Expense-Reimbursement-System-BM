package com.revature.models.users;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class User {

    //FIELDS
    protected static int userRoleID = 1;
    protected int userID;
    protected String username;
    protected byte[] password;
    protected String firstName;
    protected String lastName;
    protected String emailAddress;
    public String userType;

    //CONSTRUCTORS
    public User() {
        super();
        userType = "User";
    }
    public User(int userID, String username, String password, String firstName, String lastName, String emailAddress) {
        this.userID = userID;
        this.username = username;
        this.password = User.encryptPassword(password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        userType = "User";
    }

    //GETTER AND SETTER
    public int getUserRoleID() {
        //the userRoleID variable is static and shared with all subclasses, however, each subclass overrides the
        //getUserRoleID method to return a userRoleID that's unique for each sub-class type.

        //We return this as an Integer instead of a normal int so it can be easily converted to a string for JSON purposes
        return userRoleID;
    }
    public void setUserRoleID(int userRoleID) {
        //for whatever reason, if the generic user type gets deleted from the database then we'll need to be able to
        //change the static variable
        this.userRoleID = userRoleID;
    }
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public byte[] getPassword() {
        return password;
    }
    public void setPassword(byte[] password) {
        this.password = password;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    //EQUALS, HASHCODE AND TOSTRING
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userID == user.userID && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(emailAddress, user.emailAddress);
    }
    @Override
    public int hashCode() {
        return Objects.hash(userID, username, password, firstName, lastName, emailAddress);
    }
    @Override
    public String toString() {
        return userType + "{" +
                "userRoleID='" + getUserRoleID() + '\'' +
                ", userID='" + userID + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }

    //METHODS
    public static byte[] encryptPassword(String password) {
        //the encryption technique for all users will be the same, therefore we use a static function
        //for encryption of passwords. Encrypted passwords are saved as byte arrays in the database
        //to avoid UTF-8 vs. ASCII shortfall within the database. Each character in Java is 2 bytes
        //in length, thus the length of the return byte array will be 2 X password.length()

        byte[] encryptedBytes = new byte[password.length() * 2]; //2 bytes for every character
        for(int i = 0; i < password.length(); i++) {
            char newChar = (char)(password.charAt(i) + 25);
            encryptedBytes[2 * i] = (byte) (newChar>>8);
            encryptedBytes[2 * i + 1] = (byte) newChar;
        }
        return encryptedBytes;
    }
    public static String decryptPassword(byte[] encryptedPassword) {
        //every two bytes in the encrypted password makes up a character. Take two bytes from the
        //encrypted password at a time to get the encrypted character, then subtract 25 from this
        //character to get the decrypted character

        StringBuilder decryptedPassword = new StringBuilder();
        for(int i = 0; i < encryptedPassword.length; i += 2) {
            //Bitwise operations work a little differently between Java and C++ so the below line of code was very frustrating to work out.
            //I'm writing this long note down so that I don't forget this in the future (hopefully...). Apparently doing any kind of binary operation on a
            //byte type (i.e. left shift, right shift, binary AND, OR or XOR) will automatically cast the result into an integer. This means that
            //if there happens to be a 1 in the leading bit of what would be the resulting byte (or character) from a binary operation,
            //it's cast into an integer with a negative value because Java doesn't have unsigned integer types. To mitigate this,
            //convert a byte into an integer by doing (byte & 0xFF), this will give us an integer of the form 0x000000FF which can then be safely
            //left shifted by 8 to give us the correct character value without needing to worry about weird negative integer conversions.
            char c = (char)(((encryptedPassword[i] & 0xFF) << 8) | (encryptedPassword[i + 1] & 0xFF) - 25);
            decryptedPassword.append(c);
        }
        return decryptedPassword.toString();
    }
    /*
    Basic users have no methods associated with them other then what's needed for JSON. This class exists in the off-chance
    that a more specific user class (such as FinanceManager or Engineer) gets accidentally deleted from the database.
    In this scenario all users of that type will revert to a default user which can then later be changed back by an admin.
    The reason for this is that the usertype field in the database must be a non-null value so we can see what kind of access
    they have.
     */
}
