package com.medicalproject.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import static com.medicalproject.DB.DBCRUD.deleteAppointmentDB;
import static com.medicalproject.TimeControl.convertToLocalDateTime;

public class AppointmentDetailsController {

@FXML
private Label appDate;

@FXML
private Label appReason;

@FXML
private Label appSpecialization;

@FXML
private Label appTime;

@FXML
private Label doctorID;

@FXML
private Label patientID;
    // Method to set the appointment details in the corresponding labels
    public void setAppointmentDetails(String reason, String specialization, int docID, int patID, String date, String time){
        patientID.setText(String.valueOf(patID));
        doctorID.setText(String.valueOf(docID));
        appTime.setText(time);
        appDate.setText(date);
        appSpecialization.setText(specialization);
        appReason.setText(reason);
    }
    public void deleteAppointment(int patientID, String date, String time){
        // Convert the date and time to a LocalDateTime object and pass it to the deleteAppointmentDB method
        deleteAppointmentDB(patientID, convertToLocalDateTime(time, date));
    }
}
