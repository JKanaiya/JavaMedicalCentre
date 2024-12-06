package com.medicalproject.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

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

    public static int instanceID;

    public void loginButtonOnAction(ActionEvent ae){
        if(!IDTextField.getText().isBlank() && !PasswordTextField.getText().isBlank()){
            if (attemptLogin(Integer.parseInt(IDTextField.getText()), PasswordTextField.getText())){
                Stage stage = (Stage) LoginBtn.getScene().getWindow();
                stage.close();
                System.out.println(getRole());
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
                instanceID = ID;
                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                // Calculate 90% of screen width and height
                double width = screenBounds.getWidth() * 0.9;
                double height = screenBounds.getHeight() * 0.9;

                switch (getRole()) {
                    case "Admin" -> {
                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/AdminDashboard.fxml")));
                        Stage stage = new Stage();
                        Scene scene = new Scene(root, width, height);
                        stage.setScene(scene);
                        // Center the window on the screen
                        stage.centerOnScreen();
                        stage.show();
                    }
                    case "Doctor" -> {
                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/DoctorDashboard.fxml")));
                        Stage stage = new Stage();
                        Scene scene = new Scene(root, width, height);

                        stage.setScene(scene);
                        // Center the window on the screen
                        stage.centerOnScreen();
                        stage.show();
                    }
                    case "Staff" -> {
                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/StaffDashboard.fxml")));
                        Stage stage = new Stage();
                        Scene scene = new Scene(root, width, height);
                        stage.setScene(scene);
                        // Center the window on the screen
                        stage.centerOnScreen();
                        stage.show();
                    }
                }
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
