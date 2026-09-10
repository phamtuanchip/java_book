import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TextEditor extends JFrame {
    private JTextArea textArea;
    private JFileChooser fileChooser;
    private String currentFilePath;

    public TextEditor() {
        setTitle("Text Editor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create menu bar and menu items
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem saveAsMenuItem = new JMenuItem("Save As");
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Create text area and file chooser
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        fileChooser = new JFileChooser();

        // Add components to frame
        add(scrollPane);

        // Add listeners to menu items
        newMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newFile();
            }
        });

        openMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });

        saveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });

        saveAsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveFileAs();
            }
        });
    }

    private void newFile() {
        textArea.setText("");
        currentFilePath = null;
    }

    private void openFile() {
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                textArea.read(reader, null);
                reader.close();
                currentFilePath = file.getPath();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error opening file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFile() {
        if (currentFilePath == null) {
            saveFileAs();
        } else {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(currentFilePath));
                textArea.write(writer);
                writer.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFileAs() {
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                textArea.write(writer);
                writer.close();
                currentFilePath = file.getPath();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        editor.setVisible(true);
    }
}
