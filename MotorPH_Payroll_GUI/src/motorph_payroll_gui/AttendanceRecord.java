/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph_payroll_gui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;

/**
 * @author Group 21
 * Tracks daily time logs and computes valid business work hours.
 */
public class AttendanceRecord {
    private LocalDate date;
    private LocalTime timeIn;
    private LocalTime timeOut;
    private double overtimeHours;
    private String employeeID;

    // Time-Tracking Architecture Constants
    private static final LocalTime SHIFT_START = LocalTime.of(8, 0);
    private static final LocalTime GRACE_PERIOD_CUTOFF = LocalTime.of(8, 10);
    private static final LocalTime SHIFT_END = LocalTime.of(17, 0);
    private static final int LUNCH_DURATION_MINUTES = 60;

    public AttendanceRecord(LocalDate date, LocalTime timeIn, LocalTime timeOut, double overtimeHours, String employeeID) {
        this.date = date;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.overtimeHours = overtimeHours;
        this.employeeID = employeeID;
    }

    public double calculateDailyHours() {
        LocalTime effectiveLogout = timeOut.isAfter(SHIFT_END) ? SHIFT_END : timeOut;
        LocalTime effectiveLogin = timeIn.isBefore(SHIFT_START) ? SHIFT_START : timeIn;

        long workedMinutes = Duration.between(effectiveLogin, effectiveLogout).toMinutes();

        if (workedMinutes > LUNCH_DURATION_MINUTES) {
            workedMinutes -= LUNCH_DURATION_MINUTES;
        } else {
            return 0.0;
        }

        double hoursDecimal = workedMinutes / 60.0;

        // Morning Buffer Grace Exception rule
        if (!timeIn.isAfter(GRACE_PERIOD_CUTOFF) && effectiveLogout.equals(SHIFT_END)) {
            return 8.0 + overtimeHours;
        }

        return Math.min(hoursDecimal, 8.0) + overtimeHours;
    }

    public LocalDate getDate() { return date; }
    public String getEmployeeID() { return employeeID; }
}
