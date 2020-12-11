package models;

import enumerations.GameMode;
import enumerations.GameTheme;
import models.cards.PropertyCard;
import storage.Constants;
import storage.StorageUtil;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class Bank {

    // Constants
    private static final double RETURN_RATE = 0.5;
    private int houseCount;
    private int hotelCount;



    //Variables for bankman
    // private ArrayList<Integer> playerLoans;
    private ArrayList<PropertyCard> propertyCards;
    private ArrayList<Currency> currencyRates;

    //Constructor
    public Bank(GameTheme gameTheme, GameMode gameMode) {
        StorageUtil util = new StorageUtil();
        propertyCards = new ArrayList<>();
        try{
            propertyCards = util.getPropertyCards(gameMode, gameTheme);
        }catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("ERROR (3001): INVALID FILE " + e.getMessage() + " ");
        }
        houseCount = 32;
        hotelCount = 12;
    }

    //Functions
    public void changeCurrencies(){
        int currencyCount = currencyRates.size();
        // for each currency, generate a random number between -1 and 1
        // and change the currency rate accordingly
        for (int i = 1; i < currencyCount; i++) {
            double currentRate = currencyRates.get(i).getRate();
            double change = ((Math.random() * (1 - (-1))) + (-1));
            currencyRates.get(i).setRate(change + (change * currentRate));
        }
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

    public int exchangeMoney(Player p, String fromCurrency, String toCurrency, int exchangeAmount) {

        if (fromCurrency.equals(Constants.CURRENCY_NAMES[0]) && toCurrency.equals(Constants.CURRENCY_NAMES[0])) {
            System.out.println("Invalid exchange currencies");
            return -1;
        }

        // Only allow exchange from Monopoly Dollar or to Monopoly Dollar
        if (!fromCurrency.equals(Constants.CURRENCY_NAMES[0]) && !toCurrency.equals(Constants.CURRENCY_NAMES[0])) {
            System.out.println("Invalid exchange currencies");
            return -1;
        }

        // Get the players money
        Map<String, Integer> playerMoney = p.getMoney();

        // Check if the player's money is sufficient to exchange
        int fromAmountOfPlayer = playerMoney.get(fromCurrency);
        if (fromAmountOfPlayer < exchangeAmount) {
            System.out.println("Money is not sufficient");
            return -2;
        }

        // Update the player's money
        double rate = (double) currencyRates.stream().filter(s-> s.getName().equals(toCurrency)).collect(Collectors.toList()).get(0).getRate();
        int newAmount = (int)(exchangeAmount / rate);
        p.addMoney(toCurrency, newAmount);
        p.removeMoney(fromCurrency, exchangeAmount);
        return 0;
    }

    //Getters and Setters
    public ArrayList<Currency> getCurrencyRates() {
        return currencyRates;
    }

    public void setCurrencyRates(ArrayList<Currency> currencyRates) {
        this.currencyRates = currencyRates;
    }

    public ArrayList<Integer> getPlayerLoans() {
        return null;
    }

    public void setPlayerLoans(ArrayList<ArrayList<Integer>> playerLoans) {
    }

    public static double getReturnRate() {
        return RETURN_RATE;
    }

    public int getHouseCount() {
        return houseCount;
    }

    public int getHotelCount() {
        return hotelCount;
    }

    public void setHouseCount(int houseCount) {
        this.houseCount = houseCount;
    }

    public void setHotelCount(int hotelCount) {
        this.hotelCount = hotelCount;
    }

    public ArrayList<PropertyCard> getPropertyCards() {
        return propertyCards;
    }

    public void setPropertyCards(ArrayList<PropertyCard> propertyCards) {
        this.propertyCards = propertyCards;
    }
}
