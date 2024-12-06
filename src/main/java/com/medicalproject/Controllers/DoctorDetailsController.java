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
    /**
     * Sets the doctor's details in the respective labels.
     * This method updates the UI with the provided doctor information.
     *
     * @param email The doctor's email address.
     * @param iD The doctor's unique ID.
     * @param name The doctor's name.
     * @param phoneNum The doctor's phone number (BigInteger for large numbers).
     * @param specialization The doctor's field of specialization.
     */
    public void setDoctorDetails(String email, int iD, String name, BigInteger phoneNum, String specialization){
        doctorEmail.setText(email);
        doctorID.setText(String.valueOf(iD));
        doctorName.setText(name);
        doctorPhoneNo.setText(String.valueOf(phoneNum));
        doctorSpecialization.setText(specialization);
    }
}
