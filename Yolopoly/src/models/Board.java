package models;

import models.cards.ChanceCard;
import models.cards.CommunityChestCard;
import models.squares.Square;

import java.util.ArrayList;

public class Board {
    private ArrayList<Square> squares;
    private ArrayList<CommunityChestCard> commCards;
    private ArrayList<ChanceCard> chanceCards;

    public Board() {
    }
}
