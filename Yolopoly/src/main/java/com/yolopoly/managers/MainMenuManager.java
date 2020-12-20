package com.yolopoly.managers;

import java.io.File;
import java.util.ArrayList;

public class MainMenuManager {

    private static MainMenuManager outerEngine = null;

    //Variables
    private LobbyManager lobbyManager;
    private String hosterNick;
    private boolean nickSet;

    //Constructor
    private MainMenuManager() {
        nickSet = false;
    }

    public boolean isNickSet() {
        return nickSet;
    }

    public void setNickSet(boolean nickSet) {
        this.nickSet = nickSet;
    }

    public static MainMenuManager getInstance(){
        if(outerEngine == null){
            outerEngine = new MainMenuManager();
        }
        return outerEngine;
    }

    public void setHosterNick(String hosterNick){
        nickSet = true;
        this.hosterNick = hosterNick;
    }

    public String getHosterNick(){
        return this.hosterNick;
    }

    //Functions
    public void singlePlayGame(){}

    //Sunucuya katılma
    public void multiPlayGame(){}

    public File getHelp(){return null;}
    public ArrayList<Integer> getSettings(){return null;}
    public boolean setSettings(){return false;}
    public File getCredits(){return null;}
    public boolean quit(){return false;}
}
