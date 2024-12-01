package com.medicalproject;


import com.medicalproject.DB.DBCRUD;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Scanner;


public class Appointments{
//    replace with data from Java DataDe
    public void createAppointment(String time, String date, String Specialization){

        TimeControl tc = new TimeControl();

        LocalDateTime dateTime = tc.convertSimpleToLocalDateTime(time, date);

        if(!DBCRUD.getSpecializedMap(Specialization, dateTime).isEmpty()){
            try(Scanner scan = new Scanner(System.in);){
                System.out.println("Please enter the details of the appointment: PatientID, DoctorID, Reason");
                int PatientID = scan.nextInt();
                int DoctorID = scan.nextInt();
                String Reason = scan.nextLine();
//                no but seriously correct this
                DBCRUD dbs = new DBCRUD();
                dbs.addAppointment(PatientID, DoctorID, Reason, dateTime, Specialization);
            }
            catch (NullPointerException npe){
                System.err.println(npe.getLocalizedMessage());
            }
            catch (Exception e) {
                e.getLocalizedMessage();
            }
        }
        else{
            System.out.println("There is no Doctor that fits the Specialization that is free at that time, please try another time / day");
        }
    }
}
