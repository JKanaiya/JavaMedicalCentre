package com.medicalproject.Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static com.medicalproject.DB.DBCRUD.getNewID;
import static com.medicalproject.DB.DBCRUD.registerDoctor;

public class RegisterDoctorController implements Initializable {

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

    @FXML
    private TextField doctorEmail;

    @FXML
    private Label genDoctorID;
    // This method handles the "Register Doctor" button click event
    @FXML
    private void addDoctor(ActionEvent event){
        // Check if the passwords entered match
        if(!doctorPassword.getText().equals(doctorPasswordConfirm.getText()) ){
            // If passwords match, proceed with the registration process
            invalidFormMessage.setText("Passwords Do not Match!, Please Correct and Try Again");
        }
            int check = registerDoctor(Integer.parseInt(genDoctorID.getText()),
                    String.valueOf(doctorPassword.getText().hashCode()), doctorSpecialization.getText(),
                    doctorName.getText(), Integer.parseInt(doctorPhoneNumber.getText()), doctorEmail.getText());
        // Check if the doctor registration was successful
        if(check == 1){
           System.out.println("success");
           closeWindow((Node) event.getSource());
        }
        else {
            invalidFormMessage.setText("Invalid Details, Please correct and try again");
        }
    }
    // This method is triggered when the user clicks the "Cancel" button
    @FXML
    private void cancelRegistration(ActionEvent event){
        // Close the registration window
        closeWindow((Node) event.getSource());
    }
    // This method closes the window triggered by the event
    public void closeWindow(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set the generated Doctor ID to the label when the window is initialized
        genDoctorID.setText(getNewID("Users", "ID"));
    }
}
