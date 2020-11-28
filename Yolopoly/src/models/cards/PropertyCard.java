package models.cards;

import java.util.ArrayList;

public abstract class PropertyCard {
    private final int id;
    private final String name;
    private final int cost;
    private final ArrayList<Integer> rentPrices;
    private final int mortgagePrice;
    private int ownedBy;

    public PropertyCard(int id, String name, int cost, ArrayList<Integer> rentPrices, int mortgagePrice) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.rentPrices = rentPrices;
        this.mortgagePrice = mortgagePrice;
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
}
