package com.medicalproject;
/**
 *
 * @author robert
 */
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bills {
        private final int billId;
        private final LocalDateTime billDate;
        private final double billTotal;
        private final String patientName;
        private final String modeOfPayment;

        public Bills(int billId, String patientName, LocalDateTime billDate, double billTotal, String patientName1, String modeOfPayment, List<String> procedures) {
            this.billId = billId;
            this.billDate = billDate;
            this.billTotal = billTotal;
            this.patientName = patientName;
            this.modeOfPayment = modeOfPayment;
        }
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
        @Override
        public String toString() {
            StringBuilder proceduresList = new StringBuilder();
            procedures.get("Strep Test");

            return "Ph.Medial--------------------------------------------\n" +
                    "Bill ID           : " + billId + "\n" +
                    patientName + "-----------------------------" + billDate + "\n" +
                    "----------------------------------------------------------"+ "\n" +
                    "Procedures        :\n" + proceduresList +
                    "----------------------------------------------------------"+ "\n" +
                     "Mode of Payment   : " + modeOfPayment + "\n" +
                    "Total -------------------------------------------" + billTotal;

        }
}
