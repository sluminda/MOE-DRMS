package com.discipline.drms.login;

import javafx.fxml.FXML;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class MainPageController {

    @FXML
    private TilePane tilePane;

    @FXML
    private VBox dashboard;
    @FXML
    private VBox dailyLetters;
    @FXML
    private VBox disciplinaryAction;
    @FXML
    private VBox tableView;
    @FXML
    private VBox reports;
    @FXML
    private VBox masterTables;

    @FXML
    public void initialize() {
        if (tilePane != null) {
            System.out.println("TilePane initialized");
        }
        if (dashboard != null && dailyLetters != null && disciplinaryAction != null &&
                tableView != null && reports != null && masterTables != null) {
            System.out.println("All VBox elements initialized");
        }
    }

    public void setUserRole(String userRole) {
        boolean isOwner = "Owner".equals(userRole);
        boolean isAdmin = "Admin".equals(userRole);

        if (dashboard != null) dashboard.setVisible(true);
        if (dailyLetters != null) dailyLetters.setVisible(true);
        if (disciplinaryAction != null) disciplinaryAction.setVisible(true);
        if (tableView != null) tableView.setVisible(true);

        if (reports != null) reports.setVisible(isOwner);
        if (masterTables != null) masterTables.setVisible(isOwner || isAdmin);

        if (tilePane != null) {
            tilePane.getChildren().removeIf(node -> !node.isVisible());
            tilePane.requestLayout();
        }
    }
}
