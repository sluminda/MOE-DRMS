package com.discipline.drms.template;

import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class FloatingLabelTextField extends StackPane {
    private TextField textField;
    private Label floatingLabel;

    public FloatingLabelTextField() {
        textField = new TextField();
        floatingLabel = new Label();
        floatingLabel.getStyleClass().add("floating-label");

        textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                translateLabelUp();
            } else {
                if (textField.getText().isEmpty()) {
                    translateLabelDown();
                }
            }
        });

        getChildren().addAll(floatingLabel, textField);
        setStyle("-fx-padding: 10; -fx-alignment: center-left;");
    }

    public void setLabelText(String text) {
        floatingLabel.setText(text);
    }

    public TextField getTextField() {
        return textField;
    }

    private void translateLabelUp() {
        TranslateTransition transition = new TranslateTransition(Duration.millis(200), floatingLabel);
        transition.setToY(-25);
        transition.play();
    }

    private void translateLabelDown() {
        TranslateTransition transition = new TranslateTransition(Duration.millis(200), floatingLabel);
        transition.setToY(0);
        transition.play();
    }
}
