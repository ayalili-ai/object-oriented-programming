import com.formdev.flatlaf.FlatLightLaf;
import gui.MainWindow;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            try {
                FlatLightLaf.setup();
                new MainWindow().setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error starting application: " + e.getMessage(),
                        "Startup Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}