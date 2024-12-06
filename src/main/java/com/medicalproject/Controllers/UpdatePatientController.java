package com.medicalproject.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static com.medicalproject.DB.DBCRUD.updatePatientDB;

public class UpdatePatientController {

    @FXML
    private Button cancelBtn;

    @FXML
    private Button confirmUpdate;

    @FXML
    private TextField patientAddress;

    @FXML
    private TextField patientAge;

    @FXML
    private TextField patientBloodGroup;

    @FXML
    private TextField patientGender;

    @FXML
    private TextField patientHeight;

    @FXML
    private Label patientID;

    @FXML
    private Label patientName;

    @FXML
    private TextField patientWeight;

    @FXML
    void cancelUpdate(ActionEvent event) {

    }

    @FXML
    void updatePatient(ActionEvent event) {

    }

    public void setDetails(String name, int ID, Double weight, Double height, String gender, int age, String bloodGroup, String address) {
        patientAddress.setText(address);
        patientHeight.setText(String.valueOf(height));
        patientWeight.setText(String.valueOf(weight));
        patientID.setText(String.valueOf(ID));
        patientName.setText(name);
        patientGender.setText(gender);
        patientAge.setText(String.valueOf(age));
        patientBloodGroup.setText(bloodGroup);
    }
    public void confirmUpdate(){
        updatePatientDB(Integer.parseInt(patientID.getText()),
                patientAddress.getText(), Integer.parseInt(patientAge.getText()), patientBloodGroup.getText(),
                Double.parseDouble(patientWeight.getText()), Double.parseDouble(patientHeight.getText()));
    }
}
