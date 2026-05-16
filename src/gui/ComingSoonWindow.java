package gui;

import javax.swing.*;
import java.awt.*;

public class ComingSoonWindow extends JFrame {
    public ComingSoonWindow(String featureName) {
        setTitle("Coming Soon");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("🚧 " + featureName + " Coming Soon 🚧", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(label, BorderLayout.CENTER);
    }
}
