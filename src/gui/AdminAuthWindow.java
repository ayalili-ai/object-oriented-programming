package gui;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

public class AdminAuthWindow extends JFrame {
    public AdminAuthWindow() {
        FlatLightLaf.setup();
        setTitle("Admin Login");
        setSize(420, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel center = new JPanel(new GridLayout(3, 1, 10, 10));
        JTextField emailField = new JTextField();
        emailField.setBorder(BorderFactory.createTitledBorder("Admin Email"));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        JButton loginBtn = new JButton("Login");

        center.add(emailField);
        center.add(passwordField);
        center.add(loginBtn);
        add(center, BorderLayout.CENTER);

        JButton backBtn = new JButton("← Back");
        backBtn.addActionListener(e -> { new MainWindow().setVisible(true); dispose(); });
        add(backBtn, BorderLayout.SOUTH);

        loginBtn.addActionListener(e -> { new AdminDashboard().setVisible(true); dispose(); });
    }
}
