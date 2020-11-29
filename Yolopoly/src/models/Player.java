package models;

import enumerations.Pawn;
import interfaces.Holdable;
import models.cards.PlaceCard;
import models.cards.PropertyCard;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * The type Player.
 */
public class Player {
    //Variables
    private String name;
    private Pawn pawn;
    private int doublesCount;
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

    public boolean isGetLoansCurrently() {
        return getLoansCurrently;
    }

    public void setGetLoansCurrently(boolean getLoansCurrently) {
        this.getLoansCurrently = getLoansCurrently;
    }

    //Variables for bankman
    private boolean getLoansCurrently;

    public int getLoanTurn() {
        return loanTurn;
    }

    public void setLoanTurn(int loanTurn) {
        this.loanTurn = loanTurn;
    }

    private int loanTurn;

    public int getLoan() {
        return loan;
    }

    public void setLoan(int loan) {
        this.loan = loan;
    }

    private int loan;
    private ArrayList<Integer> moneyOnBank;

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
        this.money = 0;
        this.ownedPlaces = new ArrayList<>();
        this.railroadsOwned = 0;
        this.utilitiesOwned = 0;
        this.isBankrupt = false;
        this.isHuman = isHuman;
        this.loan = 0;
        this.doublesCount = 0;
        this.getLoansCurrently = false;
    }

    //Functions

    public int countPlayersColor(Square square){

        return (int) ownedPlaces.stream().filter(s-> s.getColor() == square.getColor()).count();
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
    public boolean addMoney(int amount){
        money = money + amount;
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
    public boolean removeMoney(int amount){
        if (money >= amount) {
            money = money - amount;
            return true;
        }
        return false;
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
            System.out.println("Player " + name + " bought the property " + card.getName());
            return true;
        }
        System.out.println("Player " + name + " bought the property " + card.getName());
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
        if (doublesCount == 1) {
            isThreeTimesDoubled = true;
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
     * Gets money.
     *
     * @return the money
     */
    public int getMoney() {
        return money;
    }

    /**
     * Sets money.
     *
     * @param money the money
     */
    public void setMoney(int money) {
        this.money = money;
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
        this.currentPosition = currentPosition;
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
}
