package models.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static sample.Main.changeScreen;

public class OuterController {

    @FXML
    AnchorPane quit_game_ask;

    public Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

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

    @FXML
    public void lobbyScreen() throws Exception {
        changeScreen("../models/controllers/MiddleController.fxml");
    }







}
