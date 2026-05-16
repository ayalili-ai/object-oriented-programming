package logic;

import exceptions.InvalidStatusTransitionException;
import models.Application;
import models.Status;

import java.time.LocalDate;

public class AdminService {

    // Admin pre-accepts an application and sets the interview date
    public void adminAccept(Application application, LocalDate interviewDate) {

        if (application == null) {
            throw new IllegalArgumentException("Application must not be null.");
        }

        if (interviewDate == null) {
            throw new IllegalArgumentException("Interview date must not be null.");
        }

        if (application.getStatus() != Status.PENDING_ADMIN) {
            throw new InvalidStatusTransitionException(
                    "Only PENDING_ADMIN applications can be accepted by admin."
            );
        }

        application.setStatus(Status.ACCEPTED_ADMIN);
        application.setInterviewDate(interviewDate);

        // Admin accept is not final decision
        application.setRejectType(null);
        application.setRejectReason(null);
        application.setDateFinalDecision(null);
    }

    // Admin rejects an application with an administrative reason
    public void adminReject(Application application, String reason) {

        if (application == null) {
            throw new IllegalArgumentException("Application must not be null.");
        }

        if (reason == null || reason.trim().isEmpty()) {
            throw new IllegalArgumentException("Rejection reason must not be empty.");
        }

        if (application.getStatus() != Status.PENDING_ADMIN) {
            throw new InvalidStatusTransitionException(
                    "Only PENDING_ADMIN applications can be rejected by admin."
            );
        }

        application.setStatus(Status.REJECTED_ADMIN);
        application.setRejectType("Administrative");
        application.setRejectReason(reason);
        application.setInterviewDate(null);
        application.setDateFinalDecision(LocalDate.now());
    }
}