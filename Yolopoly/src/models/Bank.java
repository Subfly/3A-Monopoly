package models;

import models.cards.PropertyCard;

import java.util.ArrayList;

public class Bank {
    //Variables
    private ArrayList<PropertyCard> propertyCards;
    private ArrayList<Double> currencyRates;

    //Variables for bankman
    private ArrayList<ArrayList<Integer>> playerMoneys;

    //Constructor
    public Bank() {
    }

    //Funcitons
    public void changeCurrencies(){}

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
}
