# MotorPH Employee App

## Overview
The goal of the MotorPH Employee App is to simulate a simplified employee payroll system that allows users to manage employee-related payroll information through a user-friendly graphical interface.

---

Project References: MPHCR01, MPHCR02, MPHCR03, MPHCR04

---

## Portal Authentication Security
Access to the centralized system requires the following administrative staff credentials:

  * Username: payroll_staff
  * Password: 12345

---

## System Core GUI Elements
The graphical implementation features a fixed-dimension dashboard layout (1100x650) built entirely via Java Swing, utilizing a custom gradient background.

### 1. Left Action Panel (Employee Creation & Management Form)
  * Employee Number (Numeric format validation input field)
  * Last Name / First Name (Text format validation fields)
  * Rate Per Day / Days Worked (Positive numeric decimal input fields)
  * Deduction Infrastructure IDs (Optional digits-only fields for SSS, PhilHealth, TIN, and Pag-IBIG Numbers)
  * Interactive Control Triggers `Add New Record`, `Update Record`, and `Delete Record` buttons.

### 2. Right Action Panel (Data Visualization & Ledger)
  * Employee Master Ledger Table (`JTable`):** Displays comprehensive 11-column matrices of employee data, structural identification details, and batch payroll balances. Cell inline editing is disabled to preserve data         integrity.
  * Selected Employee Information Viewer: A dedicated display context panel that renders live detail maps (ID Number, Full Name, Gross Pay, and Net Pay) directly upon record row selection inside the ledger.
  * Computational Footers: Houses the dynamic multi-layered loading status indicator and the system-wide `Compute Salaries` execution button.

---

## Implemented Feature Request Index

### MPHCR01 – Feature 1: GUI Integration
* Description: Converted the inherited legacy procedural console engine into an intuitive, event-driven Graphical User Interface (GUI) powered by Java Swing.
* Technical Implementations:
  * Replaced manual console read lines with real-time text components.
  * Added event-driven user interactions leveraging action and document listeners.
  * Preserved core underlying back-end data architectures while decoupling presentation layers.

### MPHCR02 – Feature 2: Employee Record Viewing and Creation
* Description: Implemented standard localized database persistence utilizing flat CSV storage mapping, binding structural file layers directly to live structural frontend views.
* Technical Implementations:
  * Formed seamless database linkages to extract record values dynamically into a structured `JTable`.
  * Implemented an interactive input pipeline to cleanly append incoming new employee profiles directly onto existing CSV documents.
  * Deployed a defensive input filtering infrastructure powered by strict regular expressions (RegEx) to prevent character domain issues and duplicate ID assignments.

### MPHCR03 – Feature 3: Salary Computation Module
* Description: Introduced an industrial payroll calculator module capable of executing standard structural accounting equations over batch-loaded employee data.
* Technical Implementations:
  * Placed all salary algorithms within a dedicated domain file (`SalaryComputationModule.java`).
  * Structured system-wide payroll routines over parallel dynamic arrays mapping individual daily wage arrays and time allocations to resolve gross pay.
  * Automated sequential deductions calculations tracking structural criteria matrices (SSS, PhilHealth, Pag-IBIG, and progressive Withholding Taxes) to extrapolate final Net Pay balances.
  * Overwrites underlying flat CSV rows automatically with formatted 2-decimal scale values following successful batch calculation triggers.

### MPHCR04 – Feature 4: Update and Delete Employee Records
* Description: Completed total system CRUD operations by empowering operators to securely modify or purge existing database profiles.
* Technical Implementations:
  * Tied row selection events to extract indices directly from the `JTable` matrix, mapping chosen data arrays seamlessly back into editable input fields.
  * Implemented an inline updating engine that rewrites file records with new configurations upon successful data structure validation checks.
  * Integrated an administrative safety guard utilizing `JOptionPane` dialog boxes to verify operational intent before processing database delete triggers.

---

## Exception Handling & Validation Policy
To ensure a stable environment and runtime safety, the platform relies on strict validation checks:
1. Presence Checks: Prevents operational data commits if primary identification entries are left empty.
2. Numeric Validation: Protects numeric domains from invalid character types via intensive try-catch blocks and parsing routines.
3. Data Conflict Resolution: Evaluates active record states prior to final commits to block duplicate index values from degrading file systems.
4. Graceful Failures: Replaces runtime system breaks with readable `JOptionPane` alert messages to provide clear system status updates to users.

---

# Group 21 Members:
- Daniel Castro
- Justine Nolie Tolentino
- Levi Liane Justin Sadaran
- Masahide Takahashi
