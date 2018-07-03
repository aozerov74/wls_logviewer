package log.utils;

import javax.swing.*;
import java.awt.*;

public class SearchBar extends JPanel {
    private JTextArea textArea;
    private TextModel model;

    public SearchBar(TextModel model, int width) {
        this.model = model;
        //setLayout( new BorderLayout() );
        //setBorder( BorderFactory.createEtchedBorder() );
        setBorder(SwingUtils.getPanelBorder());

        setPreferredSize(new Dimension(width, 30));
        setSize(new Dimension(width, 30));

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        textArea = new JTextArea();
        //textArea.setSize();

        JButton btn = new JButton("Search");
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.addActionListener(e-> {
            this.model.updateAllText(getSearchText());
        });

        add(textArea);
        add(btn);
    }

    public String getSearchText() {
        return textArea.getText();
    }
}
