package models.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class OuterController {

    @FXML
    AnchorPane quit_game_ask;

    @FXML
    AnchorPane main_anchor;

    @FXML
    private void quitGamePressed(){
        quit_game_ask.setDisable(false);
        quit_game_ask.setStyle("-fx-opacity: 1;");
    }

    @FXML
    private void quitGameYesPressed(){
        System.exit(0);
    }

    @FXML
    private void quitGameNoPressed(){
        quit_game_ask.setStyle("-fx-opacity: 0;");
        quit_game_ask.setDisable(true);
    }






}
