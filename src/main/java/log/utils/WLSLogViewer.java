package log.utils;

import javax.swing.*;

public class WLSLogViewer {
    private static void createAndShowGUI() {
        MainWindow imageviewer = new MainWindow();
        imageviewer.create();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UIManager.put("swing.boldMetal", Boolean.FALSE);
            createAndShowGUI();
        });
    }
}