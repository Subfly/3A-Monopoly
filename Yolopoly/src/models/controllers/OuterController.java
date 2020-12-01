package models.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import static sample.Main.changeScreen;

public class OuterController {

    @FXML
    AnchorPane quit_game_ask, menu, coming_soon, nick_handler;

    boolean menu_is_enable = true;

    @FXML
    public void nick_handler(KeyEvent e) throws Exception {
        if (e.getCode().equals(KeyCode.ENTER)){
            //Get Nick //TODO
            changeScreen("../models/controllers/MiddleController.fxml");
        }
        else if (e.getCode().equals(KeyCode.ESCAPE)){
            nick_handler.setStyle("-fx-opacity: 0;");
            nick_handler.setDisable(true);
            set_menu_enable_disable();
        }
    }

    @FXML
    private void quitGamePressed(){
        quit_game_ask.setStyle("-fx-opacity: 1;");
        quit_game_ask.setDisable(false);
        set_menu_enable_disable();
    }

    @FXML
    private void quitGameNoPressed(){
        quit_game_ask.setStyle("-fx-opacity: 0;");
        quit_game_ask.setDisable(true);
        set_menu_enable_disable();
    }

    @FXML
    private void quitGameYesPressed(){
        System.exit(0);
    }

    @FXML
    private void coming_soon(){
        coming_soon.setStyle("-fx-opacity: 1;");
        coming_soon.setDisable(false);
        set_menu_enable_disable();
    }

    @FXML
    private void close_coming_soon(){
        coming_soon.setStyle("-fx-opacity: 0;");
        coming_soon.setDisable(true);
        set_menu_enable_disable();
    }

    @FXML
    public void lobbyScreen(){
        nick_handler.setStyle("-fx-opacity: 1;");
        nick_handler.setDisable(false);
        set_menu_enable_disable();
    }

    private void set_menu_enable_disable(){
        menu_is_enable = !menu_is_enable;
        if (menu_is_enable){
            menu.setDisable(false);
            menu.setStyle("-fx-opacity: 1;");
        }
        else {
            menu.setDisable(true);
            menu.setStyle("-fx-opacity: 0;");
        }
    }





}
