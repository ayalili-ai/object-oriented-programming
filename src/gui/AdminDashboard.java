package gui;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {
    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        JButton viewPendingBtn = new JButton("View Pending Applications");
        JButton acceptBtn = new JButton("Accept Application");
        JButton rejectBtn = new JButton("Reject Application");
        JButton logoutBtn = new JButton("Logout");

        panel.add(viewPendingBtn);
        panel.add(acceptBtn);
        panel.add(rejectBtn);
        panel.add(logoutBtn);
        add(panel);

        viewPendingBtn.addActionListener(e -> new ComingSoonWindow("Pending Applications").setVisible(true));
        acceptBtn.addActionListener(e -> new ComingSoonWindow("Admin Accept").setVisible(true));
        rejectBtn.addActionListener(e -> new ComingSoonWindow("Admin Reject").setVisible(true));
        logoutBtn.addActionListener(e -> { new MainWindow().setVisible(true); dispose(); });
    }
}
