package com.medicalproject.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import static com.medicalproject.DB.DBCRUD.*;

public class PatientDetailsController {

    @FXML
    private Label patientAddress;

    @FXML
    private Label patientAge;

    @FXML
    private Label patientBloodGroup;

    @FXML
    private Label patientGender;

    @FXML
    private Label patientHeight;

    @FXML
    private Label patientID;

    @FXML
    private Label patientName;

    @FXML
    private Label patientWeight;

    public void setPatientDetails(String name, String gender, int iD, String bloodGroup, int age, String address, Double height, Double weight){
        patientName.setText(name);
        patientGender.setText(gender);
        patientBloodGroup.setText(bloodGroup);
        patientAddress.setText(address);
        patientHeight.setText(String.valueOf(height));
        patientWeight.setText(String.valueOf(weight));
        patientID.setText(String.valueOf(iD));
        patientAge.setText(String.valueOf(age));
    }
    public void deletePatient(int patientID){
        removeData(patientID, "Patients", "PatientID");
    }
}