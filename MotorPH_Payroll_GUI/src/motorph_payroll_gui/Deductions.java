/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph_payroll_gui;

/**
 * @author Group 21
 * Implements tax brackets based on MotorPH's website.
 */
public class Deductions {
    private double sssRate = 0.045;
    private double philHealthRate = 0.03;
    private double pagIbigRate = 0.02;
    private double incomeTax = 0.10;

    public double calculateSSS(double monthlyGross) {
        if (monthlyGross < 3250) return 135.0;
        if (monthlyGross >= 24750) return 1125.0;
        int stepCount = (int) ((monthlyGross - 3250) / 500) + 1;
        return 135.0 + (stepCount * 22.50);
    }

    public double calculatePhilHealth(double monthlyGross) {
        double salaryBase = (monthlyGross <= 10000) ? 10000 : (monthlyGross >= 60000) ? 60000 : monthlyGross;
        return (salaryBase * 0.03) / 2;
    }

    public double calculatePagIbig(double monthlyGross) {
        if (monthlyGross >= 1000 && monthlyGross <= 1500) {
            return monthlyGross * 0.01;
        } else if (monthlyGross > 1500) {
            double contribution = monthlyGross * 0.02;
            return Math.min(contribution, 100.0);
        }
        return 0.0;
    }

    public double calculateWithholdingTax(double taxableIncome) {
        if (taxableIncome < 20833) return 0.0;
        if (taxableIncome < 33333) return (taxableIncome - 20833) * 0.20;
        if (taxableIncome < 66667) return 2500 + (taxableIncome - 33333) * 0.25;
        if (taxableIncome < 166667) return 10833 + (taxableIncome - 66667) * 0.30;
        if (taxableIncome < 666667) return 40833.33 + (taxableIncome - 166667) * 0.32;
        return 200833.33 + (taxableIncome - 666667) * 0.35;
    }
}
