package models;

import java.util.ArrayList;

public class Engine {
    //Variables
    private ArrayList<String> chat;
    private ArrayList<String> log;
    private ArrayList<Player> players;
    private Dice dice;
    private Board board;
    private Bank bank;
    private int currentPlayer;

    //Constructor
    public Engine(boolean isSavedGamePlaying) {
        startGame(isSavedGamePlaying);
    }

    //Functions
    public void startGame(boolean isSavedGamePlaying){}
    public void playTurn(){}
    public boolean isGameOver(){return false;}
    public void drawCard(){}
    public void buyProperty(){}
    public void sellProperty(){}
    public void createAuction(){}
    public void buildBuilding(Building buildingType, int buildingCount){}
    public void destructBuilding(Building buildingType, int buildingCount){}

    //Getters and Setters
}
