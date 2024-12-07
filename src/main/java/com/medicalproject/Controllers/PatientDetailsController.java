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
    /**
     * Sets the patient's details in the respective labels.
     * This method updates the UI with the provided patient information.
     *
     * @param name The patient's name.
     * @param gender The patient's gender.
     * @param iD The patient's unique ID.
     * @param bloodGroup The patient's blood group.
     * @param age The patient's age.
     * @param address The patient's address.
     * @param height The patient's height (Double type).
     * @param weight The patient's weight (Double type).
     */
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
    /**
     * Deletes the patient with the given ID from the database.
     * This method will invoke the removeData method to remove the patient's data.
     *
     * @param patientID The ID of the patient to be removed.
     */
    public void deletePatient(int patientID){
        removeData(patientID, "Patients", "PatientID");
    }
}