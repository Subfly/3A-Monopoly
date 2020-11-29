package models;

import enumerations.GameTheme;
import models.cards.ChanceCard;
import models.cards.CommunityChestCard;

import java.util.ArrayList;

public class Board {
    //Variables
    private ArrayList<Square> squares;
    private ArrayList<CommunityChestCard> commCards;
    private ArrayList<ChanceCard> chanceCards;

    //Constructor
    public Board(GameTheme gameTheme) {}

    //Functions
    public int countColors(Square square){
        return (int) squares.stream().filter(s-> s.getColor() == square.getColor()).count();
    }

    public int countHousesOnSquaresOfaColor(Square square){
        return (int) squares.stream().filter(s-> s.getColor() == square.getColor()).filter(s -> s.getHouseCount() > 0).count();
    }
    public Square getSpecificSquare(int index){
        return squares.get(index);
    }

    //Getters and Setters

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
