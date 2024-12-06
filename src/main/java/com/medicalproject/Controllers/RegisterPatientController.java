package com.medicalproject.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static com.medicalproject.DB.DBCRUD.getNewID;
import static com.medicalproject.DB.DBCRUD.registerPatientDB;

public class RegisterPatientController implements Initializable {

    @FXML
    private TextField age;

    @FXML
    private TextField bloodGroup;

    @FXML
    private Label genPatientID;

    @FXML
    private TextField gender;

    @FXML
    private TextField height;

    @FXML
    private Label invalidFormMessage;

    @FXML
    private TextField patientAddress;

    @FXML
    private TextField patientName;

    @FXML
    private TextField weight;

    @FXML
    private void addPatient(ActionEvent event){
        int check = registerPatientDB(patientName.getText(), patientAddress.getText(), gender.getText(), Integer.parseInt(age.getText()), bloodGroup.getText(), Double.parseDouble(weight.getText()), Double.parseDouble(height.getText()));
        if(check == 1){
            System.out.println("success");
            closeWindow((Node) event.getSource());
        }
        else {
            invalidFormMessage.setText("Invalid patient details, Please try again");
        }
    }
    @FXML
    private void cancelRegisterBtn(ActionEvent event){
        closeWindow((Node) event.getSource());
    }
    public void closeWindow(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genPatientID.setText(getNewID("Patients", "PatientID"));
    }
}