package com.yolopoly.models.bases;

import com.yolopoly.enumerations.Building;
import com.yolopoly.enumerations.Colors;
import com.yolopoly.enumerations.SquareType;

public class Square {
    //Variables

    private int id;
    private SquareType type;
    private String name;

    private int cost;
    private Colors color;
    private boolean isBought;
    private int houseCount;
    private int hotelCount;
    private boolean houseCheck;
    private int level;

    //Constructor
    public Square(){}
    public Square(int id, SquareType type, String name, int cost, Colors color) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.cost = cost;
        this.color = color;
        this.isBought = false;
        this.houseCount = 0;
        this.hotelCount = 0;
        this.houseCheck = false;
        this.level = 0;
    }

    //Functions

    public void increaseLevel(){
        level++;
    }
    public void decreaseLevel(){
        level--;
    }
    public int getRentMultiplier(){
        int total = houseCount;
        if(hotelCount != 0){
            total += hotelCount;
        }
        return total;
    }
    public void build( Building buildingType){
        if( buildingType == Building.House ){

            houseCount++;
            houseCheck = true;
            increaseLevel();
//            System.out.println("House increased");
//            System.out.println("Player has got home");

        }
        if( buildingType == Building.Hotel ){

            hotelCount++;
            increaseLevel();
            houseCount = 0;
//            System.out.println("Hotel increased");
//            System.out.println("Player has got hotel");


        }
    }
    public void destroy(Building buildingType){

        if( buildingType == Building.House ) {
            houseCount--;
            decreaseLevel();
//            System.out.println("House decreased");

            if( houseCount == 0 ){
//                System.out.println("Player hasn't got home");
            }
        }
        if( buildingType == Building.Hotel ){
            hotelCount--;
            decreaseLevel();
            houseCount = 4;
//            System.out.println("Hotel decreased");
//            System.out.println("Player hasn't got hotel");
        }

    }




    //Getters and Setters
    public SquareType getType() {
        return type;
    }

    public void setType(SquareType type) {
        this.type = type;
    }

    public boolean isHouseCheck() {
        return houseCheck;
    }

    public void setHouseCheck(boolean houseCheck) {
        this.houseCheck = houseCheck;
    }

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
