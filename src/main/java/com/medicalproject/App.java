package com.medicalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Login.fxml")));
        Scene scene = new Scene(root);
        // Set stage properties
        primaryStage.setScene(scene);
        // Center the window on the screen
        primaryStage.centerOnScreen();
        // Optional: prevent window resizing
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
