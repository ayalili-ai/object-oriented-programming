package gui;

import com.formdev.flatlaf.FlatLightLaf;
import models.Company;
import storage.FileManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CompanyAuthWindow extends JFrame {

    private final FileManager fileManager;
    private final List<Company> companies;

    public CompanyAuthWindow() {

        FlatLightLaf.setup();

        fileManager = new FileManager();
        companies = fileManager.loadCompanies();

        setTitle("Company Login / Register");
        setSize(520, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel center = new JPanel(new GridLayout(7, 1, 10, 10));
        center.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JTextField nameField = new JTextField();
        nameField.setBorder(BorderFactory.createTitledBorder("Company Name"));

        JTextField addressField = new JTextField();
        addressField.setBorder(BorderFactory.createTitledBorder("Address"));

        JTextField emailField = new JTextField();
        emailField.setBorder(BorderFactory.createTitledBorder("Company Email"));

        JTextField phoneField = new JTextField();
        phoneField.setBorder(BorderFactory.createTitledBorder("Phone"));

        JTextField industryField = new JTextField();
        industryField.setBorder(BorderFactory.createTitledBorder("Industry"));

        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register Company");

        center.add(nameField);
        center.add(addressField);
        center.add(emailField);
        center.add(phoneField);
        center.add(industryField);
        center.add(loginBtn);
        center.add(registerBtn);

        add(center, BorderLayout.CENTER);

        JButton backBtn = new JButton("← Back");
        add(backBtn, BorderLayout.SOUTH);

        // ===== BACK =====
        backBtn.addActionListener(e -> {
            new MainWindow().setVisible(true);
            dispose();
        });

        // ===== LOGIN =====
        loginBtn.addActionListener(e -> {

            String email = emailField.getText().trim();

            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter company email.");
                return;
            }

            Company found = null;

            for (Company company : companies) {
                if (company.getEmail().equals(email)) {
                    found = company;
                    break;
                }
            }

            if (found != null) {
                new CompanyDashboard().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Company not found. Please register first.");
            }
        });

        // ===== REGISTER =====
        registerBtn.addActionListener(e -> {

            try {

                String name = nameField.getText().trim();
                String address = addressField.getText().trim();
                String email = emailField.getText().trim();
                String phone = phoneField.getText().trim();
                String industry = industryField.getText().trim();

                if (name.isEmpty() || address.isEmpty()
                        || email.isEmpty() || phone.isEmpty()
                        || industry.isEmpty()) {

                    JOptionPane.showMessageDialog(this,
                            "Please fill all fields.");
                    return;
                }

                if (emailAlreadyExists(email)) {
                    JOptionPane.showMessageDialog(this,
                            "This company email already exists.");
                    return;
                }

                int id = getNextCompanyId();

                Company company = new Company(
                        id,
                        name,
                        address,
                        email,
                        phone,
                        industry
                );

                companies.add(company);

                fileManager.saveCompanies(companies);

                JOptionPane.showMessageDialog(this,
                        "Company registered successfully!");

                new CompanyDashboard().setVisible(true);

                dispose();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(this,
                        ex.getMessage());
            }
        });
    }

    private boolean emailAlreadyExists(String email) {

        for (Company company : companies) {

            if (company.getEmail().equals(email)) {
                return true;
            }
        }

        return false;
    }

    private int getNextCompanyId() {

        int max = 0;

        for (Company company : companies) {

            if (company.getId() > max) {
                max = company.getId();
            }
        }

        return max + 1;
    }
}