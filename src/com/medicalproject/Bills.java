package src.com.medicalproject;
/**
 *
 * @author robert
 */
import java.time.LocalDateTime;
import java.util.List;

public class Bills {
        private final int billId;
//        private final String patient;
//        private final String doctor;
        private final LocalDateTime billDate;
        private final double billAmount;
        private final String modeOfPayment;
        private final List<String> procedures;

        public Bills(int billId, String patient, String doctor, LocalDateTime billDate, double billAmount, String modeOfPayment, List<String> procedures) {
            this.billId = billId;
//            this.patient = patient;
//            this.doctor = doctor;
            this.billDate = billDate;
            this.billAmount = billAmount;
            this.modeOfPayment = modeOfPayment;
            this.procedures = procedures;
        }

        @Override
        public String toString() {
            StringBuilder proceduresList = new StringBuilder();
            for (String procedure : procedures) {
                proceduresList.append("  - ").append(procedure).append("\n");
            }

            return "---------------------- BILL DETAILS ----------------------\n" +
                    "Bill ID           : " + billId + "\n" +
//                    "Patient Name      : " + patient.getName() + "\n" +
//                    "Doctor Name       : " + doctor.getName() + "\n" +
                    "Bill Date         : " + billDate + "\n" +
                    "Procedures        :\n" + proceduresList +
                    "Bill Amount       : Ksh " + String.format("%.2f", billAmount) + "\n" +
                    "Mode of Payment   : " + modeOfPayment + "\n" +
                    "----------------------------------------------------------";
        }
}
