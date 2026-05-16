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
    private List<InternshipOffer> displayedOffers;
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
        this.displayedOffers = offers;
        this.applications = applications;
        this.currentStudent = currentStudent;

        FlatLightLaf.setup();

        setTitle("Available Internship Offers");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        searchField = new JTextField();
        searchField.setBorder(BorderFactory.createTitledBorder("Search by title, domain, or keyword"));

        JButton searchBtn = new JButton("Search");
        JButton resetBtn = new JButton("Reset");

        JPanel searchButtons = new JPanel(new GridLayout(1, 2, 5, 5));
        searchButtons.add(searchBtn);
        searchButtons.add(resetBtn);

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButtons, BorderLayout.EAST);

        add(searchPanel, BorderLayout.NORTH);

        String[] columns = {"Title", "Company", "Domain", "Location", "Deadline"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        offerTable = new JTable(model);
        offerTable.setRowHeight(32);
        offerTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        offerTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        add(new JScrollPane(offerTable), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton applyBtn = new JButton("Apply");
        JButton backBtn = new JButton("← Back");

        bottom.add(applyBtn);
        bottom.add(backBtn);

        add(bottom, BorderLayout.SOUTH);

        refreshTable(offers);

        searchBtn.addActionListener(e -> {
            String keyword = searchField.getText().trim();

            if (keyword.isEmpty()) {
                refreshTable(offers);
                return;
            }

            List<InternshipOffer> results =
                    offerSearchService.searchByKeyword(offers, keyword);

            refreshTable(results);
        });

        resetBtn.addActionListener(e -> {
            searchField.setText("");
            refreshTable(offers);
        });

        applyBtn.addActionListener(e -> {
            int selectedRow = offerTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an offer to apply.");
                return;
            }

            InternshipOffer selectedOffer = displayedOffers.get(selectedRow);

            try {
                Application app = applicationService.apply(currentStudent, selectedOffer);

                applications.add(app);

                fileManager.saveApplications(applications);

                JOptionPane.showMessageDialog(this, "Application submitted successfully!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        backBtn.addActionListener(e -> {
            new StudentDashboard(currentStudent).setVisible(true);
            dispose();
        });
    }

    private void refreshTable(List<InternshipOffer> offerList) {
        displayedOffers = offerList;

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