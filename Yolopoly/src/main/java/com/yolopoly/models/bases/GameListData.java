package com.yolopoly.models.bases;

import com.yolopoly.enumerations.GameMode;
import com.yolopoly.enumerations.GameTheme;

public class GameListData {
    private String admin;
    private GameMode mode;
    private GameTheme theme;
    private int playerCount;
    private String password;

    public GameListData() {}

    public GameListData(String admin, GameMode mode, GameTheme theme, int playerCount, String password) {
        this.admin = admin;
        this.mode = mode;
        this.theme = theme;
        this.playerCount = playerCount;
        this.password = password;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public GameMode getMode() {
        return mode;
    }

    public void setMode(GameMode mode) {
        this.mode = mode;
    }

    public GameTheme getTheme() {
        return theme;
    }

    public void setTheme(GameTheme theme) {
        this.theme = theme;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "GameListData{" +
                "admin='" + admin + '\'' +
                ", mode=" + mode +
                ", theme=" + theme +
                ", playerCount=" + playerCount +
                ", password='" + password + '\'' +
                '}';
    }
}