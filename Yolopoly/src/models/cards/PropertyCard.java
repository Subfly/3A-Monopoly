package models.cards;

import java.util.ArrayList;

public abstract class PropertyCard {
    private final ArrayList<Integer> rentPrices;
    private final int mortagePrice;
    private final int id;
    private String name;
    private int ownedBy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PropertyCard(ArrayList<Integer> rentPrices, int mortagePrice, int id) {
        this.rentPrices = rentPrices;
        this.mortagePrice = mortagePrice;
        this.id = id;
        this.ownedBy = -1;
    }

    public ArrayList<Integer> getRentPrices() {
        return rentPrices;
    }

    public int getMortagePrice() {
        return mortagePrice;
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
