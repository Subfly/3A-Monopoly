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
