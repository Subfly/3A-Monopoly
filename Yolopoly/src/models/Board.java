package models;

import enumerations.GameTheme;
import models.cards.ChanceCard;
import models.cards.CommunityChestCard;
import models.cards.DrawableCard;

import java.util.ArrayList;

public class Board {
    //Variables
    private ArrayList<Square> squares;
    private ArrayList<DrawableCard> commCards;
    private ArrayList<DrawableCard> chanceCards;

    //Constructor
    public Board(GameTheme gameTheme) {}

    //Functions
    public int countColors(Square square){
        return (int) squares.stream().filter(s-> s.getColor() == square.getColor()).count();
    }

    public boolean hasHouseAllSquares(Square square){
        if (squares.stream().filter(s-> s.getColor() == square.getColor()).filter(s -> s.isHouseCheck() == true ).count() == countColors( square ) ) {
            return true;
        }
        return false;
    }
    public Square getSpecificSquare(int index){
        return squares.get(index);
    }

    public DrawableCard drawChanceCard(){
        var cardDrawn = this.chanceCards.get(0);
        this.chanceCards.remove(0);
        this.chanceCards.add(cardDrawn);
        return cardDrawn;
    }

    public DrawableCard drawCommunityChestCard(){
        var cardDrawn = this.commCards.get(0);
        this.commCards.remove(0);
        this.commCards.add(cardDrawn);
        return cardDrawn;
    }

    //Getters and Setters

    public ArrayList<Square> getSquares() {
        return squares;
    }

    public void setSquares(ArrayList<Square> squares) {
        this.squares = squares;
    }

    public ArrayList<DrawableCard> getCommCards() {
        return commCards;
    }

    public void setCommCards(ArrayList<DrawableCard> commCards) {
        this.commCards = commCards;
    }

    public ArrayList<DrawableCard> getChanceCards() {
        return chanceCards;
    }

    public void setChanceCards(ArrayList<DrawableCard> chanceCards) {
        this.chanceCards = chanceCards;
    }
}
