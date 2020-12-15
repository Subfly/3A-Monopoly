package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import managers.LobbyManager;
import managers.MainMenuManager;


import static main.Main.changeScreen;

public class OuterController {

    @FXML
    AnchorPane quit_game_ask, menu, coming_soon, nick_handler, settings;

    @FXML
    Slider music_slider = new Slider();

    @FXML
    TextArea nick_handler_field;

    @FXML
    TextField music_field;

    boolean menu_is_enable = true;

    LobbyManager me;
    MainMenuManager oe;

    public OuterController(){
        me = LobbyManager.getInstance();
        oe = MainMenuManager.getInstance();
    }

    public void initialize() {
        music_slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue.doubleValue());
            music_field.setText(String.valueOf(newValue.doubleValue()));
        });

    }

    @FXML
    public void nick_handler(KeyEvent e) throws Exception {
        if (e.getCode().equals(KeyCode.ENTER)){
            oe.setHosterNick(nick_handler_field.getText().substring(0, nick_handler_field.getText().length()-1));
            changeScreen("../controllers/MiddleController.fxml");
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
    private void settings(){
        settings.setStyle("-fx-opacity: 1;");
        settings.setDisable(false);
        set_menu_enable_disable();
    }

    @FXML
    private void close_settings(){
        settings.setStyle("-fx-opacity: 0;");
        settings.setDisable(true);
        set_menu_enable_disable();
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
