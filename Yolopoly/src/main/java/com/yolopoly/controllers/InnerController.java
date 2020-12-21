package com.yolopoly.controllers;

import com.yolopoly.Main;
import com.yolopoly.enumerations.DrawableCardType;
import com.yolopoly.enumerations.GameMode;
import com.yolopoly.enumerations.SquareType;
import com.yolopoly.managers.InGameManager;
import com.yolopoly.managers.LobbyManager;
import com.yolopoly.models.bases.Player;
import com.yolopoly.models.bases.Square;
import com.yolopoly.models.cards.PropertyCard;
import com.yolopoly.storage.Constants;
import javafx.animation.TranslateTransition;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import static com.yolopoly.Main.changeScreen;

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

    @FXML
    ImageView cards, cards_background, jail_ask, pay_option, roll_option;

    @FXML
    AnchorPane cards_anchor;

    @FXML
    Label players_name, players_money, players_money_edit;

    @FXML
    ImageView players_pawn;

    @FXML
    AnchorPane auction_anchor;

    @FXML
    ImageView option_100, option_250, option_500, option_pass, option_quit;

    @FXML
    ImageView auction_index1, auction_index2, auction_index3, auction_index4, auction_index5, auction_index6, auction_index7, auction_index8, auction_property_card;

    @FXML
    Label current_bid, property_price;

    @FXML
    ImageView chance_button, loan_button;

    @FXML
    Label currency_eur, currency_usd, currency_try, multiplier_label;

    @FXML
    ImageView d_c1,d_c2,d_c3,d_c4,d_c5,d_c6,d_c7,d_c8,d_c9,d_c10,d_c11,d_c12,d_c13,d_c14,d_c15,d_c16,d_c17,d_c18;//,d_c19,d_c20,d_c21,d_c22,d_c23,d_c24,d_c25,d_c26,d_c27,d_c28,d_c29,d_c30;

    @FXML
    AnchorPane in_game_menu, settings;

    @FXML
    ImageView to_0, add_10k, add_100k, add_1m, change_eur, change_try, exchange, add_field_image;

    @FXML
    Label add_field, try_parity, eur_parity, try_balance, eur_balance, to_from;

    ImageView[] deck_card_list;
    ImageView[] pawns;
    ImageView[] player_indexes;
    ImageView[] square_bars;
    ArrayList<ImageView> pawnTeam1;
    ArrayList<ImageView> pawnTeam2;
    ImageView[] auction_indexes;


    final String LOBBY_SETTINGS = "scenes/sources/lobby-settings/";
    final String LOBBY_PAWNS = "/scenes/sources/lobby-settings/pawns/";
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

        //cards
        d_c1 = new ImageView();d_c2 = new ImageView();d_c3 = new ImageView();d_c4 = new ImageView();d_c5 = new ImageView();d_c6 = new ImageView();d_c7 = new ImageView();d_c8 = new ImageView();d_c9 = new ImageView();d_c10 = new ImageView();d_c11 = new ImageView();d_c12 = new ImageView();d_c13 = new ImageView();d_c14 = new ImageView();d_c15 = new ImageView();d_c16 = new ImageView();d_c17 = new ImageView();d_c18 = new ImageView();//d_c19 = new ImageView();d_c20 = new ImageView();d_c21 = new ImageView();d_c22 = new ImageView();d_c23 = new ImageView();d_c24 = new ImageView();d_c25 = new ImageView();d_c26 = new ImageView();d_c27 = new ImageView();d_c28 = new ImageView();d_c29 = new ImageView();d_c30 = new ImageView();

        cards_background = new ImageView();
        cards = new ImageView();
        cards_anchor = new AnchorPane();

        deck_card_field = new AnchorPane();

        pay_option = new ImageView();
        roll_option = new ImageView();
        jail_ask = new ImageView();

        //For Debug Issues
        testTextField = new Label();
        players_money = new Label();
        players_money_edit = new Label();
        players_name = new Label();

        players_pawn = new ImageView();

        option_100 = new ImageView();
        option_250 = new ImageView();
        option_500 = new ImageView();
        option_quit = new ImageView();
        option_pass = new ImageView();

        auction_index1 = new ImageView();
        auction_index2 = new ImageView();
        auction_index3 = new ImageView();
        auction_index4 = new ImageView();
        auction_index5 = new ImageView();
        auction_index6 = new ImageView();
        auction_index7 = new ImageView();
        auction_index8 = new ImageView();
        auction_property_card = new ImageView();

        current_bid = new Label();
        property_price = new Label();

        loan_button = new ImageView();
        chance_button = new ImageView();
        add_field_image = new ImageView();

        currency_eur = new Label();
        currency_usd = new Label();
        currency_try = new Label();
    }

    ArrayList<ImageView> pawns_of_players;
    ArrayList<ImageView> indexes_of_players;

    @FXML
    public void initialize() {
        card_image.setVisible(false);

        igm = InGameManager.getInstance();
        lm = LobbyManager.getInstance();

        pawns_of_players = new ArrayList<>();
        indexes_of_players = new ArrayList<>();

        square_bars = new ImageView[]{sq_bar1, sq_bar3, sq_bar6, sq_bar8, sq_bar9, sq_bar11, sq_bar13, sq_bar14, sq_bar16, sq_bar18, sq_bar19, sq_bar21, sq_bar23, sq_bar24, sq_bar26, sq_bar27, sq_bar29, sq_bar31, sq_bar32, sq_bar34, sq_bar37, sq_bar39};
        pawns = new ImageView[]{pawn_1, pawn_2, pawn_3, pawn_4, pawn_5, pawn_6, pawn_7, pawn_8};
        player_indexes = new ImageView[]{player_index1, player_index2, player_index3, player_index4, player_index5, player_index6, player_index7, player_index8};
        pawnTeam1 = new ArrayList<>();
        pawnTeam2 = new ArrayList<>();

        deck_card_list = new ImageView[]{d_c1,d_c2,d_c3,d_c4,d_c5,d_c6,d_c7,d_c8,d_c9,d_c10,d_c11,d_c12,d_c13,d_c14,d_c15,d_c16,d_c17,d_c18}; //d_c19,d_c20,d_c21,d_c22,d_c23,d_c24,d_c25,d_c26,d_c27,d_c28,d_c29,d_c30
        auction_indexes = new ImageView[]{auction_index1,auction_index2,auction_index3,auction_index4,auction_index5,auction_index6,auction_index7,auction_index8};

        int tmpIndex = 0;

        for (Player i : igm.getPlayers()) {
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

        cards.setVisible(false);
        cards_background.setVisible(false);
        cards_anchor.setVisible(false);

        pay_option.setVisible(false);
        roll_option.setVisible(false);
        jail_ask.setVisible(false);

        auction_anchor.setVisible(false);

        set_turn_GUI();
        initializeSettings();
        update_deck();

        for (ImageView iv :deck_card_list){
            iv.setVisible(false);
        }

        multiplier_label = new Label();

        if (igm.getGameMode() != GameMode.bankman){
            loan_button.setVisible(false);
            chance_button.setVisible(false);

            currency_eur.setVisible(false);
            currency_usd.setVisible(false);
            currency_try.setVisible(false);

            multiplier_label.setVisible(false);

            eur_balance.setVisible(false);
            eur_parity.setVisible(false);

            try_balance.setVisible(false);
            try_parity.setVisible(false);

            currency_try.setVisible(false);
            currency_eur.setVisible(false);

            change_eur.setVisible(false);
            change_try.setVisible(false);

            to_0.setVisible(false);
            add_1m.setVisible(false);
            add_10k.setVisible(false);
            add_100k.setVisible(false);
            add_field.setVisible(false);

            add_field_image.setVisible(false);

            exchange.setVisible(false);

            to_from.setVisible(false);
        }
        else {
            loan_button.setVisible(true);
            chance_button.setVisible(true);

            currency_eur.setVisible(true);
            currency_usd.setVisible(true);
            currency_try.setVisible(true);

            multiplier_label.setVisible(true);

            try_parity.setText("₺ / $ = " + String.format("%.2f",igm.getBank().getCurrencyRates().get(1).getRate()));
            eur_parity.setText("€ / $ = " + String.format("%.2f",igm.getBank().getCurrencyRates().get(2).getRate()));

            try_balance.setText(igm.getPlayers().get(0).getMoney().get(Constants.CURRENCY_NAMES[1]).toString());
            eur_balance.setText(igm.getPlayers().get(0).getMoney().get(Constants.CURRENCY_NAMES[2]).toString());

            add_field.setText("0K");

        }


    }

    InGameManager igm;
    LobbyManager lm;

    ArrayList<Player> playerArrayList;
    int playerCount;

    private void initializeSettings() {
        playerCount = lm.getPlayerCount();
        playerArrayList = lm.getPlayerArrayList();
    }

    int turn = 0;

    @FXML
    private void level_up() {
        igm.levelUp(last_index_of_info_card, multiplier);
        update_square_info();
        update_deck();
        set_log();
        update_square_info();
        square_update_GUI();
    }

    @FXML
    private void level_down() {
        igm.levelDown(last_index_of_info_card, multiplier);
        update_square_info();
        set_log();
        update_deck();
        update_square_info();
        square_update_GUI();
    }

    String last_currency = "try";

    @FXML
    public void change_currency(MouseEvent e){
        String currency = e.getPickResult().getIntersectedNode().getId().replace("currency_","");

        switch (currency){
            case "eur" -> last_currency = "Euro";
            case "usd" -> last_currency = "Monopoly Dollar";
        }

        System.out.println();

        igm.exchangeCurrency(last_currency, currency, 100000);
    }

    boolean get_chanced = false;
    double multiplier = 1;

    @FXML
    public void get_chance(){
        if (!get_chanced){
            multiplier = igm.generateChanceMultiplier();
            System.out.println(multiplier);
            get_chanced = true;
            multiplier_label.setText("X" + multiplier);
        }

        //show on label
    }

    boolean loan_taken = false;

    @FXML
    public void loan_action(){
        if (!loan_taken){
            igm.giveLoan(5000000); //TODO decide
            loan_taken = true;
            players_money_edit.setText(igm.parser(5000000));
            update_deck();
        }
        else {
            igm.receiveLoanBackFromPlayer(multiplier);
            loan_taken = false;
            players_money_edit.setText("");
        }
        update_deck();
    }

    boolean did_auction = false;

    /*
     * RETURN VALUES
     * 1 => NORMAL END
     * 2 => DID NOT PAID LOANS
     * 3 => GAME DONE, REMAINING PLAYER WINS!
     * 4 => AUCTION
     */
    private void end_turn(){
        get_chanced = false;
        multiplier = 1;
        multiplier_label.setText("X" + multiplier);
        int pawnIndex = igm.getCurrentPlayerId();
        int result = igm.endTurn();

        System.out.println(igm.isCurrentPlayerHuman() + " insan mısınız lan " + result);

        if (result == 4){
            start_auction();
            did_auction = true;
        }
        else if (result == 1){
            turn++;
            turn = turn % pawns_of_players.size();
            set_turn_GUI();
            state_of_bot = 0;
            if (igm.getPlayers().get(igm.getCurrentPlayerId()).isHuman()){
                can_roll_dice = true;
            }
            if (did_auction){
                did_auction = false;
                play_bot();
            }
        }
        else if(result == 2){
            pawns_of_players.get(pawnIndex).setVisible(true);
            pawns_of_players.remove(pawnIndex);
        }
        update_deck();
    }

    public void start_auction(){
        auction_anchor.setVisible(true);
        int counter = 0;
        for (Player p : igm.getParticipants()){
            int tmp_pawn_index = p.getPawnIndex();
            System.out.println(LOBBY_PAWNS + tmp_pawn_index + "");
            set_image_helper(auction_indexes[counter], LOBBY_PAWNS, "pawn-" + tmp_pawn_index + "");
            counter++;
        }
        current_bid.setText(igm.parser(igm.getCurrentBid()));
        property_price.setText(igm.getSpecificProperty(igm.getAuctionPropertyIndex()).getCost()+"");
        set_image_helper(auction_property_card, "/scenes/sources/property-cards/", "index" + igm.getAuctionPropertyIndex());
        auction_turn_player = true;
    }

    int auction_turn_index = 0;

    public void update_auction(){
        current_bid.setText(igm.parser(igm.getCurrentBid()));
    }

    public void reduce_participant(){
        int counter = 0;
        for (ImageView iv : auction_indexes){
            if (counter >= igm.getParticipants().size()){
                iv.setVisible(false);
            }
            counter++;
        }
        counter = 0;
        for (Player p : igm.getParticipants()){
            int tmp_pawn_index = p.getPawnIndex();
            set_image_helper(auction_indexes[counter], LOBBY_PAWNS, "pawn-" + tmp_pawn_index + "");
            counter++;
        }
    }

    boolean auction_turn_player = false;

    public void end_auction_turn(){
        auction_turn_index = igm.getCurrentPlayerAuctioning();

        if(igm.checkAuctionStatus()){
            System.out.println("buraya giriyo mu ki bu ya");
            igm.endAuction();
            auction_anchor.setVisible(false);
            end_turn();
        }
        else if (igm.getParticipants().get(auction_turn_index).isHuman()){
            auction_turn_player = true;
        }
        else if (!igm.getParticipants().get(auction_turn_index).isHuman()){
            int result_of_bot = igm.auctionMakeDecision();

            switch (result_of_bot){
                case -104 -> update_auction();
                case -103 -> reduce_participant();
                case -102 -> update_auction();
            }
            set_log();
            System.out.println(result_of_bot);
            end_auction_turn();
        }
    }

    @FXML
    public void make_action(MouseEvent e){
        if (auction_turn_player){
            String tmp_bid = e.getPickResult().getIntersectedNode().getId().replace("option_","");
            if (tmp_bid.equals("quit")){
                igm.pullOffAuction(false);
            }
            else if(tmp_bid.equals("pass")){
                igm.continueAuction(0);
            }
            else {
                int bid = Integer.parseInt(tmp_bid);
                igm.continueAuction(bid * 1000);
            }
            auction_turn_player = false;
            update_auction();
            end_auction_turn();
        }
    }

    public void clear_auction(){
        int counter = 0;
        for (ImageView iv : auction_indexes){
            iv.setImage(null);
            iv.setVisible(true);
            counter++;
        }



    }

    int state_of_bot = 0;
    int result_of_bots_cards = 0;
    int old_position_of_bot = 0;


    private void play_bot(){
        if (!igm.isCurrentPlayerHuman()){
            // -1- jailed
            // 0 - move w dice
            // 1 - move wout dice
            // 2 - finito


            if (!igm.getPlayers().get(igm.getCurrentPlayerId()).isInJail()){
                //TODO haahha

                if (state_of_bot == 0){
                    igm.rollDice();

                    int dice1 = igm.getDice().getDice1();
                    int dice2 = igm.getDice().getDice2();
                    int total = igm.getDice().getTotal();

                    old_position_of_bot = igm.getPlayers().get(igm.getCurrentPlayerId()).getCurrentPosition();

                    set_image_helper(dice_1, "/scenes/sources/dice/" , "dice_" + dice1);
                    set_image_helper(dice_2, "/scenes/sources/dice/" , "dice_" + dice2);

                    movePawn(pawns_of_players.get(turn), total, pawnTeam2.contains(pawns_of_players.get(igm.getCurrentPlayerId())), old_position_of_bot);
                    result_of_bots_cards = igm.makeDecision(total, dice1 == dice2);
                    update_deck();
                    old_position_of_bot += total;
                    boolean bot_jailed = igm.getPlayers().get(igm.getCurrentPlayerId()).isInJail();

                    if (!bot_jailed && dice1 == dice2){
                        state_of_bot = 0;
                    }
                    else if (bot_jailed){
                        state_of_bot = 0;
                    }
                    else if (result_of_bots_cards == -3 || (result_of_bots_cards >= 0 && result_of_bots_cards <= 39)){
                        state_of_bot = 1;
                    }
                    else {
                        end_turn();
                    }
                }
                else if (state_of_bot == 1){
                    int move_count_of_bot;
                    if (result_of_bots_cards == -3){
                        move_count_of_bot = 37;
                    }
                    else {
                        if (result_of_bots_cards > old_position_of_bot){
                            move_count_of_bot = result_of_bots_cards - old_position_of_bot;
                        }
                        else {
                            move_count_of_bot = 40 - (old_position_of_bot - result_of_bots_cards);
                        }
                    }
                    // TODO bişiler var burda go'dan sonra dert oğlu dert
                    // FIXME Bot GO square'e gittiğinde indexi yanlış kalıyo
                    movePawn(pawns_of_players.get(turn), move_count_of_bot , pawnTeam2.contains(pawns_of_players.get(igm.getCurrentPlayerId())), old_position_of_bot);
                    result_of_bots_cards = igm.makeDecision(move_count_of_bot, false);
                    update_deck();

                    if (result_of_bots_cards == -3 || (result_of_bots_cards >= 0 && result_of_bots_cards <= 39)){
                        state_of_bot = 1;
                    }
                    else {
                        end_turn();
                    }
                }
            }
            else {
                if (igm.getPlayers().get(igm.getCurrentPlayerId()).getInJailTurnCount() == 0){
                    int move_count_of_bot;
                    if (old_position_of_bot < 10){
                        move_count_of_bot = 10 - old_position_of_bot;
                    }
                    else {
                        move_count_of_bot = 50 - old_position_of_bot;
                    }
                    movePawn(pawns_of_players.get(turn), move_count_of_bot , pawnTeam2.contains(pawns_of_players.get(igm.getCurrentPlayerId())), old_position_of_bot);
                    end_turn();
                }
                else {
                    igm.jailMakeDecision(1);
                    play_bot();
                }
            }
            set_log();
        }
        else {
        }
    }

    @FXML
    public void make_exchange(){
        int index = 0;

        if (chosen_cur == "eur"){
            index = 2;
        }
        else if (chosen_cur == "try"){
            index = 1;
        }

        if (ex_direction.equals("to")){
            igm.exchangeCurrency(Constants.CURRENCY_NAMES[0], Constants.CURRENCY_NAMES[index],amount * 1000);
        }
        else {
            igm.exchangeCurrency(Constants.CURRENCY_NAMES[index], Constants.CURRENCY_NAMES[0],amount * 1000);
        }
        update_deck();
    }

    int amount = 0;
    String chosen_cur = "eur";

    @FXML
    public void add_money_to_exchange(MouseEvent e){
        String s = ((ImageView)e.getSource()).getId();
        switch (s) {
            case "to_0" -> {
                amount = 0;
            }
            case "add_10k" -> {
                amount += 10;
            }
            case "add_100k" -> {
                amount += 100;
            }
            case "add_1m" -> {
                amount += 1000;
            }
        }
        add_field.setText(amount + "K");
    }

    public String ex_direction = "to";

    @FXML
    public void exchange(){
        if(ex_direction.equals("to")){
            ex_direction = "from";
            to_from.setText("FROM");
        }
        else {
            ex_direction = "to";
            to_from.setText("TO");
        }
    }

    @FXML
    public void change_parity(MouseEvent e){
        String s = ((ImageView)e.getSource()).getId().replace("change_","");
        if (s.equals("eur")){
            chosen_cur = "eur";
            set_image_helper(change_eur, "/scenes/sources/bankman/", "eur_on");
            set_image_helper(change_try, "/scenes/sources/bankman/", "try_off");
        }
        else {
            chosen_cur = "try";
            set_image_helper(change_eur, "/scenes/sources/bankman/", "eur_off");
            set_image_helper(change_try, "/scenes/sources/bankman/", "try_on");
        }
    }

    @FXML
    public void actionButtonPressed() {
        if (!can_roll_dice) {
            if(igm.isCurrentPlayerHuman()){
                update_deck();
                end_turn();
                play_bot();
            }
        } else
            testTextField.setText("Tur sende değil");
    }

    private void set_log() {
        String tmpLog = "";
        for (String i : igm.getLog()) {
            String tmp = tmpLog;
            tmpLog = i + "\n";
            tmpLog += tmp;
        }
        test_label.setText(tmpLog);
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
            oldposition = oldposition % 40;
            moveCount--;

//            tmpPawn.setLayoutX(pawnXtmp);
//            tmpPawn.setLayoutY(pawnYtmp);

        }
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), tmpPawn);
        tt.setByX(pawnXtmp - tmpPawn.getLayoutX());
        tt.setByY(pawnYtmp - tmpPawn.getLayoutY());
        tt.setOnFinished(e -> {
            start_turn();
        });
        tt.setAutoReverse(false);
        tt.play();
    }

    @FXML
    public void payforjailoption(){
        igm.payForGetOutOfJail(1);

        jail_ask.setVisible(false);
        cards_anchor.setVisible(false);
        roll_option.setVisible(false);
        pay_option.setVisible(false);
        cards_background.setVisible(false);
    }

    boolean diceforjail = false;

    @FXML
    public void rolldiceoption(){
        diceforjail = true;

        jail_ask.setVisible(false);
        cards_anchor.setVisible(false);
        roll_option.setVisible(false);
        pay_option.setVisible(false);
        cards_background.setVisible(false);
    }

    @FXML
    AnchorPane deck_card_field;

    int current_deck = 0;

    public void update_deck(){
        Player player = igm.getPlayers().get(current_deck);
        ArrayList<PropertyCard> cards = player.getOwnedPlaces();

        players_name.setText(player.getName());
        players_money.setText(igm.parser(player.getMonopolyMoneyAmount()));
        String pawn_index = pawns_of_players.get(current_deck).getId().replace("pawn_","");
        set_image_helper(players_pawn,"/scenes/sources/lobby-settings/pawns/", "pawn-" + pawn_index);

        int counter = 0;
        if (cards != null){
            for (PropertyCard c : cards){
                int index = c.getId();
                deck_card_list[counter].setVisible(true);
                set_image_helper(deck_card_list[counter],"/scenes/sources/property-cards/", "index" + index);
                deck_card_list[counter].setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 2, 0, 0, 0);");
                counter++;
            }
        }

        if (igm.getGameMode() == GameMode.bankman){
            try_parity.setText("₺ / $ = " + igm.getBank().getCurrencyRates().get(1).getRate());
            eur_parity.setText("€ / $ = " + igm.getBank().getCurrencyRates().get(2).getRate());

            try_balance.setText(igm.parser(igm.getPlayers().get(0).getMoney().get(Constants.CURRENCY_NAMES[1])));
            eur_balance.setText(igm.parser(igm.getPlayers().get(0).getMoney().get(Constants.CURRENCY_NAMES[2])));
        }
    }

    @FXML
    public void set_deck(MouseEvent e){
        String tmp_index = e.getPickResult().getIntersectedNode().getId().replace("player_index", "");
        int index = Integer.parseInt(tmp_index);
        current_deck = index - 1;
        for (ImageView iv : deck_card_list) {
            iv.setImage(null);
        }
        update_deck();
    }

    @FXML
    public void show_decks_card(MouseEvent e){
        Player player = igm.getPlayers().get(current_deck);
        ArrayList<PropertyCard> cards = player.getOwnedPlaces();
        String tmp_index = e.getPickResult().getIntersectedNode().getId().replace("d_c", "");
        int index = Integer.parseInt(tmp_index) - 1;

        int squareId = igm.getBoard().getSquares().get(cards.get(index).getId()).getId();
        System.out.println(player.getName() + " " + index + " " + squareId);
        PropertyCard tmpcard = igm.getSpecificProperty(squareId);
        Square tmpSquare = igm.getBoard().getSpecificSquare(squareId);
        if (is_square(tmpSquare, "buy")) {
            int tmpSquareLevel = tmpSquare.getLevel();
            card_image.setVisible(true);
            System.out.println("/scenes/sources/property-cards/index" + cards.get(index).getId() + "");
            set_image_helper(card_image, "/scenes/sources/property-cards/index", cards.get(index).getId() + "");
            set_image_helper(info_card, "/scenes/sources/property-cards/", "info-card");
            buy_button.setVisible(true);
            sell_button.setVisible(true);
            assert tmpcard != null;
            if (igm.getOwner(squareId) != null) {
                if (tmpSquareLevel != -1){
                    setInfoCard(tmpSquareLevel, igm.getOwner(squareId).getName(), tmpcard.getRentPrices().get(tmpSquareLevel)); //TODO bugfix
                }
                else {
                    setInfoCard(tmpSquareLevel, igm.getOwner(squareId).getName(), 0);
                }
            } else {
                setInfoCard(tmpSquareLevel, "-", tmpcard.getCost());
            }
        } else {
            System.out.println("ehelelele");
        }

        try {
            boolean levelUp = igm.checkLevelStatus(cards.get(index).getId()).get(lvup);
            boolean levelDown = igm.checkLevelStatus(cards.get(index).getId()).get(lvdw);
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
            System.out.println(npe.getMessage());
        }
    }

    @FXML
    public void clear_decks_card(){
        clear_indexes();
        last_index_of_info_card = -1;
    }

    private void start_turn(){
        Player p = igm.getPlayers().get(igm.getCurrentPlayerId());
        if (p.isHuman()){
            if (drawable_card_info == 1){
                set_image_helper(cards, "/scenes/sources/drawable-cards/", "chance-back");
                cards.setVisible(true);
                cards_background.setVisible(true);
                cards_anchor.setVisible(true);
            }
            else if (drawable_card_info == 2){
                set_image_helper(cards, "/scenes/sources/drawable-cards/", "chest-back");
                cards.setVisible(true);
                cards_background.setVisible(true);
                cards_anchor.setVisible(true);
            }
            else if (player_goes_jail){
                get_player_jail(old_position_of_player);
                player_goes_jail = false;
            }
            else if(p.isInJail()){
                jail_ask.setVisible(true);
                cards_anchor.setVisible(true);
                roll_option.setVisible(true);
                pay_option.setVisible(true);
                cards_background.setVisible(true);
            }
            update_deck();
        }
        else {
            play_bot();
        }
    }

    boolean first_time = true;

    // B O T

    boolean is_player_get_jailed = false;

    public void get_player_jail(int current_place){
        int move_count_of_player;
        if (current_place < 10){
            move_count_of_player = 10 - current_place;
        }
        else {
            move_count_of_player = 50 - current_place;
        }
        System.out.println(current_place + " sanane böyle almak istedi canım " + move_count_of_player + " move count falan bakalım iki ----------------");
        movePawn(pawns_of_players.get(turn), move_count_of_player, pawnTeam2.contains(pawns_of_players.get(igm.getCurrentPlayerId())), current_place);
        is_player_get_jailed = false;
        end_turn();
    }

    boolean player_goes_jail = false;
    int old_position_of_player = 0;
    int new_position_of_player = 0;
    int card_result_of_player = -98;
    boolean player_viewed_card = false;

    /*
     * RETURN VALUES EXPLAINED
     * -99 => PLAYER BROKE
     * -3 => Backward
     * 0 - 39 => to index
     * 5100 => DRAWN GOOJC
     * 5200 => DRAWN GTJC
     * 6000 => PAY MONEY FOR BUILDINGS
     * 6100 => PAY TO BANK
     * 7000 => BIRTHDAY GIFT BABY!
     * UNKNOWN VALUE > 100000 => EITHER PAY MONEY OR DRAW CHANCE CARD
     */

    boolean can_roll_dice = true;

    int drawable_card_info = 0;

    @FXML
    public void roll_dice() {

        if (can_roll_dice){
            can_roll_dice = false;
            igm.rollDice();
//            igm.getDice().setDice1(4);
//            igm.getDice().setDice2(4);
            int dice1 = igm.getDice().getDice1();
            int dice2 = igm.getDice().getDice2();
            int total = igm.getDice().getTotal();

            boolean is_double = dice1 == dice2;

            can_roll_dice = is_double;

            set_image_helper(dice_1, "/scenes/sources/dice/", "dice_" + dice1);
            set_image_helper(dice_2, "/scenes/sources/dice/", "dice_" + dice2);

            if ((diceforjail && is_double) || !diceforjail){

                old_position_of_player = igm.getCurrentPlayerCurrentPosition();

                if (!(igm.getPlayers().get(igm.getCurrentPlayerId()).getDoublesCount() == 2 && is_double)){
                    movePawn(pawns_of_players.get(turn), total, pawnTeam2.contains(pawns_of_players.get(igm.getCurrentPlayerId())), old_position_of_player);
                }

                int result_of_start_turn = igm.startTurn(total, is_double, multiplier);

                new_position_of_player = igm.getCurrentPlayerCurrentPosition();
                //old
                if (result_of_start_turn == -2){
                    System.out.println(old_position_of_player + " old pos falan jail öncesi");
                    get_player_jail(old_position_of_player);
                    can_roll_dice = false;
                }
                else if (result_of_start_turn == 1){
                    drawable_card_info = 1;
                    card_result_of_player = igm.drawCard(DrawableCardType.Chance, 1);
                }
                else if (result_of_start_turn == 2){
                    drawable_card_info = 2;
                    card_result_of_player = igm.drawCard(DrawableCardType.Community, 1);
                }
                else if (result_of_start_turn == 3){
                }
                else if (result_of_start_turn == 4){
                    old_position_of_player += total;
                    player_goes_jail = true;
                }
                else if (result_of_start_turn == 7){
                }
                diceforjail = false;
            }
            else if (diceforjail && !is_double){
                diceforjail = false;
                end_turn();
                movePawn(pawns_of_players.get(turn), 40 , pawnTeam2.contains(pawns_of_players.get(igm.getCurrentPlayerId())), 10);
            }
            else if (!can_roll_dice){
                testTextField.setText("la tur sende değil dur bi");
            }
        }
        set_log();
        update_deck();
    }

    private void check_drawable_cards(){
        if (card_result_of_player == -3){
            int move_count_of_player;
            old_position_of_player = old_position_of_player + igm.getDice().getTotal();
            move_count_of_player = 37;
            int result = igm.startTurn(move_count_of_player, false, multiplier);
            movePawn(pawns_of_players.get(turn), move_count_of_player, pawnTeam2.contains(pawns_of_players.get(igm.getCurrentPlayerId())), old_position_of_player);
            if (result == 2){
                check_drawable_cards();
            }
        }
        else if (card_result_of_player >= 0 && card_result_of_player <= 39){
            int move_count_of_player;
            old_position_of_player = igm.getCurrentPlayerCurrentPosition();
            if (card_result_of_player > old_position_of_player){
                move_count_of_player = card_result_of_player - old_position_of_player;
            }
            else {
                move_count_of_player = 40 - (old_position_of_player - card_result_of_player);
            }
            igm.startTurn(move_count_of_player, false, multiplier);
            movePawn(pawns_of_players.get(turn), move_count_of_player, pawnTeam2.contains(pawns_of_players.get(igm.getCurrentPlayerId())), old_position_of_player);
        }
        else if (card_result_of_player == 5200) {
            get_player_jail((old_position_of_player + igm.getDice().getTotal()) % 40);
            player_drawed_jail_card = true;
            can_roll_dice = false;
        }
        else {
        }
    }

    boolean player_drawed_jail_card = false;

    @FXML
    public void close_drawable_cards(){
        drawable_card_info = 0;
        cards.setVisible(false);
        cards_background.setVisible(false);
        cards_anchor.setVisible(false);

        check_drawable_cards();
        player_viewed_card = true;
    }

    private boolean is_square(Square s, String type) {
        return switch (type) {
            case "buy" -> s.getType() == SquareType.NormalSquare || s.getType() == SquareType.RailroadSquare || s.getType() == SquareType.UtilitySquare;
            case "build" -> s.getType() == SquareType.NormalSquare;
            default -> false;
        };
    }

    private void update_square_info() {
        int squareId = igm.getBoard().getSquares().get(last_index_of_info_card).getId();
        PropertyCard tmpcard = igm.getSpecificProperty(squareId);
        Square tmpSquare = igm.getBoard().getSpecificSquare(squareId);
        if (is_square(tmpSquare, "buy")) {
            int tmpSquareLevel = tmpSquare.getLevel();
            card_image.setVisible(true);
            set_image_helper(card_image, "/scenes/sources/property-cards/", last_tmp_index_of_info_card);
            set_image_helper(info_card, "/scenes/sources/property-cards/", "info-card");
            buy_button.setVisible(true);
            sell_button.setVisible(true);
            assert tmpcard != null;
            if (igm.getOwner(squareId) != null) {
                if (tmpSquareLevel != -1){
                    setInfoCard(tmpSquareLevel, igm.getOwner(squareId).getName(), tmpcard.getRentPrices().get(tmpSquareLevel));
                }
                else {
                    setInfoCard(tmpSquareLevel, igm.getOwner(squareId).getName(), 0);
                }
            } else {
                setInfoCard(tmpSquareLevel, "-", tmpcard.getCost());
            }
        } else {
            System.out.println("ehelelele");
        }

        try {
            boolean levelUp = igm.checkLevelStatus(last_index_of_info_card).get(lvup);
            boolean levelDown = igm.checkLevelStatus(last_index_of_info_card).get(lvdw);
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
            System.out.println(npe.getMessage());
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
            set_image_helper(player_index,"/scenes/sources/", "circle");
            String tmp_pawn_name = pawns_of_players.get(i).getId().replace("pawn_button", "");
            tmp_pawn_name = tmp_pawn_name.replace("_", "-");
            set_image_helper(player_index, LOBBY_PAWNS, tmp_pawn_name);
            indexes_of_players.get(i++).setStyle("-fx-effect: dropshadow(three-pass-box, rgb(255,255,255), 0, 0, 0, 0);");
        }
        indexes_of_players.get(turn).setStyle("-fx-effect: dropshadow(three-pass-box, rgb(55,199,219), 8, 0, 0, 0);");
    }

    private void square_update_GUI() {
        int i = 0;
        for (Square s : igm.getBoard().getSquares()) {
            String position;
            int id = s.getId();
            if (id <= 10 || (id >= 20 && id <= 30)) {
                position = "v";
            } else
                position = "h";
            if (is_square(s, "build")) {
                if (s.getLevel() == 0) {
                    square_bars[i].setVisible(false);
                } else if (s.getLevel() == 5) {
                    set_image_helper(square_bars[i],"/scenes/sources/squares/", "sq-hotel-" + position );
                } else {
                    set_image_helper(square_bars[i],"/scenes/sources/squares/", "sq-house-" + s.getLevel() + "-" + position);
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
                set_image_helper(house_slot0, PROPERTY_CARDS, "house");
            }
            case 2 -> {
                house_slot3.setImage(null);
                house_slot2.setImage(null);
                set_image_helper(house_slot0, PROPERTY_CARDS, "house");
                set_image_helper(house_slot1, PROPERTY_CARDS, "house");
            }
            case 3 -> {
                set_image_helper(house_slot0, PROPERTY_CARDS, "house");
                set_image_helper(house_slot1, PROPERTY_CARDS, "house");
                set_image_helper(house_slot2, PROPERTY_CARDS, "house");
                house_slot3.setImage(null);
            }
            case 4 -> {
                set_image_helper(house_slot0, PROPERTY_CARDS, "house");
                set_image_helper(house_slot1, PROPERTY_CARDS, "house");
                set_image_helper(house_slot2, PROPERTY_CARDS, "house");
                set_image_helper(house_slot3, PROPERTY_CARDS, "house");
            }
            case 5 -> {
                set_image_helper(house_slot0, PROPERTY_CARDS, "hotel-bottom");
                set_image_helper(house_slot1, PROPERTY_CARDS, "hotel-middle");
                set_image_helper(house_slot2, PROPERTY_CARDS, "hotel-middle");
                set_image_helper(house_slot3, PROPERTY_CARDS, "hotel-top");
            }
        }
    }

    final String PROPERTY_CARDS = "/scenes/sources/property-cards/";

    //Image Set Helper
    public void set_image_helper(ImageView iv, String path, String name){
        String imageUrl = getClass().getResource(path + name + PNG).toExternalForm();
        Image image = new Image(imageUrl);
        iv.setImage(image);
    }

//    @FXML
//    public void saveGame(){
//        //TODO: SAİDCAN BURAYI DOĞRU ŞEKİLDE EDİTLE
//        igm.saveAndExit();
//        try{
//            closeButtonPressed();
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//    }

    @FXML
    public void exit_pressed(){
        in_game_menu.setVisible(true);
        in_game_menu.setDisable(false);
    }

    @FXML
    public void exit_save_back(MouseEvent e) throws Exception{
        String pressed = e.getPickResult().getIntersectedNode().getId();
        if (pressed.equals("quit")){
            InGameManager.clear();
            LobbyManager.clear();
            Main.changeScreen("src/main/resources/scenes/OuterController.fxml");
        }
        else if (pressed.equals("back")){
            in_game_menu.setVisible(false);
            in_game_menu.setDisable(true);
        }
        else {
            igm.saveAndExit();
            InGameManager.clear();
            LobbyManager.clear();
            Main.changeScreen("src/main/resources/scenes/OuterController.fxml");
        }
    }

    @FXML
    public void settings_pressed(){
        settings.setDisable(false);
        settings.setVisible(true);
    }

    @FXML
    public void save_settings(){

        //TODO save settings

        settings.setDisable(true);
        settings.setVisible(false);
    }
}