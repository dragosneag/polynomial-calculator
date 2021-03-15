package view;

import controller.Operation;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends AppFrame{

    private JTextField polynom1Field;
    private JTextField polynom2Field;
    private JButton derivate1Button;
    private JButton integrate1Button;
    private JButton derivate2Button;
    private JButton integrate2Button;
    private JButton sumButton;
    private JButton subtractionButton;
    private JButton multiplicationButton;
    private JButton divisionButton;
    private JButton clearButton;
    private JTextField resultField;

    public void initialize() {
        this.setTitle("Calculator");
        this.setSize(600, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);

        JPanel panel1 = new JPanel();
        panel1.setLayout(null);

        initializeCalculator(panel1,"","","");
        initializeCalculatorListeners();

        this.setContentPane(panel1);
        this.setVisible(true);
    }

    /**Design of the interface*/

    private void initializeCalculator(JPanel panel, String resultText, String poly1, String poly2){

        JLabel titleLabel = new JLabel("Polynomial Calculator");
        titleLabel.setBounds(190, 10, 300, 70);
        Font labelFont = titleLabel.getFont();
        titleLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, 20));

        JLabel polyLabel = new JLabel("Insert polynomials:");
        polyLabel.setBounds(205, 70, 200, 30);
        labelFont = polyLabel.getFont();
        polyLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, 20));

        JLabel polynom1 = new JLabel("Polynom 1");
        polynom1.setBounds(20, 140, 200, 30);

        polynom1Field = new JTextField();
        polynom1Field.setText(poly1);
        polynom1Field.setBounds(90, 140, 250, 30);

        JLabel polynom2 = new JLabel("Polynom 2");
        polynom2.setBounds(20, 180, 200, 30);

        polynom2Field = new JTextField();
        polynom2Field.setText(poly2);
        polynom2Field.setBounds(90, 180, 250, 30);

        derivate1Button = new JButton("Derivate");
        derivate1Button.setBounds(350, 140, 100, 30);

        integrate1Button = new JButton("Integrate");
        integrate1Button.setBounds(460, 140, 100, 30);

        derivate2Button = new JButton("Derivate");
        derivate2Button.setBounds(350, 180, 100, 30);

        integrate2Button = new JButton("Integrate");
        integrate2Button.setBounds(460, 180, 100, 30);

        sumButton = new JButton("SUM");
        sumButton.setBounds(20, 230, 100, 30);

        subtractionButton = new JButton("SUBTRACT");
        subtractionButton.setBounds(130, 230, 100, 30);

        multiplicationButton = new JButton("MULTIPLY");
        multiplicationButton.setBounds(240, 230, 100, 30);

        divisionButton = new JButton("DIVIDE");
        divisionButton.setBounds(350, 230, 100, 30);

        clearButton = new JButton("CLEAR");
        clearButton.setBounds(460, 230, 100, 30);

        JLabel result = new JLabel("Result: ");
        result.setBounds(70, 310, 100, 30);
        labelFont = result.getFont();
        result.setFont(new Font(labelFont.getName(), Font.PLAIN, 17));

        resultField = new JTextField(resultText);
        resultField.setBounds(150,310,250,30);

        JLabel formatWarning = new JLabel("The format of a polynomial should be:ax^n+bx^(n-1)+...+cx+d, where a,b,c,etc. are integers");
        formatWarning.setBounds(30, 410, 500, 30);
        labelFont = formatWarning.getFont();
        formatWarning.setFont(new Font(labelFont.getName(), Font.PLAIN, 13));

        panel.add(titleLabel);
        panel.add(polyLabel);
        panel.add(polynom1);
        panel.add(polynom1Field);
        panel.add(polynom2);
        panel.add(polynom2Field);
        panel.add(derivate1Button);
        panel.add(integrate1Button);
        panel.add(derivate2Button);
        panel.add(integrate2Button);
        panel.add(sumButton);
        panel.add(subtractionButton);
        panel.add(multiplicationButton);
        panel.add(divisionButton);
        panel.add(clearButton);
        panel.add(result);
        panel.add(resultField);
        panel.add(formatWarning);
    }

    /**For each button, we check if the text provided by the user is with the correct format.
     * If yes , we perform the required operation, and then check if there is any blank field
     * that should not be blank.*/

    private void initializeCalculatorListeners(){

        derivate1Button.addActionListener(e -> {

            Operation operation = null;
            try {
                operation = new Operation(polynom1Field.getText(),polynom2Field.getText());
            } catch (ValidationFailedException exception) {
                displayErrorMessage(exception);
            }

            operation.polynomialDerivative(operation.getPolynomial1());

            JPanel panel2 = new JPanel();
            panel2.setLayout(null);

            if(polynom1Field.getText().equals("")){
                ValidationFailedException exception = new ValidationFailedException("Polynom is blank");
                displayErrorMessage(exception);
                initializeCalculator(panel2,"","",operation.getPolynomial2().getThisPolynomial(0));
            }
            else{
                if(polynom2Field.getText().equals("")) {
                    initializeCalculator(panel2, operation.getResult().getThisPolynomial(0), operation.getPolynomial1().getThisPolynomial(0), "");
                }
                else {
                    initializeCalculator(panel2, operation.getResult().getThisPolynomial(0), operation.getPolynomial1().getThisPolynomial(0), operation.getPolynomial2().getThisPolynomial(0));
                }
            }
            initializeCalculatorListeners();

            this.setContentPane(panel2);
            this.setVisible(true);

        });

        integrate1Button.addActionListener(e -> {

            Operation operation = null;
            try {
                operation = new Operation(polynom1Field.getText(),polynom2Field.getText());
            } catch (ValidationFailedException exception) {
                displayErrorMessage(exception);
            }

            operation.polynomialIntegration(operation.getPolynomial1());

            JPanel panel2 = new JPanel();
            panel2.setLayout(null);

            if(polynom1Field.getText().equals("")){
                ValidationFailedException exception = new ValidationFailedException("Polynom is blank");
                displayErrorMessage(exception);
                initializeCalculator(panel2,"","",operation.getPolynomial2().getThisPolynomial(0));
            }
            else{
                if(polynom2Field.getText().equals("")) {
                    initializeCalculator(panel2, operation.getResult().getThisPolynomial(1), operation.getPolynomial1().getThisPolynomial(0), "");
                }
                else {
                    initializeCalculator(panel2, operation.getResult().getThisPolynomial(1), operation.getPolynomial1().getThisPolynomial(0), operation.getPolynomial2().getThisPolynomial(0));
                }
            }
            initializeCalculatorListeners();

            this.setContentPane(panel2);
            this.setVisible(true);
        });

        derivate2Button.addActionListener(e -> {

            Operation operation = null;
            try {
                operation = new Operation(polynom1Field.getText(),polynom2Field.getText());
            } catch (ValidationFailedException exception) {
                displayErrorMessage(exception);
            }

            operation.polynomialDerivative(operation.getPolynomial2());

            JPanel panel2 = new JPanel();
            panel2.setLayout(null);

            if(polynom1Field.getText().equals("")){
                initializeCalculator(panel2,operation.getResult().getThisPolynomial(0),"",operation.getPolynomial2().getThisPolynomial(0));
            }
            else{
                if(polynom2Field.getText().equals("")) {
                    ValidationFailedException exception = new ValidationFailedException("Polynom is blank");
                    displayErrorMessage(exception);
                    initializeCalculator(panel2, "", operation.getPolynomial1().getThisPolynomial(0), "");
                }
                else {
                    initializeCalculator(panel2, operation.getResult().getThisPolynomial(0), operation.getPolynomial1().getThisPolynomial(0), operation.getPolynomial2().getThisPolynomial(0));
                }
            }
            initializeCalculatorListeners();

            this.setContentPane(panel2);
            this.setVisible(true);

        });

        integrate2Button.addActionListener(e -> {

            Operation operation = null;
            try {
                operation = new Operation(polynom1Field.getText(),polynom2Field.getText());
            } catch (ValidationFailedException exception) {
                displayErrorMessage(exception);
            }

            operation.polynomialIntegration(operation.getPolynomial2());

            JPanel panel2 = new JPanel();
            panel2.setLayout(null);

            if(polynom1Field.getText().equals("")){
                initializeCalculator(panel2,operation.getResult().getThisPolynomial(1),"",operation.getPolynomial2().getThisPolynomial(0));
            }
            else{
                if(polynom2Field.getText().equals("")) {
                    ValidationFailedException exception = new ValidationFailedException("Polynom is blank");
                    displayErrorMessage(exception);
                    initializeCalculator(panel2, "", operation.getPolynomial1().getThisPolynomial(0), "");
                }
                else {
                    initializeCalculator(panel2, operation.getResult().getThisPolynomial(1), operation.getPolynomial1().getThisPolynomial(0), operation.getPolynomial2().getThisPolynomial(0));
                }
            }
            initializeCalculatorListeners();

            this.setContentPane(panel2);
            this.setVisible(true);
        });

        sumButton.addActionListener(e -> {

            Operation operation = null;
            try {
                operation = new Operation(polynom1Field.getText(),polynom2Field.getText());
            } catch (ValidationFailedException exception) {
                displayErrorMessage(exception);
            }

            operation.polynomialSum();

            JPanel panel2 = new JPanel();
            panel2.setLayout(null);

            if(polynom1Field.getText().length() == 0){
                ValidationFailedException exception = new ValidationFailedException("Polynom is blank");
                displayErrorMessage(exception);
                initializeCalculator(panel2,"","",operation.getPolynomial2().getThisPolynomial(0));
            }
            else{
                if(polynom2Field.getText().length() == 0) {
                    ValidationFailedException exception = new ValidationFailedException("Polynom is blank");
                    displayErrorMessage(exception);
                    initializeCalculator(panel2,"",operation.getPolynomial1().getThisPolynomial(0),"");
                }
                else {
                    initializeCalculator(panel2, operation.getResult().getThisPolynomial(0), operation.getPolynomial1().getThisPolynomial(0), operation.getPolynomial2().getThisPolynomial(0));
                }
            }

            initializeCalculatorListeners();

            this.setContentPane(panel2);
            this.setVisible(true);
        });

        subtractionButton.addActionListener(e -> {

            Operation operation = null;
            try {
                operation = new Operation(polynom1Field.getText(),polynom2Field.getText());
            } catch (ValidationFailedException exception) {
                displayErrorMessage(exception);
            }

            operation.polynomialSubtraction();

            JPanel panel2 = new JPanel();
            panel2.setLayout(null);

            if(polynom1Field.getText().equals("")){
                ValidationFailedException exception = new ValidationFailedException("Polynom is blank");
                displayErrorMessage(exception);
                initializeCalculator(panel2,"","",operation.getPolynomial2().getThisPolynomial(0));
            }
            else{
                if(polynom2Field.getText().equals("")) {
                    ValidationFailedException exception = new ValidationFailedException("Polynom is blank");
                    displayErrorMessage(exception);
                    initializeCalculator(panel2, "", operation.getPolynomial1().getThisPolynomial(0), "");
                }
                else {
                    initializeCalculator(panel2, operation.getResult().getThisPolynomial(0), operation.getPolynomial1().getThisPolynomial(0), operation.getPolynomial2().getThisPolynomial(0));
                }
            }
            initializeCalculatorListeners();

            this.setContentPane(panel2);
            this.setVisible(true);
        });

        multiplicationButton.addActionListener(e -> {

            Operation operation = null;
            try {
                operation = new Operation(polynom1Field.getText(),polynom2Field.getText());
            } catch (ValidationFailedException exception) {
                displayErrorMessage(exception);
            }

            operation.polynomialMultiplication();

            JPanel panel2 = new JPanel();
            panel2.setLayout(null);

            if(polynom1Field.getText().equals("")){
                ValidationFailedException exception = new ValidationFailedException("Polynom is blank");
                displayErrorMessage(exception);
                initializeCalculator(panel2,"","",operation.getPolynomial2().getThisPolynomial(0));
            }
            else{
                if(polynom2Field.getText().equals("")) {
                    ValidationFailedException exception = new ValidationFailedException("Polynom is blank");
                    displayErrorMessage(exception);
                    initializeCalculator(panel2,"", operation.getPolynomial1().getThisPolynomial(0), "");
                }
                else {
                    initializeCalculator(panel2, operation.getResult().getThisPolynomial(0), operation.getPolynomial1().getThisPolynomial(0), operation.getPolynomial2().getThisPolynomial(0));
                }
            }
            initializeCalculatorListeners();

            this.setContentPane(panel2);
            this.setVisible(true);
        });

        divisionButton.addActionListener(e -> {

            Operation operation = null;
            if(polynom1Field.getText().equals("0") || polynom2Field.getText().equals("0")){
                ValidationFailedException exception = new ValidationFailedException("Cannot divide by 0");
                displayErrorMessage(exception);
            }
            else {
                try {
                    operation = new Operation(polynom1Field.getText(), polynom2Field.getText());
                } catch (ValidationFailedException exception) {
                    displayErrorMessage(exception);
                }

                operation.polynomialDivision();
            }

            JPanel panel2 = new JPanel();
            panel2.setLayout(null);

            JLabel remainderOfDivison = new JLabel("Remainder:  " + operation.getRemainderOfDivision().getThisPolynomial(1));
            remainderOfDivison.setBounds(150, 350, 200, 30);

            if(polynom1Field.getText().equals("")){
                ValidationFailedException exception = new ValidationFailedException("Polynom is blank");
                displayErrorMessage(exception);
                initializeCalculator(panel2,"","",operation.getPolynomial2().getThisPolynomial(0));
            }
            else{
                if(polynom2Field.getText().equals("")) {
                    ValidationFailedException exception = new ValidationFailedException("Polynom is blank");
                    displayErrorMessage(exception);
                    initializeCalculator(panel2, "", operation.getPolynomial1().getThisPolynomial(0), "");
                }
                else {
                    initializeCalculator(panel2, operation.getResult().getThisPolynomial(1), operation.getPolynomial1().getThisPolynomial(0), operation.getPolynomial2().getThisPolynomial(0));
                    panel2.add(remainderOfDivison);
                }
            }
            initializeCalculatorListeners();

            this.setContentPane(panel2);
            this.setVisible(true);
        });

        clearButton.addActionListener(e -> {

            JPanel panel2 = new JPanel();
            panel2.setLayout(null);

            initializeCalculator(panel2,"","","");
            initializeCalculatorListeners();

            this.setContentPane(panel2);
            this.setVisible(true);
        });
    }
}