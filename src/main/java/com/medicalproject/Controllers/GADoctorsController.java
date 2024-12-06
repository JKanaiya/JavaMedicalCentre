package com.medicalproject.Controllers;

import com.medicalproject.DB.DBCRUD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.medicalproject.TimeControl.convertToLocalDateTime;

public class GADoctorsController {

    @FXML
    private Button cancelCheckBtn;

    @FXML
    private Button checkAvailableBtn;

    @FXML
    private DatePicker dateSelected;

    @FXML
    private Label errorMessageLabel;

    @FXML
    private TextField specializationSelected;

    @FXML
    private TextField timeSelected;

    @FXML
    private void checkSpecializationAvailability(ActionEvent event) throws IOException {
        if (!DBCRUD.getSpecializedMap(specializationSelected.getText(), convertToLocalDateTime(timeSelected.getText(), dateSelected.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))).isEmpty()) {
            closeWindow((Node) event.getSource());
            loadAppointmentForm();
        } else {
            errorMessageLabel.setText("Sorry, No Doctors in that field are available at that time");
        }
    }


    @FXML
    private void loadAppointmentForm() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/AppointmentForm.fxml")));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        // Center the window on the screen
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    private void closeWindow(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public void closeWindow(ActionEvent event) {
        closeWindow((Node) event.getSource());
    }
}