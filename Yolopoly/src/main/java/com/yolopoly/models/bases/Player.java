package com.yolopoly.models.bases;

import com.yolopoly.enumerations.Pawn;
import com.yolopoly.models.cards.DrawableCard;
import com.yolopoly.models.cards.PropertyCard;
import com.yolopoly.storage.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Player.
 */
public class Player {

    //Pre Variables
    private String name;
    private Pawn pawn;
    private boolean isHuman;

    //In variables
    private int doublesCount;
    private Map<String, Integer> money;
    private int currentPosition;
    private int railroadsOwned;
    private int utilitiesOwned;
    private boolean isInJail;
    private int inJailTurnCount;
    private boolean isThreeTimesDoubled;
    private boolean isBankrupt;
    private ArrayList<PropertyCard> ownedPlaces;
    private ArrayList<DrawableCard> savedCards;
    private int pawnIndex;
    private boolean discardedFromGame;


    //Variables for bankman
    private boolean getLoanCurrently;
    private int loanTurn;
    private int loan;

    public int getLoanTurn() {
        return loanTurn;
    }

    public void setLoanTurn(int loanTurn) {
        this.loanTurn = loanTurn;
    }

    public void resetLoan() {
        setLoanTurn(0);
        setGetLoanCurrently(false);
        setLoan(0);
    }

    public boolean decrementLoanTurn() {
        if(this.isGetLoanCurrently()) {
            this.loanTurn--;
            return true;
        }
        return false;
    }

    public int getLoan() {
        return loan;
    }

    public void setLoan(int loan) {
        this.loan = loan;
    }

    public int getPawnIndex() {
        return pawnIndex;
    }

    public void setPawnIndex(int pawnIndex) {
        this.pawnIndex = pawnIndex;
    }


    /**
     * Instantiates a new Player.
     *
     * @param name    the name
     * @param isHuman the is human
     */
    //Constructor
    public Player(String name, boolean isHuman) {
        this.name = name;
        this.isInJail = false;
        this.isThreeTimesDoubled = false;
        this.ownedPlaces = new ArrayList<>();
        this.railroadsOwned = 0;
        this.utilitiesOwned = 0;
        this.inJailTurnCount = 0;
        this.isBankrupt = false;
        this.isHuman = isHuman;
        this.loan = 0;
        this.doublesCount = 0;
        this.getLoanCurrently = false;
        this.money = new HashMap<>();
        this.savedCards = new ArrayList<>();
        for (String cName : Constants.CURRENCY_NAMES) {
            money.put(cName, 0);
        }
        this.discardedFromGame = false;
    }

    //Functions
    public boolean addToSavedCards(DrawableCard card){
        this.savedCards.add(card);
        return true;
    }

    public DrawableCard removeFromSavedCards(){
        //As only the GOFJ Cards can be present here
        try{
            this.savedCards.remove(0);
            return removeFromSavedCards();
        }catch (IndexOutOfBoundsException e){
            System.out.println("No saved cards found.");
            return null;
        }
    }



    public PropertyCard getSpecificCard(int index){
        int propertyIndex = -1;
        int count = 0;
        for ( PropertyCard s : ownedPlaces ) {
            if ( s.getId() == index ){
                propertyIndex = count;
                return ownedPlaces.get(propertyIndex);
            }
            count++;
        }
        return null;
    }




    /**
     * Adds money to player.
     *
     * @param amount the amount to be added
     * @return the boolean if the operation is succedeed
     */
    public boolean addMoney(String currencyName, int amount){
        int oldMoney = money.get(currencyName);
        int newMoney = oldMoney + amount;
        money.replace(currencyName, newMoney);
        return true;
    }


    /**
     * Remove money from the player.
     *
     * The function first checks if the amount of the money that the
     * player is enough
     *
     * @param amount the amount to be removed from the player
     * @return if the operation is succedeed
     */
    public boolean removeMoney(String currencyName, int amount){
        int oldMoney = money.get(currencyName);
        int newMoney = oldMoney - amount;
        if (newMoney < 0) {
            System.out.println("Money is not sufficient");
            return false;
        }
        money.replace(currencyName, newMoney);
        return true;
    }

    /**
     * Is owned boolean.
     *
     * @param card the card
     * @return the boolean
     */
    public boolean isOwned(PropertyCard card){
        if (ownedPlaces.contains(card)) {
            return true;
        }
        return false;
    }

    /**
     * Own property boolean.
     *
     * @param card the card
     * @return the boolean
     */
    public boolean ownProperty(PropertyCard card){
        if (!isOwned(card)) {
            ownedPlaces.add(card);
            //System.out.println("Player " + name + " bought the property " + card.getName());
            return true;
        }
        //System.out.println("Player " + name + " bought the property " + card.getName());
        return false;
    }

    /**
     * Sell property boolean.
     *
     * @param card the card
     * @return the boolean
     */
    public boolean sellProperty(PropertyCard card){
        if (isOwned(card)) {
            ownedPlaces.remove(card);
            System.out.println("Player " + name + " sold the property " + card.getName());
            return true;
        }
        return false;
    }

    /**
     * Increment doubles count boolean.
     *
     * @return the boolean
     */
    public boolean incrementDoublesCount() {
        doublesCount = doublesCount + 1;
        if (doublesCount == 3) {
            this.setThreeTimesDoubled(true);
            this.setInJail(true); // doubles three times, go to jail
            this.resetDoublesCount();
        }
        return true;
    }


    /**
     * Reset doubles count boolean.
     *
     * @return the boolean
     */
    public boolean resetDoublesCount() {
        doublesCount = 0;
        return true;
    }

    public boolean resetInJailTurnCount() {
        this.setInJailTurnCount(0);
        return true;
    }

    public boolean incrementInJailTurnCount() {
        if (this.isInJail()) {
            this.inJailTurnCount++;
            return true;
        }
        return false;
    }

    public int getDoublesCount() {
        return doublesCount;
    }

    public void setDoublesCount(int doublesCount) {
        this.doublesCount = doublesCount;
    }

    public Map<String, Integer> getMoney() {
        return money;
    }

    public int getMonopolyMoneyAmount() {
        return money.get(Constants.CURRENCY_NAMES[0]);
    }
    public void setMoney(Map<String, Integer> money) {
        this.money = money;
    }

    public ArrayList<PropertyCard> getOwnedPlaces() {
        return ownedPlaces;
    }

    public void setOwnedPlaces(ArrayList<PropertyCard> ownedPlaces) {
        this.ownedPlaces = ownedPlaces;
    }

    public ArrayList<DrawableCard> getSavedCards() {
        return savedCards;
    }

    public void setSavedCards(ArrayList<DrawableCard> savedCards) {
        this.savedCards = savedCards;
    }

    public void setStartMoney(int amount) {
        money.replace(Constants.CURRENCY_NAMES[0], amount);
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    //Getters and Setters

    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets pawn.
     *
     * @return the pawn
     */
    public Pawn getPawn() {
        return pawn;
    }

    /**
     * Sets pawn.
     *
     * @param pawn the pawn
     */
    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    /**
     * Gets current position.
     *
     * @return the current position
     */
    public int getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Sets current position.
     *
     * @param currentPosition the current position
     */
    public void setCurrentPosition(int currentPosition) {
        this.currentPosition =  currentPosition % 40; //PÜÜÜÜÜÜÜÜÜÜÜÜ
    }

    /**
     * Gets railroads owned.
     *
     * @return the railroads owned
     */
    public int getRailroadsOwned() {
        return railroadsOwned;
    }

    /**
     * Sets railroads owned.
     *
     * @param railroadsOwned the railroads owned
     */
    public void setRailroadsOwned(int railroadsOwned) {
        this.railroadsOwned = railroadsOwned;
    }

    /**
     * Gets utilities owned.
     *
     * @return the utilities owned
     */
    public int getUtilitiesOwned() {
        return utilitiesOwned;
    }

    /**
     * Sets utilities owned.
     *
     * @param utilitiesOwned the utilities owned
     */
    public void setUtilitiesOwned(int utilitiesOwned) {
        this.utilitiesOwned = utilitiesOwned;
    }

    /**
     * Is human boolean.
     *
     * @return the boolean
     */
    public boolean isHuman() {
        return isHuman;
    }

    /**
     * Sets human.
     *
     * @param human the human
     */
    public void setHuman(boolean human) {
        isHuman = human;
    }

    /**
     * Is in jail boolean.
     *
     * @return the boolean
     */
    public boolean isInJail() {
        return isInJail;
    }

    /**
     * Sets in jail.
     *
     * @param inJail the in jail
     */
    public void setInJail(boolean inJail) {
        isInJail = inJail;
    }

    /**
     * Is three times doubled boolean.
     *
     * @return the boolean
     */
    public boolean isThreeTimesDoubled() {
        return isThreeTimesDoubled;
    }

    /**
     * Sets three times doubled.
     *
     * @param threeTimesDoubled the three times doubled
     */
    public void setThreeTimesDoubled(boolean threeTimesDoubled) {
        isThreeTimesDoubled = threeTimesDoubled;
    }

    /**
     * Is bankrupt boolean.
     *
     * @return the boolean
     */
    public boolean isBankrupt() {
        return isBankrupt;
    }

    /**
     * Sets bankrupt.
     *
     * @param bankrupt the bankrupt
     */
    public void setBankrupt(boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public boolean isGetLoanCurrently() {
        return getLoanCurrently;
    }

    public void setGetLoanCurrently(boolean getLoansCurrently) {
        this.getLoanCurrently = getLoansCurrently;
    }

    public boolean isDiscardedFromGame() {
        return discardedFromGame;
    }

    public void setDiscardedFromGame(boolean discardedFromGame) {
        this.discardedFromGame = discardedFromGame;
    }

    public int getInJailTurnCount() {
        return inJailTurnCount;
    }

    public void setInJailTurnCount(int inJailTurnCount) {
        this.inJailTurnCount = inJailTurnCount;
    }
}
