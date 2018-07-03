package log.utils;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class RightPanel extends JPanel {
    private JTabbedPane tabbedPane;
    private MainWindow main;
    private SearchBar bar;
    private int index = 0;
    private TextModel model;

    public RightPanel(MainWindow main) {
        model = new TextModel();
        //super(new GridLayout(1, 1));
        setLayout(new BorderLayout());

        this.main = main;
        setBorder(SwingUtils.getPanelBorder());

        addSearchBar(this.getWidth());

        tabbedPane = new JTabbedPane();
        tabbedPane.addChangeListener(new ChangeListener() {
            private int current = -1;
            public void stateChanged(ChangeEvent e) {
                int i = tabbedPane.getSelectedIndex();
                if (current != -1 && current != i) {
                    current = i;
                    model.updateSelectedText(i, getFilterText());
                }
            }
        });

        tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        //tabbedPane.setOpaque(false);
        add(tabbedPane);
        //tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

     private void addSearchBar(int width) {
        bar = new SearchBar(model, width);
        bar.setBorder(new BevelBorder(BevelBorder.LOWERED));
        add(bar, BorderLayout.NORTH);
    }

    public String getFilterText() {
        return bar.getSearchText();
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public void createTab(String path) {
        String fileName = TextUtils.getFileNameFromPath(path);

        String filterText = getFilterText();
        TextPanel panel = model.createNewText(index, path, filterText);

        tabbedPane.add(fileName, panel);
        tabbedPane.setTabComponentAt(index++, new ButtonTabComponent(this, path));
        tabbedPane.setSelectedIndex(index-1);
    }

    public void removeTab(ButtonTabComponent comp) {
        int i = tabbedPane.indexOfTabComponent(comp);

        model.remove(i);

        if (i != -1) {
            tabbedPane.remove(i);
            index--;
        }
    }
}
