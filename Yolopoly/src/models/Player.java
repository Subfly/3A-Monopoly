package models;

import enumerations.Pawn;
import interfaces.Holdable;
import models.cards.PropertyCard;
import java.util.ArrayList;

public class Player {
    //Variables
    private String name;
    private Pawn pawn;
    private int money;
    private int currentPosition;
    private int railroadsOwned;
    private int utilitiesOwned;
    private boolean isHuman;
    private boolean isInJail;
    private boolean isThreeTimesDoubled;
    private boolean isBankrupt;
    private ArrayList<PropertyCard> ownedPlaces;
    private ArrayList<Holdable> savedCards;

    //Variables for bankman
    private int loan;
    private ArrayList<Integer> moneyOnBank;

    //Constructor
    public Player(String name, boolean isHuman) {
        this.name = name;
        this.isInJail = false;
        this.isThreeTimesDoubled = false;
        this.money = 0;
        this.ownedPlaces = new ArrayList<>();
        this.railroadsOwned = 0;
        this.utilitiesOwned = 0;
        this.isBankrupt = false;
        this.isHuman = isHuman;
    }

    //Functions
    public boolean addMoney(int amount){
        return false;
    }

    public boolean removeMoney(int amount){
        return false;
    }

    public boolean isOwned(PropertyCard card){
        return false;
    }

    public boolean ownProperty(int squareIndex){
        return false;
    }

    public boolean sellProperty(int squareIndex){
        return false;
    }

    //Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getRailroadsOwned() {
        return railroadsOwned;
    }

    public void setRailroadsOwned(int railroadsOwned) {
        this.railroadsOwned = railroadsOwned;
    }

    public int getUtilitiesOwned() {
        return utilitiesOwned;
    }

    public void setUtilitiesOwned(int utilitiesOwned) {
        this.utilitiesOwned = utilitiesOwned;
    }

    public boolean isHuman() {
        return isHuman;
    }

    public void setHuman(boolean human) {
        isHuman = human;
    }

    public boolean isInJail() {
        return isInJail;
    }

    public void setInJail(boolean inJail) {
        isInJail = inJail;
    }

    public boolean isThreeTimesDoubled() {
        return isThreeTimesDoubled;
    }

    public void setThreeTimesDoubled(boolean threeTimesDoubled) {
        isThreeTimesDoubled = threeTimesDoubled;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public void setBankrupt(boolean bankrupt) {
        isBankrupt = bankrupt;
    }
}
