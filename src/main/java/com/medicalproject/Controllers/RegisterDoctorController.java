package com.medicalproject.Controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterDoctorController {

    @FXML
    private Button cancelRegistration;

    @FXML
    private PasswordField doctorPassword;

    @FXML
    private TextField doctorName;

    @FXML
    private PasswordField doctorPasswordConfirm;

    @FXML
    private TextField doctorPhoneNumber;

    @FXML
    private TextField doctorSpecialization;

    @FXML
    private Label genIDLocation;

    @FXML
    private Label invalidFormMessage;

    @FXML
    private Button registerDoctorBtn;

}
