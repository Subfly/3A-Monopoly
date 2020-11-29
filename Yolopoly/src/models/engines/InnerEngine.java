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
    public void buyProperty(){}
    public void sellProperty(){}
    public void createAuction(){}


    public void addToChat(String data, String userName){
        chat.add(userName + ":\n" + data);
    }

    public void addToLog(String logAction, String userName){
        log.add("Player " + userName + " has " + logAction);
    }
    public Map<Boolean, Integer> checkBuildBuilding(Building buildingType){

        Map<Boolean, Integer> checkAndCountHouses = new HashMap<>();
        Map<Boolean, Integer> checkAndCountHotel = new HashMap<>();

        Player currentPlayer = players.get(currentPlayerId);
        Square currentSquare = board.getSpecificSquare(squareIndex);

        int colorsCountOnBoard = board.countColors(currentSquare);
        int colorsCountOnPlayer = currentPlayer.countPlayersColor(currentSquare);
        int houseCountOnSquare = currentSquare.getHouseCount();
        int currentMoney = currentPlayer.getMoney();
        PlaceCard currentPlace = (PlaceCard) currentPlayer.getSpecificCard(squareIndex);

        if ( colorsCountOnBoard == colorsCountOnPlayer ){   // Checks player has all squares with same the color (is there a mortgage or not check it!!!)
            if ( buildingType == Building.House ){

                int priceOfAHouse = currentPlace.getHousePrice();
                if ( houseCountOnSquare > 0 ){  // Checks the square has a house or not

                    int houseCountControl = board.countHousesOnSquaresOfaColor(currentSquare);
                    if (houseCountControl == colorsCountOnBoard){   // Checks other squares have houses or not
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
                    else { // if there is one house and other squares don't have a house
                        checkAndCountHouses.put(false, 0);
                        System.out.println("Player has already a house in this square");
                    }
                }
                else {  // if the square has no house
                    if ( currentMoney > priceOfAHouse ){    // checks player's money is enough for a house
                        checkAndCountHouses.put(true, 1);
                        System.out.println("Player can buy a house");

                    }
                    else {
                        checkAndCountHouses.put(false, 0);
                        System.out.println("Not sufficient money");
                    }
                }
                return checkAndCountHouses;

            }
            if ( buildingType == Building.Hotel ){
                if ( houseCountOnSquare == 4 ) { // checks square has 4 houses

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
                else {  // houses are not enough
                    checkAndCountHotel.put(false, 0);
                    System.out.println("not enough houses");
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
    public boolean checkDestructBuilding(){
        return true;
    }
    public void buildBuilding(Building buildingType, int count) {
        Map<Boolean, Integer> check = checkBuildBuilding(buildingType);
        Map.Entry<Boolean, Integer> entry = check.entrySet().iterator().next();
        Boolean key = entry.getKey();
        int value = entry.getValue();
        if ( key == true ) {
            for ( int i = 0; i < value; i++ ) {
                board.getSquares().get(squareIndex).build(buildingType);
            }
        }
        else {
            System.out.println("Player cannot build anything");
        }
    }


    public void destructBuilding(Building buildingType, int buildingCount){

    }

    public File getSettings(){return null;}
    public void setSettings(){}
    public boolean changePlayerToBot(int index){
        players.get(index).setHuman(false);
        return true;
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

    public void setCurrentPlayerId(int currentPlayerId) { this.currentPlayerId = currentPlayerId; }
}
