package com.yolopoly.models.cards;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.yolopoly.enumerations.Colors;

import java.util.ArrayList;
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PlaceCard.class),
        @JsonSubTypes.Type(value = RailroadCard.class),
        @JsonSubTypes.Type(value = UtilityCard.class),
})
public abstract class PropertyCard {


    private static final double MORTGAGE_PENALTY = 0.10;
    private final int id;
    private final Colors color;
    private final String name;
    private final int cost;
    private final ArrayList<Integer> rentPrices;
    private final int mortgagePrice;
    private boolean isMortgaged;
    private int ownedBy;

    public PropertyCard(int id, Colors color, String name, int cost, ArrayList<Integer> rentPrices, int mortgagePrice) {
        this.id = id;
        this.color = color;
        this.name = name;
        this.cost = cost;
        this.rentPrices = rentPrices;
        this.mortgagePrice = mortgagePrice;
        this.isMortgaged = false;
        this.ownedBy = -1;
    }

    public static double getMortgagePenalty() {
        return MORTGAGE_PENALTY;
    }

    public Colors getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public ArrayList<Integer> getRentPrices() {
        return rentPrices;
    }

    public int getMortgagePrice() {
        return mortgagePrice;
    }

    public int getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(int ownedBy) {
        this.ownedBy = ownedBy;
    }

    public boolean isMortgaged() {
        return isMortgaged;
    }

    public void setMortgaged(boolean mortgaged) {
        isMortgaged = mortgaged;
    }
}
