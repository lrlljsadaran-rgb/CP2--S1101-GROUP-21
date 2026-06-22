package motorph_payroll_gui;

/**
 * @author S1101 - Group 21
 */
public class SalaryComputationModule {

    public static double computeGrossPay(double rate, double days) {
        return rate * days;
    }

    public static double computeSSS(double gross) {
        if (gross < 3250) {
            return 135.0;
        }
        if (gross >= 24750) {
            return 1125.0;
        }
        
        double baseDifference = gross - 3250;
        int intervalsCount = (int) (baseDifference / 500) + 1;
        return 135.0 + (intervalsCount * 22.50);
    }

    public static double computePhilHealth(double gross) {
        double trackBase = gross;
        if (gross <= 10000) {
            trackBase = 10000;
        } else if (gross >= 60000) {
            trackBase = 60000;
        }
        return (trackBase * 0.03) / 2;
    }

    public static double computePagIBIG(double gross) {
        if (gross >= 1000 && gross <= 1500) {
            return gross * 0.01;
        }
        if (gross > 1500) {
            double rateAmount = gross * 0.02;
            if (rateAmount > 100.0) {
                return 100.0;
            } else {
                return rateAmount;
            }
        }
        return 0.0;
    }

    public static double computeWithholdingTax(double taxable) {
        if (taxable >= 20833 && taxable < 33333) {
            return (taxable - 20833) * 0.20;
        }
        if (taxable >= 33333 && taxable < 66667) {
            return 2500 + (taxable - 33333) * 0.25;
        }
        if (taxable >= 66667 && taxable < 166667) {
            return 10833 + (taxable - 66667) * 0.30;
        }
        if (taxable >= 166667 && taxable < 666667) {
            return 40833.33 + (taxable - 166667) * 0.32;
        }
        if (taxable >= 666667) {
            return 200833.33 + (taxable - 666667) * 0.35;
        }
        return 0.0;
    }

    public static double computeDeductions(double sss, double ph, double pi, double tax) {
        return sss + ph + pi + tax;
    }

    public static double computeNetPay(double gross, double totalDeductions) {
        return gross - totalDeductions;
    }
}