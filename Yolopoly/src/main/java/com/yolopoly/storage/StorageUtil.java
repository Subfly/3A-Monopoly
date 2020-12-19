package com.yolopoly.storage;
import com.yolopoly.enumerations.Colors;
import com.yolopoly.enumerations.GameMode;
import com.yolopoly.enumerations.GameTheme;
import com.yolopoly.enumerations.SquareType;
import com.yolopoly.managers.InGameManager;
import com.yolopoly.models.bases.Square;
import com.yolopoly.models.cards.*;
import org.json.JSONObject;

import java.io.*;
import java.util.*;

public class StorageUtil {
    public StorageUtil() {
    }

    public ArrayList<Square> getSquaresData(GameMode mode, GameTheme theme) throws FileNotFoundException {
        ArrayList<Square> squares = new ArrayList<>();

        File file = null;
        switch (theme){
            case vanilla -> file = new File("src/main/java/com/yolopoly/data/themes/Vanilla.json");
            case ankara -> file = new File("src/main/java/com/yolopoly/data/themes/Ankara.json");
            case bilkent -> file = new File("src/main/java/com/yolopoly/data/themes/Bilkent.json");
            case halloween -> file = new File("src/main/java/com/yolopoly/data/themes/Halloween.json");
        }
        String content = new Scanner(file).useDelimiter("\\Z").next();

        //Read JSON
        JSONObject jo = new JSONObject(content);

        //Get all JSON data part by part
        var boardJSON = jo.getJSONObject("board");

        var boardKeys = boardJSON.keys();
        ArrayList<String> dummyBoard = new ArrayList<>();
        while(boardKeys.hasNext()){
            dummyBoard.add(boardKeys.next());
        }
        Collections.sort(dummyBoard);
        for (String s : dummyBoard) {
            var squareJSON = boardJSON.getJSONObject(s);
            int id = squareJSON.getInt("id");
            int typeHolder = squareJSON.getInt("type");
            SquareType type = null;
            switch (typeHolder){
                case 0 -> type = SquareType.GoSquare;
                case 1 -> type = SquareType.JailSquare;
                case 2 -> type = SquareType.FreeParkingSquare;
                case 3 -> type = SquareType.GoToJailSquare;
                case 4 -> type = SquareType.CommunityChestSquare;
                case 5 -> type = SquareType.ChanceSquare;
                case 6 -> type = SquareType.TaxSquare;
                case 7 -> type = SquareType.RailroadSquare;
                case 8 -> type = SquareType.UtilitySquare;
                case 9 -> type = SquareType.NormalSquare;
            }
            String name = squareJSON.getString("name");
            int price = squareJSON.getInt("price");
            int colorHolder = squareJSON.getInt("color");
            Colors color = null;
            switch (colorHolder){
                case 0 -> color = Colors.Brown;
                case 1 -> color = Colors.Cyan;
                case 2 -> color = Colors.Pink;
                case 3 -> color = Colors.Orange;
                case 4 -> color = Colors.Red;
                case 5 -> color = Colors.Yellow;
                case 6 -> color = Colors.Green;
                case 7 -> color = Colors.Blue;
                case 8 -> color = Colors.White;
            }
            Square square = new Square(id, type, name, price, color);
            squares.add(square);
        }
        squares.sort(Comparator.comparingInt(Square::getId));
        return squares;
    }

    public ArrayList<ChanceCard> getChanceCards(GameMode mode, GameTheme theme) throws FileNotFoundException {
        ArrayList<ChanceCard> chanceCards = new ArrayList<>();

        File file = null;
        switch (theme){
            case vanilla -> file = new File("src/main/java/com/yolopoly/data/themes/Vanilla.json");
            case ankara -> file = new File("src/main/java/com/yolopoly/data/themes/Ankara.json");
            case bilkent -> file = new File("src/main/java/com/yolopoly/data/themes/Bilkent.json");
            case halloween -> file = new File("src/main/java/com/yolopoly/data/themes/Halloween.json");
        }
        String content = new Scanner(file).useDelimiter("\\Z").next();

        //Read JSON
        JSONObject jo = new JSONObject(content);
        var cardsJSON = jo.getJSONObject("cards"); //Useless
        var chanceJSON = cardsJSON.getJSONObject("chance");
        var chanceKeys = chanceJSON.keys();
        ArrayList<String> dummyChances = new ArrayList<>();
        while(chanceKeys.hasNext()){
            dummyChances.add(chanceKeys.next());
        }
        Collections.shuffle(dummyChances);

        for (String s : dummyChances) {
            var chanceCardJSON = chanceJSON.getJSONObject(s);
            String message = chanceCardJSON.getString("message") + " " + chanceCardJSON.getInt("moveToIndex");
            boolean isComposed = chanceCardJSON.getBoolean("isComposed");
            boolean isMoving = chanceCardJSON.getBoolean("isMoving");
            int moveToIndex = chanceCardJSON.getInt("moveToIndex");
            int moveInCounts = chanceCardJSON.getInt("moveInCounts");
            boolean isGettingMoney = chanceCardJSON.getBoolean("isGettingMoney");
            int moneyGet = chanceCardJSON.getInt("moneyGet");
            int moneyOwe = chanceCardJSON.getInt("moneyOwe");
            boolean isRelatedToBuildings = chanceCardJSON.getBoolean("isRelatedToBuildings");
            int moneyForHouses = chanceCardJSON.getInt("moneyForHouses");
            int moneyForHotels = chanceCardJSON.getInt("moneyForHotels");
            boolean isGOOJC = chanceCardJSON.getBoolean("isGOOJC");
            boolean isGTJC = chanceCardJSON.getBoolean("isGTJC");
            boolean isEachPlayerIncluded = chanceCardJSON.getBoolean("isEachPlayerIncluded");
            boolean isDrawingChanceCard = chanceCardJSON.getBoolean("isDrawingChanceCard");

            ChanceCard card = new ChanceCard(message, isComposed, isMoving, moveToIndex, moveInCounts, isGettingMoney, moneyGet, moneyOwe, isRelatedToBuildings, moneyForHouses, moneyForHotels, isGOOJC, isGTJC, isEachPlayerIncluded, isDrawingChanceCard);
            chanceCards.add(card);
        }
        return chanceCards;
    }

    public ArrayList<CommunityChestCard> getCommunityChestCards(GameMode mode, GameTheme theme) throws FileNotFoundException {
        ArrayList<CommunityChestCard> communityCards = new ArrayList<>();

        File file = null;
        switch (theme){
            case vanilla -> file = new File("src/main/java/com/yolopoly/data/themes/Vanilla.json");
            case ankara -> file = new File("src/main/java/com/yolopoly/data/themes/Ankara.json");
            case bilkent -> file = new File("src/main/java/com/yolopoly/data/themes/Bilkent.json");
            case halloween -> file = new File("src/main/java/com/yolopoly/data/themes/Halloween.json");
        }
        String content = new Scanner(file).useDelimiter("\\Z").next();

        //Read JSON
        JSONObject jo = new JSONObject(content);
        var cardsJSON = jo.getJSONObject("cards"); //Useless
        var communityJSON = cardsJSON.getJSONObject("community");
        var chanceKeys = communityJSON.keys();
        ArrayList<String> dummyComms = new ArrayList<>();
        while(chanceKeys.hasNext()){
            dummyComms.add(chanceKeys.next());
        }
        Collections.shuffle(dummyComms);

        for (String s : dummyComms) {
            var commCardJSON = communityJSON.getJSONObject(s);
            String message = commCardJSON.getString("message");
            boolean isComposed = commCardJSON.getBoolean("isComposed");
            boolean isMoving = commCardJSON.getBoolean("isMoving");
            int moveToIndex = commCardJSON.getInt("moveToIndex");
            int moveInCounts = commCardJSON.getInt("moveInCounts");
            boolean isGettingMoney = commCardJSON.getBoolean("isGettingMoney");
            int moneyGet = commCardJSON.getInt("moneyGet");
            int moneyOwe = commCardJSON.getInt("moneyOwe");
            boolean isRelatedToBuildings = commCardJSON.getBoolean("isRelatedToBuildings");
            int moneyForHouses = commCardJSON.getInt("moneyForHouses");
            int moneyForHotels = commCardJSON.getInt("moneyForHotels");
            boolean isGOOJC = commCardJSON.getBoolean("isGOOJC");
            boolean isGTJC = commCardJSON.getBoolean("isGTJC");
            boolean isEachPlayerIncluded = commCardJSON.getBoolean("isEachPlayerIncluded");
            boolean isDrawingChanceCard = commCardJSON.getBoolean("isDrawingChanceCard");

            CommunityChestCard card = new CommunityChestCard(message, isComposed, isMoving, moveToIndex, moveInCounts, isGettingMoney, moneyGet, moneyOwe, isRelatedToBuildings, moneyForHouses, moneyForHotels, isGOOJC, isGTJC, isEachPlayerIncluded, isDrawingChanceCard);
            communityCards.add(card);
        }
        return communityCards;
    }

    public ArrayList<PropertyCard> getPropertyCards(GameMode mode, GameTheme theme) throws FileNotFoundException {
        ArrayList<PropertyCard> propertyCards = new ArrayList<>();

        File file = null;
        switch (theme){
            case vanilla -> file = new File("src/main/java/com/yolopoly/data/themes/Vanilla.json");
            case ankara -> file = new File("src/main/java/com/yolopoly/data/themes/Ankara.json");
            case bilkent -> file = new File("src/main/java/com/yolopoly/data/themes/Bilkent.json");
            case halloween -> file = new File("src/main/java/com/yolopoly/data/themes/Halloween.json");
        }
        String content = new Scanner(file).useDelimiter("\\Z").next();

        //Read JSON
        JSONObject jo = new JSONObject(content);
        var cardsJSON = jo.getJSONObject("cards"); //Useless

        //Get place cards from the JSON file
        var placeJSON = cardsJSON.getJSONObject("place");
        var placeKeys = placeJSON.keys();
        ArrayList<String> dummyPlaces = new ArrayList<>();
        while(placeKeys.hasNext()){
            dummyPlaces.add(placeKeys.next());
        }
        Collections.sort(dummyPlaces);

        for (String s : dummyPlaces) {
            var placeCardJSON = placeJSON.getJSONObject(s);
            int id = placeCardJSON.getInt("id");
            int colorHolder = placeCardJSON.getInt("color");
            Colors color = null;
            switch (colorHolder){
                case 0 -> color = Colors.Brown;
                case 1 -> color = Colors.Cyan;
                case 2 -> color = Colors.Pink;
                case 3 -> color = Colors.Orange;
                case 4 -> color = Colors.Red;
                case 5 -> color = Colors.Yellow;
                case 6 -> color = Colors.Green;
                case 7 -> color = Colors.Blue;
                case 8 -> color = Colors.White;
            }
            String name = placeCardJSON.getString("name");
            int cost = placeCardJSON.getInt("cost");
            ArrayList<Integer> rentPrices = new ArrayList<>();
            var rentPricesHolder = placeCardJSON.getJSONArray("rentPrices");
            for (Object o : rentPricesHolder) {
                rentPrices.add((int) o);
            }
            int mortgagePrice = placeCardJSON.getInt("mortgagePrice");
            int housePrice = placeCardJSON.getInt("housePrice");
            int hotelPrice = placeCardJSON.getInt("hotelPrice");

            PlaceCard placeCard = new PlaceCard(id, color, name, cost, rentPrices, mortgagePrice, housePrice, hotelPrice);
            propertyCards.add(placeCard);
        }

        //Get railroad cards form JSON file
        var railroadJSON = cardsJSON.getJSONObject("railroad");
        var railroadKeys = railroadJSON.keys();
        ArrayList<String> dummyRailroads = new ArrayList<>();
        while(railroadKeys.hasNext()){
            dummyRailroads.add(railroadKeys.next());
        }
        Collections.sort(dummyRailroads);

        for (String s : dummyRailroads) {
            var railroadCardJSON = railroadJSON.getJSONObject(s);
            int id = railroadCardJSON.getInt("id");
            int colorHolder = railroadCardJSON.getInt("color");
            Colors color = null;
            switch (colorHolder){
                case 0 -> color = Colors.Brown;
                case 1 -> color = Colors.Cyan;
                case 2 -> color = Colors.Pink;
                case 3 -> color = Colors.Orange;
                case 4 -> color = Colors.Red;
                case 5 -> color = Colors.Yellow;
                case 6 -> color = Colors.Green;
                case 7 -> color = Colors.Blue;
                case 8 -> color = Colors.White;
            }
            String name = railroadCardJSON.getString("name");
            int cost = railroadCardJSON.getInt("cost");
            ArrayList<Integer> rentPrices = new ArrayList<>();
            var rentPricesHolder = railroadCardJSON.getJSONArray("rentPrices");
            for (Object o : rentPricesHolder) {
                rentPrices.add((int) o);
            }
            int mortgagePrice = railroadCardJSON.getInt("mortgagePrice");

            RailroadCard railroadCard = new RailroadCard(id, color, name, cost, rentPrices, mortgagePrice);
            propertyCards.add(railroadCard);
        }

        var utilityJSON = cardsJSON.getJSONObject("utility");
        var utilityKeys = utilityJSON.keys();
        ArrayList<String> dummyUtilities = new ArrayList<>();
        while(utilityKeys.hasNext()){
            dummyUtilities.add(utilityKeys.next());
        }
        Collections.sort(dummyUtilities);

        for (String s : dummyUtilities) {
            var utilityCardJSON = utilityJSON.getJSONObject(s);
            int id = utilityCardJSON.getInt("id");
            int colorHolder = utilityCardJSON.getInt("color");
            Colors color = null;
            switch (colorHolder){
                case 0 -> color = Colors.Brown;
                case 1 -> color = Colors.Cyan;
                case 2 -> color = Colors.Pink;
                case 3 -> color = Colors.Orange;
                case 4 -> color = Colors.Red;
                case 5 -> color = Colors.Yellow;
                case 6 -> color = Colors.Green;
                case 7 -> color = Colors.Blue;
                case 8 -> color = Colors.White;
            }
            String name = utilityCardJSON.getString("name");
            int cost = utilityCardJSON.getInt("cost");
            ArrayList<Integer> rentPrices = new ArrayList<>();
            var rentPricesHolder = utilityCardJSON.getJSONArray("rentPrices");
            for (Object o : rentPricesHolder) {
                rentPrices.add((int) o);
            }
            int mortgagePrice = utilityCardJSON.getInt("mortgagePrice");

            UtilityCard utilityCard = new UtilityCard(id, color, name, cost, rentPrices, mortgagePrice);
            propertyCards.add(utilityCard);
        }

        propertyCards.sort(Comparator.comparingInt(PropertyCard::getId));

        return propertyCards;
    }

    public ArrayList<String> getPawnData(GameMode mode, GameTheme theme) throws FileNotFoundException {
        ArrayList<String> pawns = new ArrayList<>();

        File file = null;
        switch (theme){
            case vanilla -> file = new File("src/main/java/com/yolopoly/data/themes/Vanilla.json");
            case ankara -> file = new File("src/main/java/com/yolopoly/data/themes/Ankara.json");
            case bilkent -> file = new File("src/main/java/com/yolopoly/data/themes/Bilkent.json");
            case halloween -> file = new File("src/main/java/com/yolopoly/data/themes/Halloween.json");
        }
        String content = new Scanner(file).useDelimiter("\\Z").next();

        //Read JSON
        JSONObject jo = new JSONObject(content);
        var pawnJSON = jo.getJSONObject("pawns");
        var pawnKeys = pawnJSON.keys();
        ArrayList<String> dummyPawns = new ArrayList<>();
        while(pawnKeys.hasNext()){
            dummyPawns.add(pawnKeys.next());
        }
        Collections.sort(dummyPawns);
        for (String s : dummyPawns) {
            var pawnPath = pawnJSON.getString(s);
            pawns.add(pawnPath);
        }
        return pawns;
    }

    public ArrayList<Integer> getSettings() throws IOException {
        File file = new File("../settings.json");
        if(file.exists()){
            ArrayList<Integer> dataToReturn = new ArrayList<>();
            String content = new Scanner(file).useDelimiter("\\Z").next();
            JSONObject settings = new JSONObject(content);
            var audio = settings.getInt("audio");
            var music = settings.getInt("music");
            dataToReturn.add(audio);
            dataToReturn.add(music);
            return dataToReturn;
        }else{
            boolean isSuccess = file.createNewFile();
            if(isSuccess){
                System.out.println("Settings file created");
                JSONObject jo = new JSONObject();
                jo.put("audio", 50);
                jo.put("music", 50);
                FileWriter writer = new FileWriter("../settings.json");
                writer.write(jo.toString());
                writer.close();
                System.out.println("Settings created successfully");
                ArrayList<Integer> dataToReturn = new ArrayList<>();
                dataToReturn.add(50);
                dataToReturn.add(50);
                return dataToReturn;
            }else{
                System.out.println("Settings file can not be created");
                return null;
            }
        }
    }

    public boolean setSettings(int audio, int music) throws IOException {
        File file = new File("../settings.json");
        FileWriter writer = new FileWriter(file);
        writer.write("");
        JSONObject jo = new JSONObject();
        jo.put("audio", audio);
        jo.put("music", music);
        writer.write(jo.toString());
        writer.close();
        return true;
    }

    public boolean saveGame(InGameManager engine) throws IOException {
        File file = new File("../" + UUID.randomUUID().toString() + ".json");
        FileWriter writer = new FileWriter(file);
        writer.write("");
        JSONObject jo = new JSONObject();

        jo.put("board", engine.getBoard());
        jo.put("squares", engine.getBoard().getSquares());
        jo.put("chances", engine.getBoard().getChanceCards());
        jo.put("comms", engine.getBoard().getCommCards());
        jo.put("chat", engine.getChat());
        jo.put("curPlayerId", engine.getCurrentPlayerId());
        jo.put("log", engine.getLog());
        jo.put("players", engine.getPlayers());
        jo.put("propertyCards", engine.getBank().getPropertyCards());
        jo.put("state", engine.getState());
        writer.write(jo.toString());
        writer.close();
        System.out.println("Save successful!");
        return true;
    }

    public boolean loadGame(){
        //TODO: WAITING FOR SAVE GAME STATUS
        return false;
    }
}
