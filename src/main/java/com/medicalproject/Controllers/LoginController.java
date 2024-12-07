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
    /**
     * Updates the login message label with the provided message.
     * @param message Message to display on the loginMessageLabel.
     */
    public void setLabel(String message){
        loginMessageLabel.setText(message);
    }
    /**
     * Sets the role of the user.
     * @param value The role to be set.
     */
    public static void setRole(String value) {
        role = value;
    }

    /**
     * Retrieves the role of the user.
     * @return The user's role.
     */
    public static String getRole(){
        return role;
    }

    public static int instanceID;
    /**
     * Handles the login button click event.
     * Validates inputs, hashes the password, and attempts login.
     * @param ae The event triggered by the login button click.
     */
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
    /**
     * Handles the cancel button click event.
     * Closes the current login window.
     * @param ae The event triggered by the cancel button click.
     */
    public void cancelButtonOnAction (ActionEvent ae){
        Stage stage = (Stage) CancelLoginBtn.getScene().getWindow();
        stage.close();
    }
    /**
     * Attempts to log in the user by validating their credentials.
     * @param ID The user ID entered in the text field.
     * @param password The plain-text password entered in the password field.
     * @return True if login is successful, false otherwise.
     */
    public boolean attemptLogin(int ID, String password){
        try{
            if(validateUser(ID, String.valueOf(password.hashCode()))){
                instanceID = ID;
                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                // Calculate 90% of screen width and height
                double width = screenBounds.getWidth() * 0.9;
                double height = screenBounds.getHeight() * 0.9;
//               Load the Appropriate Windows based on the role of the user
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
