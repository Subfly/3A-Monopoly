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
    public ArrayList<Integer> getSettings(){return null;}
    public boolean setSettings(){return false;}
    public void handleFeedback(String playerEmail, String playerMessage) throws MessagingException {
        EmailClient.sendMailToPlayer(playerEmail);
        EmailClient.sendMailToSelf(playerEmail, playerMessage);
    }
    public File getCredits(){return null;}
    public boolean quit(){return false;}
}
