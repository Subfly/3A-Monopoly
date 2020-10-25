package models.cards;

import java.util.ArrayList;

public abstract class PropertyCard {
    private final ArrayList<Integer> rentPrices;
    private final int mortagePrice;
    private final int sellPrice;
    private final int id;
    private int ownedBy;

    public PropertyCard(ArrayList<Integer> rentPrices, int mortagePrice, int sellPrice, int id) {
        this.rentPrices = rentPrices;
        this.mortagePrice = mortagePrice;
        this.sellPrice = sellPrice;
        this.id = id;
        this.ownedBy = -1;
    }

    public ArrayList<Integer> getRentPrices() {
        return rentPrices;
    }

    public int getMortagePrice() {
        return mortagePrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public int getId() {
        return id;
    }

    public int getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(int ownedBy) {
        this.ownedBy = ownedBy;
    }
}
