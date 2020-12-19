package com.yolopoly.models.cards;

public abstract class DrawableCard {
    //Variables
    //Chance Card Related
    private String message;
    private boolean isComposed; //Boolean to check if the card contains more than one situation
    private boolean isMoving; //Boolean to control move
    private int moveToIndex; //int variable to move player to a Square
    private int moveInCounts; //int variable to move player to amount
    private boolean isGettingMoney; //Boolean to control money gain or lose
    private int moneyGet; //int variable that holds money that the player gains
    private int moneyOwe; //int variable that holds money that the player owns
    private boolean isRelatedToBuildings; //Boolean to check if the card is related to buildings
    private int moneyForHouses;
    private int moneyForHotels;
    private boolean isGOOJC; //Boolean to hold the value if the card is a "Get Out Of Jail Card"
    private boolean isGTJC; //Boolean to hold the value if the card is a "Go To Jail Card"

    //Community Chest Card Related
    private boolean isEachPlayerIncluded; //Boolean to check if the operation includes other players
    private boolean isDrawingChanceCard; //Boolean value to check if the operation is drawing a chance card

    //Variables for bankman
    private double multiplier;

    public DrawableCard(String message, boolean isComposed, boolean isMoving, int moveToIndex, int moveInCounts, boolean isGettingMoney, int moneyGet, int moneyOwe, boolean isRelatedToBuildings, int moneyForHouses, int moneyForHotels, boolean isGOOJC, boolean isGTJC, boolean isEachPlayerIncluded, boolean isDrawingChanceCard) {
        this.message = message;
        this.isComposed = isComposed;
        this.isMoving = isMoving;
        this.moveToIndex = moveToIndex;
        this.moveInCounts = moveInCounts;
        this.isGettingMoney = isGettingMoney;
        this.moneyGet = moneyGet;
        this.moneyOwe = moneyOwe;
        this.isRelatedToBuildings = isRelatedToBuildings;
        this.moneyForHouses = moneyForHouses;
        this.moneyForHotels = moneyForHotels;
        this.isGOOJC = isGOOJC;
        this.isGTJC = isGTJC;
        this.isEachPlayerIncluded = isEachPlayerIncluded;
        this.isDrawingChanceCard = isDrawingChanceCard;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isComposed() {
        return isComposed;
    }

    public void setComposed(boolean composed) {
        isComposed = composed;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public int getMoveToIndex() {
        return moveToIndex;
    }

    public void setMoveToIndex(int moveToIndex) {
        this.moveToIndex = moveToIndex;
    }

    public int getMoveInCounts() {
        return moveInCounts;
    }

    public void setMoveInCounts(int moveInCounts) {
        this.moveInCounts = moveInCounts;
    }

    public boolean isGettingMoney() {
        return isGettingMoney;
    }

    public void setGettingMoney(boolean gettingMoney) {
        isGettingMoney = gettingMoney;
    }

    public int getMoneyGet() {
        return moneyGet;
    }

    public void setMoneyGet(int moneyGet) {
        this.moneyGet = moneyGet;
    }

    public int getMoneyOwe() {
        return moneyOwe;
    }

    public void setMoneyOwe(int moneyOwe) {
        this.moneyOwe = moneyOwe;
    }

    public boolean isRelatedToBuildings() {
        return isRelatedToBuildings;
    }

    public void setRelatedToBuildings(boolean relatedToBuildings) {
        isRelatedToBuildings = relatedToBuildings;
    }

    public int getMoneyForHouses() {
        return moneyForHouses;
    }

    public void setMoneyForHouses(int moneyForHouses) {
        this.moneyForHouses = moneyForHouses;
    }

    public int getMoneyForHotels() {
        return moneyForHotels;
    }

    public void setMoneyForHotels(int moneyForHotels) {
        this.moneyForHotels = moneyForHotels;
    }

    public boolean isGOOJC() {
        return isGOOJC;
    }

    public void setGOOJC(boolean GOOJC) {
        isGOOJC = GOOJC;
    }

    public boolean isGTJC() {
        return isGTJC;
    }

    public void setGTJC(boolean GTJC) {
        isGTJC = GTJC;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public boolean isEachPlayerIncluded() {
        return isEachPlayerIncluded;
    }

    public void setEachPlayerIncluded(boolean eachPlayerIncluded) {
        isEachPlayerIncluded = eachPlayerIncluded;
    }

    public boolean isDrawingChanceCard() {
        return isDrawingChanceCard;
    }

    public void setDrawingChanceCard(boolean drawingChanceCard) {
        isDrawingChanceCard = drawingChanceCard;
    }
}
