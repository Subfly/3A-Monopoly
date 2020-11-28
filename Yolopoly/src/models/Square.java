package models;

import enumerations.Building;
import enumerations.SquareType;

public class Square {
    //Variables

    private int id;
    private SquareType type;
    private String name;
    private int cost;
    //private Color color;
    private boolean isBought;
    private int houseCount;
    private int hotelCount;

    //Constructor

    public Square(int id, SquareType type, String name, int cost /*, Color color */) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.cost = cost;
        //this.color = color;
        this.isBought = false;
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
