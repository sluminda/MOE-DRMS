package com.discipline.drms.login;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import java.net.URL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController {
    @FXML
    private TextField user;

    @FXML
    private PasswordField password;

    @FXML
    private Button login;

    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());

    @FXML
    public void handleLogin() {
        String username = user.getText();
        String pwd = password.getText();

        if (username.isEmpty() || pwd.isEmpty()) {
            showAlert("Please enter both username and password");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/drms";
        String dbUser = "root";
        String dbPassword = "1220";

        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword)) {
            String sql = "SELECT password_hash, salt, role FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String passwordHash = resultSet.getString("password_hash");
                String salt = resultSet.getString("salt");
                String role = resultSet.getString("role");

                boolean isPasswordValid = PasswordUtil.verifyPassword(pwd, passwordHash, salt);
                if (isPasswordValid) {
                    loadMainPage(role);
                } else {
                    showAlert("Invalid username or password");
                }
            } else {
                showAlert("Invalid username or password");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Database connection failed", e);
            showAlert("Database connection failed: " + e.getMessage());
        }
    }

    private void loadMainPage(String role) {
        try {
            URL fxmlUrl = getClass().getResource("/com/discipline/drms/interfaces/combined/mainPage.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            MainPageController mainPageController = loader.getController();
            if (mainPageController != null) {
                mainPageController.setUserRole(role);
            } else {
                LOGGER.log(Level.SEVERE, "MainPageController is null");
            }

            Stage stage = (Stage) login.getScene().getWindow();
            Scene scene = new Scene(root, 800, 600);

            stage.setScene(scene);

            // Force a layout pass
            root.requestLayout();

            // Force a resize event to ensure proper rendering
//            stage.setWidth(stage.getWidth() + 1);
//            stage.setHeight(stage.getHeight() + 1);
//            stage.setWidth(stage.getWidth() - 1);
//            stage.setHeight(stage.getHeight() - 1);

            stage.show();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to load the main page", e);
            showAlert("Failed to load the main page: " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
