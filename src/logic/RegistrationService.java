package logic;

import models.Admin;
import models.Company;
import models.Student;
import models.User;

import java.util.List;

public class RegistrationService {

    public Student registerStudent(
            List<User> users,
            int id,
            String fullName,
            String email,
            String password,
            String phoneNumber,
            int bacRegistrationNumber,
            String major,
            int yearLevel,
            String universityId
    ) {
        validateUserData(users, id, fullName, email, password, phoneNumber);

        if (bacRegistrationNumber <= 0) {
            throw new IllegalArgumentException("Bac registration number must be positive.");
        }

        if (major == null || major.trim().isEmpty()) {
            throw new IllegalArgumentException("Major must not be empty.");
        }

        if (yearLevel <= 0) {
            throw new IllegalArgumentException("Year level must be positive.");
        }

        if (universityId == null || universityId.trim().isEmpty()) {
            throw new IllegalArgumentException("University ID must not be empty.");
        }

        Student student = new Student(
                id,
                fullName,
                email,
                password,
                phoneNumber,
                bacRegistrationNumber,
                major,
                yearLevel,
                universityId
        );

        users.add(student);
        return student;
    }

    public Admin registerAdmin(
            List<User> users,
            int id,
            String fullName,
            String email,
            String password,
            String phoneNumber,
            String adminCode
    ) {
        validateUserData(users, id, fullName, email, password, phoneNumber);

        if (adminCode == null || adminCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Admin code must not be empty.");
        }

        Admin admin = new Admin(
                id,
                fullName,
                email,
                password,
                phoneNumber,
                adminCode
        );

        users.add(admin);
        return admin;
    }

    public Company registerCompany(
            List<Company> companies,
            int id,
            String name,
            String address,
            String email,
            String phone,
            String industry
    ) {
        if (companies == null) {
            throw new IllegalArgumentException("Companies list must not be null.");
        }

        if (id <= 0) {
            throw new IllegalArgumentException("Company ID must be positive.");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Company name must not be empty.");
        }

        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Company address must not be empty.");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Company email must not be empty.");
        }

        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Company phone must not be empty.");
        }

        if (industry == null || industry.trim().isEmpty()) {
            throw new IllegalArgumentException("Industry must not be empty.");
        }

        for (Company company : companies) {
            if (company.getId() == id) {
                throw new IllegalArgumentException("Company ID already exists.");
            }

            if (company.getEmail().equalsIgnoreCase(email)) {
                throw new IllegalArgumentException("Company email already exists.");
            }

            if (company.getPhone().equals(phone)) {
                throw new IllegalArgumentException("Company phone already exists.");
            }
        }

        Company company = new Company(
                id,
                name,
                address,
                email,
                phone,
                industry
        );

        companies.add(company);
        return company;
    }

    private void validateUserData(
            List<User> users,
            int id,
            String fullName,
            String email,
            String password,
            String phoneNumber
    ) {
        if (users == null) {
            throw new IllegalArgumentException("Users list must not be null.");
        }

        if (id <= 0) {
            throw new IllegalArgumentException("User ID must be positive.");
        }

        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name must not be empty.");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email must not be empty.");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password must not be empty.");
        }

        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number must not be empty.");
        }

        for (User user : users) {
            if (user.getId() == id) {
                throw new IllegalArgumentException("User ID already exists.");
            }

            if (user.getEmail().equalsIgnoreCase(email)) {
                throw new IllegalArgumentException("Email already exists.");
            }

            if (user.getPhoneNumber().equals(phoneNumber)) {
                throw new IllegalArgumentException("Phone number already exists.");
            }
        }
    }
}