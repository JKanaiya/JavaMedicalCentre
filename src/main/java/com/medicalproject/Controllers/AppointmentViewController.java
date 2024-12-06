package com.medicalproject.Controllers;

import com.medicalproject.DB.DBCRUD;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.medicalproject.Controllers.LoginController.*;
import static com.medicalproject.DB.DBCRUD.getFutureAppointments;
import static com.medicalproject.DB.DBCRUD.searchAppointmentDB;

public class AppointmentViewController implements Initializable {

    @FXML
    private ListView<Map<String,String>> allList;

    @FXML
    private Button newAppointmentBtn;

    @FXML
    private ListView<Map<String,String>> todayList;
    /**
     * Method to open the "getAvailableDoctors" window for scheduling a new appointment.
     * This is triggered when the "New Appointment" button is clicked.
     */
    @FXML
    private void attemptNewAppointment() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/getAvailableDoctors.fxml")));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        // Center the window on the screen
        stage.centerOnScreen();
        stage.show();
    }
    /**
     * Method to populate the allList (future appointments) based on the user's role.
     * Doctors see appointments related to them, while patients see appointments for them.
     */
    public void setSearchAppointments(){
        if(role.equals("Doctor")){
            allList.setItems(FXCollections.observableArrayList(searchAppointmentDB(instanceID)));
            // Customize how each item appears in the ListView
            allList.setCellFactory(lv -> new ListCell<>() {
                @Override
                public void updateItem(Map<String, String> appointment, boolean empty) {
                    super.updateItem(appointment, empty);
                    if (empty || appointment == null) {
                        setText(null);
                    } else {
                        // Format how each patient appears in the list
                        setText(String.format("Time: %s        DoctorID: %s         PatientID: %s         Date: %s        Specialization: %s    ",
                                appointment.get("time"),
                                appointment.get("doctorID"),
                                appointment.get("patientID"),
                                appointment.get("date"),
                                appointment.get("specialization")));
                    }
                }
            });
        }
        else{
            allList.setItems(FXCollections.observableArrayList(getFutureAppointments()));
            // Customize how each item appears in the ListView
            allList.setCellFactory(lv -> new ListCell<>() {
                @Override
                public void updateItem(Map<String, String> appointment, boolean empty) {
                    super.updateItem(appointment, empty);
                    if (empty || appointment == null) {
                        setText(null);
                    } else {
                        // Format how each patient appears in the list
                        setText(String.format("Time: %s        DoctorID: %s         PatientID: %s         Date: %s        Specialization: %s    ",
                                appointment.get("time"),
                                appointment.get("doctorID"),
                                appointment.get("patientID"),
                                appointment.get("date"),
                                appointment.get("specialization")));
                    }
                }
            });
        }
        }
    /**
     * Method to populate the todayList (appointments for today) based on the user's role.
     * Doctors see today's appointments for themselves, while patients see their own appointments.
     */
    public void getTodayAppointments(){
        if(role.equals("Doctor")){
            todayList.setItems(FXCollections.observableArrayList(DBCRUD.getDoctorTodayAppointments(instanceID)));
            // Customize how each item appears in the ListView
            todayList.setCellFactory(lv -> new ListCell<>() {
                @Override
                public void updateItem(Map<String, String> appointment, boolean empty) {
                    super.updateItem(appointment, empty);
                    if (empty || appointment == null) {
                        setText(null);
                    } else {
                        // Format how each patient appears in the list
                        setText(String.format("Time: %s        DoctorID: %s         PatientID: %s         Date: %s        Specialization: %s    ",
                                appointment.get("time"),
                                appointment.get("doctorID"),
                                appointment.get("patientID"),
                                appointment.get("date"),
                                appointment.get("specialization")));
                    }
                }
            });
        }
        else{
            allList.setItems(FXCollections.observableArrayList(DBCRUD.getTodayAppointments()));
            // Customize how each item appears in the ListView
            allList.setCellFactory(lv -> new ListCell<>() {
                @Override
                public void updateItem(Map<String, String> appointment, boolean empty) {
                    super.updateItem(appointment, empty);
                    if (empty || appointment == null) {
                        setText(null);
                    } else {
                        // Format how each patient appears in the list
                        setText(String.format("Time: %s        DoctorID: %s         PatientID: %s         Date: %s        Specialization: %s    ",
                                appointment.get("time"),
                                appointment.get("doctorID"),
                                appointment.get("patientID"),
                                appointment.get("date"),
                                appointment.get("specialization")));
                    }
                }
            });
        }
    }
    /**
     * This method is called automatically when the controller is initialized.
     * It sets up the future appointments and today's appointments based on the user's role.
     */
   @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSearchAppointments();
        getTodayAppointments();
    }
}
