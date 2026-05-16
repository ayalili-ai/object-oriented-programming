package models;
import java.util.ArrayList;
import java.util.List;
//Represents a student in the system
//Extends User and adds student-specific attributes
public class Student extends User {
    public int getBacRegistrationNumber() {
        return bacRegistrationNumber;
    }

    public void setBacRegistrationNumber(int bacRegistrationNumber) {
        this.bacRegistrationNumber = bacRegistrationNumber;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(int yearLevel) {
        this.yearLevel = yearLevel;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    private int bacRegistrationNumber; // رقم تسجيل الباك
    private String major ;//تخصص الطالب
    private int yearLevel;//السنة الدراسية
    private String universityId;// رقم الطالب في الجامعة
    private boolean isBlocked;//هل الطالب ممنوع من التقديم
    private List<Application> applications;//قائمة الطلبات التي قدمها
    public Student(int id, String fullName, String email,String password , String phoneNumber,int bacRegistrationNumber, String major ,int yearLevel, String universityId){
        super(id, fullName, email , password , phoneNumber);
        this.bacRegistrationNumber = bacRegistrationNumber;
        this.major = major;
        this.yearLevel= yearLevel;
        this.universityId= universityId;
        this.isBlocked = false; // Default: student is not blocked
        this.applications = new ArrayList<>();


    }
    //Getters & Setters
}
