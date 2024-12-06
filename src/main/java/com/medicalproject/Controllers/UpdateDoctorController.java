package com.medicalproject.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.math.BigInteger;

public class UpdateDoctorController {

    @FXML
    private Button cancelBtn;

    @FXML
    private Button confirmUpdate;

    @FXML
    private Label doctorID;

    @FXML
    private Label doctorName;

    @FXML
    private TextField updateDoctorEmail;

    @FXML
    private TextField updateDoctorPhoneNo;

    @FXML
    private TextField updateDoctorSpec;

    public void setDetails(int iD, String name, String email, BigInteger phoneNo, String specialization) {
        doctorID.setText(String.valueOf(iD));
        doctorName.setText(name);
        updateDoctorPhoneNo.setText(String.valueOf(phoneNo));
        updateDoctorSpec.setText(specialization);
        updateDoctorEmail.setText(email);
    }
}