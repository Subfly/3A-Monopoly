package models.cards;

import enumerations.Colors;

import java.util.ArrayList;

public class RailroadCard extends PropertyCard{
    public RailroadCard(int id, String name, int cost, Colors color, ArrayList<Integer> rentPrices, int mortgagePrice) {
        super(id, name, cost, color, rentPrices, mortgagePrice);
    }
}
