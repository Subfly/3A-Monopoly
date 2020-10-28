package models.cards;

import enumerations.DrawableCard;
import interfaces.Holdable;

public class ChanceCard implements Holdable {
    //Variables
    private DrawableCard type;
    private String message;
    private int moneyOthers;
    private int moneyBank;
    private boolean getOutTicket;
    private boolean isPositionChanger;
    private int positionChange;
    private int moneyForHouses;
    private int moneyForHotel;
    private boolean pickACard;

    //Variables for bankman
    private double multiplier;

    public ChanceCard(DrawableCard type, String message, int moneyOthers, int moneyBank, boolean getOutTicket, boolean isPositionChanger, int positionChange, int moneyForHouses, int moneyForHotel, boolean pickACard, double multiplier) {
        this.type = type;
        this.message = message;
        this.moneyOthers = moneyOthers;
        this.moneyBank = moneyBank;
        this.getOutTicket = getOutTicket;
        this.isPositionChanger = isPositionChanger;
        this.positionChange = positionChange;
        this.moneyForHouses = moneyForHouses;
        this.moneyForHotel = moneyForHotel;
        this.pickACard = pickACard;
        this.multiplier = multiplier;
    }

    public DrawableCard getType() {
        return type;
    }

    public void setType(DrawableCard type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMoneyOthers() {
        return moneyOthers;
    }

    public void setMoneyOthers(int moneyOthers) {
        this.moneyOthers = moneyOthers;
    }

    public int getMoneyBank() {
        return moneyBank;
    }

    public void setMoneyBank(int moneyBank) {
        this.moneyBank = moneyBank;
    }

    public boolean isGetOutTicket() {
        return getOutTicket;
    }

    public void setGetOutTicket(boolean getOutTicket) {
        this.getOutTicket = getOutTicket;
    }

    public boolean isPositionChanger() {
        return isPositionChanger;
    }

    public void setPositionChanger(boolean positionChanger) {
        isPositionChanger = positionChanger;
    }

    public int getPositionChange() {
        return positionChange;
    }

    public void setPositionChange(int positionChange) {
        this.positionChange = positionChange;
    }

    public int getMoneyForHouses() {
        return moneyForHouses;
    }

    public void setMoneyForHouses(int moneyForHouses) {
        this.moneyForHouses = moneyForHouses;
    }

    public int getMoneyForHotel() {
        return moneyForHotel;
    }

    public void setMoneyForHotel(int moneyForHotel) {
        this.moneyForHotel = moneyForHotel;
    }

    public boolean isPickACard() {
        return pickACard;
    }

    public void setPickACard(boolean pickACard) {
        this.pickACard = pickACard;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }
}
