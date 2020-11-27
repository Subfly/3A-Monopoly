package models;

import enumerations.Building;
import enumerations.Colors;
import enumerations.SquareType;

public class Square {
    //Variables
    private SquareType type;
    private Colors color;
    private boolean isBought;
    private boolean hasHome;
    private boolean hasHotel;
    private int cost;
    private int houseCount;
    private int hotelCount;
    private int id;

    //Constructor
    public Square(SquareType type, Colors color, int cost, int id) {
        this.type = type;
        this.cost = cost;
        this.color = color;
        this.id = id;
        this.isBought = false;
        this.hasHome = false;
        this.hasHotel = false;
        this.houseCount = 0;
        this.hotelCount = 0;
    }

    //Functions
    public void build( Building buildingType, int count ){
        if( buildingType == Building.House ){
            //if( houseCount < 4 && ( houseCount + count ) <= 4 ) { //inner enginede yapılacak
            houseCount++;
            System.out.println("House increased");
            hasHome = true;
            System.out.println("Player has got home");

        }
        if( buildingType == Building.Hotel ){
            //if( houseCount == 4 &&  hasHotel == false && count == 1 ){ //inner enginede yapılacak
            hotelCount++;
            System.out.println("Hotel increased");
            hasHotel = true;
            System.out.println("Player has got hotel");


        }
    }
    public void destroy(Building buildingType, int count){

        if( buildingType == Building.House ) { //&& houseCount > 0 && ( houseCount - count ) >= 0 ){ //inner enginede yapılacak
            houseCount--;
            System.out.println("House decreased");

            if( houseCount == 0 ){
                hasHome = false;
                System.out.println("Player hasn't got home");
            }
        }
        if( buildingType == Building.Hotel ){ // && hasHotel == true ){ //inner enginede yapılacak
            hotelCount--;
            System.out.println("Hotel decreased");
            hasHotel = false;
            System.out.println("Player hasn't got hotel");
        }

    }


    //Getters and Setters
    public SquareType getType() {
        return type;
    }

    public void setType(SquareType type) {
        this.type = type;
    }
    public Colors getColor() {
        return color;
    }

    public void setType(Colors color) {
        this.color = color;
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
