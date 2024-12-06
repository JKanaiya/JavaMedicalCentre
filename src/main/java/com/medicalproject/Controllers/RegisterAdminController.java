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

public class RegisterAdminController implements Initializable {
    @FXML // fx:id="adminPassword"
    private PasswordField adminPassword; // Value injected by FXMLLoader

    @FXML // fx:id="adminPasswordConfirm"
    private PasswordField adminPasswordConfirm; // Value injected by FXMLLoader

    @FXML // fx:id="cancelRegBtn"
    private Button cancelRegBtn; // Value injected by FXMLLoader

    @FXML // fx:id="genAdminID"
    private Label genAdminID; // Value injected by FXMLLoader

    @FXML // fx:id="invalidFormMessage"
    private Label invalidFormMessage; // Value injected by FXMLLoader

    @FXML // fx:id="registerAdminBtn"
    private Button registerAdminBtn; // Value injected by FXMLLoader

    @FXML
    private void addAdmin(ActionEvent event){
        if(adminPassword.getText().equals(adminPasswordConfirm.getText()) ){
            int check = registerAdmin(Integer.parseInt(genAdminID.getText()),
                    String.valueOf(adminPassword.getText().hashCode()));
            if(check == 1){
                System.out.println("success");
                closeWindow((Node) event.getSource());
            }
            else {
                invalidFormMessage.setText("Invalid Details, Please correct and try again");
            }
        }
        else {
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
        genAdminID.setText(getNewID("Users", "ID"));
    }
}
