package models.cards;

import enumerations.Colors;

import java.util.ArrayList;

public class PlaceCard extends PropertyCard{
    private int housePrice;
    private int hotelPrice;

    public PlaceCard(int id, String name, int cost, Colors color, ArrayList<Integer> rentPrices, int mortgagePrice, int housePrice, int hotelPrice) {
        super(id, name, cost, color, rentPrices, mortgagePrice);
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
