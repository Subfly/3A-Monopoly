package com.yolopoly.controllers;

import com.yolopoly.Main;
import com.yolopoly.enumerations.GameMode;
import com.yolopoly.enumerations.GameTheme;
import com.yolopoly.managers.InGameManager;
import com.yolopoly.managers.LobbyManager;
import com.yolopoly.managers.MainMenuManager;
import com.yolopoly.models.bases.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Objects;


public class MiddleController {

    @FXML //Lobby Size Button
    ImageView lobby_size2, lobby_size3, lobby_size4, lobby_size5, lobby_size6, lobby_size7, lobby_size8;
    @FXML //Mode Buttons
    ImageView mode_vanilla, mode_bankman;
    @FXML //Theme Buttons
    ImageView theme_vanilla, theme_bilkent, theme_ankara, theme_halloween;
    @FXML //Pawn Buttons
    ImageView pawn_button1, pawn_button2, pawn_button3, pawn_button4, pawn_button5, pawn_button6, pawn_button7, pawn_button8;
    @FXML //Kick Buttons
    ImageView kick_player_1, kick_player_2, kick_player_3, kick_player_4, kick_player_5, kick_player_6, kick_player_7;
    @FXML //Nick Labels
    Label nick_player_0, nick_player_1, nick_player_2, nick_player_3, nick_player_4, nick_player_5, nick_player_6, nick_player_7;
    @FXML //Player's Rolled Dice - For Online - //TODO
    ImageView dice_player_0, dice_player_1, dice_player_2, dice_player_3, dice_player_4, dice_player_5, dice_player_6, dice_player_7;
    @FXML //Player's Chosen Pawns - For Online - //TODO
    ImageView pawn_player_0, pawn_player_1, pawn_player_2, pawn_player_3, pawn_player_4, pawn_player_5, pawn_player_6, pawn_player_7;
    @FXML //Lobby Heading
    Label lobby_heading;
    @FXML
    Label test_label;
    @FXML
    GridPane player_list_grid;

    ImageView[] theme_buttons;
    ImageView[] lobby_size_buttons;
    ImageView[] pawn_buttons;
    ImageView[] kick_player_buttons;
    Label[] nick_player_labels;
    ImageView[] pawn_players;

    LobbyManager me;
    MainMenuManager oe;
    InGameManager ie;

    int player_count;
    int bot_count;
    String nickname;

    final String LOBBY_SETTINGS = "scenes/sources/lobby-settings/";
    final String LOBBY_PAWNS = "scenes/sources/lobby-settings/pawns/";
    final String PNG = ".png";

    ArrayList<Integer> availablePawns;
    int oldPown = 1;

    public MiddleController() {
        me = LobbyManager.getInstance();
        oe = MainMenuManager.getInstance();
        ie = InGameManager.getInstance();

        this.nickname = oe.getHosterNick();
        this.player_count = 1;
        this.bot_count = 0;

        me.getAdmin().setName(nickname);
        me.getAdmin().setPawnIndex(1);

        player_list_grid = new GridPane();
    }

    @FXML
    public void initialize() {
        theme_buttons = new ImageView[]{theme_vanilla, theme_bilkent, theme_ankara, theme_halloween};
        lobby_size_buttons = new ImageView[]{lobby_size2, lobby_size3, lobby_size4, lobby_size5, lobby_size6, lobby_size7, lobby_size8};
        pawn_buttons = new ImageView[]{pawn_button1, pawn_button2, pawn_button3, pawn_button4, pawn_button5, pawn_button6, pawn_button7, pawn_button8};
        nick_player_labels = new Label[]{nick_player_0, nick_player_1, nick_player_2, nick_player_3, nick_player_4, nick_player_5, nick_player_6, nick_player_7};
        kick_player_buttons = new ImageView[]{kick_player_1, kick_player_2, kick_player_3, kick_player_4, kick_player_5, kick_player_6, kick_player_7};
        pawn_players = new ImageView[]{pawn_player_0, pawn_player_1, pawn_player_2, pawn_player_3, pawn_player_4, pawn_player_5, pawn_player_6, pawn_player_7};
        availablePawns = new ArrayList<>();

        me.setGameTheme(GameTheme.vanilla);
        me.setGameMode(GameMode.vanilla);

        for (int i = 2;i<=8;i++){
            availablePawns.add(i);
        }

        set_all_images();
    }

    private void changePlayerList(){
        int max = 8;
        for (int i = 1; i < max; i++){
            pawn_players[i].setVisible(false);
            nick_player_labels[i].setVisible(false);
            kick_player_buttons[i-1].setVisible(false);
        }
        for (int i = 1;i < player_count;i++){
            pawn_players[i].setVisible(true);
            nick_player_labels[i].setVisible(true);
            nick_player_labels[i].setText(me.getPlayerArrayList().get(i).getName());
            kick_player_buttons[i-1].setVisible(true);
            set_image_helper(pawn_players[i], LOBBY_PAWNS, "pawn-" + me.getPlayerArrayList().get(i).getPawnIndex());
        }
    }

    @FXML
    private void kick_player(MouseEvent e){
        int index = Integer.parseInt(e.getPickResult().getIntersectedNode().getId().replace("kick_player_", ""));
        availablePawns.add(me.getPlayerArrayList().get(index).getPawnIndex());
        me.kickPlayer(index);
        player_count--;
        bot_count--;
        set_lobby_size_min();
        changePlayerList();
        set_log("Bot has been kicked");
        setAvailablePawnsGUI();
    }

    @FXML
    private void add_bot(){
        if (me.addBot()){
            Player tmpBot = me.getPlayerArrayList().get(player_count);
            int randomIndexForBotPawnFalan = (int)(Math.random()*(8 - player_count));
            tmpBot.setPawnIndex(availablePawns.get(randomIndexForBotPawnFalan));
            set_image_helper(pawn_players[player_count], LOBBY_PAWNS, "pawn-" + availablePawns.get(randomIndexForBotPawnFalan));
            availablePawns.remove(randomIndexForBotPawnFalan);
            player_count++;
            bot_count++;
            set_lobby_size_min();
            changePlayerList();
            set_log("Bot has been added");
            setAvailablePawnsGUI();
        }
        else {
            set_log("Max player! Cannot add bot!");
        }
    }

    @FXML
    public void change_pawn(MouseEvent e){
        availablePawns.add(oldPown);
        int chosen_pawn = Integer.parseInt(e.getPickResult().getIntersectedNode().getId().replace("pawn_button", ""));
        pawn_GUI_helper(false, chosen_pawn);
        set_log("Pawn has been changed");
        me.getAdmin().setPawnIndex(chosen_pawn);
        availablePawns.remove((Integer)(chosen_pawn));
        oldPown = chosen_pawn;
        setAvailablePawnsGUI();
    }

    @FXML
    public void change_theme(MouseEvent e){
        String lobby_theme = e.getPickResult().getIntersectedNode().getId().replace("theme_", "");
        me.setGameTheme(GameTheme.valueOf(lobby_theme));
        theme_GUI_helper(lobby_theme);
        set_log("Lobby theme changed to " + lobby_theme);
    }

    @FXML
    public void change_mode(MouseEvent e){
        String lobby_mode = e.getPickResult().getIntersectedNode().getId().replace("mode_", "");
        me.setGameMode(GameMode.valueOf(lobby_mode));
        mode_GUI_helper(lobby_mode);
        set_log("Lobby mode changed to " + lobby_mode);
    }

    @FXML
    public void change_lobby_size(MouseEvent e){
        int lobby_size_option = Integer.parseInt(e.getPickResult().getIntersectedNode().getId().replace("lobby_size", ""));
        me.setMaxPlayerCount(lobby_size_option);
        lobby_size_GUI_helper(lobby_size_option);
        set_log("Lobby size changed to " + lobby_size_option);
    }

    private void setAvailablePawnsGUI(){
        String pawn_image_name = "pawn-";
        for (int i = 0; i < pawn_buttons.length; i++){
            set_image_helper(pawn_buttons[i], LOBBY_PAWNS, pawn_image_name + (i+1));
            pawn_buttons[i].setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 8, 0, 0, 0);");
            pawn_buttons[i].setDisable(true);
        }
        for (Integer i : availablePawns){
            set_image_helper(pawn_buttons[i-1], LOBBY_PAWNS, pawn_image_name + i);
            pawn_buttons[i-1].setStyle("-fx-effect: dropshadow(three-pass-box, rgba(217,24,40,0.6), 8, 0, 0, 0);");
            pawn_buttons[i-1].setDisable(false);
        }
        pawn_buttons[oldPown - 1].setStyle("-fx-effect: dropshadow(three-pass-box, rgba(86,191,132,0.6), 8, 0, 0, 0);");
    }

    private void pawn_GUI_helper(boolean initial, int chosen_pawn){
        if (initial){
            for (int i = 0; i < pawn_buttons.length; i++){
                String pawn_image_name = "pawn-";
                set_image_helper(pawn_buttons[i], LOBBY_PAWNS, pawn_image_name + (i+1));
                pawn_buttons[i].setStyle("-fx-effect: dropshadow(three-pass-box, rgba(217,24,40,0.6), 8, 0, 0, 0);");
            }
        }
        else {
            for (ImageView pawn_button : pawn_buttons) {
                pawn_button.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(217,24,40,0.6), 8, 0, 0, 0);");
            }
        }
        pawn_buttons[chosen_pawn-1].setStyle("-fx-effect: dropshadow(three-pass-box, rgba(86,191,132,0.6), 8, 0, 0, 0);");
        set_image_helper(pawn_player_0, LOBBY_PAWNS, "pawn-" + chosen_pawn);
    }

    private void set_lobby_size_min(){
        for (ImageView lobby_size_button : lobby_size_buttons) {
            lobby_size_button.setDisable(false);
            lobby_size_button.setStyle("-fx-opacity: 100%");
        }
        for (int i = 0; i < player_count - 2; i++){
            lobby_size_buttons[i].setDisable(true);
            lobby_size_buttons[i].setStyle("-fx-opacity: 50%");
        }
    }

    private void theme_GUI_helper(String game_theme){
        set_image_helper(theme_vanilla, LOBBY_SETTINGS, "vanilla-red");
        set_image_helper(theme_bilkent, LOBBY_SETTINGS, "bilkent-red");
        set_image_helper(theme_ankara, LOBBY_SETTINGS, "ankara-red");
        set_image_helper(theme_halloween, LOBBY_SETTINGS, "halloween-red");
        switch (game_theme) {
            case "vanilla" -> set_image_helper(theme_vanilla, LOBBY_SETTINGS, "vanilla-green");
            case "bilkent" -> set_image_helper(theme_bilkent, LOBBY_SETTINGS, "bilkent-green");
            case "ankara" -> set_image_helper(theme_ankara, LOBBY_SETTINGS, "ankara-green");
            case "halloween" -> set_image_helper(theme_halloween, LOBBY_SETTINGS, "halloween-green");
        }
    }

    private void mode_GUI_helper(String game_mode){
        set_image_helper(mode_vanilla, LOBBY_SETTINGS, "vanilla-red");
        set_image_helper(mode_bankman, LOBBY_SETTINGS, "bankman-red");
        switch (game_mode){
            case "vanilla" -> set_image_helper(mode_vanilla, LOBBY_SETTINGS, "vanilla-green");
            case "bankman" -> set_image_helper(mode_bankman, LOBBY_SETTINGS, "bankman-green");
        }
    }

    private void lobby_size_GUI_helper(int lobby_size_option){
        String image_name = lobby_size_option + "-green";
        for (int i = 0; i < lobby_size_buttons.length ; i++){
            set_image_helper(lobby_size_buttons[i], LOBBY_SETTINGS, (i + 2) + "-red");
        }
        set_image_helper(lobby_size_buttons[lobby_size_option-2], LOBBY_SETTINGS, image_name);
    }

    @FXML
    public void closeButtonPressed() throws Exception{
        Main.changeScreen("src/main/resources/scenes/OuterController.fxml");
    }

    @FXML
    public void readyButtonPressed() throws Exception{
        ie.initializeGame(false, me.getGameMode(), me.getGameTheme(), me.getPlayerArrayList());
        Main.changeScreen("src/main/resources/scenes/InnerController.fxml");
    }

    //Initialize Screen With Images
    public void set_all_images(){
        //Lobby Size Buttons
        lobby_size_GUI_helper(2);
        //Mode Buttons
        mode_GUI_helper("vanilla");
        //Theme Buttons
        theme_GUI_helper("vanilla");
        //Pawn Buttons
        pawn_GUI_helper(true, 1);
        //Player's Info
        set_image_helper(pawn_players[0], LOBBY_PAWNS, "pawn-1");
        nick_player_0.setText(nickname);
        lobby_heading.setText(nickname + "'s Lobby - Player List");
        //For debug but at least log?
        test_label.setText("Welcome to Yolopoly!");
    }

    private void set_log(String text){
        String old_text = test_label.getText();
        test_label.setText(old_text + "\n" + text);
    }

    //Image Set Helper
    public void set_image_helper(ImageView iv, String path, String name){
        //FIXME images do not show
        System.out.println(path + name + PNG);
        File file = new File(path + name + PNG);
        Image image = new Image(file.getClass().getClassLoader().getName());
        iv.setImage(image);

        //ClassLoader classLoader = getClass().getClassLoader();
        //String imageUrl = classLoader.getResource("images/image.png").toExternalForm();
        //Image image = new Image(imageUrl);
    }

}
