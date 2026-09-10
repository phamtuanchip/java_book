import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class InventoryManagementSystem extends JFrame implements ActionListener {

    // Declare components
    private JLabel nameLabel, quantityLabel, priceLabel;
    private JTextField nameField, quantityField, priceField;
    private JButton addButton, deleteButton;
    private JTable table;

    // Constructor
    public InventoryManagementSystem() {
        // Set window title
        setTitle("Inventory Management System");
        
        // Create components
        nameLabel = new JLabel("Product Name:");
        nameField = new JTextField(20);
        quantityLabel = new JLabel("Quantity:");
        quantityField = new JTextField(10);
        priceLabel = new JLabel("Price:");
        priceField = new JTextField(10);
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        
        // Add action listeners to buttons
        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        
        // Create table with sample data
        Object[][] data = {
            {"Product 1", 10, 20.0},
            {"Product 2", 5, 30.0},
            {"Product 3", 15, 25.0},
            {"Product 4", 20, 15.0}
        };
        String[] columnNames = {"Product Name", "Quantity", "Price"};
        table = new JTable(data, columnNames);
        
        // Set layout
        setLayout(new BorderLayout());
        
        // Create panels for components
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(quantityLabel);
        inputPanel.add(quantityField);
        inputPanel.add(priceLabel);
        inputPanel.add(priceField);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        
        // Add components to content pane
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Set window size and make visible
        setSize(500, 300);
        setVisible(true);
    }
    
    // ActionListener method
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            // Add new row to table
            Object[] row = {nameField.getText(), Integer.parseInt(quantityField.getText()), Double.parseDouble(priceField.getText())};
            ((DefaultTableModel) table.getModel()).addRow(row);
            // Clear input fields
            nameField.setText("");
            quantityField.setText("");
            priceField.setText("");
        } else if (e.getSource() == deleteButton) {
            // Delete selected row from table
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                ((DefaultTableModel) table.getModel()).removeRow(selectedRow);
            }
        }
    }
    
    // Main method
    public static void main(String[] args) {
        new InventoryManagementSystem();
    }
}
