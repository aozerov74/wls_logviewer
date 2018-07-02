package log.utils;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class RightPanel extends JPanel {
    private JTabbedPane tabbedPane;
    private MainWindow main;
    private SearchBar bar;
    private int index = 0;
    private Map<Integer, String> paths = new HashMap<>();
    private Map<Integer, TextPanel> panels = new HashMap<>();


    public RightPanel(MainWindow main) {
        //super(new GridLayout(1, 1));
        setLayout(new BorderLayout());

        this.main = main;
        setBorder(SwingUtils.getPanelBorder());

        addSearchBar(this.getWidth());

        tabbedPane = new JTabbedPane();
        tabbedPane.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
            int i = tabbedPane.getSelectedIndex();
            String path = paths.get(i);
            TextPanel p = panels.get(i);
            String filterText = getFilterText();
            p.updateText(readFile(path, filterText));
        }
    });
        tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        //tabbedPane.setOpaque(false);
        add(tabbedPane);
        //tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

     private void addSearchBar(int width) {
        bar = new SearchBar(width);
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
        String fileName = getFileNameFromPath(path);

        paths.put(index, path);

        TextPanel panel = createTextPanel(path);

        panels.put(index, panel);

        tabbedPane.add(fileName, panel);
        tabbedPane.setTabComponentAt(index++, new ButtonTabComponent(this, path));
        tabbedPane.setSelectedIndex(index-1);
    }

    public void removeTab(ButtonTabComponent comp) {
        int i = tabbedPane.indexOfTabComponent(comp);

        paths.remove(index);
        panels.remove(index);

        if (i != -1) {
            tabbedPane.remove(i);
            index--;
        }
    }

    private String getFileNameFromPath(String path) {
        if (path.contains(File.separator)) {
            return path.substring(path.lastIndexOf(File.separator) + 1);
        }
        return path;
    }

    private String readFile(String path, String filter) {
        StringBuffer stringBuffer = null;
		try {
			File file = new File(path);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
			    if (filter != null && !filter.trim().equals("")) {
			        if (line.contains(filter)) {
                        stringBuffer.append(line);
                        stringBuffer.append("\n");
                    }
                } else {
			        stringBuffer.append(line);
			        stringBuffer.append("\n");
                }

			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuffer.toString();
	}

    private TextPanel createTextPanel(String path) {
        String filterText = getFilterText();
        TextPanel panel = new TextPanel(readFile(path, filterText));
        return panel;
    }
}
