package log.utils;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends JFrame implements ActionListener {
    private JMenuItem itemOpen;
    private JFileChooser fileChooser;
    private String pathToLogs;
    private StatusBar statusBar;
    private LeftPanel leftPanel;
    private RightPanel rightPanel;

    public MainWindow() {
        init();
    }

    //Display the window.
    public void create() {
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void printMessageInStatusBar(String messsage) {
        statusBar.setMessage(messsage);
    }

    public LeftPanel getLeftPanel() {
        return leftPanel;
    }

    public RightPanel getRightPanel() {
        return rightPanel;
    }

    private void init() {
        setTitle("WebLogic Log Viewer");
        setLayout(new BorderLayout());
        addMenuBar();
        addFileChooser();
        setAction();
        addSplitPane();
        statusBar = addStatusBar(this.getWidth());

        setSize(700, 500);
        setPreferredSize(new Dimension(700, 500));
    }

    private StatusBar addStatusBar(int width) {
        StatusBar statusPanel = new StatusBar(width);
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        add(statusPanel, BorderLayout.SOUTH);
        return statusPanel;
    }

    private void addSplitPane() {
        leftPanel = new LeftPanel(this);
        rightPanel = new RightPanel(this);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                           leftPanel, rightPanel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);

        add(splitPane, BorderLayout.CENTER);
    }

    private void addMenuBar() {
        JMenu fileMenu = new JMenu("File");

        itemOpen = new JMenuItem("Open");
        fileMenu.add(itemOpen);

        JMenuItem mntmClose = new JMenuItem("Exit");
        mntmClose.setMnemonic(KeyEvent.VK_Q);
        mntmClose.setAccelerator(KeyStroke.getKeyStroke(
             KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        mntmClose.setToolTipText("Exit application");
        mntmClose.addActionListener(event -> System.exit(0));
        fileMenu.add(mntmClose);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);

        setJMenuBar(menuBar);
    }

    private void addFileChooser() {
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
    }

    private void setAction() {
        itemOpen.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent eve) {
        int getfile;
        if (eve.getSource() == itemOpen) {
            getfile = fileChooser.showOpenDialog(rootPane);
            if (getfile == JFileChooser.APPROVE_OPTION) {
                pathToLogs = fileChooser.getSelectedFile().getPath();
                printMessageInStatusBar("Selected: " + pathToLogs);
                updateLeftPanel(pathToLogs);
            }
        }
    }

    private void updateLeftPanel(String path) {
        List<String> list = new ArrayList<>();
        try {
            Files.walk(Paths.get(path)).filter(Files::isRegularFile).filter(s->s.getFileName().toString().toLowerCase().contains(".log")).forEach(item -> list.add(item.toAbsolutePath().toString()));
            //Files.walk(Paths.get(path)).filter(Files::isRegularFile).forEach(item -> list.add(item.toAbsolutePath().toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        leftPanel.setListData(list);
    }
}
