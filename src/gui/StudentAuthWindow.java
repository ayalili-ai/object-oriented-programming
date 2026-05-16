package gui;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.util.List;

import logic.RegistrationService;
import storage.FileManager;
import models.Student;
import models.User;

public class StudentAuthWindow extends JFrame {
    private RegistrationService registrationService;
    private FileManager fileManager;
    private List<User> users;       // liste globale des utilisateurs
    private List<Student> students; // liste spécifique des étudiants

    public StudentAuthWindow(RegistrationService registrationService, FileManager fileManager,
                             List<User> users, List<Student> students) {
        this.registrationService = registrationService;
        this.fileManager = fileManager;
        this.users = users;
        this.students = students;

        FlatLightLaf.setup();
        setTitle("Student Login");
        setSize(420, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ===== CENTER PANEL =====
        JPanel center = new JPanel(new GridLayout(4, 1, 10, 10));
        JTextField emailField = new JTextField();
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");

        center.add(emailField);
        center.add(passwordField);
        center.add(loginBtn);
        center.add(registerBtn);
        add(center, BorderLayout.CENTER);

        // ===== BACK BUTTON =====
        JButton backBtn = new JButton("← Back");
        backBtn.addActionListener(e -> {
            new MainWindow().setVisible(true);
            dispose();
        });
        add(backBtn, BorderLayout.SOUTH);

        // ===== LOGIN =====
        loginBtn.addActionListener(e -> {
            String email = emailField.getText();
            String pass = new String(passwordField.getPassword());

            // 🔥 Exemple d’authentification simple
            Student found = null;
            for (Student s : students) {
                if (s.getEmail().equals(email) && s.getPassword().equals(pass)) {
                    found = s;
                    break;
                }
            }

            if (found != null) {
                new StudentDashboard(found).setVisible(true); // ✅ on passe l’étudiant
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password.");
            }
        });

        // ===== REGISTER =====
        registerBtn.addActionListener(e -> {
            try {
                String email = emailField.getText();
                String pass = new String(passwordField.getPassword());

                int id = users.size() + 1;       // génère un ID numérique
                String fullName = "Full Name";   // TODO: champ input
                String phone = "0555555555";     // TODO: champ input
                int bacReg = 123;                // ✅ int
                String major = "CS";             // TODO: champ input
                int yearLevel = 3;               // ✅ int
                String universityId = "UNI001";  // TODO: champ input

                Student student = registrationService.registerStudent(
                        users,
                        id,
                        fullName,
                        email,
                        pass,
                        phone,
                        bacReg,
                        major,
                        yearLevel,
                        universityId
                );

                students.add(student);
                fileManager.saveStudents(students);
                JOptionPane.showMessageDialog(this, "Student registered successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
    }
}
