package models;

import java.time.LocalDate;

/**
 * Abstract parent class for all users in the system.
 * Contains common attributes shared by Student and Admin.
 */
public abstract class User {

    protected int id;                 // Unique ID for each user
    protected String fullName;        // Full name of the user
    protected String email;           // Login email
    protected String password;        // Login password
    protected String phoneNumber;     // Contact number
    protected LocalDate dateCreated;  // Date the account was created

    public User(int id, String fullName, String email, String password, String phoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.dateCreated = LocalDate.now(); // Automatically set creation date
    }

    // Getters & Setters (generated automatically in IntelliJ)

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
