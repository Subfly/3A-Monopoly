package com.yolopoly.models.cards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yolopoly.enumerations.Colors;

import java.util.ArrayList;

public class PlaceCard extends PropertyCard{


    private static final int RENT_MULTIPLIER = 2;
    private int housePrice;
    private int hotelPrice;
    private int mortgagePrice;


    /*public PlaceCard(int id, Colors color, String name, int cost, ArrayList<Integer> rentPrices, int mortgagePrice, int housePrice, int hotelPrice) {
        super(id, color, name, cost, rentPrices, mortgagePrice);
        this.housePrice = housePrice;
        this.hotelPrice = hotelPrice;
        mortgagePrice = (int)0.9 * housePrice;
    }
*/
    public static int getRentMultiplier() {
        return RENT_MULTIPLIER;
    }

    public int getHousePrice() {
        return housePrice;
    }

    public int getHotelPrice() {
        return hotelPrice;
    }

    public int getMortgagePrice(){ return mortgagePrice;}

    public void setHousePrice(int housePrice) {
        this.housePrice = housePrice;
    }

    public void setHotelPrice(int hotelPrice) {
        this.hotelPrice = hotelPrice;
    }

    public void setMortgagePrice(int mortgagePrice) {
        this.mortgagePrice = mortgagePrice;
    }
    @JsonCreator
    public PlaceCard(@JsonProperty("id")int id, @JsonProperty("color")Colors color, @JsonProperty("name")String name, @JsonProperty("cost")int cost, @JsonProperty("rentPrices")ArrayList<Integer> rentPrices, @JsonProperty("mortgagePrice")int mortgagePrice, @JsonProperty("housePrice")int housePrice, @JsonProperty("hotelPrice")int hotelPrice) {
        super(id, color, name, cost, rentPrices, mortgagePrice);
        this.housePrice = housePrice;
        this.hotelPrice = hotelPrice;
    }
}
