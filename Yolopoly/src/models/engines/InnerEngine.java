package models.engines;

import enumerations.Building;
import models.*;
import models.cards.PlaceCard;
import models.cards.PropertyCard;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class InnerEngine {
    //Oyun işleri
    //Variables
    private ArrayList<String> chat;
    private ArrayList<String> log;
    private ArrayList<Player> players;
    private ArrayList<PropertyCard> propertyCards;
    private Dice dice;
    private Board board;
    private Bank bank;
    private int currentPlayerId;
    private int squareIndex;

    //Constructor
    public InnerEngine(boolean isSavedGamePlaying) {
        startGame(isSavedGamePlaying);

    }

    //Functions
    public void startGame(boolean isSavedGamePlaying){
    }

    public void playTurn(){

    }
    public boolean isGameOver(){return false;}
    public void drawCard(){}


    public PropertyCard getSpecificProperty( int squareIndex ){
        int propertyIndex = -1;
        int count = 0;
        for ( PropertyCard s : propertyCards ) {

            if ( s.getId() == squareIndex ){
                propertyIndex = count;
                return propertyCards.get(propertyIndex);
            }
            count++;
        }
        return null;

    }
    public boolean checkBuyProperty(Square squareToBuy, Player currentPlayer){
        int squareToBuyId = squareToBuy.getId();
        PropertyCard toGetCostOfPropertyCard = getSpecificProperty(squareToBuyId);
        if (currentPlayer.getCurrentPosition() == squareToBuyId){
            if ( squareToBuy.isBought() == false ){
                if ( currentPlayer.getMoney() >= toGetCostOfPropertyCard.getCost() && toGetCostOfPropertyCard != null){

                    System.out.println("Player can buy this property ");
                    return true;
                }
                else {
                    System.out.println("Player does not have enough money");
                    return false;
                }
            }
            else {
                System.out.println("square is already bought");
                return false;
            }
        }
        else {
            System.out.println("Player is not on this square");
            return false;
        }


    }
    public boolean checkSellProperty(){ return true;}
    public void buyProperty( Player currentPlayer, Square square){
        int squareId = square.getId();

        currentPlayer.ownProperty(propertyCards.get(squareId));
        square.setBought(true);
        propertyCards.get(squareId).setOwnedBy(currentPlayerId);

    }
    public void sellProperty(){

    }
    public void createAuction(){}


    public void addToChat(String data, String userName){
        chat.add(userName + ":\n" + data);
    }

    public void addToLog(String logAction, String userName){
        log.add("Player " + userName + " has " + logAction);
    }

    public Map<Boolean, Integer> checkBuildBuilding(Building buildingType, Square squareToBuild){

        Map<Boolean, Integer> checkAndCountHouses = new HashMap<>();
        Map<Boolean, Integer> checkAndCountHotel = new HashMap<>();

        Player currentPlayer = players.get(currentPlayerId);

        int squareToBuildIndex = squareToBuild.getId();

        int colorsCountOnBoard = board.countColors(squareToBuild);
        int colorsCountOnPlayer = currentPlayer.countPlayersColor(squareToBuild);
        int houseCountOnSquare = squareToBuild.getHouseCount();
        int hotelCountOnSquare = squareToBuild.getHotelCount();
        int currentMoney = currentPlayer.getMoney();

        PlaceCard currentPlace = (PlaceCard) currentPlayer.getSpecificCard(squareToBuildIndex);

        if ( colorsCountOnBoard == colorsCountOnPlayer ){   // Checks player has all squares with same the color (is there a mortgage or not check it!!!)
            if ( buildingType == Building.House ){

                int priceOfAHouse = currentPlace.getHousePrice();

                if ( squareToBuild.isHouseCheck() == true ){  // Checks the square has a house or not


                    if (board.hasHouseAllSquares(squareToBuild) == true){   // Checks other squares have houses or not
                        int  availableHouses = 4 - houseCountOnSquare;
                        int count = 0;
                        for (int i = 1; i <= availableHouses; i++) {    // Calculates how many houses can be bought with player's money
                            if (currentMoney >= i * priceOfAHouse){
                                count++;
                            }
                        }
                        if (count > 0) { // checks if count is positive, count houses can be bought
                            checkAndCountHouses.put(true, count);
                            System.out.println("Player can buy " + count + " houses on this square");
                        }
                        else {  // player cannot buy a house
                            checkAndCountHouses.put(false, 0);
                            System.out.println("Max number of houses or not enough money");
                        }
                    }
                    else if ( board.hasHouseAllSquares(squareToBuild) == false && houseCountOnSquare > 0 ){ // if there is one house and other squares don't have a house
                        checkAndCountHouses.put(false, 0);
                        System.out.println("Player has already a house in this square");
                    }
                    else if ( board.hasHouseAllSquares(squareToBuild) == false && houseCountOnSquare == 0 ){
                        if ( currentMoney > priceOfAHouse && currentPlayer.getCurrentPosition() == squareToBuildIndex ){    // checks player's money is enough for a house
                            checkAndCountHouses.put(true, 1);
                            System.out.println("Player can buy a house");

                        }
                        else {
                            checkAndCountHouses.put(false, 0);
                            System.out.println("Not sufficient money or player is not on that square");
                        }


                    }
                }
                else {  // if the square has no house
                    if ( currentMoney > priceOfAHouse && currentPlayer.getCurrentPosition() == squareToBuildIndex ){    // checks player's money is enough for a house
                        checkAndCountHouses.put(true, 1);
                        System.out.println("Player can buy a house");

                    }
                    else {
                        checkAndCountHouses.put(false, 0);
                        System.out.println("Not sufficient money or player is not on that square");
                    }
                }
                return checkAndCountHouses;

            }
            if ( buildingType == Building.Hotel ){
                if ( houseCountOnSquare == 4 && hotelCountOnSquare == 0) { // checks square has 4 houses

                    int priceOfAHotel = currentPlace.getHotelPrice();
                    if ( currentMoney > priceOfAHotel ){ // checks money is enough or not
                        checkAndCountHotel.put(true, 1);
                        System.out.println("Player can buy an hotel");

                    }
                    else {  //money is not enough
                        checkAndCountHotel.put(false, 0);
                        System.out.println("Not sufficient money");
                    }
                }
                else {  // houses are not enough or there is an hotel
                    checkAndCountHotel.put(false, 0);
                    System.out.println("not enough houses or there is an hotel");
                }
                return checkAndCountHotel;
            }
        }
        else{   // player does not have all squares with that color
            checkAndCountHouses.put(false, 0);
            System.out.println("Player haven't got all squares with that color");

        }
        return checkAndCountHouses;
    }
    public Map<Boolean, Integer> checkDestructBuilding(Building buildingType, Square squareToDestruct){

        Map<Boolean, Integer> checkAndCountHousesDestruct = new HashMap<>();
        Map<Boolean, Integer> checkAndCountHotelDestruct = new HashMap<>();

        Player currentPlayer = players.get(currentPlayerId);


        int houseCountOnSquare = squareToDestruct.getHouseCount();
        int hotelCountOnSquare = squareToDestruct.getHotelCount();

        int squareToDestructId = squareToDestruct.getId();
        PlaceCard currentPlace = (PlaceCard) currentPlayer.getSpecificCard(squareToDestructId);

        if ( currentPlace != null ){
            if ( buildingType == Building.Hotel) {
                if ( hotelCountOnSquare == 1 ) {
                    checkAndCountHotelDestruct.put(true, 1);
                    System.out.println("Player can destruct an hotel");
                }
                else{
                    checkAndCountHotelDestruct.put(false, 0);
                    System.out.println("there is no hotel to destruct");
                }
                return checkAndCountHotelDestruct;

            }
            if ( buildingType == Building.House ) {
                if (houseCountOnSquare > 0) {
                    checkAndCountHousesDestruct.put(true, hotelCountOnSquare);
                    System.out.println("Player can destruct " + hotelCountOnSquare + " houses ");
                }
                else {
                    checkAndCountHousesDestruct.put(false, 0);
                    System.out.println("There is no house to destruct");
                }
                return checkAndCountHousesDestruct;
            }
        }
        else {
            checkAndCountHousesDestruct.put(false, 0);
            System.out.println("Player does not have this square");
            return checkAndCountHousesDestruct;
        }
        checkAndCountHousesDestruct.put(false, 0);
        return checkAndCountHousesDestruct;

    }
    public void buildBuilding(Building buildingType, int count, Square squareToBuild, Player currentPlayer) {

        int squareToBuildIndex = squareToBuild.getId();
        PlaceCard currentPlace = (PlaceCard) currentPlayer.getSpecificCard(squareToBuildIndex);

        if ( buildingType == Building.House ){
            int money = currentPlace.getHousePrice();

            for ( int i = 0; i < count; i++){
                squareToBuild.build(buildingType);
                currentPlayer.removeMoney(money);
            }
        }
        else{
            int money = currentPlace.getHotelPrice();

            for ( int i = 0; i < count; i++){
                squareToBuild.build(buildingType);
                currentPlayer.removeMoney(money);
            }
        }

    }


    public void destructBuilding(Building buildingType, int buildingCount, Square squareToDestruct, Player currentPlayer ){

        int squareToDestructId = squareToDestruct.getId();
        PlaceCard currentPlace = (PlaceCard) currentPlayer.getSpecificCard(squareToDestructId);

        if ( buildingType == Building.House ){
            int money = currentPlace.getHousePrice() / 2;

            for ( int i = 0; i < buildingCount; i++){
                squareToDestruct.build(buildingType);
                currentPlayer.addMoney(money);
            }
        }
        else{
            int money = currentPlace.getHotelPrice();

            for ( int i = 0; i < buildingCount; i++){
                squareToDestruct.build(buildingType);
                currentPlayer.addMoney(money);
            }
        }

    }

    public File getSettings(){return null;}
    public void setSettings(){}
    public boolean changePlayerToBot(int index){
        players.get(index).setHuman(false);
        return true;
    }

    //Getters and Setters


    public ArrayList<PropertyCard> getPropertyCards() {
        return propertyCards;
    }

    public void setPropertyCards(ArrayList<PropertyCard> propertyCards) {
        this.propertyCards = propertyCards;
    }

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

    public void setCurrentPlayerId(int currentPlayerId) { this.currentPlayerId = currentPlayerId; }
}
