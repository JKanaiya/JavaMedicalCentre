package com.medicalproject.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AppointmentViewController {

    @FXML
    private ListView<?> allList;

    @FXML
    private Button newAppointmentBtn;

    @FXML
    private ListView<?> todayList;

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
}
