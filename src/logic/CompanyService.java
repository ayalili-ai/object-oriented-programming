package logic;

import exceptions.InvalidStatusTransitionException;
import exceptions.UnauthorizedActionException;
import models.Application;
import models.Company;
import models.Status;

import java.time.LocalDate;

public class CompanyService {

    // Company accepts the student after admin pre-acceptance
    public void companyAccept(Company company, Application application) {

        if (company == null || application == null) {
            throw new IllegalArgumentException("Company and application must not be null.");
        }

        if (application.getOffer() == null || application.getOffer().getCompany() == null) {
            throw new IllegalArgumentException("Application offer or company is missing.");
        }

        if (application.getOffer().getCompany().getId() != company.getId()) {
            throw new UnauthorizedActionException(
                    "This company is not allowed to accept this application."
            );
        }

        if (application.getStatus() != Status.ACCEPTED_ADMIN) {
            throw new InvalidStatusTransitionException(
                    "Only ACCEPTED_ADMIN applications can be accepted by company."
            );
        }

        application.setStatus(Status.ACCEPTED_COMPANY);

        // Clear old rejection information if any
        application.setRejectType(null);
        application.setRejectReason(null);

        application.setDateFinalDecision(LocalDate.now());
    }

    // Company rejects the student after admin pre-acceptance
    public void companyReject(Company company, Application application, String reason) {

        if (company == null || application == null) {
            throw new IllegalArgumentException("Company and application must not be null.");
        }

        if (reason == null || reason.trim().isEmpty()) {
            throw new IllegalArgumentException("Rejection reason must not be empty.");
        }

        if (application.getOffer() == null || application.getOffer().getCompany() == null) {
            throw new IllegalArgumentException("Application offer or company is missing.");
        }

        if (application.getOffer().getCompany().getId() != company.getId()) {
            throw new UnauthorizedActionException(
                    "This company is not allowed to reject this application."
            );
        }

        if (application.getStatus() != Status.ACCEPTED_ADMIN) {
            throw new InvalidStatusTransitionException(
                    "Only ACCEPTED_ADMIN applications can be rejected by company."
            );
        }

        application.setStatus(Status.REJECTED_COMPANY);
        application.setRejectType("Company");
        application.setRejectReason(reason);
        application.setDateFinalDecision(LocalDate.now());
    }
}