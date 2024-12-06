package com.medicalproject;
/**
 *
 * @author robert + jonathan
 */
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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
    public static String generateBill(int billID, int billPatientID, Map<String, Integer> selectedProcedures, LocalDate billDate, int billTotal){
            StringBuilder formattedProcedures = new StringBuilder();

            int totalWidth = 53;

            for (Map.Entry<String, Integer> entry : selectedProcedures.entrySet()) {
                String formatted = formatProcedurePrice(entry.getKey(), entry.getValue(), totalWidth);
                formattedProcedures.append(formatted).append("\n");
            }
                   return
                    "Ph.Medial--------------------------------------------\n" +
                    "Bill ID : " + billID + "\n" +
                    getNameDB("Patients", billPatientID, "PatientID") + "----------------------------------" + billDate + "\n" +
                    "----------------------------------------------------------"+ "\n" +
                    "Procedures:\n" +
                    formattedProcedures +
                    "----------------------------------------------------------"+ "\n" +
                    "Total --------------------------------------------" + billTotal;
        }

}
