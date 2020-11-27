package models;

import enumerations.GameMode;
import enumerations.GameTheme;
import models.cards.PropertyCard;

import java.util.ArrayList;

public class Bank {
    //Variables
    private ArrayList<PropertyCard> propertyCards;

    //Variables for bankman
    private ArrayList<Integer> playerLoans;
    private ArrayList<Double> currencyRates;

    //Constructor
    public Bank(GameTheme gameTheme, GameMode gameMode) {
    }

    //Functions
    public void changeCurrencies(){
        int currencyCount = currencyRates.size();
        // for each currency, generate a random number between -1 and 1
        // and change the currency rate accordingly
        for (int i = 0; i < currencyCount; i++) {
            double currentRate = currencyRates.get(i);
            double change = ((Math.random() * (1 - (-1))) + (-1));
            currencyRates.set(i, (change * currentRate));
        }
    }

    public boolean sellProperty(PropertyCard card){
        if (propertyCards.contains(card)) {
            propertyCards.remove(card);
            return true;
        }
        return false;
    }

    public boolean returnProperty(PropertyCard card){
        if (!propertyCards.contains(card)) {
            propertyCards.add(card);
        }
        return false;
    }

    public boolean giveLoan(int amount, Player player){
        player.setLoan(amount);
        player.setLoanTurn(5);
        return true;
    }

    public boolean getLoan(Player player){
        player.getLoan();
        return true;
    }

    //Getters and Setters
    public ArrayList<PropertyCard> getPropertyCards() {
        return propertyCards;
    }

    public void setPropertyCards(ArrayList<PropertyCard> propertyCards) {
        this.propertyCards = propertyCards;
    }

    public ArrayList<Double> getCurrencyRates() {
        return currencyRates;
    }

    public void setCurrencyRates(ArrayList<Double> currencyRates) {
        this.currencyRates = currencyRates;
    }

    public ArrayList<ArrayList<Integer>> getPlayerLoans() {
        return null;
    }

    public void setPlayerLoans(ArrayList<ArrayList<Integer>> playerLoans) {
    }
}
