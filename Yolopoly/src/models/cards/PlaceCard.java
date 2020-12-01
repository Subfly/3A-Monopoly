package models.cards;

import enumerations.Colors;

import java.util.ArrayList;

public class PlaceCard extends PropertyCard{
    private int housePrice;
    private int hotelPrice;

    public PlaceCard(int id, Colors color, String name, int cost, ArrayList<Integer> rentPrices, int mortgagePrice, int housePrice, int hotelPrice) {
        super(id, color, name, cost, rentPrices, mortgagePrice);
        this.housePrice = housePrice;
        this.hotelPrice = hotelPrice;
    }

    public int getHousePrice() {
        return housePrice;
    }

    public int getHotelPrice() {
        return hotelPrice;
    }
}
