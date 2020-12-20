package com.yolopoly.models.cards;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yolopoly.enumerations.Colors;

import java.util.ArrayList;

public class UtilityCard extends PropertyCard{
    @JsonCreator
    public UtilityCard(@JsonProperty("id")int id, @JsonProperty("color")Colors color, @JsonProperty("name")String name, @JsonProperty("cost")int cost, @JsonProperty("rentPrices")ArrayList<Integer> rentPrices, @JsonProperty("mortgagePrice")int mortgagePrice) {
        super(id, color, name, cost, rentPrices, mortgagePrice);

    }
}
