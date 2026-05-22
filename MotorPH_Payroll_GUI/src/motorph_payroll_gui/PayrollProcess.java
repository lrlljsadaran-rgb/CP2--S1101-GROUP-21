/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph_payroll_gui;

import java.time.LocalDate;

/**
 * @author Group 21
 * Coordinates calculations to convert time log balances into gross and net pay values.
 */
public class PayrollProcess {
    private double grossIncome;
    private double netPay;
    private LocalDate startDate;
    private LocalDate endDate;
    
    // Auxiliary variables for granular reporting output
    private double sssCalculated;
    private double philHealthCalculated;
    private double pagIbigCalculated;
    private double taxCalculated;

    public PayrollProcess(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void computeGrossPay(Employee e, AttendanceRecord a) {
        double hoursWorked = a.calculateDailyHours();
        this.grossIncome = hoursWorked * e.getHourlyRate();
    }
    
    public void addGrossPay(double grossAmount) {
        this.grossIncome += grossAmount;
    }

    public void calculateDeductions(Deductions d) {
        this.sssCalculated = d.calculateSSS(grossIncome);
        this.philHealthCalculated = d.calculatePhilHealth(grossIncome);
        this.pagIbigCalculated = d.calculatePagIbig(grossIncome);
        
        double netTaxable = grossIncome - (sssCalculated + philHealthCalculated + pagIbigCalculated);
        this.taxCalculated = d.calculateWithholdingTax(netTaxable);

        this.netPay = netTaxable - taxCalculated;
    }

    public String generatePayStub() {
        return "===== PAYSLIP =====\n" +
               "Gross Income: ₱" + String.format("%,.2f", grossIncome) + "\n" +
               "SSS: ₱" + String.format("%,.2f", sssCalculated) + " | " +
               "PhilHealth: ₱" + String.format("%,.2f", philHealthCalculated) + "\n" +
               "Pag-IBIG: ₱" + String.format("%,.2f", pagIbigCalculated) + " | " +
               "Withholding Tax: ₱" + String.format("%,.2f", taxCalculated) + "\n" +
               "Total Deductions: ₱" + String.format("%,.2f", (grossIncome - netPay)) + "\n" +
               "Net Pay: ₱" + String.format("%,.2f", netPay);
    }

    public double getGrossIncome() { return grossIncome; }
    public double getNetPay() { return netPay; }
    public double getSssCalculated() { return sssCalculated; }
    public double getPhilHealthCalculated() { return philHealthCalculated; }
    public double getPagIbigCalculated() { return pagIbigCalculated; }
    public double getTaxCalculated() { return taxCalculated; }
}
