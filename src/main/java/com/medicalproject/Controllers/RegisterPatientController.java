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
    // This method is triggered when the user clicks the "Add Patient" button
    @FXML
    private void addPatient(ActionEvent event){
        int check = registerPatientDB(patientName.getText(), patientAddress.getText(), gender.getText(), Integer.parseInt(age.getText()), bloodGroup.getText(), Double.parseDouble(weight.getText()), Double.parseDouble(height.getText()));
        // If the registration is successful, close the window, otherwise show an error message
        if(check == 1){
            System.out.println("success");
            closeWindow((Node) event.getSource());
        }
        else {
            invalidFormMessage.setText("Invalid patient details, Please try again");
        }
    }
    @FXML
    // This method is triggered when the user clicks the "Cancel" button
    private void cancelRegisterBtn(ActionEvent event){
        closeWindow((Node) event.getSource());
    }
    // Closes the current window triggered by the event
    public void closeWindow(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();  // Get the current window (stage)
        stage.close();  // Close the window
    }

    // This method is called when the controller is initialized
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set the generated Patient ID in the label when the window is initialized
        genPatientID.setText(getNewID("Patients", "PatientID"));
    }
}