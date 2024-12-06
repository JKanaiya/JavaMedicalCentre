package com.medicalproject.Controllers;

import com.medicalproject.MapEntry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

import static com.medicalproject.DB.DBCRUD.addAppointment;
import static com.medicalproject.TimeControl.convertToLocalDateTime;

public class AppointmentFormController {

    @FXML
    private Label appointmentDate;

    @FXML
    private TextField appointmentDescription;

    @FXML
    private Label appointmentTime;

    @FXML
    private Button cancelNewAppointment;

    @FXML
    private TextField doctorID;

    @FXML
    private TextField patientID;

    @FXML
    private ChoiceBox<String> availableDoctors;

    String spec = "";
    Map<Integer, String> temps = new HashMap<>();
    @FXML
    // Populates the availableDoctors list with doctors and sets specialization
    public void populateDoctorList(Map<Integer, String> tempHold, String specialization){
        temps = tempHold;
        temps.forEach((key, value) -> availableDoctors.getItems().add(String.valueOf(new MapEntry(key, value))));
        spec = specialization;
    }
    @FXML
    // Confirms the addition of the appointment by extracting data from the form and calling the database method
    private void confirmAddAppointment(ActionEvent event) {
        // Getting the selected item
        String selected = availableDoctors.getValue();
        // Extract the integer
        Integer foundKey = null;
        for (Map.Entry<Integer, String> entry : temps.entrySet()) {
            if (entry.getValue().equals(availableDoctors.getValue())) {
                foundKey = entry.getKey();
                break;
            }
        }
        // Calls the method to add the appointment to the database
        addAppointment(Integer.parseInt(patientID.getText()), foundKey,
                appointmentDescription.getText(), convertToLocalDateTime(appointmentTime.getText(), appointmentDate.getText()), spec);
        // Closes the appointment window after the confirmation
        closeWindow((Node) event.getSource());
    }
    public void setDateTime(String date, String time){
        appointmentDate.setText(date);
        appointmentTime.setText(time);
    }
    @FXML
    // Cancels the appointment creation and closes the window
    private void cancelAppCreateBtn(ActionEvent event){
        closeWindow((Node) event.getSource());
    }
    public void closeWindow(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}

