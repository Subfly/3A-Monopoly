package com.yolopoly.models.cards;


import com.yolopoly.enumerations.Colors;

import java.util.ArrayList;

public class UtilityCard extends PropertyCard{
    public UtilityCard(int id, Colors color, String name, int cost, ArrayList<Integer> rentPrices, int mortgagePrice) {
        super(id, color, name, cost, rentPrices, mortgagePrice);
    }
}
