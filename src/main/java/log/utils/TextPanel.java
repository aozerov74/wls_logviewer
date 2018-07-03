package log.utils;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class TextPanel extends JPanel {
    private final JTextArea textArea = new JTextArea();
    private final JScrollPane scrollPane;
    private Highlighter.HighlightPainter myHighlighter = new MyHighlightPainter(Color.RED);
    public TextPanel(String text) {
        //gets text area of properties description. Dynamically changes
        // when upon selection of particular property
        //textArea.setBackground(Color.GRAY);
        textArea.setRows(4);
        updateText(text, null);

        scrollPane = new JScrollPane(textArea,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(600, 60));
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        setPreferredSize(new Dimension(50, 40));
        setVisible(true);
    }

    public void updateText(String text, String filterText) {
        textArea.setText(text);
        String[] filtered = null;
        if (filterText != null && !filterText.equals("")) {
            filtered = new String[1];
            filtered[0] = filterText;
        }
        highLight(filtered);
        textArea.setCaretPosition(0);
    }

    // Creates highlights around all occurrences of pattern in textComp
    public void highLight(String[] pattern) {
        // First remove all old highlights
        removeHighlights();

        if (pattern == null) {
            return;
        }

        try {
            Highlighter hilite = textArea.getHighlighter();
            Document doc = textArea.getDocument();
            String text = doc.getText(0, doc.getLength());
            for (int i = 0; i < pattern.length; i++) {
                int pos = 0;
                // Search for pattern
                while ((pos = text.indexOf(pattern[i], pos)) >= 0) {
                    hilite.addHighlight(pos, pos + pattern[i].length(),
                        myHighlighter);
                    pos += pattern[i].length();
                }
            }
        } catch (BadLocationException e) {}

    }

    // Removes only our private highlights
    public void removeHighlights() {

        Highlighter hilite = textArea.getHighlighter();

        Highlighter.Highlight[] hilites = hilite.getHighlights();

        for (int i = 0; i < hilites.length; i++) {

            if (hilites[i].getPainter() instanceof MyHighlightPainter) {

                hilite.removeHighlight(hilites[i]);
            }
        }
    }
}
