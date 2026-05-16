package gui;

import com.formdev.flatlaf.FlatLightLaf;
import models.Application;
import models.Company;
import models.InternshipOffer;
import models.Student;
import storage.FileManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminDashboard extends JFrame {

    private JTable table;
    private List<Application> applications;

    public AdminDashboard() {

        FlatLightLaf.setup();

        setTitle("Admin Dashboard");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== LOAD DATA =====
        FileManager fileManager = new FileManager();

        List<Student> students = fileManager.loadStudents();
        List<Company> companies = fileManager.loadCompanies();
        List<InternshipOffer> offers = fileManager.loadOffers(companies);

        applications = fileManager.loadApplications(students, offers);

        // ===== TABLE =====
        String[] columns = {
                "Student",
                "Offer",
                "Company",
                "Status"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        table = new JTable(model);

        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== BUTTONS =====
        JPanel bottom = new JPanel(new FlowLayout());

        JButton acceptBtn = new JButton("Accept");
        JButton rejectBtn = new JButton("Reject");
        JButton logoutBtn = new JButton("Logout");

        bottom.add(acceptBtn);
        bottom.add(rejectBtn);
        bottom.add(logoutBtn);

        add(bottom, BorderLayout.SOUTH);

        // ===== ACCEPT =====
        acceptBtn.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select an application.");
                return;
            }

            Application app = applications.get(row);

            app.setStatus(models.Status.ACCEPTED_ADMIN);

            fileManager.saveApplications(applications);

            refreshTable();

            JOptionPane.showMessageDialog(this,
                    "Application accepted successfully!");
        });

        // ===== REJECT =====
        rejectBtn.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select an application.");
                return;
            }

            String reason = JOptionPane.showInputDialog(
                    this,
                    "Enter rejection reason:"
            );

            if (reason == null || reason.trim().isEmpty()) {
                return;
            }

            Application app = applications.get(row);

            app.setStatus(models.Status.REJECTED_ADMIN);
            app.setRejectType("Administrative");
            app.setRejectReason(reason);

            fileManager.saveApplications(applications);

            refreshTable();

            JOptionPane.showMessageDialog(this,
                    "Application rejected.");
        });

        // ===== LOGOUT =====
        logoutBtn.addActionListener(e -> {
            new MainWindow().setVisible(true);
            dispose();
        });
    }

    private void refreshTable() {

        DefaultTableModel model =
                (DefaultTableModel) table.getModel();

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
}