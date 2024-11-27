package src.com.medicalproject;


import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class Appointments{

    LocalDateTime dateTime;
//    LocalDateTime
    public boolean isSpecializedDateTimeAvailable(String time, String date, String specialization) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Parse date and time inputs
        LocalTime formatTime = LocalTime.parse(time, timeFormatter);
        LocalDate formatDate = LocalDate.parse(date, dateFormatter);

        // Combine LocalDate and LocalTime into LocalDateTime
        dateTime = LocalDateTime.of(formatDate, formatTime);

        return true;
    }
//    replace with data from Java DataDe
    public void createAppointment(String time, String date, String specialization){
        if(isSpecializedDateTimeAvailable(time, date, specialization)){
            try(Scanner scan = new Scanner(System.in);){
                System.out.println("Please enter the details of the appointment: PatientID, DoctorID, Reason");
                int PatientID = scan.nextInt();
                int DoctorID = scan.nextInt();
                String Reason = scan.nextLine();
//                no but seriously correct this
                DBQueries dbs = new DBQueries();
                dbs.addAppointment(PatientID, DoctorID, Reason, dateTime);
            } catch (Exception e) {
                e.getLocalizedMessage();
            }
        }
        else{
            System.out.println("There is no Doctor that fits the specialization that is free at that time, please try another time / day");
        }
    }
}
