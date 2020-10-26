package models.engines;

import java.io.File;

public class OuterEngine {
    //Variables
    private MiddleEngine middleEngine;
    private String hosterNick;

    //Constructor
    public OuterEngine() {
    }

    //Functions
    public void singlePlayGame(){}

    //Sunucuya katılma
    public void multiPlayGame(){}

    public File getHelp(){return null;}
    public File getSettings(){return null;}
    public void setSettings(){}
    public File getCredits(){return null;}
    public boolean quit(){return false;}
}
