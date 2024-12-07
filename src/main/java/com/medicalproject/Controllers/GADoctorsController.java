package com.medicalproject.Controllers;

import com.medicalproject.DB.DBCRUD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Map;
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
//

    /**
     *  Checks the DB to see if there are any Doctors within the specialized field that are available at the given tim
     * @param event to close window on complete
     * @throws IOException
     */
    @FXML
    private void checkSpecializationAvailability(ActionEvent event) throws IOException {
        String dateFormat = dateSelected.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Map<Integer, String> tempHold = DBCRUD.getSpecializedMap(specializationSelected.getText(), convertToLocalDateTime(timeSelected.getText(), dateFormat));
        if (!tempHold.isEmpty()) {

            closeWindow((Node) event.getSource());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AppointmentForm.fxml"));
            Parent root = loader.load();
            AppointmentFormController apc = loader.getController();
            apc.setDateTime(dateFormat, timeSelected.getText());
            apc.populateDoctorList(tempHold, specializationSelected.getText());

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            // Center the window on the screen
            stage.centerOnScreen();
            stage.show();
        } else {
            errorMessageLabel.setText("Sorry, No Doctors in that field are available at that time");
        }
    }
    /**
     *  Loads and displays the Appointment Form
     * @throws IOException
     */
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
    public void closeWindow(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public void closeWindow(ActionEvent event) {
        closeWindow((Node) event.getSource());
    }
}