package log.utils;

import java.util.HashMap;
import java.util.Map;

public class TextModel {
    private Map<Integer, String> paths = new HashMap<>();
    private Map<Integer, TextPanel> panels = new HashMap<>();

    public TextPanel createNewText(int index, String path, String filterText) {
        paths.put(index, path);
        TextPanel panel = SwingUtils.createTextPanel(path, filterText);
        panels.put(index, panel);
        return panel;
    }

    public TextPanel getTextPanel(int index) {
        return panels.get(index);
    }

    public String getPath(int index) {
        return paths.get(index);
    }

    public void remove(int index) {
        paths.remove(index);
        panels.remove(index);
    }

    public void updateSelectedText(int i, String filterText) {
        String path = getPath(i);
        TextPanel p = getTextPanel(i);
        p.updateText(TextUtils.readFile(path, filterText), filterText);
    }

    public void updateAllText(String filterText) {
        for(int index : panels.keySet()) {
            updateSelectedText(index, filterText);
        }
    }
}
