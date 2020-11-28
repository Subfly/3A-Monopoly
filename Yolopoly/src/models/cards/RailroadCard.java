package models.cards;

import java.util.ArrayList;

public class RailroadCard extends PropertyCard{
    public RailroadCard(int id, String name, int cost, ArrayList<Integer> rentPrices, int mortgagePrice) {
        super(id, name, cost, rentPrices, mortgagePrice);
    }
}
