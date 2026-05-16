package logic;

import exceptions.UnauthorizedActionException;
import models.Company;
import models.InternshipOffer;

import java.time.LocalDate;
import java.util.ArrayList;

public class OfferService {

    private static int nextOfferId = 1;

    public InternshipOffer createOffer(
            Company company,
            String title,
            String description,
            String domain,
            String location,
            String requiredSkills,
            int durationWeeks,
            double salary,
            LocalDate deadline
    ) {
        validateCompany(company);
        validateOfferData(title, description, domain, location, requiredSkills, durationWeeks, salary, deadline);

        if (company.getOffers() == null) {
            company.setOffers(new ArrayList<>());
        }

        InternshipOffer offer = new InternshipOffer(
                nextOfferId++,
                title,
                description,
                domain,
                location,
                requiredSkills,
                durationWeeks,
                salary,
                deadline,
                company
        );

        offer.setActive(true);
        company.getOffers().add(offer);

        return offer;
    }

    public void editOffer(
            Company company,
            InternshipOffer offer,
            String title,
            String description,
            String domain,
            String location,
            String requiredSkills,
            int durationWeeks,
            double salary,
            LocalDate deadline
    ) {
        validateCompany(company);
        validateOfferOwnership(company, offer);
        validateOfferData(title, description, domain, location, requiredSkills, durationWeeks, salary, deadline);

        offer.setTitle(title);
        offer.setDescription(description);
        offer.setDomain(domain);
        offer.setLocation(location);
        offer.setRequiredSkills(requiredSkills);
        offer.setDurationWeeks(durationWeeks);
        offer.setSalary(salary);
        offer.setDeadline(deadline);
    }

    public void deleteOffer(Company company, InternshipOffer offer) {
        validateCompany(company);
        validateOfferOwnership(company, offer);

        // Soft delete: keep offer for application history/references, but hide it from search/apply
        offer.setActive(false);
    }

    private void validateCompany(Company company) {
        if (company == null) {
            throw new IllegalArgumentException("Company must not be null.");
        }
    }

    private void validateOfferOwnership(Company company, InternshipOffer offer) {
        if (offer == null) {
            throw new IllegalArgumentException("Offer must not be null.");
        }

        if (offer.getCompany() == null) {
            throw new IllegalArgumentException("Offer company is missing.");
        }

        if (offer.getCompany().getId() != company.getId()) {
            throw new UnauthorizedActionException("This company is not allowed to manage this offer.");
        }
    }

    private void validateOfferData(
            String title,
            String description,
            String domain,
            String location,
            String requiredSkills,
            int durationWeeks,
            double salary,
            LocalDate deadline
    ) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title must not be empty.");
        }

        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description must not be empty.");
        }

        if (domain == null || domain.trim().isEmpty()) {
            throw new IllegalArgumentException("Domain must not be empty.");
        }

        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location must not be empty.");
        }

        if (requiredSkills == null || requiredSkills.trim().isEmpty()) {
            throw new IllegalArgumentException("Required skills must not be empty.");
        }

        if (durationWeeks <= 0) {
            throw new IllegalArgumentException("Duration must be greater than 0.");
        }

        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative.");
        }

        if (deadline == null) {
            throw new IllegalArgumentException("Deadline must not be null.");
        }
    }

    public static void setNextOfferId(int nextId) {
        nextOfferId = nextId;
    }
}