package models.cards;

import java.util.ArrayList;

public class PlaceCard extends PropertyCard{
    private int housePrice;
    private int hotelPrice;
    public PlaceCard(ArrayList<Integer> rentPrices, int mortagePrice, int sellPrice, int id, int housePrice, int hotelPrice) {
        super(rentPrices, mortagePrice, sellPrice, id);
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
