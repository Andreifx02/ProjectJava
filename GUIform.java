package com;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import java.util.*;
import javax.swing.filechooser.*;

class TextEditor extends JFrame implements ActionListener{
    Text text = new Text();
    JTextArea textArea;
    JScrollPane scrollPane;

    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem openItem;
    JMenuItem saveItem;
    JMenuItem findText;
    JMenuItem replaceText;

    TextEditor(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Text Editor");
        this.setSize(500, 500);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450,450));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();


        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        findText = new JMenuItem("Find");
        replaceText = new JMenuItem("Replace");

        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        findText.addActionListener(this);
        replaceText.addActionListener(this);

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(findText);
        fileMenu.add(replaceText);
        menuBar.add(fileMenu);


        this.setJMenuBar(menuBar);
        this.add(scrollPane);
        this.setVisible(true);
    }

    private void UpdateTextArea() {
        textArea.setText(text.toString());
    }

    private void UpdateTextFromTextArea() {
        text.readText(new Scanner(textArea.getText()));
    }

    public String toString(Map<Integer,Integer> map){
        var it = map.entrySet().iterator();
        StringBuffer sb = new StringBuffer();
        while (it.hasNext()) {
            var pair = it.next();
            sb.append(pair.getValue());
            sb.append(" found in line " + pair.getKey() + ";\n");

        }
        return sb.toString();
    }

    private void readFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooser.setFileFilter(filter);

        int response = fileChooser.showOpenDialog(null);

        if(response == JFileChooser.APPROVE_OPTION) {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            try {
                text.readText(new Scanner(file));
                UpdateTextArea();
            } catch (IOException exception) {
                System.out.println(exception.toString());
            }
        }
    }

    private void replace() {
        String request = JOptionPane.showInputDialog(this, "What do you want to replace?");
        String replace = JOptionPane.showInputDialog(this, "Replace to:");
        UpdateTextFromTextArea();
        text.replaceText(request,replace);
        UpdateTextArea();
    }

    private void find() {
        String find = JOptionPane.showInputDialog(this, "What do you want to find?");
        UpdateTextFromTextArea();
        Map<Integer,Integer> map = text.findText(find);
        JOptionPane.showMessageDialog(this,toString(map));
    }

    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));

        int response = fileChooser.showSaveDialog(null);

        if(response == JFileChooser.APPROVE_OPTION) {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            try {
                PrintWriter fileOut = new PrintWriter(file);
                fileOut.println(textArea.getText());
                fileOut.close();
            }
            catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openItem) {
            readFile();
        } else if (e.getSource() == saveItem) {
            saveFile();
        } else if (e.getSource() == findText) {
            find();
        } else if (e.getSource() == replaceText) {
            replace();
        }
    }
}
