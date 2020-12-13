package managers;

import enumerations.GameMode;
import enumerations.GameTheme;
import enumerations.Pawn;
import models.bases.Player;

import java.io.File;
import java.util.ArrayList;

public class LobbyManager {

    private static LobbyManager middleEngine = null;

    //Sunucu kurma/Lobi bu classta
    private boolean isOnline;
    private GameMode gameMode;
    private GameTheme gameTheme;
    private Player admin;
    final private String adminDummyName = "Dummy Admin";
    private ArrayList<Integer> botIds;
    private ArrayList<Pawn> pawns;

    private ArrayList<Player> playerArrayList;
    private ArrayList<Boolean> isAllReady;
    private int playerCount;
    private int maxPlayerCount;
    private int botCount;

    //Lobi Ayarları
    private String lobbyPassword;

    private LobbyManager() {
        this.playerArrayList = new ArrayList<>();
        this.admin = new Player(adminDummyName, true);
        this.playerArrayList.add(this.admin);
        this.playerCount = 1;
        this.maxPlayerCount = 2;
        this.botCount = 0;
    }

    public static LobbyManager getInstance(){
        if(middleEngine == null){
            middleEngine = new LobbyManager();
        }
        return middleEngine;
    }

    public ArrayList<Player> getPlayerArrayList() {
        return playerArrayList;
    }

    public File getSettings(){return null;}
    public void setSettings(){}

    public boolean kickPlayer(int index) {
        if (playerArrayList.remove(index) != null){
            playerCount--;
            return true;
        }
        else
            return false;
    }

    public boolean addBot(){
        
        if (playerCount < 8 && playerCount < maxPlayerCount) {
            playerArrayList.add(new Player("Bot " + (botCount + 1), false));

            playerCount++;
            botCount++;
            return true;
        }
        return false;
    }



    public boolean deleteBot(){
        if (botCount > 0) {

            playerCount--;
            botCount--;
            playerArrayList.remove(playerCount);
            return true;
        }
        return false;
    }

    public String deneme(){
        return "Mod = " + gameMode + ", Theme = " + gameTheme + " Player Count = " + playerCount;
    }

    //Getters and Setters
    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public GameTheme getGameTheme() {
        return gameTheme;
    }

    public void setGameTheme(GameTheme gameTheme) {
        this.gameTheme = gameTheme;
    }

    public String getLobbyPassword() {
        return lobbyPassword;
    }

    public void setLobbyPassword(String lobbyPassword) {
        this.lobbyPassword = lobbyPassword;
    }

    public ArrayList<Pawn> getPawns() {
        return pawns;
    }

    public void setPawns(ArrayList<Pawn> pawns) {
        this.pawns = pawns;
    }

    public ArrayList<Boolean> getIsAllReady() {
        return isAllReady;
    }

    public void setIsAllReady(ArrayList<Boolean> isAllReady) {
        this.isAllReady = isAllReady;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public int getMaxPlayerCount() {
        return maxPlayerCount;
    }

    public void setMaxPlayerCount(int maxPlayerCount) {
        this.maxPlayerCount = maxPlayerCount;
    }

    public Player getAdmin() {
        return admin;
    }

    public void setAdmin(Player admin) {
        this.admin = admin;
    }
}
