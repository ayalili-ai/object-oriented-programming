import exceptions.*;
import logic.*;
import models.*;
import storage.FileManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        RegistrationService registrationService = new RegistrationService();
        OfferService offerService = new OfferService();
        ApplicationService applicationService = new ApplicationService();
        AdminService adminService = new AdminService();
        CompanyService companyService = new CompanyService();
        OfferSearchService searchService = new OfferSearchService();
        FileManager fileManager = new FileManager();

        List<User> users = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        List<Company> companies = new ArrayList<>();
        List<InternshipOffer> offers = new ArrayList<>();
        List<Application> applications = new ArrayList<>();

        System.out.println("========== APPLICATION TEST START ==========");

        try {
            Student student = registrationService.registerStudent(
                    users, 1, "Aya", "aya@mail.com", "1234", "0555111111",
                    2024001, "Cybersecurity", 2, "UNI001"
            );
            students.add(student);

            Company company = registrationService.registerCompany(
                    companies, 1, "TechCorp", "Algiers", "tech@corp.com",
                    "0555000000", "IT"
            );

            InternshipOffer offer = offerService.createOffer(
                    company,
                    "Backend Internship",
                    "Java OOP internship",
                    "Software Development",
                    "Algiers",
                    "Java, OOP",
                    8,
                    20000,
                    LocalDate.now().plusDays(15)
            );
            offers.add(offer);

            Application application = applicationService.apply(student, offer);
            applications.add(application);
            System.out.println("1. Apply: " + application.getStatus());

            adminService.adminAccept(application, LocalDate.now().plusDays(5));
            System.out.println("2. Admin accept: " + application.getStatus());
            System.out.println("Interview date: " + application.getInterviewDate());

            companyService.companyAccept(company, application);
            System.out.println("3. Company accept: " + application.getStatus());
            System.out.println("Final decision date: " + application.getDateFinalDecision());

            System.out.println("4. Search result: "
                    + searchService.searchByKeyword(offers, "backend").size());

            fileManager.saveStudents(students);
            fileManager.saveCompanies(companies);
            fileManager.saveOffers(offers);
            fileManager.saveApplications(applications);

            System.out.println("5. Data saved.");

            List<Student> loadedStudents = fileManager.loadStudents();
            List<Company> loadedCompanies = fileManager.loadCompanies();
            List<InternshipOffer> loadedOffers = fileManager.loadOffers(loadedCompanies);
            List<Application> loadedApplications =
                    fileManager.loadApplications(loadedStudents, loadedOffers);

            System.out.println("6. Data loaded.");
            System.out.println("Loaded students: " + loadedStudents.size());
            System.out.println("Loaded companies: " + loadedCompanies.size());
            System.out.println("Loaded offers: " + loadedOffers.size());
            System.out.println("Loaded applications: " + loadedApplications.size());

            Application loadedApp = loadedApplications.get(0);

            System.out.println("Loaded student: " + loadedApp.getStudent().getFullName());
            System.out.println("Loaded company: " + loadedApp.getOffer().getCompany().getName());
            System.out.println("Loaded offer: " + loadedApp.getOffer().getTitle());
            System.out.println("Loaded status: " + loadedApp.getStatus());

            System.out.println("========== APPLICATION TEST PASSED ==========");

        } catch (Exception e) {
            System.out.println("========== APPLICATION TEST FAILED ==========");
            System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}