package models.cards;

import java.util.ArrayList;

public abstract class PropertyCard {
    private final ArrayList<Integer> rentPrices;
    private final int mortagePrice;
    private final int sellPrice;
    private final int id;

    public PropertyCard(ArrayList<Integer> rentPrices, int mortagePrice, int sellPrice, int id) {
        this.rentPrices = rentPrices;
        this.mortagePrice = mortagePrice;
        this.sellPrice = sellPrice;
        this.id = id;
    }

    public int getMortagePrice() {
        return mortagePrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public int getRentPrice(int count){
        return this.rentPrices.get(count);
    }

    public int getId() {
        return id;
    }
}
