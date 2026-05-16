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
    private List<User> users;
    private List<Student> students;

    public StudentAuthWindow(RegistrationService registrationService, FileManager fileManager,
                             List<User> users, List<Student> students) {

        this.registrationService = registrationService;
        this.fileManager = fileManager;
        this.users = users;
        this.students = students;

        FlatLightLaf.setup();

        setTitle("Student Login / Register");
        setSize(520, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel center = new JPanel(new GridLayout(10, 1, 10, 10));
        center.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JTextField fullNameField = new JTextField();
        fullNameField.setBorder(BorderFactory.createTitledBorder("Full Name"));

        JTextField emailField = new JTextField();
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));

        JTextField phoneField = new JTextField();
        phoneField.setBorder(BorderFactory.createTitledBorder("Phone Number"));

        JTextField bacField = new JTextField();
        bacField.setBorder(BorderFactory.createTitledBorder("BAC Registration Number"));

        JTextField majorField = new JTextField();
        majorField.setBorder(BorderFactory.createTitledBorder("Major"));

        JTextField yearField = new JTextField();
        yearField.setBorder(BorderFactory.createTitledBorder("Year Level"));

        JTextField universityIdField = new JTextField();
        universityIdField.setBorder(BorderFactory.createTitledBorder("University ID"));

        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");

        center.add(fullNameField);
        center.add(emailField);
        center.add(passwordField);
        center.add(phoneField);
        center.add(bacField);
        center.add(majorField);
        center.add(yearField);
        center.add(universityIdField);
        center.add(loginBtn);
        center.add(registerBtn);

        add(center, BorderLayout.CENTER);

        JButton backBtn = new JButton("← Back");
        add(backBtn, BorderLayout.SOUTH);

        backBtn.addActionListener(e -> {
            new MainWindow().setVisible(true);
            dispose();
        });

        loginBtn.addActionListener(e -> {
            String email = emailField.getText().trim();
            String pass = new String(passwordField.getPassword()).trim();

            if (email.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter email and password.");
                return;
            }

            Student found = null;

            for (Student s : students) {
                if (s.getEmail().equals(email) && s.getPassword().equals(pass)) {
                    found = s;
                    break;
                }
            }

            if (found != null) {
                new StudentDashboard(found).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password.");
            }
        });

        registerBtn.addActionListener(e -> {
            try {
                String fullName = fullNameField.getText().trim();
                String email = emailField.getText().trim();
                String pass = new String(passwordField.getPassword()).trim();
                String phone = phoneField.getText().trim();
                String bacText = bacField.getText().trim();
                String major = majorField.getText().trim();
                String yearText = yearField.getText().trim();
                String universityId = universityIdField.getText().trim();

                if (fullName.isEmpty() || email.isEmpty() || pass.isEmpty()
                        || phone.isEmpty() || bacText.isEmpty()
                        || major.isEmpty() || yearText.isEmpty()
                        || universityId.isEmpty()) {

                    JOptionPane.showMessageDialog(this, "Please fill all fields.");
                    return;
                }

                int bacReg = Integer.parseInt(bacText);
                int yearLevel = Integer.parseInt(yearText);

                int id = students.size() + 1;

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

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "BAC Registration Number and Year Level must be numbers.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
    }
}