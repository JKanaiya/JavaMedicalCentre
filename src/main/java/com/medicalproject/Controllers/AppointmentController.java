package com.medicalproject.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AppointmentController {

    @FXML
    private DatePicker appointmentDate;

    @FXML
    private TextField appointmentDescription;

    @FXML
    private TextField appointmentTime;

    @FXML
    private Button cancelNewAppointment;

    @FXML
    private TextField doctorID;

    @FXML
    private TextField patientID;

    @FXML
    private Button submitAppointment;
//    when searching for a name, consider below
// String container = "aBcDeFg";
//String content = "dE";
//boolean containerContainsContent = StringUtils.containsIgnoreCase(container, content);
}

