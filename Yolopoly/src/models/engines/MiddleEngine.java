package models.engines;

import enumerations.GameMode;
import enumerations.GameTheme;
import enumerations.Pawn;
import models.Bank;
import models.Board;
import models.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class MiddleEngine {
    private InnerEngine innerEngine;
    //Sunucu kurma/Lobi bu classta
    private boolean isOnline;
    private GameMode gameMode;
    private GameTheme gameTheme;
    private String hosterNick;
    private ArrayList<Integer> botIds;
    private ArrayList<Pawn> pawns;
    private ArrayList<Player> players;

    private ArrayList<Boolean> isAllReady;
    private int playerCount;
    private int maxPlayerCount;

    //Lobi Ayarları
    private String lobbyPassword;

    public MiddleEngine(boolean isOnline, String hosterNick) {
        this.isOnline = isOnline;
        this.hosterNick = hosterNick;
        this.playerCount = 1;
        this.isAllReady = new ArrayList<>();
        isAllReady.add(false);
        for (int i = 0; i < 8; i++) {
            botIds.add(i);
        }
    }

    //Functions
    // Starts the game
    public void initializeGame(){
        innerEngine = new InnerEngine(false);
        innerEngine.setBank(new Bank(gameTheme, gameMode));
        innerEngine.setBoard(new Board(gameTheme));
        innerEngine.setChat(new ArrayList<String>());
        innerEngine.setLog(new ArrayList<String>());
    }

    public File getSettings(){return null;}
    public void setSettings(){}

    public boolean kickPlayer(String nick) {
        /*
        int index = playerNicks.indexOf(nick);
        if (index != -1) {
            humanitySettings.remove(index);

            return true;
        }
        return false;

         */
        return false;
    }

    public boolean addBot(){
        /*
        if (playerCount < 8 && playerCount < maxPlayerCount) {
            humanitySettings.add(false);
            int num = botNum.get(0);
            botNum.remove(num);
            botNum.add(num);
            playerNicks.add(("Bot" + num));
            playerCount++;
            return true;
        }

         */
        return false;
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

    public String getHosterNick() {
        return hosterNick;
    }

    public void setHosterNick(String hosterNick) {
        this.hosterNick = hosterNick;
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

}
