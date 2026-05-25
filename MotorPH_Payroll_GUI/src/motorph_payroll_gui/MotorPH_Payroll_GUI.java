package motorph_payroll_gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * @author S1101 - Group 21
 * Project Reference: MPHCR01-Feature 1
 */
public class MotorPH_Payroll_GUI {
    
    // =========================================================================
    // USER PORTAL
    // =========================================================================
    
    // UI Dimensions
    private static final int WIDTH = 540;
    private static final int HEIGHT = 360;
    
    // Fonts used in the UI
    private static final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 18);

    // Frame components
    private static JFrame windowFrame;
    private static CardLayout cardLayout;
    private static JPanel mainContainer;
    private static JPanel loginPanel;
    private static JPanel loadingPanel;
    private static JPanel formPanel;

    // Login fields
    private static JTextField txtUsername;
    private static JPasswordField txtPassword;
    private static JButton btnLogin;

    // Variables for the loading bar animation
    private static int progressValue = 0;
    private static Timer loadingTimer;
    private static JLabel lblStatusText;
    private static boolean isCalculating = false;

    // Temporary variables to hold data
    private static String savedEmpId = "";
    private static String savedEmpName = "";
    private static int savedMonth = 1;

    private static void initWindow() {
        windowFrame = new JFrame("MotorPH Payroll Portal CP2 MS1 Project of S1101 - Group 21");
        windowFrame.setSize(WIDTH, HEIGHT);
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowFrame.setLocationRelativeTo(null);
        windowFrame.setResizable(false);
    }

    private static void showScreen(String screenName) {
        cardLayout.show(mainContainer, screenName);
    }

    private static void checkLoginButtonState() {
        String user = txtUsername.getText().trim();
        String pass = new String(txtPassword.getPassword());
        
        if (!user.isEmpty() && !pass.isEmpty()) {
            btnLogin.setEnabled(true);
        } else {
            btnLogin.setEnabled(false);
        }
    }

    private static void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.equals("employee") && password.equals("12345")) {
            isCalculating = false;
            animationSequence("Logging In...");
        } else {
            JOptionPane.showMessageDialog(windowFrame,
                "The Username or Password credentials you entered are incorrect.",
                "Authentication Failed",
                JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
            txtPassword.requestFocus();
        }
    }

    private static void animationSequence(String statusMessage) {
        lblStatusText.setText(statusMessage);
        showScreen("LOADING");
        progressValue = 0;

        loadingTimer = new Timer(20, e -> {
            progressValue += 2;
            loadingPanel.repaint(); 

            if (progressValue >= 100) {
                loadingTimer.stop();
                
                if (isCalculating) {
                    executeCalculations(savedEmpId, savedEmpName, savedMonth);
                }
                showScreen("MAIN_PANEL");
            }
        });
        loadingTimer.start();
    }

    // =========================================================================
    // EMPLOYEE
    // =========================================================================
    private static JTextField txtEmpId;
    private static JTextField txtEmpName;

    private static void checkFormCompletion() {
        String id = txtEmpId.getText().trim();
        String name = txtEmpName.getText().trim();
        String month = txtMonthRange.getText().trim();
        
        // Enable calculate button if fields are filled up
        boolean codeComplete = !id.isEmpty() || !name.isEmpty() || !month.isEmpty();
        btnCalculate.setEnabled(codeComplete);
    }

    private static void resetFormFields() {
        txtEmpId.setText("");
        txtEmpName.setText("");
        txtMonthRange.setText("");
        txtEmpId.requestFocus();
        checkFormCompletion();
    }

    // =========================================================================
    // ATTENDANCE RECORD
    // =========================================================================
    private static JTextField txtMonthRange;

    // =========================================================================
    // PAYROLL
    // =========================================================================
    private static JButton btnCalculate;
    private static JButton btnReset;

    private static void initUIComponents() {
        txtUsername = new JTextField(16);
        txtPassword = new JPasswordField(16);
        btnLogin = new JButton("Login");
        
        txtEmpId = new JTextField(16);
        txtEmpName = new JTextField(16);
        txtMonthRange = new JTextField(16);
        
        btnCalculate = new JButton("Calculate Payroll");
        btnReset = new JButton("Reset Fields");
        lblStatusText = new JLabel("", JLabel.CENTER);

        cardLayout = new CardLayout();
        
        mainContainer = new JPanel(cardLayout) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                Color startColor = new Color(177, 172, 245);
                Color endColor = new Color(243, 172, 187);
                
                GradientPaint gp = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // LOGIN PANEL SETUP
        loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setOpaque(false);
        loginPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 180), 2, true),
            "Portal Authentication Security", TitledBorder.LEFT, TitledBorder.TOP, FONT_TITLE, new Color(44, 62, 80)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblUser = new JLabel("Username:");
        lblUser.setFont(FONT_LABEL);
        lblUser.setForeground(new Color(44, 62, 80));
        loginPanel.add(lblUser, gbc);
        gbc.gridx = 1;
        loginPanel.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblPass = new JLabel("Password:");
        lblPass.setFont(FONT_LABEL);
        lblPass.setForeground(new Color(44, 62, 80));
        loginPanel.add(lblPass, gbc);
        gbc.gridx = 1;
        loginPanel.add(txtPassword, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        loginPanel.add(btnLogin, gbc);

        // LOADING SCREEN SETUP
        loadingPanel = new JPanel(new BorderLayout(10, 20)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int barWidth = 300;
                int barHeight = 14;
                int x = (getWidth() - barWidth) / 2;
                int y = (getHeight() / 2) + 10;

                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.fillRoundRect(x, y, barWidth, barHeight, 10, 10);

                // Dark solid progress track fill
                g2d.setColor(new Color(44, 62, 80));
                int currentProgressWidth = (int) ((progressValue / 100.0) * barWidth);
                g2d.fillRoundRect(x, y, currentProgressWidth, barHeight, 10, 10);
            }
        };
        loadingPanel.setOpaque(false);
        lblStatusText.setFont(FONT_TITLE);
        lblStatusText.setForeground(new Color(44, 62, 80));
        loadingPanel.add(lblStatusText, BorderLayout.CENTER);

        // MAIN FORM SETUP
        formPanel = new JPanel(new BorderLayout(10, 10));
        formPanel.setOpaque(false);

        JLabel lblTitle = new JLabel("Welcome to MotorPH Payroll System", JLabel.CENTER);
        lblTitle.setFont(FONT_HEADER);
        lblTitle.setForeground(new Color(44, 62, 80));
        formPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel inputGrid = new JPanel(new GridBagLayout());
        inputGrid.setOpaque(false);
        inputGrid.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 180), 2, true),
            "Employee Payroll Calculation", TitledBorder.LEFT, TitledBorder.TOP, FONT_TITLE, new Color(44, 62, 80)
        ));

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblId = new JLabel("Employee Number:");
        lblId.setFont(FONT_LABEL);
        lblId.setForeground(new Color(44, 62, 80));
        inputGrid.add(lblId, gbc);
        gbc.gridx = 1;
        inputGrid.add(txtEmpId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblName = new JLabel("Employee Name:");
        lblName.setFont(FONT_LABEL);
        lblName.setForeground(new Color(44, 62, 80));
        inputGrid.add(lblName, gbc);
        gbc.gridx = 1;
        inputGrid.add(txtEmpName, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblMonth = new JLabel("Pay Coverage (Month 1-12):");
        lblMonth.setFont(FONT_LABEL);
        lblMonth.setForeground(new Color(44, 62, 80));
        inputGrid.add(lblMonth, gbc);
        gbc.gridx = 1;
        inputGrid.add(txtMonthRange, gbc);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        buttonRow.setOpaque(false);
        buttonRow.add(btnReset);
        buttonRow.add(btnCalculate);

        formPanel.add(inputGrid, BorderLayout.CENTER);
        formPanel.add(buttonRow, BorderLayout.SOUTH);

        mainContainer.add(loginPanel, "LOGIN");
        mainContainer.add(loadingPanel, "LOADING");
        mainContainer.add(formPanel, "MAIN_PANEL");

        windowFrame.setContentPane(mainContainer);
    }

    private static void setupListeners() {
        btnLogin.addActionListener(e -> handleLogin());
        btnCalculate.addActionListener(e -> validateAndSubmitForm());
        btnReset.addActionListener(e -> resetFormFields());

        DocumentListener textWatcher = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { checkFormCompletion(); }
            @Override public void removeUpdate(DocumentEvent e) { checkFormCompletion(); }
            @Override public void changedUpdate(DocumentEvent e) { checkFormCompletion(); }
        };

        txtEmpId.getDocument().addDocumentListener(textWatcher);
        txtEmpName.getDocument().addDocumentListener(textWatcher);
        txtMonthRange.getDocument().addDocumentListener(textWatcher);
        
        DocumentListener loginWatcher = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { checkLoginButtonState(); }
            @Override public void removeUpdate(DocumentEvent e) { checkLoginButtonState(); }
            @Override public void changedUpdate(DocumentEvent e) { checkLoginButtonState(); }
        };
        txtUsername.getDocument().addDocumentListener(loginWatcher);
        txtPassword.getDocument().addDocumentListener(loginWatcher);
    }

    private static void validateAndSubmitForm() {
        String idInput = txtEmpId.getText().trim();
        String nameInput = txtEmpName.getText().trim();
        String monthInput = txtMonthRange.getText().trim();

        try {
            if (!idInput.matches("\\d+")) {
                throw new NumberFormatException("The Employee Number you entered is invalid. Please use numeric digits only (e.g., 10001).");
            }

            if (!nameInput.matches("^[a-zA-Z\\s.\\-']+$")) {
                throw new IllegalArgumentException("The Employee Name you entered is invalid. Names cannot contain numbers or special characters.");
            }

            int targetMonth = Integer.parseInt(monthInput);
            if (targetMonth < 1 || targetMonth > 12) {
                throw new IllegalArgumentException("Please enter a valid month number between 1 (January) and 12 (December).");
            }

            savedEmpId = idInput;
            savedEmpName = nameInput;
            savedMonth = targetMonth;
            
            isCalculating = true;
            animationSequence("Calculating Payroll Summary...");

        } catch (NumberFormatException nfe) {
            String errorMsg = nfe.getMessage().contains("Employee Number") ? nfe.getMessage()
                    : "The Pay Coverage field must be a number representing a month. Please enter a value from 1 to 12.";
            JOptionPane.showMessageDialog(windowFrame, errorMsg, "Input Format Error", JOptionPane.ERROR_MESSAGE);

        } catch (IllegalArgumentException iae) {
            String title = iae.getMessage().contains("Name") ? "Invalid Employee Name" : "Month Out of Range";
            int type = iae.getMessage().contains("Name") ? JOptionPane.ERROR_MESSAGE : JOptionPane.WARNING_MESSAGE;
            JOptionPane.showMessageDialog(windowFrame, iae.getMessage(), title, type);
        }
    }

    private static void executeCalculations(String empId, String fullName, int selectedMonth) {
        double hourlyRate = 535.00;
        double standardHours = 8.0;
        int workingDays = 21;

        double gross = calculateGross(standardHours, hourlyRate, workingDays);
        double sss = calculateSss(gross);
        double philHealth = calculatePhilHealth(gross);
        double pagIbig = calculatePagIbig(gross);
        
        double totalStatutory = aggregateDeductions(new double[]{sss, philHealth, pagIbig});
        double taxableIncome = gross - totalStatutory;
        double incomeTax = calculateIncomeTax(taxableIncome);
        
        double[] finalDeductionsArray = { totalStatutory, incomeTax };
        double totalDeductions = aggregateDeductions(finalDeductionsArray);
        double netPay = gross - totalDeductions;

        String receipt = formatOutputString(empId, fullName, hourlyRate, gross, 
                sss, philHealth, pagIbig, incomeTax, totalDeductions, netPay);

        JOptionPane.showMessageDialog(windowFrame, receipt, "Calculation Complete", JOptionPane.INFORMATION_MESSAGE);
    }

    private static String formatOutputString(String id, String name, double rate, double gross, 
            double sss, double ph, double ibig, double tax, double totalDeduct, double net) {
        return "===== MOTORPH PAYSLIP SUMMARY =====\n" +
               "Employee ID: " + id + "\n" +
               "Employee Name: " + name + "\n" +
               "Base Hourly Rate: ₱" + String.format("%,.2f", rate) + "\n" +
               "-----------------------------------\n" +
               "Gross Calculated Income: ₱" + String.format("%,.2f", gross) + "\n" +
               "-----------------------------------\n" +
               "SSS Contribution: ₱" + String.format("%,.2f", sss) + "\n" +
               "PhilHealth Premium: ₱" + String.format("%,.2f", ph) + "\n" +
               "Pag-IBIG Contribution: ₱" + String.format("%,.2f", ibig) + "\n" +
               "Income Tax Deducted: ₱" + String.format("%,.2f", tax) + "\n" +
               "-----------------------------------\n" +
               "Total Deductions: ₱" + String.format("%,.2f", totalDeduct) + "\n" +
               "NET PAY: ₱" + String.format("%,.2f", net);
    }

    // =========================================================================
    // DEDUCTIONS
    // =========================================================================
    
    private static double calculateGross(double hours, double rate, int days) {
        return hours * rate * days;
    }

    private static double calculateSss(double gross) {
        if (gross < 3250) return 135.0;
        if (gross >= 24750) return 1125.0;
        return 135.0 + (((int) ((gross - 3250) / 500) + 1) * 22.50);
    }

    private static double calculatePhilHealth(double gross) {
        double base = (gross <= 10000) ? 10000 : (gross >= 60000) ? 60000 : gross;
        return (base * 0.03) / 2;
    }

    private static double calculatePagIbig(double gross) {
        if (gross >= 1000 && gross <= 1500) return gross * 0.01;
        if (gross > 1500) return Math.min(gross * 0.02, 100.0);
        return 0.0;
    }

    private static double calculateIncomeTax(double taxable) {
        if (taxable >= 20833 && taxable < 33333) return (taxable - 20833) * 0.20;
        if (taxable >= 33333 && taxable < 66667) return 2500 + (taxable - 33333) * 0.25;
        if (taxable >= 66667 && taxable < 166667) return 10833 + (taxable - 66667) * 0.30;
        if (taxable >= 166667 && taxable < 666667) return 40833.33 + (taxable - 166667) * 0.32;
        if (taxable >= 666667) return 200833.33 + (taxable - 666667) * 0.35;
        return 0.0;
    }

    private static double aggregateDeductions(double[] items) {
        double sum = 0.0;
        for (int i = 0; i < items.length; i++) {
            sum += Math.abs(items[i]);
        }
        return sum;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            initWindow();
            initUIComponents();
            setupListeners();
            checkFormCompletion();
            showScreen("LOGIN");
            windowFrame.setVisible(true);
        });
    }
}