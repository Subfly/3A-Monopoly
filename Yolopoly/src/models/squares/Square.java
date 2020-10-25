package models.squares;

import models.Building;

public class Square {
    //Variables
    private SquareType type;
    private boolean isBought;
    private boolean hasHome;
    private boolean hasHotel;
    private int cost;
    private int houseCount;
    private int hotelCount;
    private int id;

    //Constructor
    public Square(SquareType type, int cost, int id) {
        this.type = type;
        this.cost = cost;
        this.id = id;
        this.isBought = false;
        this.hasHome = false;
        this.hasHotel = false;
        this.houseCount = 0;
        this.hotelCount = 0;
    }

    //Functions
    public void build(Building buildingType, int count){}
    public void destroy(Building buildingType, int count){}

    //Getters and Setters
    public SquareType getType() {
        return type;
    }

    public void setType(SquareType type) {
        this.type = type;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }

    public boolean isHasHome() {
        return hasHome;
    }

    public void setHasHome(boolean hasHome) {
        this.hasHome = hasHome;
    }

    public boolean isHasHotel() {
        return hasHotel;
    }

    public void setHasHotel(boolean hasHotel) {
        this.hasHotel = hasHotel;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getHouseCount() {
        return houseCount;
    }

    public void setHouseCount(int houseCount) {
        this.houseCount = houseCount;
    }

    public int getHotelCount() {
        return hotelCount;
    }

    public void setHotelCount(int hotelCount) {
        this.hotelCount = hotelCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
