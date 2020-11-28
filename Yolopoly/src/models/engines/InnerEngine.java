package models.engines;

import enumerations.Building;
import models.*;

import java.io.File;
import java.util.ArrayList;


public class InnerEngine {
    //Oyun işleri
    //Variables
    private ArrayList<String> chat;
    private ArrayList<String> log;
    private ArrayList<Player> players;
    private Dice dice;
    private Board board;
    private Bank bank;
    private int currentPlayer;
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

    public void buildBuilding(Building buildingType, int buildingCount){
        //ArrayList<Square> toBuild = board.getSquares();
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

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) { this.currentPlayer = currentPlayer; }
}
