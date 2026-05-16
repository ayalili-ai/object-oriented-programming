package gui;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import logic.OfferSearchService;
import logic.ApplicationService;
import storage.FileManager;

import models.Company;
import models.InternshipOffer;
import models.Application;
import models.Student;

public class StudentDashboard extends JFrame {

    private Student student;

    public StudentDashboard(Student student) {
        this.student = student;

        FlatLightLaf.setup();

        setTitle("Student Dashboard");
        setSize(650, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel center = new JPanel(new GridLayout(3, 1, 15, 15));
        center.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        JButton viewOffersBtn = new JButton("View Internship Offers");
        JButton viewStatusBtn = new JButton("View Application Status");
        JButton logoutBtn = new JButton("Logout");

        center.add(viewOffersBtn);
        center.add(viewStatusBtn);
        center.add(logoutBtn);

        add(center, BorderLayout.CENTER);

        viewOffersBtn.addActionListener(e -> {
            OfferSearchService offerSearchService = new OfferSearchService();
            ApplicationService applicationService = new ApplicationService();
            FileManager fileManager = new FileManager();

            List<Company> companies = fileManager.loadCompanies();
            List<InternshipOffer> offers = fileManager.loadOffers(companies);
            List<Student> students = fileManager.loadStudents();
            List<Application> applications = fileManager.loadApplications(students, offers);

            Student loadedStudent = findStudentById(students, student.getId());

            if (loadedStudent == null) {
                JOptionPane.showMessageDialog(this, "Student not found.");
                return;
            }

            new OfferWindow(
                    offerSearchService,
                    applicationService,
                    fileManager,
                    offers,
                    applications,
                    loadedStudent
            ).setVisible(true);

            dispose();
        });

        viewStatusBtn.addActionListener(e -> {
            FileManager fileManager = new FileManager();

            List<Company> companies = fileManager.loadCompanies();
            List<InternshipOffer> offers = fileManager.loadOffers(companies);
            List<Student> students = fileManager.loadStudents();
            List<Application> applications = fileManager.loadApplications(students, offers);

            String[] columns = {
                    "Offer",
                    "Company",
                    "Status",
                    "Reason",
                    "Interview Date"
            };

            DefaultTableModel model = new DefaultTableModel(columns, 0);

            for (Application app : applications) {
                if (app.getStudent().getId() == student.getId()) {
                    model.addRow(new Object[]{
                            app.getOffer().getTitle(),
                            app.getOffer().getCompany().getName(),
                            app.getStatus(),
                            app.getRejectReason() == null ? "" : app.getRejectReason(),
                            app.getInterviewDate() == null ? "" : app.getInterviewDate()
                    });
                }
            }

            JTable statusTable = new JTable(model);
            statusTable.setRowHeight(30);
            statusTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            statusTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "No applications found.",
                        "Application Status",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            JScrollPane scrollPane = new JScrollPane(statusTable);
            scrollPane.setPreferredSize(new Dimension(750, 250));

            JOptionPane.showMessageDialog(
                    this,
                    scrollPane,
                    "Application Status",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        logoutBtn.addActionListener(e -> {
            new MainWindow().setVisible(true);
            dispose();
        });
    }

    private Student findStudentById(List<Student> students, int id) {
        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }
}