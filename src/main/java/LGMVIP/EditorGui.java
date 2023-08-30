package LGMVIP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

public class EditorGui implements ActionListener {
    static JFrame settingFrame;
    static JTextArea textArea;
    static JSpinner fontSizeSpinner;
    JFrame textEditor;
    JComboBox fontBox;

    void createGui() {
        ///creating a frame for out textEditor
        textEditor = new JFrame("untitled - textPad");
        textEditor.setLocationRelativeTo(null);
        textEditor.setSize(500, 500);
        textEditor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Making a menuBar for our textEditor
        JMenuBar menuBar = getjMenuBar();
        textEditor.setJMenuBar(menuBar);

        //creating a textarea for our frame
        textArea = new JTextArea();
        textArea.setBounds(0, 0, 500, 500);
        textArea.setLineWrap(true); //for wrapping the line
        textArea.setWrapStyleWord(true); //for wrapping the word
        textArea.setFont(new Font("Arial", Font.PLAIN, 20));
        textEditor.add(textArea);

        //adding scroll bar in the textArea
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(0, 0, 500, 500);
        textEditor.add(scrollPane);


        textEditor.setVisible(true);

    }

    // Method to create a menu bar
    private JMenuBar getjMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        // setting color for the background in menu bar

        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu setting = new JMenu("Setting");


        //adding inside file menu in the menu bar
        JMenuItem newFile = new JMenuItem("New");
        JMenuItem openFile = new JMenuItem("Open");
        JMenuItem saveFile = new JMenuItem("Save");
        JMenuItem saveAndSubmit = new JMenuItem("Save and Submit");
        JMenuItem printFile = new JMenuItem("Print");
        JMenuItem exit = new JMenuItem("Exit");

        file.add(newFile);
        file.add(openFile);
        file.add(saveFile);
        file.add(saveAndSubmit);
        file.add(printFile);
        file.add(exit);

        //adding inside edit menu in the menu bar
        JMenuItem cut = new JMenuItem("Cut");
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem paste = new JMenuItem("Paste");
        edit.add(copy);
        edit.add(cut);
        edit.add(paste);

        //adding inside setting menu in the menu bar
        JMenuItem font = new JMenuItem("Font");
        setting.add(font);

        ///dding action listener
        for (JMenuItem jMenuItem : Arrays.asList(newFile, font, openFile, saveFile, cut, copy, paste, exit, printFile, saveAndSubmit)) {
            jMenuItem.addActionListener(this);
        }
        // adding menuBar in the frame
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(setting);
        return menuBar;
    }

    private void settingFrame() {
        settingFrame = new JFrame("Setting");
        settingFrame.setLocationRelativeTo(null);
        settingFrame.setSize(300, 200);
        settingFrame.setLayout(null);
        settingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingFrame.setVisible(true);

        //adding a label for font size
        JLabel fontSizeLabel = new JLabel("Font Size: ");
        fontSizeLabel.setBounds(10, 30, 100, 25);
        settingFrame.add(fontSizeLabel);
        //adding a spinner for font size
        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setBounds(150, 30, 50, 25);
        fontSizeSpinner.setValue(20);
        fontSizeSpinner.addChangeListener(e -> textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (int) fontSizeSpinner.getValue())));
        settingFrame.add(fontSizeSpinner);

        //adding a button for font color
        JLabel fontColorLabel = new JLabel("Change Font Color: ");
        fontColorLabel.setBounds(10, 70, 200, 25);
        settingFrame.add(fontColorLabel);
        JButton colorButton = new JButton("Change Color");
        colorButton.setBounds(150, 70, 150, 25);
        colorButton.setFocusable(false);
        //adding action listener for color button
        colorButton.addActionListener(e -> {
            Color color = JColorChooser.showDialog(null, "Choose a color", Color.black);
            textArea.setForeground(color);
        });
        settingFrame.add(colorButton);

        // adding a label for font family
        JLabel fontFamilyLabel = new JLabel("Font Family: ");
        fontFamilyLabel.setBounds(10, 110, 100, 25);
        settingFrame.add(fontFamilyLabel);

        // adding a comboBox for font family
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontBox = new JComboBox(fonts);
        fontBox.setBounds(150, 110, 150, 25);
        fontBox.setSelectedItem("Arial");
        fontBox.addActionListener(e -> textArea.setFont(new Font((String) fontBox.getSelectedItem(), Font.PLAIN, textArea.getFont().getSize())));
        settingFrame.add(fontBox);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String menuPressed = e.getActionCommand();
        switch (menuPressed) {
            case "New":
                textArea.setText("");
                textEditor.setTitle("Untitled -textPad");

                break;
            case "Open":
                JFileChooser fileChooser1 = new JFileChooser();
                fileChooser1.setCurrentDirectory(new File("."));
                int response1 = fileChooser1.showOpenDialog(null);
                if (response1 == JFileChooser.APPROVE_OPTION) {
                    File file = new File(fileChooser1.getSelectedFile().getAbsolutePath());
                    try {
                        FileReader fileReader = new FileReader(file);
                        textArea.read(fileReader, null);
                        fileReader.close();
                        textEditor.setTitle(file.getName() + " -textPad");
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
                break;
            case "Save":

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("."));

                int response = fileChooser.showSaveDialog(null);
                if (response == JFileChooser.APPROVE_OPTION) {
                    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                    try {
                        FileWriter fileWriter = new FileWriter(file, false);
                        fileWriter.write(textArea.getText());
                        fileWriter.close();
                        textEditor.setTitle(file.getName() + " -textPad");
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
                break;

            case "Save and Submit":
                JFileChooser fileChooser2 = new JFileChooser();
                fileChooser2.setCurrentDirectory(new File("."));

                int response2 = fileChooser2.showSaveDialog(null);
                if (response2 == JFileChooser.APPROVE_OPTION) {
                    File file = new File(fileChooser2.getSelectedFile().getAbsolutePath());
                    try {
                        FileWriter fileWriter = new FileWriter(file, false);
                        fileWriter.write(textArea.getText());
                        fileWriter.close();
                        textEditor.setTitle(file.getName() + " -textPad");
                        // Exit the program
                        JOptionPane optionPane = new JOptionPane();
                        JOptionPane.showMessageDialog(null, "File Saved Successfully");
                        System.exit(0);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
                break;
            //when this hbutton is pressed then the selected text will be cut
            case "Cut":
                textArea.cut();
                break;
            //when this hbutton is pressed then the selected text will be copy
            case "Copy":
                textArea.copy();
                break;
            // when this button is pressed then the selected text will be paste
            case "Paste":
                //adding the paste function with exception handeling in try catch block
                textArea.paste();
                break;
            case "Font":
                settingFrame();
                break;

            case "Print":
                try {
                    textArea.print();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            case "Exit":
                System.exit(0);
                break;

        }

    }
}
