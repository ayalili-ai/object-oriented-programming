package gui;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import logic.OfferSearchService;
import logic.ApplicationService;
import storage.FileManager;
import models.InternshipOffer;
import models.Application;
import models.Student;

public class StudentDashboard extends JFrame {
    private Student student; // ✅ l’étudiant connecté

    public StudentDashboard(Student student) {
        this.student = student;

        FlatLightLaf.setup();
        setTitle("Student Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel center = new JPanel(new GridLayout(3, 1, 15, 15));
        JButton viewOffersBtn = new JButton("View Internship Offers");
        JButton viewStatusBtn = new JButton("View Application Status");
        JButton logoutBtn = new JButton("Logout");

        center.add(viewOffersBtn);
        center.add(viewStatusBtn);
        center.add(logoutBtn);
        add(center, BorderLayout.CENTER);

        // ===== ACTIONS =====
        viewOffersBtn.addActionListener(e -> {
            OfferSearchService offerSearchService = new OfferSearchService();
            ApplicationService applicationService = new ApplicationService();
            FileManager fileManager = new FileManager();
            List<InternshipOffer> offers = new ArrayList<>();
            List<Application> applications = new ArrayList<>();

            new OfferWindow(offerSearchService, applicationService, fileManager, offers, applications, student).setVisible(true);
            dispose();
        });

        viewStatusBtn.addActionListener(e -> new ComingSoonWindow("Application Status").setVisible(true));
        logoutBtn.addActionListener(e -> { new MainWindow().setVisible(true); dispose(); });
    }
}
