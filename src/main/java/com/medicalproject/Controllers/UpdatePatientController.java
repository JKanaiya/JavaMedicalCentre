package com.medicalproject.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    private void cancelUpdate(ActionEvent event) {
        closeWindow((Node) event.getSource());
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
    @FXML
    public void confirmUpdate(ActionEvent event){
        updatePatientDB(Integer.parseInt(patientID.getText()),
                patientAddress.getText(), Integer.parseInt(patientAge.getText()), patientBloodGroup.getText(),
                Double.parseDouble(patientWeight.getText()), Double.parseDouble(patientHeight.getText()));
        closeWindow((Node) event.getSource());
    }
    public void closeWindow(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}
