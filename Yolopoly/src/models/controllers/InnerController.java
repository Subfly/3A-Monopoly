package models.controllers;

import enumerations.Pawn;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import models.Dice;
import models.Player;

import java.util.ArrayList;

import static sample.Main.changeScreen;

public class InnerController {

    @FXML
    ImageView index0, index1, index2, index3, index4, index5, index6, index7, index8, index9, index10,index11,index12,index13,index14,index15,index16,index17,index18,index19, index20,index21,index22,index23,index24,index25,index26,index27,index28,index29, index30,index31,index32,index33,index34,index35,index36,index37,index38,index39;

    @FXML
    ImageView sq_bar1, sq_bar3, sq_bar6, sq_bar8, sq_bar9, sq_bar11, sq_bar13, sq_bar14, sq_bar16 ,sq_bar18, sq_bar19,sq_bar21, sq_bar23, sq_bar24, sq_bar26, sq_bar27, sq_bar29, sq_bar31, sq_bar32, sq_bar34, sq_bar37, sq_bar39;

    @FXML
    ImageView card_image, info_card, house_slot3, house_slot2, house_slot1, house_slot0;

    @FXML
    Label owner_label, owner_nick, price_rent_label, price_rent_value;

    @FXML
    ImageView pawn_1, pawn_2, pawn_3, pawn_4, pawn_5, pawn_6, pawn_7, pawn_8;

    @FXML
    Label testTextField;

    @FXML
    ImageView dice_1, dice_2;

    @FXML
    ImageView player_index1, player_index2, player_index3, player_index4, player_index5, player_index6, player_index7, player_index8;

    @FXML
    ImageView buy_button, sell_button;

    ImageView[] pawnTest;
    ImageView[] player_indexes;
    ArrayList<ImageView> pawnTeam1;
    ArrayList<ImageView> pawnTeam2;

    Player p1, p2, p3, p4, p5, p6, p7, p8;

    Pawn pw1, pw2, pw3, pw4;

    Dice dice;

    boolean pressedEndTurn = false;

    Player[] players;

    public InnerController() {
        //Card Image and Info Card Variables Declaration
        card_image = new ImageView();
        house_slot0 = new ImageView();house_slot1 = new ImageView();house_slot2 = new ImageView();house_slot3 = new ImageView();
        owner_label = new Label();owner_nick = new Label();
        price_rent_label = new Label();price_rent_value = new Label();
        sell_button = new ImageView();buy_button = new ImageView();

        //Hotel and House Bars
        sq_bar1  = new ImageView();sq_bar3  = new ImageView();sq_bar6  = new ImageView();sq_bar8  = new ImageView();sq_bar9  = new ImageView();sq_bar11 = new ImageView();sq_bar13 = new ImageView();sq_bar14 = new ImageView();sq_bar16 = new ImageView();sq_bar18 = new ImageView();sq_bar19 = new ImageView();sq_bar21 = new ImageView();sq_bar23 = new ImageView();sq_bar24 = new ImageView();sq_bar26 = new ImageView();sq_bar27 = new ImageView();sq_bar29 = new ImageView();sq_bar31 = new ImageView();sq_bar32 = new ImageView();sq_bar34 = new ImageView();sq_bar37 = new ImageView();sq_bar39 = new ImageView();

        //Players Big Pawn Images
        player_index1 = new ImageView();player_index2 = new ImageView();player_index3 = new ImageView();player_index4 = new ImageView();player_index5 = new ImageView();player_index6 = new ImageView();player_index7 = new ImageView();player_index8 = new ImageView();

        //Pawns
        pawn_1 = new ImageView();pawn_2 = new ImageView();pawn_3 = new ImageView();pawn_4 = new ImageView();pawn_5 = new ImageView();pawn_6 = new ImageView();pawn_7 = new ImageView();pawn_8 = new ImageView();

        //Dices
        dice = new Dice();
        dice_1 = new ImageView();dice_2 = new ImageView();

        //For Debug Issues
        testTextField = new Label();

        //Players -- Debug -- It should not be there i guess
        p1 = new Player("p1", false);p2 = new Player("p2", false);p3 = new Player("p3", true);p4 = new Player("p4", false);p5 = new Player("p5", false);p6 = new Player("p6", false);p7 = new Player("p7", false);p8 = new Player("p8", false);
    }

    @FXML
    public void initialize() {
        pressedEndTurn = true;
        card_image.setVisible(false);

        players = new Player[]{p1,p2,p3,p4,p5,p6,p7,p8};

        pawnTest = new ImageView[]{pawn_1, pawn_2, pawn_3, pawn_4, pawn_5, pawn_6, pawn_7, pawn_8};

        player_indexes = new ImageView[]{player_index1, player_index2, player_index3, player_index4, player_index5, player_index6, player_index7, player_index8};

        pawnTeam1 = new ArrayList<>();
        pawnTeam2 = new ArrayList<>();

        pawnTeam1.add(pawn_1);pawnTeam1.add(pawn_2);pawnTeam1.add(pawn_5);pawnTeam1.add(pawn_6);

        pawnTeam2.add(pawn_3);pawnTeam2.add(pawn_4);pawnTeam2.add(pawn_7);pawnTeam2.add(pawn_8);

        owner_label.setVisible(false);
        owner_nick.setVisible(false);
        price_rent_label.setVisible(false);
        price_rent_value.setVisible(false);

        player_indexes[0].setImage(new Image(getClass().getResourceAsStream("sources/circle_active.png")));

        updateScreen();
    }

    int turn = 0;

    @FXML
    public void testFunction(){
        pressedEndTurn = true;

        turn++;
        turn = turn % 8;

        for (ImageView player_index : player_indexes) {
            player_index.setImage(new Image(getClass().getResourceAsStream("sources/circle.png")));
        }
        player_indexes[turn].setImage(new Image(getClass().getResourceAsStream("sources/circle_active.png")));

        System.out.println(turn);
    }

    public void movePawn(Player tmpPlayer, ImageView tmpPawn, int moveCount, boolean pawnIsLonged){
        int tmpCrtPst = tmpPlayer.getCurrentPosition();

        double pawnXtmp = tmpPawn.getLayoutX();
        double pawnYtmp = tmpPawn.getLayoutY();

        while (moveCount > 0){

            if(tmpPlayer.getCurrentPosition() <= 9){
                if (pawnIsLonged && (tmpPlayer.getCurrentPosition() == 0 || tmpPlayer.getCurrentPosition() == 9)){
                    pawnYtmp -= 124;
                }
                else if (!pawnIsLonged && (tmpPlayer.getCurrentPosition() == 0 || tmpPlayer.getCurrentPosition() == 9)){
                    pawnYtmp -= 104;
                }
                else{
                    pawnYtmp -= 80;
                }
            }
            else if(tmpPlayer.getCurrentPosition() <= 19){
                if (pawnIsLonged && (tmpPlayer.getCurrentPosition() == 10 || tmpPlayer.getCurrentPosition() == 19)){
                    pawnXtmp += 124;
                }
                else if (!pawnIsLonged && (tmpPlayer.getCurrentPosition() == 10 || tmpPlayer.getCurrentPosition() == 19)){
                    pawnXtmp += 104;
                }
                else {
                    pawnXtmp += 80;
                }
            }
            else if(tmpPlayer.getCurrentPosition() <= 29){
                if (pawnIsLonged && (tmpPlayer.getCurrentPosition() == 20 || tmpPlayer.getCurrentPosition() == 29)){
                    pawnYtmp += 124;
                }
                else if (!pawnIsLonged && (tmpPlayer.getCurrentPosition() == 20 || tmpPlayer.getCurrentPosition() == 29)){
                    pawnYtmp += 104;
                }
                else {
                    pawnYtmp += 80;
                }
            }
            else if(tmpPlayer.getCurrentPosition() <= 39){
                if (pawnIsLonged && (tmpPlayer.getCurrentPosition() == 30 || tmpPlayer.getCurrentPosition() == 39)){
                    pawnXtmp -= 124;
                }
                else if (!pawnIsLonged && (tmpPlayer.getCurrentPosition() == 30 || tmpPlayer.getCurrentPosition() == 39)){
                    pawnXtmp -= 104;
                }
                else {
                    pawnXtmp -= 80;
                }
            }

            tmpCrtPst++;
            moveCount--;

            //tmpPawn.setLayoutX(pawnXtmp);
            //tmpPawn.setLayoutY(pawnYtmp);

            TranslateTransition tt = new TranslateTransition(Duration.millis(1000), tmpPawn);
            tt.setByX(pawnXtmp - tmpPawn.getLayoutX());
            tt.setByY(pawnYtmp - tmpPawn.getLayoutY());
            tt.setAutoReverse(false);
            tt.play();

            tmpPlayer.setCurrentPosition(tmpCrtPst);
        }
    }

    @FXML
    public void roll_dice(){
        if (pressedEndTurn){
            dice.roll();
            int dice1 = dice.getDice1();
            int dice2 = dice.getDice2();
            int total = dice.getTotal();

            System.out.println("sources/dice/dice_" + dice1 + ".png");

            dice_1.setImage(new Image(getClass().getResourceAsStream("sources/dice/dice_" + dice1 + ".png")));
            dice_2.setImage(new Image(getClass().getResourceAsStream("sources/dice/dice_" + dice2 + ".png")));

            boolean tmpLonged = pawnTeam2.contains(pawnTest[turn]);

            movePawn(players[turn], pawnTest[turn], total, tmpLonged);

            testTextField.setText("dice1: " + dice1 + " dice2: " + dice2 + " total: " + total + " current pos : " + players[turn].getCurrentPosition());
            pressedEndTurn = false;
        }
        else {
            testTextField.setText("Turu bitir önce");
        }
    }

    int lastIndexOfCard = -1;

    @FXML
    public void indexes(MouseEvent e) throws NullPointerException {
        String tmpIndex = e.getSource().toString().substring(13,21).replace(',',' ').trim();
        if(lastIndexOfCard != Integer.parseInt(tmpIndex.replace("index", ""))){
            lastIndexOfCard = Integer.parseInt(tmpIndex.replace("index", ""));
            card_image.setVisible(true);
            try {
                card_image.setImage(new Image(getClass().getResourceAsStream("sources/property-cards/" + tmpIndex + ".png")));
                info_card.setImage(new Image(getClass().getResourceAsStream("sources/property-cards/info-card.png")));
                buy_button.setVisible(true);
                sell_button.setVisible(true);
            }
            catch (NullPointerException npe){
                System.out.println("Error :" + npe.getMessage());
            }
            switch (tmpIndex) {
                case "index39" -> {
                    setInfoCard(3, "Etophiana", 1000000);
                }
                case "index37" -> {
                    setInfoCard(2, "HolyGuard", 500000);
                }
                case "index3" -> {
                    setInfoCard(5, "Subfly", 750000);
                }
                case "index6" -> {
                    setInfoCard(0, "-", 150000);
                }
            }
        }
        else {
            lastIndexOfCard = -1;
            clear_indexes();
        }
    }

    public void updateScreen(){
        sq_bar39.setImage(new Image(getClass().getResourceAsStream("sources/squares/sq-house-3-h.png")));
        sq_bar37.setImage(new Image(getClass().getResourceAsStream("sources/squares/sq-house-2-h.png")));
        sq_bar3.setImage(new Image(getClass().getResourceAsStream("sources/squares/sq-hotel-v.png")));
        sq_bar34.setImage(new Image(getClass().getResourceAsStream("sources/squares/sq-hotel-h.png")));
        sq_bar32.setImage(new Image(getClass().getResourceAsStream("sources/squares/sq-house-3-h.png")));
        sq_bar21.setImage(new Image(getClass().getResourceAsStream("sources/squares/sq-hotel-v.png")));
    }

    public void clear_indexes(){
        card_image.setVisible(false);buy_button.setVisible(false);sell_button.setVisible(false);
        card_image.setImage(null);info_card.setImage(null);house_slot0.setImage(null);house_slot1.setImage(null);house_slot2.setImage(null);house_slot3.setImage(null);
        owner_nick.setText(" ");price_rent_label.setText(" ");price_rent_value.setText("");
        owner_label.setVisible(false);owner_nick.setVisible(false);price_rent_label.setVisible(false);price_rent_value.setVisible(false);
    }

    public void setInfoCard(int houseCount, String owner, int pr_value){

        String pr;
        if (owner.equals("-")) {
            pr = "Price";
        }
        else {
            pr = "Rent";
        }

        owner_label.setVisible(true);
        owner_nick.setVisible(true);
        price_rent_label.setVisible(true);
        price_rent_value.setVisible(true);

        owner_nick.setText(owner);
        price_rent_label.setText(pr);
        price_rent_value.setText(pr_value + "");
        switch (houseCount){
            case 0 -> {
                house_slot3.setImage(null);
                house_slot2.setImage(null);
                house_slot1.setImage(null);
                house_slot0.setImage(null);
            }
            case 1 -> {
                house_slot3.setImage(null);
                house_slot2.setImage(null);
                house_slot1.setImage(null);
                house_slot0.setImage(new Image(getClass().getResourceAsStream("sources/property-cards/house.png")));
            }
            case 2 -> {
                house_slot3.setImage(null);
                house_slot2.setImage(null);
                house_slot1.setImage(new Image(getClass().getResourceAsStream("sources/property-cards/house.png")));
                house_slot0.setImage(new Image(getClass().getResourceAsStream("sources/property-cards/house.png")));
            }
            case 3 -> {
                house_slot3.setImage(null);
                house_slot2.setImage(new Image(getClass().getResourceAsStream("sources/property-cards/house.png")));
                house_slot1.setImage(new Image(getClass().getResourceAsStream("sources/property-cards/house.png")));
                house_slot0.setImage(new Image(getClass().getResourceAsStream("sources/property-cards/house.png")));
            }
            case 4 -> {
                house_slot3.setImage(new Image(getClass().getResourceAsStream("sources/property-cards/house.png")));
                house_slot2.setImage(new Image(getClass().getResourceAsStream("sources/property-cards/house.png")));
                house_slot1.setImage(new Image(getClass().getResourceAsStream("sources/property-cards/house.png")));
                house_slot0.setImage(new Image(getClass().getResourceAsStream("sources/property-cards/house.png")));
            }
            case 5 -> {
                house_slot0.setImage(new Image(getClass().getResourceAsStream("sources/property-cards/hotel-bottom.png")));
                house_slot1.setImage(new Image(getClass().getResourceAsStream("sources/property-cards/hotel-middle.png")));
                house_slot2.setImage(new Image(getClass().getResourceAsStream("sources/property-cards/hotel-middle.png")));
                house_slot3.setImage(new Image(getClass().getResourceAsStream("sources/property-cards/hotel-top.png")));
            }
        }
    }

    @FXML
    public void closeButtonPressed() throws Exception{
        changeScreen("../models/controllers/OuterController.fxml");
    }
}