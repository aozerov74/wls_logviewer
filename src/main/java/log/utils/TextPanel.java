package log.utils;

import javax.swing.*;
import java.awt.*;

public class TextPanel extends JPanel {
    final JTextArea textArea = new JTextArea();
    public TextPanel(String text) {
        //gets text area of properties description. Dynamically changes
        // when upon selection of particular property
        //textArea.setBackground(Color.GRAY);
        textArea.setRows(4);
        updateText(text);

        final JScrollPane scrollPane = new JScrollPane(textArea,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(600, 60));
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        setPreferredSize(new Dimension(50, 40));
        setVisible(true);
    }

    public void updateText(String text) {
        textArea.setText(text);
    }
}
