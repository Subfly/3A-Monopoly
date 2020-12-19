package com.yolopoly.managers;

import com.yolopoly.enumerations.*;
import com.yolopoly.models.bases.*;
import com.yolopoly.models.cards.PlaceCard;
import com.yolopoly.models.cards.PropertyCard;
import com.yolopoly.storage.Constants;
import com.yolopoly.storage.StorageUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class InGameManager {

    // Constants
    private final static int JAIL_TURN_COUNT = 3;
    private final static int AUCTION_START_MONEY = 500000;

    private static InGameManager innerEngine = null;
    private EffectManager effectManager;

    //**
    // Variables
    //**
    private ArrayList<String> chat;
    private ArrayList<String> log;
    private ArrayList<Player> players;
    private Dice dice;
    private Board board;
    private Bank bank;
    private int currentPlayerId;
    private GameState state;
    private GameMode gameMode;

    //Auction Related
    private int currentBid;
    private int auctionPropertyIndex;
    private int currentPlayerAuctioning;
    private ArrayList<Player> participants;

    //Broken player related
    /**
     * IMPORTANT!!!!!
     * THIS HASH IS THE CORE OF BANKRUPTCY
     * HENCE BE AWARE USING IT
     * <====VALUES====>
     *      FROM 0 TO PLAYERS.SIZE-1 ===> MONEY PAYING FROM A PARTICULAR PLAYER TO OTHER PLAYERS
     *      PLAYER.SIZE ===> MONEY PAYING TO BANK
     *      PLAYER.SIZE + 1 ===> MONEY PAYING AS TAX
     * <====STRUCTURE====>
     *     PAYING PLAYER ID / WHERE TO PAY / PAYMENT AMOUNT
     */
    private HashMap<Integer, HashMap<Integer, Integer>> brokenPlayersMoneyHash;

    //**
    // Constructor
    //**
    private InGameManager(){
    }

    public static InGameManager getInstance(){
        if(innerEngine == null){
            innerEngine = new InGameManager();
        }
        return innerEngine;
    }

    public static synchronized void clear(){
        innerEngine = new InGameManager();
    }

    public void initializeGame(boolean isSavedGamePlaying, GameMode mode, GameTheme theme, ArrayList<Player> players) {
        if(isSavedGamePlaying){
            //TODO: IMPLEMENT SAVED GAME
        }else{
            chat = new ArrayList<>();
            log = new ArrayList<>();
            this.players = players;
            bank = new Bank(theme, mode);
            dice = new Dice();
            board = new Board(mode, theme);
            currentPlayerId = 0;
            state = GameState.Linear;
            currentBid = 0;
            auctionPropertyIndex = -1;
            currentPlayerAuctioning = -1;
            brokenPlayersMoneyHash = new HashMap<>();
            gameMode = mode;
            this.participants = new ArrayList<>();
            effectManager = EffectManager.getInstance();

            for (Player p : players){
                p.setCurrentPosition(0);
                p.setStartMoney(Constants.START_MONEY);
            }
        }
    }

    //**
    // Functions
    //**
    public int getRent(int diceResult){

        int currentPlayerIndex = players.get(getCurrentPlayerId()).getCurrentPosition();
        PropertyCard prop = getSpecificProperty(currentPlayerIndex);
        Square square = board.getSpecificSquare(currentPlayerIndex);
        int rentAmount = 0;
        Player paidToPlayer = players.get(prop.getOwnedBy());

        if(square.getType() == SquareType.NormalSquare){

            int countPlayersColor = countPlayersColor(paidToPlayer, square);
            int countBoardColor = board.countColors(square);
            rentAmount = prop.getRentPrices().get(square.getRentMultiplier());
            if (countBoardColor == countPlayersColor){
                rentAmount = rentAmount * 2;
                System.out.println(square + " iki kat kira");
            }

        }else if(square.getType() == SquareType.RailroadSquare){
            int counter = -1;
            for(PropertyCard p : paidToPlayer.getOwnedPlaces()){
                if(board.getSpecificSquare(p.getId()).getType() == SquareType.RailroadSquare){
                    counter += 1;
                }
            }
            rentAmount = prop.getRentPrices().get(counter);
        }else if(square.getType() == SquareType.UtilitySquare){
            int counter = -1;
            for(PropertyCard p : paidToPlayer.getOwnedPlaces()){
                if(board.getSpecificSquare(p.getId()).getType() == SquareType.UtilitySquare){
                    counter += 1;
                }
            }
            int baseRent = prop.getRentPrices().get(counter);
            rentAmount = baseRent * diceResult;
        }
        return rentAmount;
    }

    public ArrayList<Currency> getCurrencies(){
        return this.bank.getCurrencyRates();
    }

    public int getCurrentPlayerCurrentPosition(){
        return players.get(currentPlayerId).getCurrentPosition();
    }

    public PropertyCard getSpecificProperty(int squareIndex ){
        for(PropertyCard p: bank.getPropertyCards()){
            if(p.getId() == squareIndex){
                return p;
            }
        }
        return null;
    }

    public Player getOwner(int squareIndex){
        var prop = getSpecificProperty(squareIndex);
        if(prop.getOwnedBy() != -1){
            return players.get(prop.getOwnedBy());
        }
        return null;
    }

    public int saveAndExit(){
        StorageUtil util = new StorageUtil();
        try{
            return util.saveGame(this) ? 1 : 0;
        }catch (IOException e){
            System.out.println("ERROR (3001) SAVE FAILED");
        }
        return 0;
    }

    public ArrayList<Integer> getSettings(){
        StorageUtil util = new StorageUtil();
        try{
            return util.getSettings();
        } catch (IOException e){
            System.out.println("ERROR (1001): INVALID FILE");
        }
        return null;
    }

    public void setSettings(int audio, int music){
        StorageUtil util = new StorageUtil();
        try{
            util.setSettings(audio, music);
        }catch (IOException e){
            System.out.println("ERROR (1002): INVALID WRITE TO FILE");
        }
    }

    public boolean changePlayerToBot(int index){
        var player = players.get(index);
        player.setHuman(false);
        players.set(index, player);
        return true;
    }

    public void addToChat(String data, String userName){
        chat.add(userName + ":\n" + data);
    }

    public void addToLog(String logAction, String userName){
        log.add("Player " + userName + " has " + logAction);
    }

    //**
    // Bot Handlers
    //**
    public boolean isCurrentPlayerHuman(){
        return this.players.get(currentPlayerId).isHuman();
    }

    /*
     * RETURN VALUES
     * -101 => BOT SELECTED TO ROLL DICE IN JAIL
     * -100 => ERROR OCCUR
     * -99 => BANKRUPT, ACCEPT DIRECTLY
     * -98 => PAID DEBTS
     * -97 => FREELY MADE DECISIONS
     * -3 => MOVE BACKWARDS
     * VALUE BETWEEN 0 TO 39 => MOVE TO INDEX
     */
    public void jailMakeDecision(double multiplier){
        Player bot = players.get(currentPlayerId);
        //Pay money and get out
        bot.removeMoney(Constants.CURRENCY_NAMES[0], (int)(Bank.getJailPenalty() * multiplier));
        bot.setInJail(false);
        bot.resetInJailTurnCount();
        bot.resetDoublesCount();
    }

    public int makeDecision(int diceResult, boolean isDouble){
        double multiplier = 1;
        if (this.gameMode ==  GameMode.bankman) {
            int decision = (int)(Math.random() * 2 + 1);
            if(decision == 1){
                multiplier = this.generateChanceMultiplier(diceResult);
            }
        }

        int result = startTurn(diceResult, isDouble, multiplier);

        Player bot = players.get(currentPlayerId);
        if(result == -100){
            int jailDecision = (int) (Math.random() * 2 + 1);
            if(jailDecision == 1){
                //Pay money and get out
                bot.removeMoney(Constants.CURRENCY_NAMES[0], (int) (Bank.getJailPenalty() * multiplier));
                bot.setInJail(false);
                bot.resetInJailTurnCount();
                bot.resetDoublesCount();
                //Make decision method again
                return makeDecision(diceResult, isDouble);
            }
        }
        else if(result == -99){
            return payDebtBot(bot, multiplier);
        }
        else if(result == 1){
            int cardRes = drawCard(DrawableCardType.Chance, multiplier);
            if (cardRes == -99){
                return payDebtBot(bot, multiplier);
            }
            else if (cardRes == -3){
                return cardRes;
            }
            else if(cardRes >= 0 && cardRes <= 39){
                return cardRes;
            }
        }
        else if(result == 2){
            int cardRes = drawCard(DrawableCardType.Community, multiplier);
            if (cardRes == -99){
                return payDebtBot(bot, multiplier);
            }
            else if (cardRes == -3){
                return cardRes;
            }
            else if(cardRes >= 0 && cardRes <= 39){
                return cardRes;
            }
            else if (cardRes >= 100000){
                int decision = (int) (Math.random() * 2 + 1);
                if(decision == 1){
                    if(bot.removeMoney(Constants.CURRENCY_NAMES[0], (int)(cardRes * multiplier))){
                        return -97;
                    }else{
                        //YİNE Mİ BATTIN BE HACI YETER YA
                        return payDebtBot(bot, multiplier);
                    }
                }else{
                    int cardResAgain = drawCard(DrawableCardType.Chance, multiplier);
                    if (cardResAgain == -99){
                        return payDebtBot(bot, multiplier);
                    }
                    else if (cardResAgain == -3){
                        return cardResAgain;
                    }
                    else if(cardResAgain >= 0 && cardResAgain <= 39){
                        return cardResAgain;
                    }
                }
            }
            else if (cardRes == 6000 || cardRes == 6100 || cardRes == 7000){
                return -98;
            }
        }
        else if(result == 7){
            Square squareToBuy = board.getSquares().get(bot.getCurrentPosition());
            boolean isBuyable = (squareToBuy.getType() == SquareType.NormalSquare) || (squareToBuy.getType() == SquareType.RailroadSquare) || (squareToBuy.getType() == SquareType.UtilitySquare);
            if(checkBuyProperty(bot.getCurrentPosition()) && isBuyable){
                //Just buy the area
                buyProperty();
            }
            int totalBoughtProperties = bot.getOwnedPlaces().size();
            int randomArea = (int)(Math.random() * totalBoughtProperties + 1);
            if(checkBuildBuilding(Building.House, board.getSpecificSquare(randomArea)).containsKey(true)){
                //Build house
                levelUp(randomArea, multiplier);
            }
            if(checkBuildBuilding(Building.Hotel, board.getSpecificSquare(randomArea)).containsKey(true)){
                //Build hotel
                levelUp(randomArea, multiplier);
            }
            return -97;
        }
        else {
            return -99;
        }
        return -100;
    }

    //**
    // Bankruptcy Handlers
    //**
    public boolean payDebt(double multiplier){
        Player player = players.get(currentPlayerId);
        effectManager.playMoneyEffect();
        var debtData = brokenPlayersMoneyHash.get(currentPlayerId);
        int moneyPayIndex = debtData.entrySet().iterator().next().getKey();
        int debt = debtData.get(moneyPayIndex);
        if(moneyPayIndex < players.size()){
            //Paying money to other player
            player.removeMoney(Constants.CURRENCY_NAMES[0], (int)(debt * multiplier));
            Player otherPlayer = players.get(moneyPayIndex);
            otherPlayer.addMoney(Constants.CURRENCY_NAMES[0], (int)(debt * multiplier));
            player.setBankrupt(false);
            return true;
        }else if(moneyPayIndex == players.size()){
            //Paying to bank, just pay...
            player.removeMoney(Constants.CURRENCY_NAMES[0], (int)(debt * multiplier));
            player.setBankrupt(false);
            return true;
        }else if(moneyPayIndex == players.size() + 1){
            //Paying to free park space :D
            player.removeMoney(Constants.CURRENCY_NAMES[0], (int)(debt * multiplier));
            board.addToTaxMoney(debt);
            player.setBankrupt(false);
            return true;
        }
        return false;
    }

    public int payDebtBot(Player bot, double multiplier){
        var debtData = brokenPlayersMoneyHash.get(currentPlayerId);
        int moneyPayIndex = debtData.entrySet().iterator().next().getKey();
        int debt = debtData.get(moneyPayIndex);
        //Look in properties
        for(PropertyCard p : bot.getOwnedPlaces()){
            Square s = board.getSpecificSquare(p.getId());
            //Select the one with less valuable
            if(s.getLevel() == 0){
                //Mortgage that place
                levelDown(s.getId(), multiplier);
                //If has enough money
                if(bot.getMonopolyMoneyAmount() >= debt){
                    if(payDebt(multiplier)){
                        return -98;
                    }else{
                        return -100;
                    }
                }
            }
        }
        //Look in properties but now start to sell hotels and houses
        for(PropertyCard p : bot.getOwnedPlaces()){
            Square s = board.getSpecificSquare(p.getId());
            //Select the first one that has hotels or houses
            while(s.getLevel() > 0){
                //Mortgage that place
                levelDown(s.getId(), multiplier);
                //If has enough money
                if(bot.getMonopolyMoneyAmount() >= debt){
                    if(payDebt(multiplier)){
                        return -98;
                    }else{
                        return -100;
                    }
                }
            }
        }
        //BROKE HACI GEÇMİŞ OLSUN
        return -99;
    }

    //**
    // Private Functions
    //**
    private String parser(int amount){
        int million = 0;
        int remainder = 0;
        int thousand = 0;
        if(amount >= 1000000){
            million = amount / 1_000_000;
            remainder = (amount - (million * 1_000_000)) / 1_000;
            return "" + million + "." + remainder + " M";
        }else if(amount >= 100000){
            thousand = amount/ 1000;
            return "" + thousand + " K";
        }
        else if (amount >= 0) {
            int value = amount / 1000;
            return "" + value + "K";
        }
        return "";
    }

    private int getBuyer(int squareIndex){
        for(PropertyCard p: bank.getPropertyCards()){
            if(squareIndex == p.getId()){
                return p.getOwnedBy();
            }
        }
        return -1;
    }

    private int countPlayersColor(Player player, Square square){
        return (int) player.getOwnedPlaces().stream().filter(s-> s.getColor() == square.getColor()).count();
    }

    //**
    // Turn Related Functions
    //**
    public void rollDice(){
        effectManager.playRollEffect();
        dice.roll();
    }

    /*
     * RETURN VALUES EXPLAINED
     * -100 => PLAYER IN JAIL
     * -99 => PLAYER BROKE, PAY DEBTS
     * -2 => PLAYER DOUBLED THREE TIMES
     * -1 => ERROR APPEARED SUCCESSFULLY
     * 1 => DRAW CHANCE
     * 2 => DRAW COMMUNITY
     * 3 => PAID TAX
     * 4 => GO TO JAIL
     * 5 => GET TAXES FROM PARK
     * 6 => PAID RENT
     * 7 => EVERYTHING IS DONE, GOODBYE!
     *
     * JAIL ALGORITHM
     * -99 => LET PLAYER PAY DEBTS, THEN startTurn() AGAIN
     * -100 => LET PLAYER TO CHOOSE FROM DICE OR PAY FINE
     * NO SPECIAL HANDLE IN GETTING OUT
     */
    public int startTurn(int diceResult, boolean hasRolledDouble, double multiplier){
        /*
        Basic structure of a turn is consists of:
            Rolling the dice
            Moving the Pawn where the dice show
                If Draw Card Square -> Draw according Card
                If Tax Square -> Pay Tax, the money will remain on the board
                If Go To Jail Square -> Go to Jail
                If Free Park -> Get the money that accumulated through paying taxes
                If Property Square
                    If not bought -> Buy or Pass
                    If bought -> Pay Rent
             After Movement, Player is released for actions but these calculations needs to be done:
                If player doubled -> double count incremented
                If player passed GO! Square without the help of a card, pay 2000000
                Return true as turn completed
         */

        //If currently in auction
        if(this.state == GameState.Auction){
            return -1;
        }

        if(this.gameMode == GameMode.vanilla){
            multiplier = 1;
        }

        //Start with getting player
        Player player = players.get(currentPlayerId);

        if(gameMode == GameMode.bankman){
            if(player.isGetLoanCurrently()){
                if(player.decrementLoanTurn()){
                    if(player.getLoanTurn() == 0){
                        addToLog("to pay loans to bank in this turn!", player.getName());
                    }else{
                        addToLog("only " + player.getLoanTurn() + " turns to pay loan", player.getName());
                    }
                }
            }
        }

        if(player.isInJail()){
            player.incrementInJailTurnCount();
        }

        if(player.isInJail() && hasRolledDouble){
            player.setInJail(false);
            player.resetInJailTurnCount();
        }

        int jailResult = checkJailStatus();
        if(jailResult == 1){
            return payForGetOutOfJail(multiplier);
        }else if(jailResult == 2){
            return -100;
        }

        if(hasRolledDouble){
            player.incrementDoublesCount();
        }
        else {
            player.resetDoublesCount();
        }

        if(player.isThreeTimesDoubled()){
            effectManager.playJailEffect();
            player.setInJail(true);
            player.setCurrentPosition(10);
            player.resetDoublesCount();
            player.resetInJailTurnCount();
            return -2;
        }

        //Moving the Pawn where the dice show
        int oldPosition = player.getCurrentPosition();
        player.setCurrentPosition(oldPosition + diceResult);

        if(oldPosition > player.getCurrentPosition()){
            //Passed GO! Square
            if (player.isHuman()) {
                effectManager.playMoneyEffect();
            }
            player.addMoney(Constants.CURRENCY_NAMES[0], (int)(Constants.GO_SQUARE_MONEY * multiplier));
        }

        //Get the square where pawn landed
        Square square = board.getSpecificSquare(player.getCurrentPosition());

        //Start If's
        //If chance square
        if(player.getCurrentPosition() == square.getId()){
            if(square.getType() == SquareType.ChanceSquare){
                if (player.isHuman()) {
                    effectManager.playDrawCardEffect();
                }
                return 1;
            }
            //If Community Chest Square
            else if(square.getType() == SquareType.CommunityChestSquare){
                if (player.isHuman()) {
                    effectManager.playDrawCardEffect();
                }
                return 2;
            }
            //If Tax Square
            else if(square.getType() == SquareType.TaxSquare){
                // Get the tax amount
                int taxAmount = square.getCost();
                if(player.removeMoney(Constants.CURRENCY_NAMES[0], (int) (taxAmount * multiplier))){
                    addToLog("paid tax of " + parser(taxAmount), player.getName());
                    return 3;
                }else{
                    player.setBankrupt(true);
                    HashMap<Integer, Integer> payHash = new HashMap<>();
                    payHash.put(players.size()+1, (int)(taxAmount * multiplier));
                    brokenPlayersMoneyHash.put(currentPlayerId, payHash);
                    return -99;
                }
            }
            //If Go to Jail Square
            else if(square.getType() == SquareType.GoToJailSquare){
                effectManager.playJailEffect();
                player.setCurrentPosition(10); //Move to jail hardcode
                player.setInJail(true);
                addToLog("sent to the jail", player.getName());
                return 4;
            }
            //If Free Parking Square, get money...
            else if(square.getType() == SquareType.FreeParkingSquare){
                if (player.isHuman()) {
                    effectManager.playFreeParkingEffect();
                }
                int taxAmountOnBoard = board.getMoneyOnBoard();
                if (taxAmountOnBoard != 0) {
                    if (player.isHuman()) {
                        effectManager.playMoneyEffect();
                    }
                    player.addMoney(Constants.CURRENCY_NAMES[0], (int)(taxAmountOnBoard * multiplier));
                    board.removeFromTaxMoney();
                    addToLog("got the money on the board with the amount of " + parser(taxAmountOnBoard) + " Monopoly Dollars", player.getName());
                }
                return 5;
            }else{
                //If pawn of the player landed on a property square :D Hardest part coming...
                int buyerIdOfProperty = getBuyer(square.getId());
                if(square.isBought() && buyerIdOfProperty != currentPlayerId){
                    //Create a dummy player holder to change players data in the end
                    Player paidToPlayer = players.get(buyerIdOfProperty);

                    //Find rent amount
                    PropertyCard prop = getSpecificProperty(square.getId());
                    assert prop != null;
                    int rentAmount = getRent(diceResult);

                    //Remove money from current player
                    boolean isAbleToPay = player.removeMoney(Constants.CURRENCY_NAMES[0], (int)(rentAmount * multiplier));
                    if(!isAbleToPay){
                        player.setBankrupt(true);
                        HashMap<Integer, Integer> payHash = new HashMap<>();
                        payHash.put(buyerIdOfProperty, (int)(rentAmount * multiplier));
                        brokenPlayersMoneyHash.put(currentPlayerId, payHash);
                        return -99;
                    }
                    //Add money to other player
                    paidToPlayer.addMoney(Constants.CURRENCY_NAMES[0], (int)(rentAmount * multiplier));

                    addToLog("paid " + parser(rentAmount) + " as rent to " + paidToPlayer.getName(), player.getName());
                    return 6;
                }else{
                    //Not bought, this part left to frontend
                    return 7;
                }
            }
        }else{
            //Error occur!
            return -1;
        }
    }

    /*
     * RETURN VALUES
     * 1 => NORMAL END
     * 2 => DID NOT PAID LOANS
     * 3 => GAME DONE, REMAINING PLAYER WINS!
     * 4 => AUCTION
     */
    public int endTurn(){
        if(players.size() == 1){
            return 3;
        }

        Player player = players.get(currentPlayerId);

        if(player.isGetLoanCurrently() && checkHasToPayLoanBack()){
            player.setBankrupt(true);
        }

        if (player.isInJail()){
            player.incrementInJailTurnCount();
            System.out.println(player.getInJailTurnCount() + " inner enginde");
        }

        if(player.isBankrupt()){
            //Remove player
            players.remove(currentPlayerId);
            //Return properties
            for(PropertyCard p : player.getOwnedPlaces()){
                p.setOwnedBy(-1);
                Square s = board.getSpecificSquare(p.getId());
                //Return houses and hotels
                if(s.getHotelCount() > 0 || s.getHouseCount() > 0){
                    s.setHotelCount(0);
                    s.setHouseCount(0);
                    s.setLevel(0);
                }
            }
            //Return GOOJC
            if(player.getSavedCards().size() != 0){
                var c = player.removeFromSavedCards();
                board.returnSavedCard(c);
            }
            currentPlayerId--;
            //Clear debts :D
            brokenPlayersMoneyHash.clear();
        }

        /*
        if (gameMode.equals(GameMode.bankman)) {
            // check loan for bankman mod
            player.decrementLoanTurn();
            if (checkHasToPayLoanBack()) {
                player.setDiscardedFromGame(true);
                return 2;
            }
        }
        */

        Square lastSquareMadeSomething = board.getSpecificSquare(players.get(currentPlayerId).getCurrentPosition());
//        boolean isBuyable = (lastSquareMadeSomething.getType() == SquareType.NormalSquare) || (lastSquareMadeSomething.getType() == SquareType.UtilitySquare) || (lastSquareMadeSomething.getType() == SquareType.RailroadSquare);
//        if(isBuyable && !lastSquareMadeSomething.isBought()){
//            createAuction();
//            return 4;
//        }

        this.currentPlayerId += 1;

        if(this.currentPlayerId > players.size() - 1){
            this.currentPlayerId = 0;
        }

        if (this.gameMode == GameMode.bankman){
            changeCurrenciesOfBank();
        }

        return 1;
    }

    //**
    // Action Related Functions
    //**
    public void buyProperty(){
        //Get changing data
        Player currentPlayer = players.get(currentPlayerId);
        Square square = board.getSpecificSquare(currentPlayer.getCurrentPosition());
        PropertyCard card = getSpecificProperty(square.getId());
        System.out.println("Name of area is: " + card.getName());
        System.out.println("Id of the square is: " + square.getId());
        System.out.println("Id of the prop is: " + card.getId());

        //Make changes on data
        card.setOwnedBy(currentPlayerId);
        currentPlayer.ownProperty(getSpecificProperty(square.getId()));

        //Bura sürekli siniliniyor kafayı yiyeceğim - Ali
        currentPlayer.removeMoney(Constants.CURRENCY_NAMES[0], card.getCost());

        //Save changes on data
        //TODO ponçik ali taha olur böyle şeyler
        bank.getPropertyCards().set(bank.getPropertyCards().indexOf(card), card);
        board.buySquare(square.getId());

        addToLog("bought property named : " + card.getName(), currentPlayer.getName());
    }

    public void mortgagePlace(int squareIndex, double multiplier) {
        Player player = players.get(getCurrentPlayerId());
        player.getSpecificCard(squareIndex).setMortgaged(true);
        board.getSquares().get(squareIndex).setLevel(-1);
        PropertyCard currentPlace = player.getSpecificCard(squareIndex);
        int moneyToAdd = currentPlace.getMortgagePrice();
        if (player.isHuman()) {
            effectManager.playMoneyEffect();
        }
        player.addMoney(Constants.CURRENCY_NAMES[0], (int)(moneyToAdd * multiplier));

    }
    public boolean removeMortgageFromPlace(int squareIndex, double multiplier) {
        Player player = players.get(getCurrentPlayerId());
        PropertyCard currentPlace = player.getSpecificCard(squareIndex);
        int mortgagePrice = currentPlace.getMortgagePrice();
        int mortgagePenalty = (int) (mortgagePrice * PropertyCard.getMortgagePenalty());
        if (player.removeMoney(Constants.CURRENCY_NAMES[0], (int)((mortgagePrice + mortgagePenalty) * multiplier))) {
            player.getSpecificCard(squareIndex).setMortgaged(false);
            board.getSquares().get(squareIndex).setLevel(0);
            return true;
        }
        return false;
    }

    public void createAuction(){
        this.state = GameState.Auction;
        this.auctionPropertyIndex = players.get(currentPlayerId).getCurrentPosition();
        this.currentBid = AUCTION_START_MONEY;
        participants.addAll(players);
        this.currentPlayerAuctioning = 0;
        addToLog("created auction on the property", players.get(currentPlayerId).getName());
    }

    public void continueAuction(int bidIncrease){
        addToLog("increased bid by: " + parser(bidIncrease), participants.get(currentPlayerAuctioning).getName());
        this.currentPlayerAuctioning += 1;
        if(this.currentPlayerAuctioning > participants.size()){
            this.currentPlayerAuctioning = 0;
        }
        this.currentBid += bidIncrease;
    }

    //TODO: POSSIBLE LOGIC ERROR (but I think I solved it :D - Ali the Lele)
    public void pullOffAuction(){
        addToLog("left auction", participants.get(currentPlayerAuctioning).getName());
        participants.remove(currentPlayerAuctioning);
        if(currentPlayerAuctioning == this.participants.size() - 1){
            //If last player
            this.currentPlayerAuctioning = -1;
        }else{
            this.currentPlayerAuctioning -= 1;
        }
        continueAuction(0);
    }

    public void endAuction(){
        if(checkAuctionStatus()){
            Player currentPlayer = participants.get(currentPlayerAuctioning);
            Square square = board.getSpecificSquare(auctionPropertyIndex);
            PropertyCard card = bank.getPropertyCards().get(square.getId());

            //Make changes on data
            card.setOwnedBy(currentPlayerAuctioning);
            currentPlayer.ownProperty(getSpecificProperty(square.getId()));
            bank.getPropertyCards().set(square.getId(), card);
            board.buySquare(square.getId());
            addToLog("bought property for: " + parser(this.currentBid), participants.get(currentPlayerAuctioning).getName());

            //Continue game in linear from the next player
            this.currentPlayerId += 1;
            if(this.currentPlayerId > players.size() - 1){
                this.currentPlayerId = 0;
            }

            this.state = GameState.Linear;
        }
    }

    public void buildBuilding(Building buildingType, int squareIndex, double multiplier) {
        Player player = players.get(currentPlayerId);

        Square squareToBuild = board.getSpecificSquare(squareIndex);
        PlaceCard currentPlace = (PlaceCard) player.getSpecificCard(squareToBuild.getId());

        int money;
        if ( buildingType == Building.House ){
            money = currentPlace.getHousePrice();
            bank.decrementHouseCount();
        }
        else{
            money = currentPlace.getHotelPrice();
            bank.decrementHotelCount();
        }

        board.build(buildingType, squareToBuild.getId());

        player.removeMoney(Constants.CURRENCY_NAMES[0], (int)(money * multiplier));

        addToLog("built structure on the property: " + currentPlace.getName(), player.getName());
    }

    public void destructBuilding(Building buildingType, int squareIndex, double multiplier){
        Player player = players.get(currentPlayerId);

        Square squareToDestruct = board.getSpecificSquare(squareIndex);
        PlaceCard currentPlace = (PlaceCard) player.getSpecificCard(squareToDestruct.getId());

        int money;
        if ( buildingType == Building.House ){
            money = (int) (currentPlace.getHousePrice() / Bank.getReturnRate());
            bank.incrementHouseCount();
        }
        else{
            money = currentPlace.getHotelPrice();
            bank.decrementHotelCount();
        }

        board.destroy(buildingType, squareToDestruct.getId());

        if (player.isHuman()) {
            effectManager.playMoneyEffect();
        }
        player.addMoney(Constants.CURRENCY_NAMES[0], (int)(money * multiplier));
        addToLog("destroyed structure on the property: " + currentPlace.getName(), player.getName());
    }

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
    public int drawCard(DrawableCardType cardType, double multiplier){
        if(cardType == DrawableCardType.Chance){
            var cardDrawn = board.drawChanceCard();
            var player = players.get(currentPlayerId);
            //Algorithm
            addToLog("drawn a chance card including message: " + cardDrawn.getMessage(), player.getName());
            if(cardDrawn.isGOOJC()){
                //If card is a GOOJC, save to inventory of the current player
                player.addToSavedCards(cardDrawn);
                return 5100;
            }else if(cardDrawn.isGTJC()){
                //If card is a GTJC, move player to jail
                player.setCurrentPosition(10);
                player.setInJail(true);
                System.out.println("helal lan alitahasubuçak");
                return 5200;
            }else{
                if(cardDrawn.isComposed()){
                    //If the card has more than one operation
                    if(cardDrawn.isMoving()){
                        //If requires moving, look for movement
                        int moveToIndex = cardDrawn.getMoveToIndex();
                        int moveInCounts = cardDrawn.getMoveInCounts();
                        if(moveToIndex == -1 && moveInCounts != -1){
                            //If card specifies to move forward
                            //player.setCurrentPosition(player.getCurrentPosition() + moveInCounts);
                            return -3;
                        }else if(moveToIndex != -1 && moveInCounts == -1){
                            //If card specifies to move to another square
                            if(cardDrawn.isGettingMoney()){
                                //If passed GO! Square during move
                                int currentPosition = player.getCurrentPosition();
                                if(currentPosition > moveToIndex){
                                    if (player.isHuman()) {
                                        effectManager.playMoneyEffect();
                                    }
                                    player.addMoney(Constants.CURRENCY_NAMES[0], (int) (Constants.GO_SQUARE_MONEY * multiplier));
                                }
                            }
                            //player.setCurrentPosition(moveToIndex);
                            return moveToIndex;
                        }
                    }else if(cardDrawn.isRelatedToBuildings()){
                        //If not moving but paying for each building owned
                        var owned = player.getOwnedPlaces();
                        int housesOwned = 0;
                        int hotelsOwned = 0;
                        for(PropertyCard c : owned){
                            for(Square s : board.getSquares()){
                                if(c.getId() == s.getId()){
                                    housesOwned += s.getHouseCount();
                                    hotelsOwned += s.getHotelCount();
                                }
                            }
                        }
                        int moneyToHotels = hotelsOwned * cardDrawn.getMoneyForHotels();
                        int moneyToHoses = housesOwned * cardDrawn.getMoneyForHouses();
                        if(player.removeMoney(Constants.CURRENCY_NAMES[0], (int)((moneyToHoses + moneyToHotels) * multiplier))){
                            return 6000;
                        }else{
                            player.setBankrupt(true);
                            HashMap<Integer, Integer> payHash = new HashMap<>();
                            payHash.put(players.size(), (int)((moneyToHoses + moneyToHotels) * multiplier));
                            brokenPlayersMoneyHash.put(currentPlayerId, payHash);
                            return -99;
                        }
                    }
                }else{
                    //If not composed or moving, hence paying money
                    if(player.removeMoney(Constants.CURRENCY_NAMES[0], (int)(cardDrawn.getMoneyOwe() * multiplier))){
                        return 6100;
                    }else{
                        player.setBankrupt(true);
                        HashMap<Integer, Integer> payHash = new HashMap<>();
                        payHash.put(players.size(), (int)(cardDrawn.getMoneyOwe() * multiplier));
                        brokenPlayersMoneyHash.put(currentPlayerId, payHash);
                        return -99;
                    }
                }
            }
        }else{
            var cardDrawn = board.drawCommunityChestCard();
            var player = players.get(currentPlayerId);
            addToLog("drawn a community chest card including message: " + cardDrawn.getMessage(), player.getName());
            if(cardDrawn.isGOOJC()){
                //If card is a GOOJC, save to inventory of the current player
                player.addToSavedCards(cardDrawn);
                return 5100;
            }else if(cardDrawn.isGTJC()){
                //If card is a GTJC, move player to jail
                player.setCurrentPosition(10);
                player.setInJail(true);
                System.out.println("helal lan alitahasubuçak");
                return 5200;
            }else if(cardDrawn.isDrawingChanceCard()){
                //Needed to be handled in front-end.
                return cardDrawn.getMoneyOwe();
            }else{
                if(cardDrawn.isComposed()){
                    //If the card has more than one operation
                    if(cardDrawn.isMoving()){
                        //If requires moving, look for movement
                        int moveToIndex = cardDrawn.getMoveToIndex();
                        int moveInCounts = cardDrawn.getMoveInCounts();
                        if(moveToIndex == -1 && moveInCounts != -1){
                            //If card specifies to move forward
                            //player.setCurrentPosition(player.getCurrentPosition() + moveInCounts);
                            return -3;
                        }else if(moveToIndex != -1 && moveInCounts == -1){
                            //If card specifies to move to another square
                            if(cardDrawn.isGettingMoney()){
                                //If passed GO! Square during move
                                int currentPosition = player.getCurrentPosition();
                                if(currentPosition > moveToIndex){
                                    player.addMoney(Constants.CURRENCY_NAMES[0], (int)(Constants.GO_SQUARE_MONEY * multiplier));
                                }
                            }
                            //player.setCurrentPosition(moveToIndex);
                            return moveToIndex;
                        }
                    }else if(cardDrawn.isRelatedToBuildings()){
                        //If not moving but paying for each building owned
                        var owned = player.getOwnedPlaces();
                        int housesOwned = 0;
                        int hotelsOwned = 0;
                        for(PropertyCard c : owned){
                            for(Square s : board.getSquares()){
                                if(c.getId() == s.getId()){
                                    housesOwned += s.getHouseCount();
                                    hotelsOwned += s.getHotelCount();
                                }
                            }
                        }
                        int moneyToHouses = housesOwned * cardDrawn.getMoneyForHouses();
                        int moneyForHotels = hotelsOwned * cardDrawn.getMoneyForHotels();
                        if(player.removeMoney(Constants.CURRENCY_NAMES[0], (int)((moneyToHouses + moneyForHotels) * multiplier))){
                            if (player.isHuman()) {
                                effectManager.playMoneyEffect();
                            }
                            return 6000;
                        }else{
                            player.setBankrupt(true);
                            HashMap<Integer, Integer> payHash = new HashMap<>();
                            payHash.put(players.size(), (int)((moneyToHouses + moneyForHotels) * multiplier));
                            brokenPlayersMoneyHash.put(currentPlayerId, payHash);
                            return -99;
                        }
                    }else if(cardDrawn.isEachPlayerIncluded()){
                        if (player.isHuman()) {
                            effectManager.playMoneyEffect();
                        }
                        //Or paying money to other players
                        for (int i = 0; i < players.size(); i++) {
                            if(i != currentPlayerId){
                                var otherPlayer = players.get(i);
                                boolean ableToPay = otherPlayer.removeMoney(Constants.CURRENCY_NAMES[0], (int)(cardDrawn.getMoneyOwe() * multiplier));
                                if(!ableToPay){
                                    otherPlayer.setBankrupt(true);
                                }
                            }
                        }
                        player.addMoney(Constants.CURRENCY_NAMES[0], (int)((cardDrawn.getMoneyOwe() * players.size() - 1) * multiplier));
                        return 7000;
                    }
                }else{
                    //If not composed or moving, hence paying money
                    if(player.removeMoney(Constants.CURRENCY_NAMES[0], (int)(cardDrawn.getMoneyOwe() * multiplier))){
                        if (player.isHuman()) {
                            effectManager.playMoneyEffect();
                        }
                        return 6100;
                    }else{
                        player.setBankrupt(true);
                        HashMap<Integer, Integer> payHash = new HashMap<>();
                        payHash.put(players.size(), (int)(cardDrawn.getMoneyOwe() * multiplier));
                        brokenPlayersMoneyHash.put(currentPlayerId, payHash);
                        return -99;
                    }
                }
            }
        }
        return -1;
    }

    public void levelUp(int squareIndex, double multiplier){
        Square square = board.getSpecificSquare(squareIndex);
        int level = square.getLevel();

        if (level == -1){
            removeMortgageFromPlace(squareIndex, multiplier);
        }
        else if (level == 0 && !square.isBought()){
            buyProperty();
        }
        else if(level == 0 && square.isBought()){
            buildBuilding(Building.House, squareIndex, multiplier);
        }
        else if(level == 1){
            buildBuilding(Building.House, squareIndex, multiplier);
        }
        else if(level == 2){
            buildBuilding(Building.House, squareIndex, multiplier);
        }
        else if(level == 3){
            buildBuilding(Building.House, squareIndex, multiplier);
        }
        else if(level == 4){
            buildBuilding(Building.Hotel, squareIndex, multiplier);
        }
    }

    public void levelDown(int squareIndex, double multiplier){
        Square square = board.getSpecificSquare(squareIndex);
        int level = square.getLevel();

        if (level == 5){
            destructBuilding(Building.Hotel, squareIndex, multiplier);
        }
        else if(level == 4){
            destructBuilding(Building.House, squareIndex, multiplier);
        }
        else if(level == 3){
            destructBuilding(Building.House, squareIndex, multiplier);
        }
        else if(level == 2){
            destructBuilding(Building.House, squareIndex, multiplier);
        }
        else if(level == 1){
            destructBuilding(Building.House, squareIndex, multiplier);
        }
        else if(level == 0){
            mortgagePlace(squareIndex, multiplier);
        }
    }

    /*
     * RETURN VALUES
     * 1 => PAID AND SET FREE
     * -99 => CAN NOT PAID AND HAS TO PAY DEBTS
     * 3 => NOT EVEN IN JAIL
     */
    public int payForGetOutOfJail(double multiplier) {
        Player player = players.get(currentPlayerId);
        if (player.isInJail()) {
            if (player.removeMoney(Constants.CURRENCY_NAMES[0], (int)(Bank.getJailPenalty() * multiplier))) {
                if (player.isHuman()) {
                    effectManager.playMoneyEffect();
                }
                addToLog("has got out of jail", player.getName());
                player.setInJail(false);
                player.resetInJailTurnCount();
                return 1;
            }
            else {
                player.setBankrupt(true); // player has gone bankrupt
                HashMap<Integer, Integer> payHash = new HashMap<>();
                payHash.put(players.size(), (int)(Bank.getJailPenalty() * multiplier));
                brokenPlayersMoneyHash.put(currentPlayerId, payHash);
                return -99; // player has not enough money to get out of jail, go bankrupt
            }
        }
        return 3; // player is not even in jail, control return
    }

    //**
    // Bankman Mode Functions
    //**
    public boolean giveLoan(int amount) {
        Player player = players.get(currentPlayerId);
        if (player.isGetLoanCurrently()) {
            System.out.println("You need to pay your loan first");
            return false;
        }
        System.out.println("Gave loan to the player " + player.getName());
        addToLog("got loan in amount " + parser(amount), player.getName());
        bank.giveLoan(amount, player);
        return true;
    }

    public boolean receiveLoanBackFromPlayer(double multiplier) {
        Player player = players.get(currentPlayerId);
        if (!player.isGetLoanCurrently()) {
            System.out.println("The player is not on the loan currently");
            return false;
        }
        int amount = player.getLoan();
        if(player.removeMoney(Constants.CURRENCY_NAMES[0], (int)(amount * multiplier))){
            if (player.isHuman()) {
                effectManager.playMoneyEffect();
            }
            System.out.println("Player paid his/her loan " + player.getName());
            addToLog("paid loan back with amount of: " + parser(amount), player.getName());
            player.resetLoan();
            return true;
        }else{
            player.setBankrupt(true);
            HashMap<Integer, Integer> payHash = new HashMap<>();
            payHash.put(players.size(), (int)(amount * multiplier));
            brokenPlayersMoneyHash.put(currentPlayerId, payHash);
            return false;
        }
    }

    public boolean checkHasToPayLoanBack() {
        Player player = players.get(currentPlayerId);
        if (player.getLoanTurn() == 0) {
            return true;
        }
        return false;
    }

    public void changeCurrenciesOfBank() {
        // call this function in the end of the each turn
        this.bank.changeCurrencies();
    }

    public int exchangeCurrency(String fromCurrency, String toCurrency, int amount) {
        Player player = players.get(currentPlayerId);
        return this.bank.exchangeMoney(player, fromCurrency, toCurrency, amount);
    }

    public double generateChanceMultiplier(int diceResult) {
        double result;
        int randomResult = (int) ((Math.random() * (12 - 1)) + 1);
        if (randomResult >= 1 && randomResult <= 3) {
            result = 0.5;
        }
        else if (randomResult >= 4 && randomResult <= 6) {
            result = 1;
        }
        else if (randomResult >= 7 && randomResult <= 9) {
            result = 1.5;
        }
        else {
            result = 2.0;
        }
        addToLog("has a chance multiplier of " + result + "for the turn", players.get(currentPlayerId).getName());
        return result;
    }

    //**
    // Checker Functions
    //**

    /*
     * RETURN VALUES
     * -1 => NOT IN JAIL
     * 1 => HAS TO GET OUT
     * 2 => HAS NOT ENOUGH MONEY / BROKE
     * 3 => IN JAIL
     */
    public int checkJailStatus() {
        Player player = players.get(currentPlayerId);
        if (!player.isInJail()) {
            return -1; // Player is not in jail
        }
        int jailTurnCount = player.getInJailTurnCount();
        if (jailTurnCount == JAIL_TURN_COUNT) {
            return 1; // the player has to go out right now
        }
        if(player.getMonopolyMoneyAmount() >= Bank.getJailPenalty()){
            return 2; // the player has enough money to get out of jail
        }
        return 3; // the player has not enough money to get out of jail
    }


    public HashMap<String, Boolean> checkLevelStatus(int squareIndex){
        Player player = players.get(currentPlayerId);
        Square square = board.getSpecificSquare(squareIndex);
        PropertyCard card = getSpecificProperty(squareIndex);

        HashMap<String, Boolean> returningHash = new HashMap<>();

        if(currentPlayerId == card.getOwnedBy()){
            if(checkBuildBuilding(Building.House, square).containsKey(true) || checkBuildBuilding(Building.Hotel, square).containsKey(true) || checkDismortgage(player, square)){
                returningHash.put("levelUp", true);
            } else{
                returningHash.put("levelUp", false);
            }

            if(checkDestructBuilding(Building.House, square).containsKey(true) || checkDestructBuilding(Building.Hotel, square).containsKey(true) || checkMortgage(player, square)){
                returningHash.put("levelDown", true);
            } else{
                returningHash.put("levelDown", false);
            }
        }
        else if(currentPlayerId != card.getOwnedBy() && card.getOwnedBy() != -1){
            returningHash.put("levelUp", false);
            returningHash.put("levelDown", false);
        }
        else {
            if (checkBuyProperty(squareIndex)){
                returningHash.put("levelUp", true);
            }
            else {
                returningHash.put("levelUp", false);
            }
            returningHash.put("levelDown", false);
        }
        return returningHash;
    }

    public boolean checkBrokenStatus(){
        for(Player p : players){
            if(p.isBankrupt()){
                this.state = GameState.Broken;
                return true;
            }
        }
        return false;
    }

    public boolean checkAuctionStatus(){
        return participants.size() == 1;
    }

    public boolean checkMortgage(Player currentPlayer, Square squareToMortgage){

        int squareId = squareToMortgage.getId();
        int squareLevel = squareToMortgage.getLevel();
        PropertyCard currentPlace = currentPlayer.getSpecificCard(squareId);

        if ( currentPlayer.isOwned(currentPlace ) && squareLevel == 0 ) {
            //System.out.println("Player can mortgage this place");
            return true;
        }
        //System.out.println("Player cannot mortgage this place");
        return false;
    }

    public boolean checkDismortgage( Player currentPlayer, Square squareToDismortgage){
        int squareId = squareToDismortgage.getId();
        int squareLevel = squareToDismortgage.getLevel();
        PropertyCard currentPlace = currentPlayer.getSpecificCard(squareId);
        int dismortgageMoney = (int) (currentPlace.getMortgagePrice() * 1.10);

        if (currentPlayer.isOwned(currentPlace)) {
            if (squareLevel == -1){
                if (currentPlayer.getMonopolyMoneyAmount() >= dismortgageMoney){
                    //System.out.println("Player can dismortgage");
                    return true;
                }
                else {
                    //System.out.println("player does not have enough money to dismortgage");
                }
            }
            else {
                //System.out.println("This square is not mortgaged");
            }
        }
        else{
            //System.out.println("Player does not have this place");
        }
        return false;
    }
    public boolean checkBuyProperty(int index){
        Player currentPlayer = players.get(currentPlayerId);
        Square squareToBuy = board.getSpecificSquare(index);
        PropertyCard toGetCostOfPropertyCard = getSpecificProperty(squareToBuy.getId());
        boolean isBuyable = (squareToBuy.getType() == SquareType.NormalSquare) || (squareToBuy.getType() == SquareType.UtilitySquare) || (squareToBuy.getType() == SquareType.RailroadSquare);
        if (isBuyable && currentPlayer.getCurrentPosition() == squareToBuy.getId()){
            if (!squareToBuy.isBought()){
                if (currentPlayer.getMonopolyMoneyAmount() >= toGetCostOfPropertyCard.getCost()){
                    return true;
                }
                else {
                    //System.out.println("Player does not have enough money");
                    return false;
                }
            }
            else {
                //System.out.println("square is already bought");
                return false;
            }
        }
        else {
            //System.out.println("Player is not on this square");
            return false;
        }
    }

    public Map<Boolean, Integer> checkBuildBuilding(Building buildingType, Square squareToBuild){
        // Also, maybe the algorithm can change, according to the rulebook
        Map<Boolean, Integer> checkAndCountHouses = new HashMap<>();
        Map<Boolean, Integer> checkAndCountHotel = new HashMap<>();
        if(squareToBuild.getType() == SquareType.NormalSquare){// TODO: Check house&hotel count of bank


            //IF NO BUILDINGS AVAILABLE
            if(!bank.checkBuildingAvailability(buildingType)){
                if(buildingType == Building.House){
                    checkAndCountHouses.put(false, 0);
                    return checkAndCountHouses;
                }else if(buildingType == Building.Hotel){
                    checkAndCountHotel.put(false, 0);
                    return checkAndCountHotel;
                }
            }

            Player currentPlayer = players.get(currentPlayerId);

            int squareToBuildIndex = squareToBuild.getId();

            int colorsCountOnBoard = board.countColors(squareToBuild);
            int colorsCountOnPlayer = countPlayersColor(currentPlayer, squareToBuild);
            int houseCountOnSquare = squareToBuild.getHouseCount();
            int hotelCountOnSquare = squareToBuild.getHotelCount();
            int currentMoney = currentPlayer.getMonopolyMoneyAmount();
            int squareLevel = squareToBuild.getLevel();

            PlaceCard currentPlace = (PlaceCard) currentPlayer.getSpecificCard(squareToBuildIndex);

            if ( colorsCountOnBoard == colorsCountOnPlayer ){   // Checks player has all squares with same the color (is there a mortgage or not check it!!!)
                if ( buildingType == Building.House && squareLevel < 4 && squareLevel > -1 ){

                    int priceOfAHouse = currentPlace.getHousePrice();

                    if (squareToBuild.isHouseCheck()){  // Checks the square has a house once or not

                        if (board.hasHouseAllSquares(squareToBuild)){   // Checks other squares have houses or not
                            int availableHouses = 4 - houseCountOnSquare;
                            int count = 0;
                            for ( int i = 1; i <= availableHouses; i++ ) {    // Calculates how many houses can be bought with player's money
                                if (currentMoney >= i * priceOfAHouse){
                                    count++;
                                }
                            }
                            if (count > 0) { // checks if count is positive, count houses can be bought
                                checkAndCountHouses.put(true, count);
                                //System.out.println("Player can buy " + count + " houses on this square");
                            }
                            else {  // player cannot buy a house
                                checkAndCountHouses.put(false, 0);
                                //System.out.println("Max number of houses or not enough money");
                            }
                        }
                        else if ( !board.hasHouseAllSquares(squareToBuild) && houseCountOnSquare > 0 ){ // if there is one house and other squares don't have a house
                            checkAndCountHouses.put(false, 0);
                            //System.out.println("Player has already a house in this square");
                        }
                        else if ( !board.hasHouseAllSquares(squareToBuild) && houseCountOnSquare == 0 ){
                            if ( currentMoney > priceOfAHouse && currentPlayer.getCurrentPosition() == squareToBuildIndex ){    // checks player's money is enough for a house
                                checkAndCountHouses.put(true, 1);
                                //System.out.println("Player can buy a house");
                            }
                            else {
                                checkAndCountHouses.put(false, 0);
                                //System.out.println("Not sufficient money or player is not on that square");
                            }
                        }
                    }
                    else {  // if the square has no house
                        if ( currentMoney > priceOfAHouse && currentPlayer.getCurrentPosition() == squareToBuildIndex ){    // checks player's money is enough for a house
                            checkAndCountHouses.put(true, 1);
                            //System.out.println("Player can buy a house");
                        }
                        else {
                            checkAndCountHouses.put(false, 0);
                            //System.out.println("Not sufficient money or player is not on that square");
                        }
                    }
                    return checkAndCountHouses;
                }
                if ( buildingType == Building.Hotel && squareLevel == 4){
                    if ( (houseCountOnSquare == 4 && hotelCountOnSquare == 0)) { // checks square has 4 houses
                        int priceOfAHotel = currentPlace.getHotelPrice();
                        if ( currentMoney > priceOfAHotel ){ // checks money is enough or not
                            checkAndCountHotel.put(true, 1);
                            //System.out.println("Player can buy an hotel");

                        }
                        else {  //money is not enough
                            checkAndCountHotel.put(false, 0);
                            //System.out.println("Not sufficient money");
                        }
                    }
                    else {  // houses are not enough or there is an hotel
                        checkAndCountHotel.put(false, 0);
                        //System.out.println("not enough houses or there is an hotel");
                    }
                    return checkAndCountHotel;
                }
                else{

                    checkAndCountHouses.put(false, 0);
                    //System.out.println("place is mortgaged");
                }
            }
            else{   // player does not have all squares with that color
                checkAndCountHouses.put(false, 0);
                //System.out.println("Player haven't got all squares with that color");
            }

        }
        else{
            checkAndCountHouses.put(false, 0);
        }
        return checkAndCountHouses;
    }
    public Map<Boolean, Integer> checkDestructBuilding(Building buildingType, Square squareToDestruct){
        // Also, maybe the algorithm can change, according to the rulebook

        Map<Boolean, Integer> checkAndCountHousesDestruct = new HashMap<>();
        Map<Boolean, Integer> checkAndCountHotelDestruct = new HashMap<>();
        if (squareToDestruct.getType() == SquareType.NormalSquare){
            Player currentPlayer = players.get(currentPlayerId);

            int houseCountOnSquare = squareToDestruct.getHouseCount();
            int hotelCountOnSquare = squareToDestruct.getHotelCount();

            int squareToDestructId = squareToDestruct.getId();
            PlaceCard currentPlace = (PlaceCard) currentPlayer.getSpecificCard(squareToDestructId);

            if ( currentPlace != null ){
                if ( buildingType == Building.Hotel) {
                    if ( hotelCountOnSquare == 1 ) {
                        checkAndCountHotelDestruct.put(true, 1);
                        //System.out.println("Player can destruct an hotel");
                    }
                    else{
                        checkAndCountHotelDestruct.put(false, 0);
                        //System.out.println("there is no hotel to destruct");
                    }
                    return checkAndCountHotelDestruct;

                }
                if ( buildingType == Building.House ) {
                    if (houseCountOnSquare > 0) {
                        checkAndCountHousesDestruct.put(true, hotelCountOnSquare);
                        //System.out.println("Player can destruct " + hotelCountOnSquare + " houses ");
                    }
                    else {
                        checkAndCountHousesDestruct.put(false, 0);
                        //System.out.println("There is no house to destruct");
                    }
                    return checkAndCountHousesDestruct;
                }
            }
            else {
                checkAndCountHousesDestruct.put(false, 0);
                //System.out.println("Player does not have this square");
                return checkAndCountHousesDestruct;
            }
        }

        checkAndCountHousesDestruct.put(false, 0);
        return checkAndCountHousesDestruct;

    }


    //Getters and Setters

    public ArrayList<String> getChat() {
        return chat;
    }

    public void setChat(ArrayList<String> chat) {
        this.chat = chat;
    }

    public ArrayList<String> getLog() {
        return log;
    }

    public void setLog(ArrayList<String> log) {
        this.log = log;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(int currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public int getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(int currentBid) {
        this.currentBid = currentBid;
    }

    public int getAuctionPropertyIndex() {
        return auctionPropertyIndex;
    }

    public void setAuctionPropertyIndex(int auctionPropertyIndex) {
        this.auctionPropertyIndex = auctionPropertyIndex;
    }

    public int getCurrentPlayerAuctioning() {
        return currentPlayerAuctioning;
    }

    public void setCurrentPlayerAuctioning(int currentPlayerAuctioning) {
        this.currentPlayerAuctioning = currentPlayerAuctioning;
    }

    public ArrayList<Player> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Player> participants) {
        this.participants = participants;
    }

    public HashMap<Integer, HashMap<Integer, Integer>> getBrokenPlayersMoneyHash() {
        return brokenPlayersMoneyHash;
    }

    public void setBrokenPlayersMoneyHash(HashMap<Integer, HashMap<Integer, Integer>> brokenPlayersMoneyHash) {
        this.brokenPlayersMoneyHash = brokenPlayersMoneyHash;
    }
}