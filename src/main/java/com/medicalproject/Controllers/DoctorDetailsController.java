package com.medicalproject.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.math.BigInteger;

public class DoctorDetailsController {

    @FXML
    private Label doctorEmail;

    @FXML
    private Label doctorID;

    @FXML
    private Label doctorName;

    @FXML
    private Label doctorPhoneNo;

    @FXML
    private Label doctorSpecialization;

    public void setDoctorDetails(String email, int iD, String name, BigInteger phoneNum, String specialization){
        doctorEmail.setText(email);
        doctorID.setText(String.valueOf(iD));
        doctorName.setText(name);
        doctorPhoneNo.setText(String.valueOf(phoneNum));
        doctorSpecialization.setText(specialization);
    }

}
