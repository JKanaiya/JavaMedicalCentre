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
    public void populateDoctorList(Map<Integer, String> tempHold, String specialization){
        temps = tempHold;
        temps.forEach((key, value) -> availableDoctors.getItems().add(String.valueOf(new MapEntry(key, value))));
        spec = specialization;
    }
    @FXML
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
        addAppointment(Integer.parseInt(patientID.getText()), foundKey,
                appointmentDescription.getText(), convertToLocalDateTime(appointmentTime.getText(), appointmentDate.getText()), spec);
        closeWindow((Node) event.getSource());
    }
    public void setDateTime(String date, String time){
        appointmentDate.setText(date);
        appointmentTime.setText(time);
    }
    @FXML
    private void cancelAppCreateBtn(ActionEvent event){
        closeWindow((Node) event.getSource());
    }
    public void closeWindow(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}

