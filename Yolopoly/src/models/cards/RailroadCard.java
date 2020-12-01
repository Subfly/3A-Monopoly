package models.cards;

import enumerations.Colors;

import java.util.ArrayList;

public class RailroadCard extends PropertyCard{
    public RailroadCard(int id, Colors color, String name, int cost, ArrayList<Integer> rentPrices, int mortgagePrice) {
        super(id, color, name, cost, rentPrices, mortgagePrice);
    }
}
