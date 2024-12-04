package com.medicalproject.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static com.medicalproject.DB.DBCRUD.validateUser;

public class LoginController {
    public static String role;
    @FXML
    public TextField IDTextField;
    @FXML
    public PasswordField PasswordTextField;
    @FXML
    public Button CancelLoginBtn;
    @FXML
    public Button LoginBtn;
    @FXML
    public Label loginMessageLabel;

    public void setLabel(String message){
        loginMessageLabel.setText(message);
    }

    public static void setRole(String value) {
        role = value;
    }

    public static String getRole(){
        return role;
    }

    public void loginButtonOnAction(ActionEvent ae){
        if(!IDTextField.getText().isBlank() && !PasswordTextField.getText().isBlank()){
            System.out.println(PasswordTextField.getText().hashCode());
            String pass = "hashed";
            System.out.println(pass.hashCode());

            if (attemptLogin(Integer.parseInt(IDTextField.getText()), PasswordTextField.getText())){
                Stage stage = (Stage) LoginBtn.getScene().getWindow();
                stage.close();
//              load the appropriate stage here
            }
        }
        else{
            setLabel("Invalid Login, Please try again");
        }

    }

    public void cancelButtonOnAction (ActionEvent ae){
        Stage stage = (Stage) CancelLoginBtn.getScene().getWindow();
        stage.close();
    }

    public boolean attemptLogin(int ID, String password){
        try{
            if(validateUser(ID, String.valueOf(password.hashCode()))){
//                if need be, add a window or smth here to show that it was successful
                System.out.println("Login Successful.\nWelcome " + getRole());
                return true;
            }
            else{
                setLabel("Invalid Login, Please try again");
                return false;
            }
        }
        catch (NumberFormatException nfe){
            setLabel("Invalid input format, please input a valid id");
            return false;
        }
    }
}
