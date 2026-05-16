package models;
import java.time.LocalDate;
// Represents an internship application submitted by a student
public class Application {
    private int id;
    private Student student;// Student who applied
    private InternshipOffer offer;// Offer applied to
    private Status status;//Current status of the application
    private String rejectType;//Administrative or Company
    private String rejectReason;//Reason of rejection
    private LocalDate interviewDate;//Interview date (if admin accepted)
    private LocalDate dateApplied; // When student applie
    private LocalDate dateFinalDecision; // When company/admin made final decision
    public Application (int id , Student student , InternshipOffer offer ){
        this.id = id;
        this.student = student;
        this.offer = offer;
        this.status = status.PENDING_ADMIN; // Default status
        this.dateApplied = LocalDate.now();
    }
    //Getters & Setters


    public LocalDate getDateApplied() {
        return dateApplied;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public InternshipOffer getOffer() {
        return offer;
    }

    public void setOffer(InternshipOffer offer) {
        this.offer = offer;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getRejectType() {
        return rejectType;
    }

    public void setRejectType(String rejectType) {
        this.rejectType = rejectType;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public LocalDate getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(LocalDate interviewDate) {
        this.interviewDate = interviewDate;
    }

    public LocalDate getDateFinalDecision() {
        return dateFinalDecision;
    }

    public void setDateFinalDecision(LocalDate dateFinalDecision) {
        this.dateFinalDecision = dateFinalDecision;
    }
}