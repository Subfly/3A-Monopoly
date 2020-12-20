package com.yolopoly.managers;

import com.yolopoly.service.EmailClient;

import javax.mail.MessagingException;
import java.io.File;
import java.util.ArrayList;

public class MainMenuManager {

    private static MainMenuManager outerEngine = null;

    //Variables
    private LobbyManager lobbyManager;
    private String hosterNick;

    //Constructor
    private MainMenuManager() {
    }

    public static MainMenuManager getInstance(){
        if(outerEngine == null){
            outerEngine = new MainMenuManager();
        }
        return outerEngine;
    }

    public void setHosterNick(String hosterNick){
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

    public ArrayList<Integer> getSettings(){
        ArrayList<Integer> values = new ArrayList<>();
        int backgroundVolume = MusicManager.getInstance().getVolume();
        values.add(backgroundVolume);
        int effectVolume = EffectManager.getInstance().getVolume();
        values.add(effectVolume);
        return values;
    }
    public boolean setSettings(int backgroundValue, int effectValue){
        if (backgroundValue != -1)
            MusicManager.getInstance().setVolume(backgroundValue);
        if (effectValue != -1)
            EffectManager.getInstance().setVolume(effectValue);
        return true;
    }

    public void handleFeedback(String playerEmail, String playerMessage) throws MessagingException {
        EmailClient.sendMailToPlayer(playerEmail);
        EmailClient.sendMailToSelf(playerEmail, playerMessage);
    }

    public File getCredits(){return null;}

    public boolean quit(){return false;}
}
