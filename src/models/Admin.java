package models;
//Represnts the system administrator
//Has special permissions to pre-validate applications
public class Admin extends User {
    private String adminCode; // Secret admin code for verification
    public Admin(int id, String fullName, String email, String password, String phoneNumber, String adminCode ){
        super(id, fullName, email , password , phoneNumber);
        this.adminCode = adminCode;

    }
    //Getters & Setters

    public String getAdminCode() {
        return adminCode;
    }

    public void setAdminCode(String adminCode) {
        this.adminCode = adminCode;
    }
}