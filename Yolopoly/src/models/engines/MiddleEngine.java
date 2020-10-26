package models.engines;

import enumerations.GameMode;
import enumerations.GameTheme;
import enumerations.Pawn;

import java.io.File;
import java.util.ArrayList;

public class MiddleEngine {
    private InnerEngine innerEngine;
    //Sunucu kurma/Lobi bu classta
    private boolean isOnline;
    private GameMode gameMode;
    private GameTheme gameTheme;
    private String hosterNick;
    private ArrayList<Boolean> humanitySettings;
    private ArrayList<Pawn> pawns;
    private ArrayList<Boolean> isAllReady;
    private int maxPlayerCount;

    //Lobi Ayarları
    private String lobbyPassword;
    private ArrayList<String> playerNicks;

    public MiddleEngine(boolean isOnline, String hosterNick) {
        this.isOnline = isOnline;
        this.hosterNick = hosterNick;
    }

    //Functions
    public void initializeGame(){}
    public File getSettings(){return null;}
    public void setSettings(){}
    public boolean kickPlayer(String nick){return false;}
    public boolean addBot(){return false;}

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

    public ArrayList<String> getPlayerNicks() {
        return playerNicks;
    }

    public void setPlayerNicks(ArrayList<String> playerNicks) {
        this.playerNicks = playerNicks;
    }

    public ArrayList<Boolean> getHumanitySettings() {
        return humanitySettings;
    }

    public void setHumanitySettings(ArrayList<Boolean> humanitySettings) {
        this.humanitySettings = humanitySettings;
    }

    public ArrayList<Pawn> getPawns() {
        return pawns;
    }

    public void setPawns(ArrayList<Pawn> pawns) {
        this.pawns = pawns;
    }
}
