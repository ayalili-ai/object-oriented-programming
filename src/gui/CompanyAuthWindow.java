package gui;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

public class CompanyAuthWindow extends JFrame {
    public CompanyAuthWindow() {
        FlatLightLaf.setup();
        setTitle("Company Login");
        setSize(420, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel center = new JPanel(new GridLayout(4, 1, 10, 10));
        JTextField emailField = new JTextField();
        emailField.setBorder(BorderFactory.createTitledBorder("Company Email"));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register Company");

        center.add(emailField);
        center.add(passwordField);
        center.add(loginBtn);
        center.add(registerBtn);
        add(center, BorderLayout.CENTER);

        JButton backBtn = new JButton("← Back");
        backBtn.addActionListener(e -> { new MainWindow().setVisible(true); dispose(); });
        add(backBtn, BorderLayout.SOUTH);

        loginBtn.addActionListener(e -> { new CompanyDashboard().setVisible(true); dispose(); });
        registerBtn.addActionListener(e -> new ComingSoonWindow("Company Registration").setVisible(true));
    }
}
