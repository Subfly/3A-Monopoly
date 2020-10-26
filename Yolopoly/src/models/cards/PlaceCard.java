package models.cards;

import java.util.ArrayList;

public class PlaceCard extends PropertyCard{
    private int housePrice;
    private int hotelPrice;
    public PlaceCard(ArrayList<Integer> rentPrices, int mortagePrice, int id, int housePrice) {
        super(rentPrices, mortagePrice, id);
        this.housePrice = housePrice;
        this.hotelPrice = housePrice * 5;
    }

    public int getHousePrice() {
        return housePrice;
    }

    public int getHotelPrice() {
        return hotelPrice;
    }
}
