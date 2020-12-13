package models.bases;

import enumerations.Building;
import enumerations.GameMode;
import enumerations.GameTheme;
import models.cards.ChanceCard;
import models.cards.CommunityChestCard;
import models.cards.DrawableCard;
import storage.StorageUtil;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class Board {
    //Variables
    private ArrayList<Square> squares;
    private ArrayList<CommunityChestCard> commCards;
    private ArrayList<ChanceCard> chanceCards;
    private int moneyOnBoard;

    //Constructor
    public Board(GameMode mode, GameTheme gameTheme) {
        StorageUtil util = new StorageUtil();
        try{
            squares = util.getSquaresData(mode, gameTheme);
            commCards = util.getCommunityChestCards(mode, gameTheme);
            chanceCards = util.getChanceCards(mode, gameTheme);
            moneyOnBoard = 0;
        }catch(FileNotFoundException e){
            System.out.println("ERROR 2001: INVALID FILE");
        }
    }

    //Functions
    public void returnSavedCard(DrawableCard card){
        if(card instanceof ChanceCard){
            chanceCards.add((ChanceCard) card);
            Collections.shuffle(chanceCards);
        }else{
            commCards.add((CommunityChestCard) card);
            Collections.shuffle(commCards);
        }
    }

    public DrawableCard drawChanceCard(){
        var cardDrawn = this.chanceCards.get(0);
        this.chanceCards.remove(0);
        if(!cardDrawn.isGOOJC()){
            this.chanceCards.add(cardDrawn);
        }
        return cardDrawn;
    }

    public DrawableCard drawCommunityChestCard(){
        var cardDrawn = this.commCards.get(0);
        this.commCards.remove(0);
        if(!cardDrawn.isGOOJC()){
            this.commCards.add(cardDrawn);
        }
        return cardDrawn;
    }

    public int countColors(Square square){
        return (int) squares.stream().filter(s-> s.getColor() == square.getColor()).count();
    }

    //TODO: GÖZDEN GEÇİRİLİCEK
    public boolean hasHouseAllSquares(Square square){
        if (squares.stream().filter(s-> s.getColor() == square.getColor()).filter(Square::isHouseCheck).count() == countColors( square ) ) {
            return true;
        }
        return false;
    }

    public Square getSpecificSquare(int index){
        return squares.get(index);
    }

    public boolean buySquare(int squareIndex){
        Square square = squares.get(squareIndex);
        if(!square.isBought()){
            square.setBought(true);
            squares.set(squareIndex, square);
            return true;
        }
        return false;
    }

    public boolean build(Building building, int squareIndex){
        var square = squares.get(squareIndex);
        square.build(building);
        squares.set(squareIndex, square);
        return true;
    }

    public boolean destroy(Building building, int squareIndex){
        var square = squares.get(squareIndex);
        square.destroy(building);
        squares.set(squareIndex, square);
        return true;
    }

    public boolean addToTaxMoney(int amount) {
        this.moneyOnBoard += amount;
        return true;
    }

    public boolean removeFromTaxMoney() {
        this.moneyOnBoard = 0;
        return true;
    }

    //Getters and Setters

    public int getMoneyOnBoard() {
        return moneyOnBoard;
    }

    public void setMoneyOnBoard(int moneyOnBoard) {
        this.moneyOnBoard = moneyOnBoard;
    }

    public ArrayList<Square> getSquares() {
        return squares;
    }

    public void setSquares(ArrayList<Square> squares) {
        this.squares = squares;
    }

    public ArrayList<CommunityChestCard> getCommCards() {
        return commCards;
    }

    public void setCommCards(ArrayList<CommunityChestCard> commCards) {
        this.commCards = commCards;
    }

    public ArrayList<ChanceCard> getChanceCards() {
        return chanceCards;
    }

    public void setChanceCards(ArrayList<ChanceCard> chanceCards) {
        this.chanceCards = chanceCards;
    }
}
