package com.medicalproject.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static com.medicalproject.DB.DBCRUD.*;

public class RegisterStaffController implements Initializable {
    @FXML // fx:id="cancelRegBtn"
    private Button cancelRegBtn; // Value injected by FXMLLoader

    @FXML // fx:id="genStaffID"
    private Label genStaffID; // Value injected by FXMLLoader

    @FXML // fx:id="invalidFormMessage"
    private Label invalidFormMessage; // Value injected by FXMLLoader

    @FXML // fx:id="registerStaffBtn"
    private Button registerStaffBtn; // Value injected by FXMLLoader

    @FXML // fx:id="staffPassword"
    private PasswordField staffPassword; // Value injected by FXMLLoader

    @FXML // fx:id="staffPasswordConfirm"
    private PasswordField staffPasswordConfirm; // Value injected by FXMLLoader
    // This method handles the "Register Staff" button click event
    @FXML
    private void addStaff(ActionEvent event){
        // Check if the passwords entered match
        if(staffPassword.getText().equals(staffPasswordConfirm.getText()) ){
            int check = registerStaff(Integer.parseInt(genStaffID.getText()),
            String.valueOf(staffPassword.getText().hashCode()));
            // Check if the staff registration was successful
            if(check == 1){
                System.out.println("success");
                closeWindow((Node) event.getSource());
            }
            else {
                invalidFormMessage.setText("Invalid Details, Please correct and try again");
            }
        }
        else {
            // If passwords do not match, display an error message
            invalidFormMessage.setText("Passwords Do not Match!, Please Correct and Try Again");
        }
    }
    public void closeWindow(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void cancelReg(ActionEvent event){
        closeWindow((Node) event.getSource());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set the generated Staff ID in the label when the window is initialized
        genStaffID.setText(getNewID("Users", "ID"));
    }
}
