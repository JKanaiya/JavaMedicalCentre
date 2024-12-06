package com.medicalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

import static com.medicalproject.Controllers.LoginController.role;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/AdminDashboard.fxml")));
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        // Calculate 90% of screen width and height
        double width = screenBounds.getWidth() * 0.9;
        double height = screenBounds.getHeight() * 0.9;

        role = "Admin";

        Scene scene = new Scene(root, width, height);

        // Set stage properties
        primaryStage.setScene(scene);
        // Center the window on the screen
        primaryStage.centerOnScreen();
        // Optional: prevent window resizing
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
