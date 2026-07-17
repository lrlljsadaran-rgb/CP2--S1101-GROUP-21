package motorph_payroll_gui;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author S1101 - Group 21 
 */
public class EmployeeFileHandler {
    
    // =========================================================
    // Handles all employee data file operations for the
    // MotorPH Employee Application. This module is responsible
    // for reading employee records from the CSV file , adding
    // new employee records, and updating the employee data
    // by rewriting the CSV file whenever changes are made.
    // =========================================================

    private static final String FILE_PATH = "MotorPH_Employee_Master_Data.csv";
    private static final String DELIMITER = ",";

    public static List<String[]> loadAllEmployees() {
        List<String[]> list = new ArrayList<>();
        File file = new File(FILE_PATH);
        
        if (!file.exists()) {
            return list;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String lineStr;
            while ((lineStr = reader.readLine()) != null) {
                if (!lineStr.trim().isEmpty()) {
                    list.add(lineStr.split(DELIMITER));
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file input stream: " + e.getMessage());
        }
        return list;
    }

    public static void addNewEmployee(String[] data) {
        try {
            FileWriter fw = new FileWriter(FILE_PATH, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            
            for (int i = 0; i < data.length; i++) {
                out.print(data[i]);
                if (i < data.length - 1) {
                    out.print(DELIMITER);
                }
            }
            out.println();
            out.close();
        } catch (IOException e) {
            System.out.println("Error appending employee row details: " + e.getMessage());
        }
    }

    public static void rewriteFileStorage(List<String[]> dataRows) {
        try {
            FileWriter fw = new FileWriter(FILE_PATH, false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            
            for (int i = 0; i < dataRows.size(); i++) {
                String[] item = dataRows.get(i);
                for (int j = 0; j < item.length; j++) {
                    out.print(item[j]);
                    if (j < item.length - 1) {
                        out.print(DELIMITER);
                    }
                }
                out.println();
            }
            out.close();
        } catch (IOException e) {
            System.out.println("Error wiping/overwriting matrix rows dataset: " + e.getMessage());
        }
    }
}