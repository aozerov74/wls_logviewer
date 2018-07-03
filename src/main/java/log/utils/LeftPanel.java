package log.utils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LeftPanel extends JPanel {
    private JList list;
    private DefaultListModel model = new DefaultListModel();

    public LeftPanel(MainWindow main) {
        list = new JList(model);
        list.addListSelectionListener(e -> {
            String path = (String)list.getSelectedValue();
            if (!e.getValueIsAdjusting()) {
                main.printMessageInStatusBar(path);
                main.getRightPanel().createTab(path);
            }
        });

        setLayout( new BorderLayout() );
        setBorder( BorderFactory.createEtchedBorder() );

        JScrollPane scroll = new JScrollPane( list );
        add( scroll, BorderLayout.CENTER );
    }

    public void setListData(List<String> list) {
        model.clear();
        for(String str : list){
            model.addElement(str);
        }
    }
}
