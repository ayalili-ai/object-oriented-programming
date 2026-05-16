package models;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
//Represents a company that posts internship offers
public class Company {
    private int id;// Unique company ID
    private String name;//Company name
    private String address;//Physical address
    private String email;//Contact email
    private String phone;// Contact phone
    private String industry;// Sector
    private LocalDate dateRegistered;//  Date company joined the system

    public LocalDate getDateRegistered() {
        return dateRegistered;
    }

    public List<InternshipOffer> getOffers() {
        return offers;
    }

    public void setOffers(List<InternshipOffer> offers) {
        this.offers = offers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    private List<InternshipOffer> offers; // All offers created by this company
    public Company(int id, String name, String address, String email, String phone, String industry){
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.industry = industry;
        this.dateRegistered = LocalDate.now();
        this.offers = new ArrayList<>();
    }
    //Getters & Setters
}


