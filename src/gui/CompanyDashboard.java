package gui;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

public class CompanyDashboard extends JFrame {
    public CompanyDashboard() {
        FlatLightLaf.setup();
        setTitle("Company Dashboard");
        setSize(650, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel center = new JPanel(new GridLayout(4, 1, 15, 15));
        JButton createOfferBtn = new JButton("Create Internship Offer");
        JButton viewApplicantsBtn = new JButton("View Applicants");
        JButton manageOffersBtn = new JButton("Manage Offers");
        JButton logoutBtn = new JButton("Logout");

        center.add(createOfferBtn);
        center.add(viewApplicantsBtn);
        center.add(manageOffersBtn);
        center.add(logoutBtn);
        add(center, BorderLayout.CENTER);

        createOfferBtn.addActionListener(e -> new ComingSoonWindow("Offer Creation").setVisible(true));
        viewApplicantsBtn.addActionListener(e -> new ComingSoonWindow("Applicants List").setVisible(true));
        manageOffersBtn.addActionListener(e -> new ComingSoonWindow("Offer Management").setVisible(true));
        logoutBtn.addActionListener(e -> { new MainWindow().setVisible(true); dispose(); });
    }
}
