package gui;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import logic.OfferSearchService;
import logic.ApplicationService;
import storage.FileManager;
import models.InternshipOffer;
import models.Student;
import models.Application;

public class OfferWindow extends JFrame {
    private OfferSearchService offerSearchService;
    private ApplicationService applicationService;
    private FileManager fileManager;
    private List<InternshipOffer> offers;
    private List<Application> applications;
    private Student currentStudent;

    private JTable offerTable;
    private JTextField searchField;

    public OfferWindow(OfferSearchService offerSearchService,
                       ApplicationService applicationService,
                       FileManager fileManager,
                       List<InternshipOffer> offers,
                       List<Application> applications,
                       Student currentStudent) {

        this.offerSearchService = offerSearchService;
        this.applicationService = applicationService;
        this.fileManager = fileManager;
        this.offers = offers;
        this.applications = applications;
        this.currentStudent = currentStudent;

        FlatLightLaf.setup();
        setTitle("Available Internship Offers");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== SEARCH BAR =====
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        JButton searchBtn = new JButton("Search");
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchBtn, BorderLayout.EAST);
        add(searchPanel, BorderLayout.NORTH);

        // ===== TABLE =====
        String[] columns = {"Title", "Company", "Domain", "Location", "Deadline"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        offerTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(offerTable);
        add(scrollPane, BorderLayout.CENTER);

        // ===== BUTTONS =====
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton applyBtn = new JButton("Apply");
        JButton backBtn = new JButton("← Back");
        bottom.add(applyBtn);
        bottom.add(backBtn);
        add(bottom, BorderLayout.SOUTH);

        // ===== LOAD OFFERS =====
        refreshTable(offers);

        // ===== ACTIONS =====
        searchBtn.addActionListener(e -> {
            String keyword = searchField.getText();
            List<InternshipOffer> results = offerSearchService.searchByKeyword(offers, keyword);
            refreshTable(results);
        });

        applyBtn.addActionListener(e -> {
            int selectedRow = offerTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an offer to apply.");
                return;
            }
            InternshipOffer selectedOffer = offers.get(selectedRow);
            try {
                Application app = applicationService.apply(currentStudent, selectedOffer);
                applications.add(app);
                fileManager.saveApplications(applications);
                fileManager.saveStudents(List.of(currentStudent));
                JOptionPane.showMessageDialog(this, "Application submitted successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        backBtn.addActionListener(e -> {
            // ✅ On passe l’étudiant connecté au StudentDashboard
            new StudentDashboard(currentStudent).setVisible(true);
            dispose();
        });
    }

    private void refreshTable(List<InternshipOffer> offerList) {
        DefaultTableModel model = (DefaultTableModel) offerTable.getModel();
        model.setRowCount(0);
        for (InternshipOffer offer : offerList) {
            model.addRow(new Object[]{
                    offer.getTitle(),
                    offer.getCompany().getName(),
                    offer.getDomain(),
                    offer.getLocation(),
                    offer.getDeadline()
            });
        }
    }
}
