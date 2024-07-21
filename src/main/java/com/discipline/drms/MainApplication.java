package com.discipline.drms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/discipline/drms/interfaces/combined/loginPage.fxml"));
        Parent root = loader.load();
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hello!");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
