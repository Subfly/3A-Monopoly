package com.yolopoly.controllers;

import com.yolopoly.managers.LobbyManager;
import com.yolopoly.managers.MainMenuManager;
import com.yolopoly.models.bases.GameListData;
import com.yolopoly.storage.FirebaseUtil;
import com.yolopoly.storage.StorageUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.HashMap;

import static com.yolopoly.Main.*;

public class OuterController {

    @FXML
    AnchorPane quit_game_ask, menu, coming_soon, nick_handler, settings;

    @FXML
    Slider music_slider = new Slider();

    @FXML
    TextArea nick_handler_field;

    @FXML
    TextField music_field;

    @FXML
    AnchorPane game_list;

    @FXML
    AnchorPane multip_options;

    @FXML
    AnchorPane feedback_screen;

    @FXML
    Label name_0, name_1, name_2, name_3, name_4, size_0, size_1, size_2, size_3, size_4, settings_0, settings_1, settings_2, settings_3, settings_4;

    @FXML
    Label join_0, join_1, join_2, join_3, join_4;

    @FXML
    ImageView field_0, field_1, field_2, field_3, field_4;

    Label[] server_names;
    Label[] server_sizes;
    Label[] server_settings;
    Label[] server_joins;
    ImageView[] server_fields;

    boolean menu_is_enable = false;

    LobbyManager me;
    MainMenuManager oe;


    public OuterController(){
        me = LobbyManager.getInstance();
        oe = MainMenuManager.getInstance();
    }

    public void initialize() {

        server_names = new Label[]{name_0, name_1, name_2, name_3, name_4};
        server_sizes = new Label[]{size_0, size_1, size_2, size_3, size_4};
        server_settings = new Label[]{settings_0, settings_1, settings_2, settings_3, settings_4};
        server_joins = new Label[]{join_0, join_1, join_2, join_3, join_4};
        server_fields = new ImageView[]{field_0, field_1, field_2, field_3, field_4};

        music_slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue.doubleValue());
            music_field.setText(String.valueOf(newValue.doubleValue()));
        });

        if (oe.isNickSet()){
            nick_handler.setVisible(false);
            nick_handler.setDisable(true);
            set_menu_enable_disable();
        }
        else {
            nick_handler.setVisible(true);
            nick_handler.setDisable(false);
        }
    }

    @FXML
    public void nick_handler(KeyEvent e) throws Exception {
        if (e.getCode().equals(KeyCode.ENTER)){
            oe.setHosterNick(nick_handler_field.getText().substring(0, nick_handler_field.getText().length()-1));
            nick_handler.setVisible(false);
            nick_handler.setDisable(true);
            set_menu_enable_disable();
        }
        else if (e.getCode().equals(KeyCode.ESCAPE)){
            System.exit(0);
        }
    }

    @FXML
    private void quitGamePressed(){
        quit_game_ask.setVisible(true);
        quit_game_ask.setDisable(false);
        set_menu_enable_disable();
    }

    @FXML
    private void quitGameNoPressed(){
        quit_game_ask.setVisible(false);
        quit_game_ask.setDisable(true);
        set_menu_enable_disable();
    }

    @FXML
    private void quitGameYesPressed(){
        System.exit(0);
    }

    @FXML
    private void settings(){
        settings.setVisible(true);
        settings.setDisable(false);
        set_menu_enable_disable();
    }

    @FXML
    private void close_settings(){
        settings.setVisible(false);
        settings.setDisable(true);
        set_menu_enable_disable();
    }

    @FXML
    private void coming_soon(){
        coming_soon.setVisible(true);
        coming_soon.setDisable(false);
        set_menu_enable_disable();
    }

    @FXML
    private void close_coming_soon(){
        coming_soon.setVisible(false);
        coming_soon.setDisable(true);
        set_menu_enable_disable();
    }

    @FXML
    public void single_player() throws Exception{
        me.setOnline(false);
        changeScreen("src/main/resources/scenes/MiddleController.fxml");
    }

    private void set_menu_enable_disable(){
        menu_is_enable = !menu_is_enable;
        if (menu_is_enable){
            menu.setDisable(false);
            menu.setVisible(true);
        }
        else {
            menu.setDisable(true);
            menu.setVisible(false);
        }
    }

    @FXML
    public void multiplayer() throws Exception{
        me.setOnline(true);
        changeScreen("src/main/resources/scenes/MiddleController.fxml");
    }

    @FXML
    public void show_game_list(){
        create_game_list();
        game_list.setVisible(true);
        game_list.setDisable(false);

        multip_options.setDisable(true);
        multip_options.setVisible(false);
    }

    @FXML
    public void close_game_list(){
        game_list.setVisible(false);
        game_list.setDisable(true);
        set_menu_enable_disable();
    }

    @FXML
    public void show_saved_game_options(){
        multip_options.setVisible(true);
        multip_options.setDisable(false);
        set_menu_enable_disable();
    }

    public void create_game_list(){
        StorageUtil util = new StorageUtil();
        int counter = 0;
        for (int i = 0;i<5;i++){
            server_names[i].setText("");
            server_settings[i].setText("");
            server_sizes[i].setText("");
            server_joins[i].setText("");
            server_fields[i].setVisible(false);
        }
        HashMap<String, ArrayList<String>> k = util.getSavedGames();

        for (String s : k.keySet()){
            server_names[counter].setText(k.get(s).get(0));
            server_settings[counter].setText(k.get(s).get(1).toUpperCase());
            server_sizes[counter].setText(k.get(s).get(2).toUpperCase());
            server_joins[counter].setText("Load");
            server_fields[counter].setVisible(true);
            counter++;
        }
    }

    @FXML
    public void join_game(MouseEvent e) throws Exception {
        me.setOnline(true);
        changeScreen("src/main/resources/scenes/MiddleController.fxml");
    }

    @FXML
    public void create_game() throws Exception{
        me.setOnline(false);
        changeScreen("src/main/resources/scenes/MiddleController.fxml");
    }

    @FXML
    public void open_feedback(){
        feedback_screen.setVisible(true);
        feedback_screen.setDisable(false);
        set_menu_enable_disable();
    }

    @FXML
    public void send_feedback(){
        feedback_screen.setVisible(false);
        feedback_screen.setDisable(true);
        set_menu_enable_disable();
    }

    @FXML
    public void close_feedback(){
        feedback_screen.setVisible(false);
        feedback_screen.setDisable(true);
        set_menu_enable_disable();
    }


}
