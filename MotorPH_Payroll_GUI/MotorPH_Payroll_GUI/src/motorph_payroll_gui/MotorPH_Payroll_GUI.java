package motorph_payroll_gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * @author S1101 - Group 21
 * Project References: MPHCR01, MPHCR02, MPHCR03, MPHCR04, MPHCR05
 */

public class MotorPH_Payroll_GUI{

    private static final int WIDTH = 1100;
    private static final int HEIGHT = 650;

    private static final Font FONT_LABEL =
            new Font("Segoe UI", Font.BOLD, 11);

    private static final Font FONT_TITLE =
            new Font("Segoe UI", Font.BOLD, 13);

    private static final Font FONT_HEADER =
            new Font("Segoe UI", Font.BOLD, 18);

    private static JFrame windowFrame;
    private static CardLayout cardLayout;
    private static JPanel mainContainer;

    private static JPanel loginPanel;
    private static JPanel loadingPanel;
    private static JPanel formPanel;

    private static JTextField txtUsername;
    private static JPasswordField txtPassword;
    private static JButton btnLogin;

    private static JTextField txtSearch;
    private static JButton btnSearch;

    private static JTextField txtEmpId;
    private static JTextField txtLastName;
    private static JTextField txtFirstName;
    private static JTextField txtRate;
    private static JTextField txtDays;
    private static JTextField txtSssNum;
    private static JTextField txtPhilHealthNum;
    private static JTextField txtTinNum;
    private static JTextField txtPagIbigNum;

    private static JButton btnAddRecord;
    private static JButton btnUpdateRecord;
    private static JButton btnDeleteRecord;
    private static JButton btnCompute;
    private static JButton btnSummary;

    private static JTable table;
    private static DefaultTableModel tableModel;

    private static JLabel lblViewId;
    private static JLabel lblViewName;
    private static JLabel lblViewGross;
    private static JLabel lblViewNet;
        //display the amount of deductions
    private static JLabel lblViewSss;
    private static JLabel lblViewPhilHealth;
    private static JLabel lblViewPagIbig;
    private static JLabel lblViewTax;

    private static JLabel lblStatusText;

    private static Timer loadingTimer;
    private static int progressValue = 0;
    private static boolean isCalculating = false;

    private static void initWindow() {

        windowFrame =
                new JFrame("MotorPH Employee Payroll System");

        windowFrame.setSize(WIDTH, HEIGHT);
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowFrame.setLocationRelativeTo(null);
        windowFrame.setResizable(false);
    }

    private static void showScreen(String name) {
        cardLayout.show(mainContainer, name);
    }

    private static void initUIComponents() {

        cardLayout = new CardLayout();

        mainContainer = new JPanel(cardLayout) {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                Graphics2D g2d =
                        (Graphics2D) g;

                GradientPaint gp =
                        new GradientPaint(
                                0,
                                0,
                                new Color(177,172,245),
                                0,
                                getHeight(),
                                new Color(243,172,187)
                        );

                g2d.setPaint(gp);
                g2d.fillRect(
                        0,
                        0,
                        getWidth(),
                        getHeight()
                );
            }
        };

        mainContainer.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        // LOGIN PANEL

        txtUsername = new JTextField(16);
        txtPassword = new JPasswordField(16);

        btnLogin = new JButton("Login");
        btnLogin.setEnabled(false);

        loginPanel =
                new JPanel(new GridBagLayout());

        loginPanel.setOpaque(false);

        loginPanel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        255,
                                        255,
                                        255,
                                        180
                                ),
                                2,
                                true
                        ),
                        "Portal Authentication Security",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        FONT_TITLE,
                        new Color(44,62,80)
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        10,
                        14,
                        10,
                        14
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;

        loginPanel.add(
                new JLabel("Username:"),
                gbc
        );

        gbc.gridx = 1;

        loginPanel.add(
                txtUsername,
                gbc
        );

        gbc.gridx = 0;
        gbc.gridy = 1;

        loginPanel.add(
                new JLabel("Password:"),
                gbc
        );

        gbc.gridx = 1;

        loginPanel.add(
                txtPassword,
                gbc
        );

        gbc.gridx = 1;
        gbc.gridy = 2;

        loginPanel.add(
                btnLogin,
                gbc
        );

        // LOADING PANEL

        lblStatusText =
                new JLabel(
                        "",
                        JLabel.CENTER
                );

        lblStatusText.setFont(FONT_TITLE);

        loadingPanel =
                new JPanel(
                        new BorderLayout()
                ) {

                    @Override
                    protected void paintComponent(
                            Graphics g
                    ) {

                        super.paintComponent(g);

                        Graphics2D g2 =
                                (Graphics2D) g;

                        int barWidth = 450;
                        int barHeight = 16;

                        int x =
                                (getWidth() - barWidth) / 2;

                        int y =
                                (getHeight() / 2) + 25;

                        g2.setColor(
                                new Color(
                                        255,
                                        255,
                                        255,
                                        100
                                )
                        );

                        g2.fillRoundRect(
                                x,
                                y,
                                barWidth,
                                barHeight,
                                10,
                                10
                        );

                        g2.setColor(
                                new Color(44,62,80)
                        );

                        int fillWidth =
                                (int)(
                                        (progressValue/100.0)
                                                * barWidth
                                );

                        g2.fillRoundRect(
                                x,
                                y,
                                fillWidth,
                                barHeight,
                                10,
                                10
                        );
                    }
                };

        loadingPanel.setOpaque(false);
        loadingPanel.add(lblStatusText);

        // MAIN PANEL

        formPanel =
                new JPanel(
                        new BorderLayout(12,12)
                );

        formPanel.setOpaque(false);

        JLabel title =
                new JLabel(
                        "MotorPH Employee Record & Payroll System",
                        JLabel.CENTER
                );

        title.setFont(FONT_HEADER);

        formPanel.add(
                title,
                BorderLayout.NORTH
        );

        JPanel leftPanel =
                new JPanel(
                        new GridBagLayout()
                );

        leftPanel.setOpaque(false);

        leftPanel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        255,
                                        255,
                                        255,
                                        150
                                ),
                                2,
                                true
                        ),
                        "Employee Management Form",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        FONT_TITLE,
                        new Color(44,62,80)
                )
        );

        GridBagConstraints c =
                new GridBagConstraints();

        c.insets =
                new Insets(4,6,4,6);

        c.fill =
                GridBagConstraints.HORIZONTAL;

        txtSearch = new JTextField(12);
        btnSearch = new JButton("Search");

        txtEmpId = new JTextField(12);
        txtLastName = new JTextField(12);
        txtFirstName = new JTextField(12);
        txtRate = new JTextField(12);
        txtDays = new JTextField(12);
        txtSssNum = new JTextField(12);
        txtPhilHealthNum = new JTextField(12);
        txtTinNum = new JTextField(12);
        txtPagIbigNum = new JTextField(12);

        btnAddRecord = new JButton("Add Record");
        btnUpdateRecord = new JButton("Update Record");
        btnDeleteRecord = new JButton("Delete Record");

        addFormRow(leftPanel,"Search ID:",txtSearch,c,0);

        c.gridx = 1;
        c.gridy = 1;
        leftPanel.add(btnSearch,c);

        addFormRow(leftPanel,"Employee No:",txtEmpId,c,2);
        addFormRow(leftPanel,"Last Name:",txtLastName,c,3);
        addFormRow(leftPanel,"First Name:",txtFirstName,c,4);
        addFormRow(leftPanel,"Rate Per Day:",txtRate,c,5);
        addFormRow(leftPanel,"Days Worked:",txtDays,c,6);
        addFormRow(leftPanel,"SSS No:",txtSssNum,c,7);
        addFormRow(leftPanel,"PhilHealth No:",txtPhilHealthNum,c,8);
        addFormRow(leftPanel,"TIN No:",txtTinNum,c,9);
        addFormRow(leftPanel,"Pag-IBIG No:",txtPagIbigNum,c,10);

        c.gridx = 1;
        c.gridy = 11;
        leftPanel.add(btnAddRecord,c);

        c.gridy = 12;
        leftPanel.add(btnUpdateRecord,c);

        c.gridy = 13;
        leftPanel.add(btnDeleteRecord,c);

        formPanel.add(
                leftPanel,
                BorderLayout.WEST
        );
                // RIGHT SIDE TABLE

        JPanel rightContainer =
                new JPanel(
                        new BorderLayout(10,10)
                );

        rightContainer.setOpaque(false);

        String[] columns = {
                "Emp ID",
                "Last Name",
                "First Name",
                "Daily Rate",
                "Days Worked",
                "SSS",
                "PhilHealth",
                "TIN",
                "Pag-IBIG",
                "Gross Pay",
                "Deductions",
                "Net Pay"
        };

        tableModel =
                new DefaultTableModel(columns,0){

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ){
                        return false;
                    }
                };

        table =
                new JTable(tableModel);

        table.setAutoResizeMode(
                JTable.AUTO_RESIZE_OFF
        );

        table.setFillsViewportHeight(true);

        JScrollPane scrollPane =
                new JScrollPane(
                        table,
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
                );

        scrollPane.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        255,
                                        255,
                                        255,
                                        130
                                ),
                                1
                        ),
                        "Employee Master Ledger Table",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        FONT_LABEL,
                        new Color(44,62,80)
                )
        );

        rightContainer.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // EMPLOYEE VIEWER PANEL

        JPanel viewerPanel =
                new JPanel(
                        new GridLayout(4,2,10,5)
                );

        viewerPanel.setOpaque(false);

        viewerPanel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        255,
                                        255,
                                        255,
                                        130
                                ),
                                1
                        ),
                        "Selected Employee Information Viewer",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        FONT_LABEL,
                        new Color(44,62,80)
                )
        );

        lblViewId =
                new JLabel("---");

        lblViewName =
                new JLabel("---");

        lblViewGross =
                new JLabel("---");

        lblViewNet =
                new JLabel("---");
                
        lblViewSss =
                new JLabel("---");

        lblViewPhilHealth =
                new JLabel("---");

        lblViewPagIbig =
                new JLabel("---");

        lblViewTax =
                new JLabel("---");

        viewerPanel.add(new JLabel("ID Number:"));
        viewerPanel.add(lblViewId);

        viewerPanel.add(new JLabel("SSS Deduction:"));
        viewerPanel.add(lblViewSss);

        viewerPanel.add(new JLabel("Full Name:"));
        viewerPanel.add(lblViewName);

        viewerPanel.add(new JLabel("PhilHealth Deduction:"));
        viewerPanel.add(lblViewPhilHealth);

        viewerPanel.add(new JLabel("Gross Pay:"));
        viewerPanel.add(lblViewGross);

        viewerPanel.add(new JLabel("Pag-IBIG Deduction:"));
        viewerPanel.add(lblViewPagIbig);

        viewerPanel.add(new JLabel("Net Pay:"));
        viewerPanel.add(lblViewNet);

        viewerPanel.add(new JLabel("Withholding Tax:"));
        viewerPanel.add(lblViewTax);

        rightContainer.add(
                viewerPanel,
                BorderLayout.SOUTH
        );

        formPanel.add(
                rightContainer,
                BorderLayout.CENTER
        );

        // BOTTOM BAR

        JPanel actionBar =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        actionBar.setOpaque(false);

        btnCompute =
                new JButton(
                        "Compute Salaries"
                );
        btnSummary =
                new JButton(
                        "Generate Summary"
                );

        actionBar.add(btnCompute);
        actionBar.add(btnSummary);

        formPanel.add(
                actionBar,
                BorderLayout.SOUTH
        );

        mainContainer.add(
                loginPanel,
                "LOGIN"
        );

        mainContainer.add(
                loadingPanel,
                "LOADING"
        );

        mainContainer.add(
                formPanel,
                "MAIN"
        );

        windowFrame.setContentPane(
                mainContainer
        );
    }

    // =========================================
    // FORM ROW HELPER
    // =========================================

    private static void addFormRow(
            JPanel panel,
            String labelText,
            JTextField field,
            GridBagConstraints gbc,
            int row
    ){

        gbc.gridx = 0;
        gbc.gridy = row;

        JLabel label =
                new JLabel(labelText);

        label.setFont(FONT_LABEL);

        panel.add(label,gbc);

        gbc.gridx = 1;

        panel.add(field,gbc);
    }

    // =========================================
    // LISTENERS
    // =========================================

    private static void setupListeners() {

        btnAddRecord.addActionListener(
                e -> addRecord()
        );

        btnUpdateRecord.addActionListener(
                e -> updateRecord()
        );

        btnDeleteRecord.addActionListener(
                e -> deleteRecord()
        );

        btnSearch.addActionListener(
                e -> searchEmployee()
        );

        btnCompute.addActionListener(e -> {

            isCalculating = true;

            animationSequence(
                    "Processing Payroll..."
            );
        });
        
        btnSummary.addActionListener(
                e -> generateSummary()
        );

        btnLogin.addActionListener(
                e -> handleLogin()
        );

        DocumentListener watcher =
                new DocumentListener() {

                    @Override
                    public void insertUpdate(
                            DocumentEvent e
                    ){
                        checkLoginButton();
                    }

                    @Override
                    public void removeUpdate(
                            DocumentEvent e
                    ){
                        checkLoginButton();
                    }

                    @Override
                    public void changedUpdate(
                            DocumentEvent e
                    ){
                        checkLoginButton();
                    }
                };

        txtUsername
                .getDocument()
                .addDocumentListener(
                        watcher
                );

        txtPassword
                .getDocument()
                .addDocumentListener(
                        watcher
                );

        table.getSelectionModel()
                .addListSelectionListener(e -> {

                    if(!e.getValueIsAdjusting()
                            &&
                            table.getSelectedRow()
                                    != -1){

                        int r = table.getSelectedRow();

                        txtEmpId.setText(
                                tableModel.getValueAt(
                                        r,
                                        0
                                ).toString()
                        );

                        txtLastName.setText(
                                tableModel.getValueAt(
                                        r,
                                        1
                                ).toString()
                        );

                        txtFirstName.setText(
                                tableModel.getValueAt(
                                        r,
                                        2
                                ).toString()
                        );

                        txtRate.setText(
                                tableModel.getValueAt(
                                        r,
                                        3
                                ).toString()
                        );

                        txtDays.setText(
                                tableModel.getValueAt(
                                        r,
                                        4
                                ).toString()
                        );

                        txtSssNum.setText(
                                tableModel.getValueAt(
                                        r,
                                        5
                                ).toString()
                        );

                        txtPhilHealthNum.setText(
                                tableModel.getValueAt(
                                        r,
                                        6
                                ).toString()
                        );

                        txtTinNum.setText(
                                tableModel.getValueAt(
                                        r,
                                        7
                                ).toString()
                        );

                        txtPagIbigNum.setText(
                                tableModel.getValueAt(
                                        r,
                                        8
                                ).toString()
                        );

                        lblViewId.setText(
                                tableModel.getValueAt(
                                        r,
                                        0
                                ).toString()
                        );

                        lblViewName.setText(
                                tableModel.getValueAt(
                                        r,
                                        2
                                )
                                        + " "
                                        +
                                        tableModel.getValueAt(
                                                r,
                                                1
                                        )
                        );

                        lblViewGross.setText(
                                tableModel.getValueAt(
                                        r,
                                        9
                                ).toString()
                        );

                        lblViewNet.setText(
                                tableModel.getValueAt(
                                        r,
                                        11
                                ).toString()
                        );
                    }
                });
    }
    // =========================================
    // LOGIN
    // =========================================

    private static void checkLoginButton() {

        String user =
                txtUsername.getText().trim();

        String pass =
                new String(
                        txtPassword.getPassword()
                );

        btnLogin.setEnabled(
                !user.isEmpty()
                        &&
                        !pass.isEmpty()
        );
    }

    private static void handleLogin() {

        String user = txtUsername.getText().trim();

        String pass =
                new String(
                        txtPassword.getPassword()
                );

        if(user.equals("payroll_staff")
                &&
                pass.equals("12345")) {

            isCalculating = false;

            animationSequence(
                    "Loading Employee Records..."
            );

        } else {

            JOptionPane.showMessageDialog(
                    windowFrame,
                    "Invalid Credentials",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================
    // LOADING ANIMATION
    // =========================================

    private static void animationSequence(
            String message
    ){

        lblStatusText.setText(message);

        showScreen("LOADING");

        progressValue = 0;

        loadingTimer =
                new Timer(
                        10,
                        e -> {

                            progressValue += 5;

                            loadingPanel.repaint();

                            if(progressValue >= 100){

                                loadingTimer.stop();

                                if(isCalculating){

                                    runPayrollProcess();

                                } else {

                                    refreshTable();
                                }

                                showScreen("MAIN");
                            }
                        }
                );

        loadingTimer.start();
    }

    // =========================================
    // SEARCH EMPLOYEE
    // =========================================
    // Searches for an employee using the
    // entered Employee ID. If a matching
    // record is found, the employee details
    // are displayed in the input fields.
    // =========================================

    private static void searchEmployee() {

        String searchId =
                txtSearch.getText().trim();

        List<String[]> list =
                EmployeeFileHandler
                        .loadAllEmployees();

        for(String[] r : list){

            if(r[0].equals(searchId)){

                txtEmpId.setText(r[0]);
                txtLastName.setText(r[1]);
                txtFirstName.setText(r[2]);
                txtRate.setText(r[3]);
                txtDays.setText(r[4]);
                txtSssNum.setText(r[5]);
                txtPhilHealthNum.setText(r[6]);
                txtTinNum.setText(r[7]);
                txtPagIbigNum.setText(r[8]);

                JOptionPane.showMessageDialog(
                        windowFrame,
                        "Employee Found!"
                );

                return;
            }
        }

        JOptionPane.showMessageDialog(
                windowFrame,
                "Employee Not Found!"
        );
    }

    // =========================================
    // ADD RECORD
    // =========================================
    // Adds a new employee record after validating
    // the entered information. The new record is
    // saved to the CSV file, and the employee
    // table is refreshed to display the changes.
    // =========================================

    private static void addRecord() {
        // Get inputs and trim extra spaces up front
        String idInput = txtEmpId.getText().trim();
        String lastNameInput = txtLastName.getText().trim();
        String firstNameInput = txtFirstName.getText().trim();
        String rateInput = txtRate.getText().trim();
        String daysInput = txtDays.getText().trim();
        String sssInput = txtSssNum.getText().trim();
        String phInput = txtPhilHealthNum.getText().trim();
        String tinInput = txtTinNum.getText().trim();
        String pagIbigInput = txtPagIbigNum.getText().trim();

        // Try block intercepts input format violations before processing or saving
        try {
            // Check foundational blank entries (Required Fields)
            if (idInput.isEmpty() || lastNameInput.isEmpty() || firstNameInput.isEmpty() ||
                    rateInput.isEmpty() || daysInput.isEmpty()) {
                throw new IllegalArgumentException("All foundational fields must be complete.");
            }

            // Validate Field (ID Number)
            if (!idInput.matches("^[0-9]+$")) {
                throw new IllegalArgumentException("Employee Number must be a valid whole number containing digits only.");
            }

            // Validate Name Fields (Only letters or spaces allowed)
            if (!lastNameInput.matches("^[a-zA-Z\\s]+$")) {
                throw new IllegalArgumentException("Last Name can only contain letters or spaces.");
            }
            if (!firstNameInput.matches("^[a-zA-Z\\s]+$")) {
                throw new IllegalArgumentException("First Name can only contain letters or spaces.");
            }

            // Validate Fields (Rate and Days Worked)
            if (!rateInput.matches("^[0-9]+(\\.[0-9]+)?$")) {
                throw new IllegalArgumentException("Rate Per Day must be a valid positive number.");
            }
            if (!daysInput.matches("^[0-9]+(\\.[0-9]+)?$")) {
                throw new IllegalArgumentException("Days Worked must be a valid positive number.");
            }

            // Validate IDs (Only digits if not blank)
            if (!sssInput.isEmpty() && !sssInput.matches("^[0-9]+$")) {
                throw new IllegalArgumentException("SSS Number must contain digits only.");
            }
            if (!phInput.isEmpty() && !phInput.matches("^[0-9]+$")) {
                throw new IllegalArgumentException("PhilHealth Number must contain digits only.");
            }
            if (!tinInput.isEmpty() && !tinInput.matches("^[0-9]+$")) {
                throw new IllegalArgumentException("TIN Number must contain digits only.");
            }
            if (!pagIbigInput.isEmpty() && !pagIbigInput.matches("^[0-9]+$")) {
                throw new IllegalArgumentException("Pag-IBIG Number must contain digits only.");
            }

        } catch (IllegalArgumentException ex) {
            // Displays whatever message was specified inside the throw blocks above
            JOptionPane.showMessageDialog(windowFrame, "Warning: " + ex.getMessage(), "Format Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Duplicate ID Checking block
        List<String[]> employees = EmployeeFileHandler.loadAllEmployees();

        for (String[] row : employees) {
            if (row[0].equals(idInput)) {
                JOptionPane.showMessageDialog(
                        windowFrame,
                        "Employee ID already exists."
                );
                return;
            }
        }

        // Apply fallback standard values if optional text blocks were skipped
        String sss = sssInput.isEmpty() ? "0" : sssInput;
        String ph = phInput.isEmpty() ? "0" : phInput;
        String tin = tinInput.isEmpty() ? "0" : tinInput;
        String pagibig = pagIbigInput.isEmpty() ? "0" : pagIbigInput;

        String[] row = {
                idInput,
                lastNameInput,
                firstNameInput,
                rateInput,
                daysInput,
                sss,
                ph,
                tin,
                pagibig,
                "0.00",
                "0.00"
        };

        EmployeeFileHandler.addNewEmployee(row);

        JOptionPane.showMessageDialog(
                windowFrame,
                "Employee Added!"
        );

        refreshTable();
        clearFields();
    }

    // =========================================
    // UPDATE RECORD
    // =========================================
    // Updates the selected employee record after
    // validating the entered information. The
    // updated data is saved to the CSV file and
    // the employee table is refreshed.
    // =========================================

    private static void updateRecord() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    windowFrame,
                    "Select a record first."
            );
            return;
        }

        String id = tableModel.getValueAt(row,0).toString();

        int confirm = JOptionPane.showConfirmDialog(
                windowFrame,
                "Update employee " + id + "?",
                "Confirm Update",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Get inputs and trim extra spaces
        String idInput = txtEmpId.getText().trim();
        String lastNameInput = txtLastName.getText().trim();
        String firstNameInput = txtFirstName.getText().trim();
        String rateInput = txtRate.getText().trim();
        String daysInput = txtDays.getText().trim();
        String sssInput = txtSssNum.getText().trim();
        String phInput = txtPhilHealthNum.getText().trim();
        String tinInput = txtTinNum.getText().trim();
        String pagIbigInput = txtPagIbigNum.getText().trim();

        // Try block intercepts input format violations
        try {
            // Check foundational blank entries
            if (idInput.isEmpty() || lastNameInput.isEmpty() || firstNameInput.isEmpty() ||
                    rateInput.isEmpty() || daysInput.isEmpty()) {
                throw new IllegalArgumentException("All foundational identification fields must be complete.");
            }

            // Validate Name Fields (Only letters or spaces allowed)
            if (!lastNameInput.matches("^[a-zA-Z\\s]+$")) {
                throw new IllegalArgumentException("Last Name can only contain letters or spaces.");
            }
            if (!firstNameInput.matches("^[a-zA-Z\\s]+$")) {
                throw new IllegalArgumentException("First Name can only contain letters or spaces.");
            }

            // Validate Field (ID Number)
            if (!idInput.matches("^[0-9]+$")) {
                throw new IllegalArgumentException("Employee Number must be a valid whole number containing digits only.");
            }

            // Validate Fields (Rate and Days Worked)
            if (!rateInput.matches("^[0-9]+(\\.[0-9]+)?$")) {
                throw new IllegalArgumentException("Rate Per Day must be a valid positive number.");
            }
            if (!daysInput.matches("^[0-9]+(\\.[0-9]+)?$")) {
                throw new IllegalArgumentException("Days Worked must be a valid positive number.");
            }

            // Validate IDs (Only digits if not blank)
            if (!sssInput.isEmpty() && !sssInput.matches("^[0-9]+$")) {
                throw new IllegalArgumentException("SSS Number must contain digits only.");
            }
            if (!phInput.isEmpty() && !phInput.matches("^[0-9]+$")) {
                throw new IllegalArgumentException("PhilHealth Number must contain digits only.");
            }
            if (!tinInput.isEmpty() && !tinInput.matches("^[0-9]+$")) {
                throw new IllegalArgumentException("TIN Number must contain digits only.");
            }
            if (!pagIbigInput.isEmpty() && !pagIbigInput.matches("^[0-9]+$")) {
                throw new IllegalArgumentException("Pag-IBIG Number must contain digits only.");
            }

        } catch (IllegalArgumentException ex) {
            // Displays whatever message was specified inside the throw block above
            JOptionPane.showMessageDialog(windowFrame, "Warning: " + ex.getMessage(), "Format Error", JOptionPane.WARNING_MESSAGE);
            return; // Stops execution to prevent broken data from saving
        }

        List<String[]> list = EmployeeFileHandler.loadAllEmployees();

        java.util.HashMap<String, String[]> map = new java.util.HashMap<>();

        for (String[] r : list) {
            map.put(r[0], r);
        }

        if (map.containsKey(id)) {
            String[] r = map.get(id);
            r[0] = idInput;
            r[1] = lastNameInput;
            r[2] = firstNameInput;
            r[3] = rateInput;
            r[4] = daysInput;
            r[5] = sssInput.isEmpty() ? "0" : sssInput;
            r[6] = phInput.isEmpty() ? "0" : phInput;
            r[7] = tinInput.isEmpty() ? "0" : tinInput;
            r[8] = pagIbigInput.isEmpty() ? "0" : pagIbigInput;
        }

        EmployeeFileHandler.rewriteFileStorage(list);

        refreshTable();

        JOptionPane.showMessageDialog(
                windowFrame,
                "Record Updated!"
        );
    }

    // =========================================
    // DELETE RECORD
    // =========================================
    // Deletes the selected employee record from
    // the CSV file after user confirmation, then
    // refreshes the employee table to reflect
    // the updated records.
    // =========================================

    private static void deleteRecord() {

        int row =
                table.getSelectedRow();

        if(row == -1){

            JOptionPane.showMessageDialog(
                    windowFrame,
                    "Select a record first."
            );

            return;
        }

        String id =
                tableModel.getValueAt(
                        row,
                        0
                ).toString();

        int confirm =
                JOptionPane.showConfirmDialog(
                        windowFrame,
                        "Delete Employee "
                                + id +
                                "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if(confirm
                !=
                JOptionPane.YES_OPTION){

            return;
        }

        List<String[]> list =
                EmployeeFileHandler
                        .loadAllEmployees();

        list.removeIf(
                r -> r[0].equals(id)
        );

        EmployeeFileHandler
                .rewriteFileStorage(list);

        refreshTable();

        JOptionPane.showMessageDialog(
                windowFrame,
                "Employee Deleted!"
        );
    }

    // =========================================
    // PAYROLL COMPUTATION
    // =========================================
    // Computes payroll for all employees, updates payroll
    // values in the CSV file, refreshes the table, and
    // displays the overall payroll computation results.
    // =========================================

    

    private static void runPayrollProcess() {

        List<String[]> list =
                EmployeeFileHandler
                        .loadAllEmployees();
        
        double totalGross = 0;
        double totalSSS = 0;
        double totalPhilHealth = 0;
        double totalPagIbig = 0;
        double totalTax = 0;
        double totalDeductions = 0;
        double totalNet = 0;

        for (int index = 0; index < list.size(); index++) {

            String[] row = list.get(index);

            try{

                double rate =
                        Double.parseDouble(
                                row[3]
                        );

                double days =
                        Double.parseDouble(
                                row[4]
                        );

                double gross =
                        SalaryComputationModule
                                .computeGrossPay(
                                        rate,
                                        days
                                );

                double sss =
                        SalaryComputationModule
                                .computeSSS(
                                        gross
                                );

                double ph =
                        SalaryComputationModule
                                .computePhilHealth(
                                        gross
                                );

                double pi =
                        SalaryComputationModule
                                .computePagIBIG(
                                        gross
                                );

                double tax =
                        SalaryComputationModule
                                .computeWithholdingTax(
                                        gross
                                );

                double deductions =
                        SalaryComputationModule
                                .computeDeductions(
                                        sss,
                                        ph,
                                        pi,
                                        tax
                                );

                double net =
                        SalaryComputationModule
                                .computeNetPay(
                                        gross,
                                        deductions
                                );
                totalGross += gross;
                totalSSS += sss;
                totalPhilHealth += ph;
                totalPagIbig += pi;
                totalTax += tax;
                totalDeductions += deductions;
                totalNet += net;
                
                if (row.length < 12) {

                    String[] expanded = new String[12];

                    for (int i = 0; i < row.length; i++) {
                        expanded[i] = row[i];
                    }

                    for (int i = row.length; i < 12; i++) {
                        expanded[i] = "0.00";
                    }

                    list.set(index, expanded);
                    row = expanded;
                }
                
                row[9] = String.format(
                                "%.2f",
                                gross
                        );

                row[10] = String.format(
                                "%.2f",
                                deductions
                        );
                row[11] = String.format(
                                "%.2f",
                                net
                        );

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        windowFrame,
                        "Invalid payroll data found for Employee ID: " + row[0]
                        + "\nPlease check the employee record.",
                        "Payroll Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }
        }

        EmployeeFileHandler.rewriteFileStorage(list);

        refreshTable();

        JOptionPane.showMessageDialog(
                windowFrame,
                "Payroll computation completed successfully.\n\n"
                + "Employees Processed : "
                + list.size()
                + "\n--------------------------------\n"
                + "TOTAL GROSS PAY : ₱"
                + String.format("%,.2f", totalGross)
                + "\n--------------------------------\n"
                + "DEDUCTION BREAKDOWN\n"
                + "\nSSS Contribution : ₱"
                + String.format("%,.2f", totalSSS)
                + "\nPhilHealth Contribution : ₱"
                + String.format("%,.2f", totalPhilHealth)
                + "\nPag-IBIG Contribution : ₱"
                + String.format("%,.2f", totalPagIbig)
                + "\nWithholding Tax : ₱"
                + String.format("%,.2f", totalTax)
                + "\n--------------------------------"
                + "\nTOTAL DEDUCTIONS : ₱"
                + String.format("%,.2f", totalDeductions)
                + "\n--------------------------------\n"
                + "TOTAL NET PAY : ₱"
                + String.format("%,.2f", totalNet)
                + "\n--------------------------------\n"
                + "You may now generate the Payroll Summary.",
                "Payroll Complete",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

        // =========================================
        // FEATURE 5 - PAYROLL SUMMARY
        // =========================================
        // Generates an overall payroll summary using
        // the computed payroll records of all employees.
        // Displays the total number of employees,
        // total gross pay, total deductions, and
        // average net pay. Users may also export
        // the generated summary to a CSV report.
        // =========================================

        private static void generateSummary() {

        List<String[]> employees =
                EmployeeFileHandler.loadAllEmployees();

        if (employees.isEmpty()) {

                JOptionPane.showMessageDialog(
                        windowFrame,
                        "No employee records found.",
                        "Payroll Summary",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
        }
        boolean payrollComputed = true;

                for (String[] row : employees) {

                if (row.length < 12 || row[9].equals("0.00")) {
                        payrollComputed = false;
                        break;
                }
                }

                if (!payrollComputed) {

                JOptionPane.showMessageDialog(
                        windowFrame,
                        "Please compute payroll before generating the summary.",
                        "Payroll Summary",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
                }

        double totalGross = 0;
        double totalDeductions = 0;
        double totalNet = 0;

        for (String[] row : employees) {

                try {

                double rate = Double.parseDouble(row[3]);

                double days =  Double.parseDouble(row[4]);

                double gross =
                        SalaryComputationModule.computeGrossPay(
                                rate,
                                days
                        );

                double sss =
                        SalaryComputationModule.computeSSS(
                                gross
                        );

                double philHealth =
                        SalaryComputationModule.computePhilHealth(
                                gross
                        );

                double pagIbig =
                        SalaryComputationModule.computePagIBIG(
                                gross
                        );

                double tax =
                        SalaryComputationModule.computeWithholdingTax(
                                gross
                        );

                double deductions =
                        SalaryComputationModule.computeDeductions(
                                sss,
                                philHealth,
                                pagIbig,
                                tax
                        );

                double net =
                        SalaryComputationModule.computeNetPay(
                                gross,
                                deductions
                        );

                totalGross += gross;
                totalDeductions += deductions;
                totalNet += net;

                }

                catch(NumberFormatException ex){

                JOptionPane.showMessageDialog(
                        windowFrame,
                        "One or more employee records contain invalid payroll information.",
                        "Payroll Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
                }

        }

    LocalDateTime now =
        LocalDateTime.now();

        DateTimeFormatter format =
        DateTimeFormatter.ofPattern(
                "MMMM dd, yyyy HH:mm"
        );

    double averageNet =
            totalNet / employees.size();

    String summary =
            "========== PAYROLL SUMMARY ==========\n\n"
            + "Generated On : "
            + now.format(format)
            
            + "\n\n"
            + "Total Employees : "
            + employees.size()

            + "\n"
            + "Total Gross Pay : ₱"
            + String.format("%,.2f", totalGross)
            
            + "\n"
            + "Total Deductions : ₱"
            + String.format("%,.2f", totalDeductions)
            + "\n"
            + "Total Net Pay : ₱"
            + String.format("%,.2f", totalNet)
            + "\n"
            + "Average Net Pay : ₱"
            + String.format("%,.2f", averageNet);

    Object[] options = {

        "Export CSV",

        "Close"

    };

    int choice = JOptionPane.showOptionDialog(

            windowFrame,
            summary,
            "Payroll Summary",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            options,
            options[1]
    );

    if(choice == JOptionPane.YES_OPTION){

        PayrollSummaryExporter.exportSummary(

                employees.size(),
                totalGross,
                totalDeductions,
                totalNet,
                averageNet,
                now
        );

    }

}

    // =========================================
    // REFRESH TABLE
    // =========================================
    // Reloads the employee records from the CSV
    // file and updates the JTable. Employee
    // records with missing payroll columns are
    // automatically padded to maintain a
    // consistent table structure.
    // =========================================

    private static void refreshTable() {

        tableModel.setRowCount(0);

        List<String[]> list =
                EmployeeFileHandler
                        .loadAllEmployees();

        for(String[] row : list){

            if(row.length < 12){

                String[] padded =
                        new String[12];

                for(int i=0;
                    i<row.length;
                    i++){

                    padded[i] = row[i];
                }

                for(int i=row.length;
                    i<12;
                    i++){

                    padded[i] = "0.00";
                }

                tableModel.addRow(
                        padded
                );

            } else {

                tableModel.addRow(
                        row
                );
            }
        }
    }

    // =========================================
    // CLEAR FIELDS
    // =========================================
    // Clears all employee input fields after
    // adding, updating, deleting, or resetting
    // an employee record.
    // =========================================

    private static void clearFields() {

        txtEmpId.setText("");
        txtLastName.setText("");
        txtFirstName.setText("");
        txtRate.setText("");
        txtDays.setText("");
        txtSssNum.setText("");
        txtPhilHealthNum.setText("");
        txtTinNum.setText("");
        txtPagIbigNum.setText("");
    }

    // =========================================
    // MAIN
    // =========================================

    public static void main(
            String[] args
    ){

        SwingUtilities.invokeLater(
                () -> {
                    initWindow();
                    initUIComponents();
                    setupListeners();
                    showScreen("LOGIN");
                    windowFrame.setVisible(
                            true
                    );
                }
        );
    }
}