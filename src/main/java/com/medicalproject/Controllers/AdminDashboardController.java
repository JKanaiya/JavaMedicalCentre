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

public class AdminDashboardController implements Initializable {

    @FXML
    private Label dateNow;

    @FXML
    private Button LogOut;

    @FXML
    private Button navAppointments;

    @FXML
    private Button manageUsersBtn;

    @FXML
    private StackPane contentArea;

    @FXML
    private Button navGenerateBill;

    @FXML
    private Button navSearch;

    @FXML
    private Label timeNow;
    /**
     * This method initializes the controller, loads the default view,
     * and starts a timeline to update the time every minute.
     *
     * @param url URL for the location of the FXML file
     * @param rb ResourceBundle containing any resources needed by the FXML
     */
    public void initialize(URL url, ResourceBundle rb) {
        // Load the default view (AppointmentView.fxml) into the content area
        loadFXML("AppointmentView.fxml");
        // Set up a timeline to update the time every minute
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.minutes(1), event -> updateTime())
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
        timeline.play();
        // Set the current time and date
        timeNow.setText(getTimeFromLDT(LocalDateTime.now()));
        dateNow.setText(getDateFromLDT(LocalDateTime.now()));
    }
    /**
     * This method updates the time label every minute.
     */
    private void updateTime() {
        // Update the time label with the current time
        timeNow.setText(getTimeFromLDT(LocalDateTime.now()));
    }
    /**
     * This method is called when the "Generate Bill" button is clicked.
     * It opens a new window (Bills.fxml) for generating bills.
     *
     * @param event The action event triggered by the button click
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    private void generateBill(ActionEvent event) throws IOException {
        // Load the Bills view in a new window
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Bills.fxml")));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        // Center the window on the screen and show it
        stage.centerOnScreen();
        stage.show();
    }


    @FXML
    private void logOut(ActionEvent event) {
        Stage stage = (Stage) LogOut.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void viewAppointments(ActionEvent event) throws IOException {
        loadFXML("AppointmentView.fxml");
    }
    /**
     * This method is called when the "Search" button is clicked.
     * It loads the adminSearch.fxml into the content area of the dashboard.
     *
     * @param event The action event triggered by the button click
     */
    @FXML
    private void viewSearch(ActionEvent event) {
        // Load the Search view into the content area
        loadFXML("adminSearch.fxml");
    }

    /**
     *  Method displays the RegisterStaff Form
     * @throws IOException
     */
    @FXML
    private void addStaff() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/RegisterStaff.fxml")));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        // Center the window on the screen
        stage.centerOnScreen();
        stage.show();
    }
    /**
     *  Method displays the RegisterAdmin Form
     * @throws IOException
     */
    @FXML
    private void addAdmin() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/RegisterAdmin.fxml")));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        // Center the window on the screen
        stage.centerOnScreen();
        stage.show();
    }
    /**
     *  Method displays the RegisterPatient Form
     * @throws IOException
     */
    @FXML
    private void addPatient() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/RegisterPatient.fxml")));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        // Center the window on the screen
        stage.centerOnScreen();
        stage.show();
    }
    /**
     *  Method displays the RegisterDoctor Form
     * @throws IOException
     */
    @FXML
    private void addDoctor() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/RegisterDoctor.fxml")));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        // Center the window on the screen
        stage.centerOnScreen();
        stage.show();
    }
    /**
     * This helper method dynamically loads an FXML file into the content area of the dashboard.
     * It ensures that the loaded view takes up the full available space and is properly aligned.
     *
     * @param fxmlFile The name of the FXML file to be loaded
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
                // Remove any minimum size constraints
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
