package com.medicalproject.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class RegisterAdminController {

    @FXML
    private PasswordField adminPassword;

    @FXML
    private PasswordField adminPasswordConfirm;

    @FXML
    private Button cancelRegBtn;

    @FXML
    private Label genAdminID;

    @FXML
    private Button registerAdminBtn;

}
