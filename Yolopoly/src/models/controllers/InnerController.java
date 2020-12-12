package models.controllers;

import enumerations.Pawn;
import enumerations.SquareType;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import models.Dice;
import models.Player;
import models.Square;
import models.cards.PropertyCard;
import models.engines.InnerEngine;
import models.engines.MiddleEngine;
import sample.Main;

import java.util.ArrayList;

import static sample.Main.changeScreen;

public class InnerController {

    @FXML
    ImageView index0, index1, index2, index3, index4, index5, index6, index7, index8, index9, index10, index11, index12, index13, index14, index15, index16, index17, index18, index19, index20, index21, index22, index23, index24, index25, index26, index27, index28, index29, index30, index31, index32, index33, index34, index35, index36, index37, index38, index39;

    @FXML
    ImageView sq_bar1, sq_bar3, sq_bar6, sq_bar8, sq_bar9, sq_bar11, sq_bar13, sq_bar14, sq_bar16, sq_bar18, sq_bar19, sq_bar21, sq_bar23, sq_bar24, sq_bar26, sq_bar27, sq_bar29, sq_bar31, sq_bar32, sq_bar34, sq_bar37, sq_bar39;

    @FXML
    ImageView card_image, info_card, house_slot3, house_slot2, house_slot1, house_slot0;

    @FXML
    Label owner_label, owner_nick, price_rent_label, price_rent_value;

    @FXML
    ImageView pawn_1, pawn_2, pawn_3, pawn_4, pawn_5, pawn_6, pawn_7, pawn_8;

    @FXML
    Label testTextField, test_label;

    @FXML
    ImageView dice_1, dice_2;

    @FXML
    ImageView player_index1, player_index2, player_index3, player_index4, player_index5, player_index6, player_index7, player_index8;

    @FXML
    ImageView buy_button, sell_button;

    ImageView[] pawns;
    ImageView[] player_indexes;
    ImageView[] square_bars;
    ArrayList<ImageView> pawnTeam1;
    ArrayList<ImageView> pawnTeam2;


    boolean pressedEndTurn = false;

    final String LOBBY_SETTINGS = "sources/lobby-settings/";
    final String LOBBY_PAWNS = "sources/lobby-settings/pawns/";
    final String PNG = ".png";


    public InnerController() {
        //Card Image and Info Card Variables Declaration
        card_image = new ImageView();house_slot0 = new ImageView();house_slot1 = new ImageView();house_slot2 = new ImageView();house_slot3 = new ImageView();owner_label = new Label();owner_nick = new Label();price_rent_label = new Label();price_rent_value = new Label();sell_button = new ImageView();buy_button = new ImageView();

        //Hotel and House Bars
        sq_bar1 = new ImageView();sq_bar3 = new ImageView();sq_bar6 = new ImageView();sq_bar8 = new ImageView();sq_bar9 = new ImageView();sq_bar11 = new ImageView();sq_bar13 = new ImageView();sq_bar14 = new ImageView();sq_bar16 = new ImageView();sq_bar18 = new ImageView();sq_bar19 = new ImageView();sq_bar21 = new ImageView();sq_bar23 = new ImageView();sq_bar24 = new ImageView();sq_bar26 = new ImageView();sq_bar27 = new ImageView();sq_bar29 = new ImageView();sq_bar31 = new ImageView();sq_bar32 = new ImageView();sq_bar34 = new ImageView();sq_bar37 = new ImageView();sq_bar39 = new ImageView();

        //Players Big Pawn Images
        player_index1 = new ImageView();player_index2 = new ImageView();player_index3 = new ImageView();player_index4 = new ImageView();player_index5 = new ImageView();player_index6 = new ImageView();player_index7 = new ImageView();player_index8 = new ImageView();

        //Pawns
        pawn_1 = new ImageView();pawn_2 = new ImageView();pawn_3 = new ImageView();pawn_4 = new ImageView();pawn_5 = new ImageView();pawn_6 = new ImageView();pawn_7 = new ImageView();pawn_8 = new ImageView();

        //Dices
        dice_1 = new ImageView();
        dice_2 = new ImageView();

        //For Debug Issues
        testTextField = new Label();
    }

    ArrayList<ImageView> pawns_of_players;
    ArrayList<ImageView> indexes_of_players;

    @FXML
    public void initialize() {
        pressedEndTurn = true;
        card_image.setVisible(false);

        //Lazım baya biliyon mu
        ie = Main.getInnerEngine();
        me = Main.getMiddleEngine();

        pawns_of_players = new ArrayList<>();
        indexes_of_players = new ArrayList<>();

        square_bars = new ImageView[]{sq_bar1, sq_bar3, sq_bar6, sq_bar8, sq_bar9, sq_bar11, sq_bar13, sq_bar14, sq_bar16, sq_bar18, sq_bar19, sq_bar21, sq_bar23, sq_bar24, sq_bar26, sq_bar27, sq_bar29, sq_bar31, sq_bar32, sq_bar34, sq_bar37, sq_bar39};
        pawns = new ImageView[]{pawn_1, pawn_2, pawn_3, pawn_4, pawn_5, pawn_6, pawn_7, pawn_8};
        player_indexes = new ImageView[]{player_index1, player_index2, player_index3, player_index4, player_index5, player_index6, player_index7, player_index8};
        pawnTeam1 = new ArrayList<>();
        pawnTeam2 = new ArrayList<>();

        int tmpIndex = 0;

        for (Player i : ie.getPlayers()) {
            pawns_of_players.add(pawns[i.getPawnIndex() - 1]);
            if (i.getPawnIndex() == 1 || i.getPawnIndex() == 2 || i.getPawnIndex() == 5 || i.getPawnIndex() == 6) {
                pawnTeam1.add(pawns[i.getPawnIndex() - 1]);
            } else {
                pawnTeam2.add(pawns[i.getPawnIndex() - 1]);
            }
            pawns[i.getPawnIndex() - 1].setVisible(true);
            indexes_of_players.add(player_indexes[tmpIndex]);
            tmpIndex++;
        }

        owner_label.setVisible(false);
        owner_nick.setVisible(false);
        price_rent_label.setVisible(false);
        price_rent_value.setVisible(false);

        set_turn_GUI();
        initializeSettings();
    }

    InnerEngine ie;
    MiddleEngine me;

    ArrayList<Player> playerArrayList;
    int playerCount;

    private void initializeSettings() {
        playerCount = me.getPlayerCount();
        playerArrayList = me.getPlayerArrayList();
    }

    int turn = 0;

    @FXML
    private void level_up() {
        ie.levelUp(last_index_of_info_card);
        update_square_info();
        set_log();
    }

    @FXML
    private void level_down() {
        ie.levelDown(last_index_of_info_card);
        update_square_info();
        set_log();
    }

    @FXML
    public void actionButtonPressed() {
        if (!pressedEndTurn) {
            if(ie.isCurrentPlayerHuman()){
                pressedEndTurn = true;
                turn++;
                turn = turn % pawns_of_players.size();
                set_turn_GUI();
                //square_update_GUI();
                ie.endTurn();

                while (!ie.isCurrentPlayerHuman()){
                    ie.rollDice();

                    int dice1 = ie.getDice().getDice1();
                    int dice2 = ie.getDice().getDice2();
                    int total = ie.getDice().getTotal();

                    int oldPosOfPlayer = ie.getPlayers().get(ie.getCurrentPlayerId()).getCurrentPosition();

                    dice_1.setImage(new Image(getClass().getResourceAsStream("sources/dice/dice_" + dice1 + ".png")));
                    dice_2.setImage(new Image(getClass().getResourceAsStream("sources/dice/dice_" + dice2 + ".png")));
                    movePawn(pawns_of_players.get(turn), total, pawnTeam2.contains(pawns_of_players.get(ie.getCurrentPlayerId())), oldPosOfPlayer);
                    Player p = ie.getPlayers().get(ie.getCurrentPlayerId());
                    //System.out.println(p.getName() + " " + p.getCurrentPosition());
                    ie.makeDecision(total, dice1 == dice2);
                    ie.endTurn();
                    turn++;
                    turn = turn % pawns_of_players.size();
                    set_turn_GUI();
                }
            }
        } else
            testTextField.setText("Tur sende değil göt");
    }

    private void set_log() {
        StringBuilder tmpLog = new StringBuilder();
        for (String i : ie.getLog()) {
            tmpLog.append(i + "\n");
        }
        test_label.setText(tmpLog.toString());
    }

    public void movePawn(ImageView tmpPawn, int moveCount, boolean pawnIsLonged, int oldposition) {
        double pawnXtmp = tmpPawn.getLayoutX();
        double pawnYtmp = tmpPawn.getLayoutY();

        while (moveCount > 0) {
            if (oldposition <= 9) {
                if (pawnIsLonged && (oldposition == 0 || oldposition == 9)) {
                    pawnYtmp -= 124;
                } else if (!pawnIsLonged && (oldposition == 0 || oldposition == 9)) {
                    pawnYtmp -= 104;
                } else {
                    pawnYtmp -= 80;
                }
            } else if (oldposition <= 19) {
                if (pawnIsLonged && (oldposition == 10 || oldposition == 19)) {
                    pawnXtmp += 124;
                } else if (!pawnIsLonged && (oldposition == 10 || oldposition == 19)) {
                    pawnXtmp += 104;
                } else {
                    pawnXtmp += 80;
                }
            } else if (oldposition <= 29) {
                if (pawnIsLonged && (oldposition == 20 || oldposition == 29)) {
                    pawnYtmp += 124;
                } else if (!pawnIsLonged && ((oldposition) == 20 || oldposition == 29)) {
                    pawnYtmp += 104;
                } else {
                    pawnYtmp += 80;
                }
            } else if (oldposition <= 39) {
                if (pawnIsLonged && (oldposition == 30 || oldposition == 39)) {
                    pawnXtmp -= 124;
                } else if (!pawnIsLonged && (oldposition == 30 || oldposition == 39)) {
                    pawnXtmp -= 104;
                } else {
                    pawnXtmp -= 80;
                }
            }
            oldposition++;
            oldposition = oldposition % 40; //PÜÜÜÜÜÜÜÜÜÜÜ
            moveCount--;

//            tmpPawn.setLayoutX(pawnXtmp);
//            tmpPawn.setLayoutY(pawnYtmp);

            TranslateTransition tt = new TranslateTransition(Duration.millis(1000), tmpPawn);
            tt.setByX(pawnXtmp - tmpPawn.getLayoutX());
            tt.setByY(pawnYtmp - tmpPawn.getLayoutY());
            tt.setAutoReverse(false);
            tt.play();
        }
    }

    // B O T

    @FXML
    public void roll_dice() {
        if (pressedEndTurn) {
            ie.rollDice();
            int dice1 = ie.getDice().getDice1();
            int dice2 = ie.getDice().getDice2();
            int total = ie.getDice().getTotal();

            int oldPosOfPlayer = ie.getPlayers().get(ie.getCurrentPlayerId()).getCurrentPosition();

            dice_1.setImage(new Image(getClass().getResourceAsStream("sources/dice/dice_" + dice1 + ".png")));
            dice_2.setImage(new Image(getClass().getResourceAsStream("sources/dice/dice_" + dice2 + ".png")));

            // TODO: Change multiplier later
            int test = ie.startTurn(total, dice1 == dice2, 1);

            int newPosOfPlayer = ie.getPlayers().get(ie.getCurrentPlayerId()).getCurrentPosition();

            movePawn(pawns_of_players.get(turn), total, pawnTeam2.contains(pawns_of_players.get(ie.getCurrentPlayerId())), oldPosOfPlayer);
            Player p = ie.getPlayers().get(ie.getCurrentPlayerId());
            //System.out.println(p.getName() + " " + p.getCurrentPosition());
            if (test == 3){
                movePawn(pawns_of_players.get(turn), 20, pawnTeam2.contains(pawns_of_players.get(ie.getCurrentPlayerId())), oldPosOfPlayer);
            }

            String oldSt = testTextField.getText();
            testTextField.setText(oldSt + "\n" + test);

            pressedEndTurn = false;
        } else {
            testTextField.setText("Turu bitir önce");
        }
        set_log();
    }

    private boolean is_square(Square s, String type) {
        return switch (type) {
            case "buy" -> s.getType() == SquareType.NormalSquare || s.getType() == SquareType.RailroadSquare || s.getType() == SquareType.UtilitySquare;
            case "build" -> s.getType() == SquareType.NormalSquare;
            default -> false;
        };
    }

    private void update_square_info() {
        int squareId = ie.getBoard().getSquares().get(last_index_of_info_card).getId();
        PropertyCard tmpcard = ie.getSpecificProperty(squareId);
        Square tmpSquare = ie.getBoard().getSpecificSquare(squareId);
        if (is_square(tmpSquare, "buy")) {
            int tmpSquareLevel = tmpSquare.getLevel();
            card_image.setVisible(true);
            card_image.setImage(new Image(getClass().getResourceAsStream("sources/property-cards/" + last_tmp_index_of_info_card + ".png")));
            info_card.setImage(new Image(getClass().getResourceAsStream("sources/property-cards/info-card.png")));
            buy_button.setVisible(true);
            sell_button.setVisible(true);
            assert tmpcard != null;
            if (ie.getOwner(squareId) != null) {
                if (tmpSquareLevel != -1){
                    setInfoCard(tmpSquareLevel, ie.getOwner(squareId).getName(), tmpcard.getRentPrices().get(tmpSquareLevel));
                }
                else {
                    setInfoCard(tmpSquareLevel, ie.getOwner(squareId).getName(), 0);
                }
            } else {
                setInfoCard(tmpSquareLevel, "-", tmpcard.getCost());
            }
        } else {
            System.out.println("ehelelele");
        }

        try {
            boolean levelUp = ie.checkLevelStatus(last_index_of_info_card).get(lvup);
            boolean levelDown = ie.checkLevelStatus(last_index_of_info_card).get(lvdw);
            if (levelUp) {
                buy_button.setDisable(false);
                buy_button.setStyle("-fx-opacity: 1");
            } else {
                buy_button.setDisable(true);
                buy_button.setStyle("-fx-opacity: 0.5");
            }
            if (levelDown) {
                sell_button.setDisable(false);
                sell_button.setStyle("-fx-opacity: 1");
            } else {
                sell_button.setDisable(true);
                sell_button.setStyle("-fx-opacity: 0.5");
            }
        } catch (NullPointerException npe) {
            //TODO bak bulcaz artık
            System.out.println("yarrak kafası " + npe.getMessage());
        }
    }

    int last_index_of_info_card = -1;
    String last_tmp_index_of_info_card = "";

    final String lvup = "levelUp";
    final String lvdw = "levelDown";

    @FXML
    public void get_square_info(MouseEvent e) throws NullPointerException {
        last_tmp_index_of_info_card = e.getSource().toString().substring(13, 21).replace(',', ' ').trim();
        if (last_index_of_info_card == -1){
            last_index_of_info_card = Integer.parseInt(last_tmp_index_of_info_card.replace("index", ""));
            update_square_info();
        }
        else if(last_index_of_info_card == Integer.parseInt(last_tmp_index_of_info_card.replace("index", ""))) {
            clear_indexes();
            last_index_of_info_card = -1;
        }
        else {
            last_index_of_info_card = Integer.parseInt(last_tmp_index_of_info_card.replace("index", ""));
            update_square_info();
        }
    }

    private void set_turn_GUI() {
        int i = 0;
        for (ImageView player_index : indexes_of_players) {
            player_index.setImage(new Image(getClass().getResourceAsStream("sources/circle.png")));
            String tmp_pawn_name = pawns_of_players.get(i).getId().replace("pawn_button", "");
            tmp_pawn_name = tmp_pawn_name.replace("_", "-");
            set_image_helper(player_index, LOBBY_PAWNS, tmp_pawn_name);
            indexes_of_players.get(i++).setStyle("-fx-effect: dropshadow(three-pass-box, rgb(255,255,255), 0, 0, 0, 0);");
        }
        indexes_of_players.get(turn).setStyle("-fx-effect: dropshadow(three-pass-box, rgb(55,199,219), 8, 0, 0, 0);");
    }

    private void square_update_GUI() {
        int i = 0;
        for (Square s : ie.getBoard().getSquares()) {
            String position;
            int id = s.getId();
            if (id <= 10 || (id >= 20 && id <= 30)) {
                position = "v";
            } else
                position = "h";
            if (is_square(s, "build")) {
                String deneme;
                if (s.getLevel() == 0) {
                    square_bars[i].setVisible(false);
                } else if (s.getLevel() == 5) {
                    deneme = "sources/squares/sq-hotel-" + position + ".png";
                    square_bars[i].setImage(new Image(getClass().getResourceAsStream(deneme)));
                } else {
                    deneme = "sources/squares/sq-house-" + s.getLevel() + "-" + position + ".png";
                    square_bars[i].setImage(new Image(getClass().getResourceAsStream(deneme)));
                }
                i++;
            }
        }
    }

    public void clear_indexes() {
        card_image.setVisible(false);
        buy_button.setVisible(false);
        sell_button.setVisible(false);
        card_image.setImage(null);
        info_card.setImage(null);
        house_slot0.setImage(null);
        house_slot1.setImage(null);
        house_slot2.setImage(null);
        house_slot3.setImage(null);
        owner_nick.setText(" ");
        price_rent_label.setText(" ");
        price_rent_value.setText("");
        owner_label.setVisible(false);
        owner_nick.setVisible(false);
        price_rent_label.setVisible(false);
        price_rent_value.setVisible(false);
    }

    public void setInfoCard(int houseCount, String owner, int pr_value) {

        String pr;
        if (owner.equals("-")) {
            pr = "Price";
        } else {
            pr = "Rent";
        }

        owner_label.setVisible(true);
        owner_nick.setVisible(true);
        price_rent_label.setVisible(true);
        price_rent_value.setVisible(true);

        owner_nick.setText(owner);
        price_rent_label.setText(pr);
        price_rent_value.setText(pr_value + "");
        switch (houseCount) {
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

    //Image Set Helper
    public void set_image_helper(ImageView iv, String path, String name) {
        iv.setImage(new Image(getClass().getResourceAsStream(path + name + PNG)));
    }

    @FXML
    public void closeButtonPressed() throws Exception {
        changeScreen("../models/controllers/OuterController.fxml");
    }
}