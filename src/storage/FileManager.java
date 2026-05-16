package storage;

import models.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {

    private static final String DATA_FOLDER = "data";
    private static final String SEP = ";";
    private static final String NULL_VALUE = "null";

    public FileManager() {
        createDataFolder();
    }

    private void createDataFolder() {
        File folder = new File(DATA_FOLDER);

        if (!folder.exists()) {
            boolean created = folder.mkdir();

            if (!created) {
                System.out.println("Could not create data folder.");
            }
        }
    }

    // ================= STUDENTS =================

    public void saveStudents(List<Student> students) {
        try (FileWriter writer = new FileWriter(DATA_FOLDER + "/students.txt", false)) {

            for (Student student : students) {
                writer.write(
                        student.getId() + SEP +
                                student.getFullName() + SEP +
                                student.getEmail() + SEP +
                                student.getPassword() + SEP +
                                student.getPhoneNumber() + SEP +
                                student.getBacRegistrationNumber() + SEP +
                                student.getMajor() + SEP +
                                student.getYearLevel() + SEP +
                                student.getUniversityId() + SEP +
                                student.isBlocked() + "\n"
                );
            }

        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }

    public List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();

        try {
            File file = new File(DATA_FOLDER + "/students.txt");

            if (!file.exists()) {
                return students;
            }

            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(SEP, -1);

                Student student = new Student(
                        Integer.parseInt(data[0]),
                        data[1],
                        data[2],
                        data[3],
                        data[4],
                        Integer.parseInt(data[5]),
                        data[6],
                        Integer.parseInt(data[7]),
                        data[8]
                );

                if (data.length > 9) {
                    student.setBlocked(Boolean.parseBoolean(data[9]));
                }

                students.add(student);
            }

            scanner.close();

        } catch (Exception e) {
            System.out.println("Error loading students: " + e.getMessage());
        }

        return students;
    }

    // ================= COMPANIES =================

    public void saveCompanies(List<Company> companies) {
        try (FileWriter writer = new FileWriter(DATA_FOLDER + "/companies.txt", false)) {

            for (Company company : companies) {
                writer.write(
                        company.getId() + SEP +
                                company.getName() + SEP +
                                company.getAddress() + SEP +
                                company.getEmail() + SEP +
                                company.getPhone() + SEP +
                                company.getIndustry() + "\n"
                );
            }

        } catch (IOException e) {
            System.out.println("Error saving companies: " + e.getMessage());
        }
    }

    public List<Company> loadCompanies() {
        List<Company> companies = new ArrayList<>();

        try {
            File file = new File(DATA_FOLDER + "/companies.txt");

            if (!file.exists()) {
                return companies;
            }

            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(SEP, -1);

                Company company = new Company(
                        Integer.parseInt(data[0]),
                        data[1],
                        data[2],
                        data[3],
                        data[4],
                        data[5]
                );

                companies.add(company);
            }

            scanner.close();

        } catch (Exception e) {
            System.out.println("Error loading companies: " + e.getMessage());
        }

        return companies;
    }

    // ================= OFFERS =================

    public void saveOffers(List<InternshipOffer> offers) {
        try (FileWriter writer = new FileWriter(DATA_FOLDER + "/offers.txt", false)) {

            for (InternshipOffer offer : offers) {
                writer.write(
                        offer.getId() + SEP +
                                offer.getTitle() + SEP +
                                offer.getDescription() + SEP +
                                offer.getDomain() + SEP +
                                offer.getLocation() + SEP +
                                offer.getRequiredSkills() + SEP +
                                offer.getDurationWeeks() + SEP +
                                offer.getSalary() + SEP +
                                offer.getDeadline() + SEP +
                                offer.isActive() + SEP +
                                offer.getCompany().getId() + "\n"
                );
            }

        } catch (IOException e) {
            System.out.println("Error saving offers: " + e.getMessage());
        }
    }

    public List<InternshipOffer> loadOffers(List<Company> companies) {
        List<InternshipOffer> offers = new ArrayList<>();

        try {
            File file = new File(DATA_FOLDER + "/offers.txt");

            if (!file.exists()) {
                return offers;
            }

            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(SEP, -1);

                int companyId = Integer.parseInt(data[10]);
                Company company = findCompanyById(companies, companyId);

                if (company != null) {
                    InternshipOffer offer = new InternshipOffer(
                            Integer.parseInt(data[0]),
                            data[1],
                            data[2],
                            data[3],
                            data[4],
                            data[5],
                            Integer.parseInt(data[6]),
                            Double.parseDouble(data[7]),
                            LocalDate.parse(data[8]),
                            company
                    );

                    offer.setActive(Boolean.parseBoolean(data[9]));
                    offers.add(offer);

                    if (company.getOffers() != null) {
                        company.getOffers().add(offer);
                    }
                }
            }

            scanner.close();

        } catch (Exception e) {
            System.out.println("Error loading offers: " + e.getMessage());
        }

        return offers;
    }

    // ================= APPLICATIONS =================

    public void saveApplications(List<Application> applications) {
        try (FileWriter writer = new FileWriter(DATA_FOLDER + "/applications.txt", false)) {

            for (Application application : applications) {
                writer.write(
                        application.getId() + SEP +
                                application.getStudent().getId() + SEP +
                                application.getOffer().getId() + SEP +
                                application.getStatus() + SEP +
                                safeString(application.getRejectType()) + SEP +
                                safeString(application.getRejectReason()) + SEP +
                                safeDate(application.getInterviewDate()) + SEP +
                                safeDate(application.getDateApplied()) + SEP +
                                safeDate(application.getDateFinalDecision()) + "\n"
                );
            }

        } catch (IOException e) {
            System.out.println("Error saving applications: " + e.getMessage());
        }
    }

    public List<Application> loadApplications(List<Student> students, List<InternshipOffer> offers) {
        List<Application> applications = new ArrayList<>();

        try {
            File file = new File(DATA_FOLDER + "/applications.txt");

            if (!file.exists()) {
                return applications;
            }

            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(SEP, -1);

                int applicationId = Integer.parseInt(data[0]);
                int studentId = Integer.parseInt(data[1]);
                int offerId = Integer.parseInt(data[2]);

                Student student = findStudentById(students, studentId);
                InternshipOffer offer = findOfferById(offers, offerId);

                if (student != null && offer != null) {
                    Application application = new Application(applicationId, student, offer);

                    application.setStatus(Status.valueOf(data[3]));
                    application.setRejectType(parseString(data[4]));
                    application.setRejectReason(parseString(data[5]));
                    application.setInterviewDate(parseDate(data[6]));

                    // dateApplied has no setter in your model, so keep constructor date for now.
                    // Best fix: add setDateApplied(LocalDate dateApplied) in Application model.
                    application.setDateFinalDecision(parseDate(data[8]));

                    applications.add(application);

                    if (student.getApplications() != null) {
                        student.getApplications().add(application);
                    }
                }
            }

            scanner.close();

        } catch (Exception e) {
            System.out.println("Error loading applications: " + e.getMessage());
        }

        return applications;
    }

    // ================= HELPERS =================

    private String safeString(String value) {
        return value == null ? NULL_VALUE : value;
    }

    private String parseString(String value) {
        return value.equals(NULL_VALUE) ? null : value;
    }

    private String safeDate(LocalDate date) {
        return date == null ? NULL_VALUE : date.toString();
    }

    private LocalDate parseDate(String value) {
        return value.equals(NULL_VALUE) ? null : LocalDate.parse(value);
    }

    private Student findStudentById(List<Student> students, int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    private Company findCompanyById(List<Company> companies, int id) {
        for (Company company : companies) {
            if (company.getId() == id) {
                return company;
            }
        }
        return null;
    }

    private InternshipOffer findOfferById(List<InternshipOffer> offers, int id) {
        for (InternshipOffer offer : offers) {
            if (offer.getId() == id) {
                return offer;
            }
        }
        return null;
    }
}