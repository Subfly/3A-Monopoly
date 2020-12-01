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
    private ArrayList<Currency> currencyRates;

    //Constructor
    public Bank(GameTheme gameTheme, GameMode gameMode) {
    }

    //Functions
    public void changeCurrencies(){
        int currencyCount = currencyRates.size();
        // for each currency, generate a random number between -1 and 1
        // and change the currency rate accordingly
        for (int i = 0; i < currencyCount; i++) {
            double currentRate = currencyRates.get(i).getRate();
            double change = ((Math.random() * (1 - (-1))) + (-1));
            currencyRates.get(i).setRate(change * currentRate);
        }
    }

    public boolean sellProperty(PropertyCard card){
        if (propertyCards.contains(card)) {
            propertyCards.remove(card);
            return true;
        }
        return false;
    }

    public final PropertyCard lookUpProperty(int id){
        for(PropertyCard p: propertyCards){
            if(p.getId() == id){
                return p;
            }
        }
        return null;
    }

    public final int getBuyer(int squareId){
        for(PropertyCard p: propertyCards){
            if(squareId == p.getId()){
                return p.getOwnedBy();
            }
        }
        return -1;
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

    public ArrayList<Currency> getCurrencyRates() {
        return currencyRates;
    }

    public void setCurrencyRates(ArrayList<Currency> currencyRates) {
        this.currencyRates = currencyRates;
    }

    public ArrayList<ArrayList<Integer>> getPlayerLoans() {
        return null;
    }

    public void setPlayerLoans(ArrayList<ArrayList<Integer>> playerLoans) {
    }
}
