package com.medicalproject.Controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.medicalproject.TimeControl.getDateFromLDT;
import static com.medicalproject.TimeControl.getTimeFromLDT;

public class DoctorDashboardController implements Initializable {

    @FXML
    private Label dateNow;

    @FXML
    private Button LogOut;

    @FXML
    private Button navAppointments;

    @FXML
    private StackPane contentArea;

    @FXML
    private Button navGenerateBill;

    @FXML
    private Button navSearch;

    @FXML
    private Label timeNow;
    /**
     * Initialize method that sets up the dashboard.
     * Loads the initial content (AppointmentView) and sets up a timeline
     * to update the time label every minute.
     */
    public void initialize(URL url, ResourceBundle rb) {
        loadFXML("AppointmentView.fxml");
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.minutes(1), event -> updateTime())
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
        timeline.play();
        timeNow.setText(getTimeFromLDT(LocalDateTime.now()));
        dateNow.setText(getDateFromLDT(LocalDateTime.now()));
    }
    /**
     * Updates the time displayed on the UI to the current time.
     */
    private void updateTime() {
        timeNow.setText(getTimeFromLDT(LocalDateTime.now()));
    }
    /**
     * Opens the Bill generation view when the generate bill button is clicked.
     *
     * @param event The action event triggered by the button click.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    @FXML
    private void generateBill(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Bills.fxml")));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            // Center the window on the screen
            stage.centerOnScreen();
            stage.show();
    }
    /**
     * Logs out the user by closing the current window.
     *
     * @param event The action event triggered by the button click.
     */
    @FXML
    private void logOut(ActionEvent event) {
        Stage stage = (Stage) LogOut.getScene().getWindow();
        stage.close();
    }
    /**
     * Opens the Appointment view when the appointments button is clicked.
     *
     * @param event The action event triggered by the button click.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    @FXML
    private void viewAppointments(ActionEvent event) throws IOException {
        loadFXML("AppointmentView.fxml");
    }
    /**
     * Opens the Search view when the search button is clicked.
     *
     * @param event The action event triggered by the button click.
     */
    @FXML
    private void viewSearch(ActionEvent event) {
        loadFXML("Search.fxml");
    }
    /**
     * Loads the FXML file into the content area of the dashboard.
     * It clears the previous content and adds the new view.
     *
     * @param fxmlFile The name of the FXML file to load.
     */
    private void loadFXML(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxmlFile));
            Node view = loader.load();
            // Set size constraints on the loaded view
            if (view != null) {
                Region region = (Region) view;
                // Allow the view to grow as large as possible
                region.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                // Remove any minimum size constraint
                region.setMinSize(0, 0);
                // Let the parent (StackPane) determine the size
                region.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            }

            // Ensure the view uses full width/height in the StackPane
            StackPane.setAlignment(view, Pos.CENTER);
            StackPane.setMargin(view, new Insets(0));
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
