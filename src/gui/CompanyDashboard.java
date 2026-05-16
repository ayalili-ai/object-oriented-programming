package gui;

import com.formdev.flatlaf.FlatLightLaf;
import models.Application;
import models.Company;
import models.InternshipOffer;
import models.Status;
import storage.FileManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class CompanyDashboard extends JFrame {

    private final FileManager fileManager;

    private final List<Company> companies;
    private final List<InternshipOffer> offers;
    private final List<Application> applications;

    private JTable offerTable;
    private JTable applicantTable;

    public CompanyDashboard() {

        FlatLightLaf.setup();

        fileManager = new FileManager();

        companies = fileManager.loadCompanies();
        offers = fileManager.loadOffers(companies);

        List<models.Student> students = fileManager.loadStudents();
        applications = fileManager.loadApplications(students, offers);

        setTitle("Company Dashboard");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===================== OFFERS TABLE =====================

        String[] offerColumns = {
                "Title",
                "Domain",
                "Location",
                "Deadline",
                "Company"
        };

        DefaultTableModel offerModel = new DefaultTableModel(offerColumns, 0);
        offerTable = new JTable(offerModel);

        offerTable.setRowHeight(32);
        offerTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        offerTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        refreshOfferTable();

        // ===================== APPLICANTS TABLE =====================

        String[] applicantColumns = {
                "Student",
                "Offer",
                "Company",
                "Status"
        };

        DefaultTableModel applicantModel = new DefaultTableModel(applicantColumns, 0);
        applicantTable = new JTable(applicantModel);

        applicantTable.setRowHeight(32);
        applicantTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        applicantTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        refreshApplicantTable();

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Offers", new JScrollPane(offerTable));
        tabs.addTab("Applicants", new JScrollPane(applicantTable));

        add(tabs, BorderLayout.CENTER);

        // ===================== BUTTONS =====================

        JPanel bottom = new JPanel(new FlowLayout());

        JButton createOfferBtn = new JButton("Create Offer");
        JButton deleteOfferBtn = new JButton("Delete Offer");
        JButton acceptApplicantBtn = new JButton("Accept Student");
        JButton rejectApplicantBtn = new JButton("Reject Student");
        JButton logoutBtn = new JButton("Logout");

        bottom.add(createOfferBtn);
        bottom.add(deleteOfferBtn);
        bottom.add(acceptApplicantBtn);
        bottom.add(rejectApplicantBtn);
        bottom.add(logoutBtn);

        add(bottom, BorderLayout.SOUTH);

        // ===================== CREATE OFFER =====================

        createOfferBtn.addActionListener(e -> {
            try {
                String title = JOptionPane.showInputDialog(this, "Offer Title:");
                if (title == null || title.trim().isEmpty()) return;

                String description = JOptionPane.showInputDialog(this, "Description:");
                if (description == null || description.trim().isEmpty()) return;

                String domain = JOptionPane.showInputDialog(this, "Domain:");
                if (domain == null || domain.trim().isEmpty()) return;

                String location = JOptionPane.showInputDialog(this, "Location:");
                if (location == null || location.trim().isEmpty()) return;

                String skills = JOptionPane.showInputDialog(this, "Required Skills:");
                if (skills == null || skills.trim().isEmpty()) return;

                int duration = Integer.parseInt(
                        JOptionPane.showInputDialog(this, "Duration (weeks):")
                );

                double salary = Double.parseDouble(
                        JOptionPane.showInputDialog(this, "Salary:")
                );

                LocalDate deadline = LocalDate.parse(
                        JOptionPane.showInputDialog(this, "Deadline (YYYY-MM-DD):")
                );

                if (companies.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No company registered.");
                    return;
                }

                Company company = companies.get(0);

                int id = getNextOfferId();

                InternshipOffer offer = new InternshipOffer(
                        id,
                        title,
                        description,
                        domain,
                        location,
                        skills,
                        duration,
                        salary,
                        deadline,
                        company
                );

                offers.add(offer);
                fileManager.saveOffers(offers);

                refreshOfferTable();

                JOptionPane.showMessageDialog(this, "Offer created successfully!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
            }
        });

        // ===================== DELETE OFFER =====================

        deleteOfferBtn.addActionListener(e -> {
            int row = offerTable.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select an offer first from the Offers tab.");
                return;
            }

            InternshipOffer selectedOffer = offers.get(row);

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Delete this offer?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) return;

            offers.remove(selectedOffer);
            fileManager.saveOffers(offers);

            refreshOfferTable();

            JOptionPane.showMessageDialog(this, "Offer deleted successfully.");
        });

        // ===================== ACCEPT APPLICANT =====================

        acceptApplicantBtn.addActionListener(e -> {
            int row = applicantTable.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select an applicant from the Applicants tab.");
                return;
            }

            Application app = applications.get(row);

            if (app.getStatus() != Status.ACCEPTED_ADMIN) {
                JOptionPane.showMessageDialog(
                        this,
                        "Company can only accept applications already accepted by admin."
                );
                return;
            }

            app.setStatus(Status.ACCEPTED_COMPANY);
            app.setDateFinalDecision(LocalDate.now());

            fileManager.saveApplications(applications);
            refreshApplicantTable();

            JOptionPane.showMessageDialog(this, "Student accepted by company.");
        });

        // ===================== REJECT APPLICANT =====================

        rejectApplicantBtn.addActionListener(e -> {
            int row = applicantTable.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select an applicant from the Applicants tab.");
                return;
            }

            Application app = applications.get(row);

            if (app.getStatus() != Status.ACCEPTED_ADMIN) {
                JOptionPane.showMessageDialog(
                        this,
                        "Company can only reject applications already accepted by admin."
                );
                return;
            }

            String reason = JOptionPane.showInputDialog(this, "Enter rejection reason:");

            if (reason == null || reason.trim().isEmpty()) return;

            app.setStatus(Status.REJECTED_COMPANY);
            app.setRejectType("Company");
            app.setRejectReason(reason);
            app.setDateFinalDecision(LocalDate.now());

            fileManager.saveApplications(applications);
            refreshApplicantTable();

            JOptionPane.showMessageDialog(this, "Student rejected by company.");
        });

        // ===================== LOGOUT =====================

        logoutBtn.addActionListener(e -> {
            new MainWindow().setVisible(true);
            dispose();
        });
    }

    private void refreshOfferTable() {
        DefaultTableModel model = (DefaultTableModel) offerTable.getModel();
        model.setRowCount(0);

        for (InternshipOffer offer : offers) {
            model.addRow(new Object[]{
                    offer.getTitle(),
                    offer.getDomain(),
                    offer.getLocation(),
                    offer.getDeadline(),
                    offer.getCompany().getName()
            });
        }
    }

    private void refreshApplicantTable() {
        DefaultTableModel model = (DefaultTableModel) applicantTable.getModel();
        model.setRowCount(0);

        for (Application app : applications) {
            model.addRow(new Object[]{
                    app.getStudent().getFullName(),
                    app.getOffer().getTitle(),
                    app.getOffer().getCompany().getName(),
                    app.getStatus()
            });
        }
    }

    private int getNextOfferId() {
        int max = 0;

        for (InternshipOffer offer : offers) {
            if (offer.getId() > max) {
                max = offer.getId();
            }
        }

        return max + 1;
    }
}