package cc.wangzijie.ocr.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class MainWindowController {
    @FXML
    public Button helloButton;
    @FXML
    private Label welcomeText;


    public void onHelloButtonClicked(ActionEvent e) {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
