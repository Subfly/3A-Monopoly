package models;

import models.cards.PropertyCard;

import java.util.ArrayList;

public class Bank {
    //Variables
    private ArrayList<PropertyCard> propertyCards;

    //Variables for bankman
    private ArrayList<ArrayList<Integer>> playerMoneys;
    private ArrayList<Double> currencyRates;

    //Constructor
    public Bank() {
    }

    //Funcitons
    public void changeCurrencies(){}
    public void sellProperty(int propertyPosition){}
    public void returnProperty(int propertyPosition){}

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

    public ArrayList<ArrayList<Integer>> getPlayerMoneys() {
        return playerMoneys;
    }

    public void setPlayerMoneys(ArrayList<ArrayList<Integer>> playerMoneys) {
        this.playerMoneys = playerMoneys;
    }
}
