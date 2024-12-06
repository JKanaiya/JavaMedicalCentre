package com.medicalproject;
/**
 *
 * @author robert + jonathan
 */
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.medicalproject.DB.DBCRUD.getNameDB;

public class Bills {
       public static Map<String, Integer> procedures = new HashMap<String, Integer>();
        static {
            procedures.put("Basic Physical Exam", 150);
            procedures.put("Blood Test Panel", 280);
            procedures.put("X-Ray", 350);
            procedures.put("ECG/EKG", 250);
            procedures.put("Ultrasound", 400);
            procedures.put("CT Scan", 800);
            procedures.put("MRI", 950);
            procedures.put("Minor Stitches", 200);
            procedures.put("Vaccine Administration", 100);
            procedures.put("Basic Dental Cleaning", 175);
            procedures.put("Eye Examination", 200);
            procedures.put("Strep Test", 120);
            procedures.put("Flu Test", 100);
            procedures.put("Allergy Testing", 300);
        }

    public static String formatProcedurePrice(String procedure, int price, int totalWidth) {
        // Format price with 2 decimal places
        String priceStr = String.format("%d", price);
        int dashCount = totalWidth - procedure.length() - priceStr.length();
        // Build the string with correct spacing
        return procedure + "-".repeat(Math.max(0, dashCount)) + priceStr;
    }
    public static void writeBillToFile(String billContent, LocalDate billDate, String patientName) throws IOException {
        // Create Bills directory if it doesn't exist
        String billsDir = "Bills";
        Files.createDirectories(Paths.get(billsDir));

        // Create file path with bill ID
        String fileName = String.format("Date_%tF_%s.txt", billDate, patientName);
        Path filePath = Paths.get(billsDir, fileName);

        // Write content to file
        Files.writeString(filePath, billContent);
    }
    public static void generateBill(int billID, int billPatientID, Map<String, Integer> selectedProcedures, LocalDate billDate, int billTotal) throws IOException {
            StringBuilder formattedProcedures = new StringBuilder();

            int totalWidth = 53;

            for (Map.Entry<String, Integer> entry : selectedProcedures.entrySet()) {
                String formatted = formatProcedurePrice(entry.getKey(), entry.getValue(), totalWidth);
                formattedProcedures.append(formatted).append("\n");
            }
            String bill = "Ph.Medial--------------------------------------------\n" +
                    "Bill ID : " + billID + "\n" +
                    getNameDB("Patients", billPatientID, "PatientID") + "----------------------------------" + String.format("%tF", billDate) + "\n" +
                    "----------------------------------------------------------"+ "\n" +
                    "Procedures:\n" +
                    formattedProcedures +
                    "----------------------------------------------------------"+ "\n" +
                    "Total --------------------------------------------" + billTotal;
            writeBillToFile(bill, billDate, getNameDB("Patients", billPatientID, "PatientID"));
        }

}
