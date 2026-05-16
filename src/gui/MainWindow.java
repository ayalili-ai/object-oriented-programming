package gui;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import logic.RegistrationService;
import storage.FileManager;
import models.User;
import models.Student;

public class MainWindow extends JFrame {

    public MainWindow() {
        FlatLightLaf.setup();
        setTitle("Internship Management System");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===================== PALETTE =====================
        Color BLUE_MAIN = new Color(0x00, 0x57, 0xD9);
        Color WHITE = Color.WHITE;

        // ===================== HEADER =====================
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BLUE_MAIN);

        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/nscs_logo.png"));
            Image logoImg = logoIcon.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
            JLabel logo = new JLabel(new ImageIcon(logoImg), SwingConstants.CENTER);
            header.add(logo, BorderLayout.CENTER);
        } catch (Exception e) {
            JLabel fallback = new JLabel("NSCS", SwingConstants.CENTER);
            fallback.setForeground(WHITE);
            fallback.setFont(new Font("Segoe UI", Font.BOLD, 28));
            header.add(fallback, BorderLayout.CENTER);
        }

        JPanel headerText = new JPanel(new GridLayout(2, 1));
        headerText.setOpaque(false);

        JLabel schoolName = new JLabel("NATIONAL SCHOOL OF CYBERSECURITY", SwingConstants.CENTER);
        schoolName.setForeground(WHITE);
        schoolName.setFont(new Font("Segoe UI", Font.BOLD, 22));
        headerText.add(schoolName);

        JLabel appName = new JLabel("Internship Management System", SwingConstants.CENTER);
        appName.setForeground(WHITE);
        appName.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        headerText.add(appName);

        header.add(headerText, BorderLayout.SOUTH);
        add(header, BorderLayout.NORTH);

        // ===================== CENTER =====================
        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(WHITE);

        try {
            ImageIcon collageIcon = new ImageIcon(getClass().getResource("/collage.png"));
            Image collageImg = collageIcon.getImage().getScaledInstance(1000, 400, Image.SCALE_SMOOTH);
            JLabel collage = new JLabel(new ImageIcon(collageImg), SwingConstants.CENTER);
            center.add(collage, BorderLayout.CENTER);
        } catch (Exception e) {
            JLabel fallback = new JLabel("Collage image missing", SwingConstants.CENTER);
            fallback.setFont(new Font("Segoe UI", Font.ITALIC, 20));
            fallback.setForeground(Color.GRAY);
            center.add(fallback, BorderLayout.CENTER);
        }

        JLabel slogan = new JLabel("Your Skills , Our Goal", SwingConstants.CENTER);
        slogan.setForeground(BLUE_MAIN);
        slogan.setFont(new Font("Segoe UI", Font.BOLD, 22));
        center.add(slogan, BorderLayout.SOUTH);

        add(center, BorderLayout.CENTER);

        // ===================== BOTTOM BUTTONS =====================
        JPanel bottom = new JPanel(new GridLayout(1, 3, 20, 0));
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));
        bottom.setBackground(WHITE);

        JButton student = new JButton("Student Space");
        JButton company = new JButton("Company Space");
        JButton admin = new JButton("Admin Space");

        Font btnFont = new Font("Segoe UI", Font.BOLD, 16);
        for (JButton b : new JButton[]{student, company, admin}) {
            b.setFont(btnFont);
            b.setBackground(BLUE_MAIN);
            b.setForeground(WHITE);
            b.setFocusPainted(false);
        }

        bottom.add(student);
        bottom.add(company);
        bottom.add(admin);
        add(bottom, BorderLayout.SOUTH);

        // ===================== ACTIONS =====================
        student.addActionListener(e -> {
            RegistrationService registrationService = new RegistrationService();
            FileManager fileManager = new FileManager();
            List<User> users = new ArrayList<>();
            List<Student> students = new ArrayList<>();

            new StudentAuthWindow(registrationService, fileManager, users, students).setVisible(true);
            dispose();
        });

        company.addActionListener(e -> {
            new CompanyAuthWindow().setVisible(true);
            dispose();
        });

        admin.addActionListener(e -> {
            new AdminAuthWindow().setVisible(true);
            dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }
}
