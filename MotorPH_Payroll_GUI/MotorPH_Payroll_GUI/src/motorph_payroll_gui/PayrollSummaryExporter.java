/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph_payroll_gui;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author S1101 - Group 21
 */
public class PayrollSummaryExporter {
    
    // =====================================================
    // Exports the generated payroll summary into a CSV file.
    // Allows the user to choose the save location and
    // displays a confirmation message after a successful export.
    // =====================================================
    public static void exportSummary(

            int totalEmployees,
            double totalGross,
            double totalDeductions,
            double totalNet,
            double averageNet,
            LocalDateTime generatedDate
    ){

        JFileChooser chooser =
                new JFileChooser();

        chooser.setDialogTitle(
                "Save Payroll Summary");

        chooser.setFileFilter(
                new FileNameExtensionFilter(
                        "CSV Files",
                        "csv")
        );

        String defaultFileName =
                "MotorPH_PayrollSummary_" +
                generatedDate.format(
                        DateTimeFormatter.ofPattern(
                                "yyyyMMdd")
                ) + ".csv";

        chooser.setSelectedFile(

                new File(defaultFileName)

        );

        int option = chooser.showSaveDialog(null);
        
        if(option != JFileChooser.APPROVE_OPTION){

            return;

        }

        File file = chooser.getSelectedFile();

        if(!file.getName().toLowerCase().endsWith(".csv")){

            file = new File(file.getAbsolutePath() + ".csv");

        }

        try{

            FileWriter writer =

                    new FileWriter(file);

            writer.write("MotorPH Payroll Summary\n");
            writer.write("\n");

            writer.write(
                    "Generated On,"
                    + generatedDate.format(
                            DateTimeFormatter.ofPattern(
                                    "MMMM dd, yyyy HH:mm"
                            )
                    )
                    + "\n"
            );

            writer.write("\n");

            // Header row for easy to read style in Excel
            writer.write("Category,Value\n");

            writer.write(
                    "Total Employees,"
                    + totalEmployees
                    + "\n"
            );

            writer.write(
                    "Total Gross Pay,"
                    + String.format("%.2f", totalGross)
                    + "\n"
            );

            writer.write(
                    "Total Deductions,"
                    + String.format("%.2f", totalDeductions)
                    + "\n"
            );
            
            writer.write(
                    "Total Net Pay,"
                    + String.format("%.2f", totalNet)
                    + "\n"
            );

            writer.write(
                    "Average Net Pay,"
                    + String.format("%.2f", averageNet)
                    + "\n"
            );
            writer.close();

            JOptionPane.showMessageDialog(
                    null,

                    "Payroll summary exported successfully." +

                    "\n\nFile Name:\n" +

                    file.getName() +

                    "\n\nSaved To:\n" +

                    file.getAbsolutePath(),
                    "Export Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

        }

        catch(IOException ex){

            JOptionPane.showMessageDialog(
                    null,
                    "Unable to export payroll summary.",
                    "Export Error",
                    JOptionPane.ERROR_MESSAGE
            );

        }

    }

}
