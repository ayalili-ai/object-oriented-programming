package models;
import java.time.LocalDate;
//represents an internship offer created by a company
public class InternshipOffer{
    private int id;
    private String title;
    private String description;
    private String domain;
    private String location;
    private String requiredSkills;
    private int durationWeeks;
    private double salary;
    private LocalDate deadline;
    private boolean isActive;
    private Company company; // The company that owns this offer
    public InternshipOffer(int id, String title, String description, String domain, String location,String requiredSkills, int durationWeeks, double salary, LocalDate deadline , Company company){
        this.id = id;
        this. title = title;
        this.description = description;
        this.domain = domain;
        this.location = location;
        this.requiredSkills = requiredSkills;
        this.salary = salary;
        this.deadline = deadline;
        this.company = company;
        this.isActive = true; // Offer is active by default
    }
    // Getters & Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDurationWeeks() {
        return durationWeeks;
    }

    public void setDurationWeeks(int durationWeeks) {
        this.durationWeeks = durationWeeks;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}