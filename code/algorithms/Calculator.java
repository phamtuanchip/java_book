import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
    private JTextField textField;
    private JButton buttonAdd, buttonSubtract, buttonMultiply, buttonDivide, buttonClear, buttonEquals;
    private double firstNumber, secondNumber, result;
    private String operation;

    public Calculator() {
        setTitle("Calculator");
        setSize(300, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        textField = new JTextField();
        buttonAdd = new JButton("+");
        buttonSubtract = new JButton("-");
        buttonMultiply = new JButton("*");
        buttonDivide = new JButton("/");
        buttonClear = new JButton("C");
        buttonEquals = new JButton("=");

        panel.add(textField);
        panel.add(buttonAdd);
        panel.add(buttonSubtract);
        panel.add(buttonMultiply);
        panel.add(buttonDivide);
        panel.add(buttonClear);
        panel.add(buttonEquals);

        buttonAdd.addActionListener(this);
        buttonSubtract.addActionListener(this);
        buttonMultiply.addActionListener(this);
        buttonDivide.addActionListener(this);
        buttonClear.addActionListener(this);
        buttonEquals.addActionListener(this);

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonAdd) {
            firstNumber = Double.parseDouble(textField.getText());
            operation = "+";
            textField.setText("");
        } else if (e.getSource() == buttonSubtract) {
            firstNumber = Double.parseDouble(textField.getText());
            operation = "-";
            textField.setText("");
        } else if (e.getSource() == buttonMultiply) {
            firstNumber = Double.parseDouble(textField.getText());
            operation = "*";
            textField.setText("");
        } else if (e.getSource() == buttonDivide) {
            firstNumber = Double.parseDouble(textField.getText());
            operation = "/";
            textField.setText("");
        } else if (e.getSource() == buttonClear) {
            textField.setText("");
        } else if (e.getSource() == buttonEquals) {
            secondNumber = Double.parseDouble(textField.getText());
            if (operation.equals("+")) {
                result = firstNumber + secondNumber;
            } else if (operation.equals("-")) {
                result = firstNumber - secondNumber;
            } else if (operation.equals("*")) {
                result = firstNumber * secondNumber;
            } else if (operation.equals("/")) {
                result = firstNumber / secondNumber;
            }
            textField.setText(String.valueOf(result));
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
