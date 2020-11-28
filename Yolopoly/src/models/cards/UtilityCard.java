package models.cards;

import java.util.ArrayList;

public class UtilityCard extends PropertyCard{
    public UtilityCard(int id, String name, int cost, ArrayList<Integer> rentPrices, int mortgagePrice) {
        super(id, name, cost, rentPrices, mortgagePrice);
    }
}
