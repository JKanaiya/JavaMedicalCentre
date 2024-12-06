package com.medicalproject.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigInteger;

import static com.medicalproject.DB.DBCRUD.updateDoctor;

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
    @FXML
    public void confirmUpdate(ActionEvent event){
        updateDoctor(Integer.parseInt(doctorID.getText()),
                updateDoctorSpec.getText(), Integer.parseInt(updateDoctorPhoneNo.getText()),updateDoctorEmail.getText());

        closeWindow((Node) event.getSource());
    }
    public void closeWindow(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void cancelUpdate(ActionEvent event){
        closeWindow((Node) event.getSource());
    }
}