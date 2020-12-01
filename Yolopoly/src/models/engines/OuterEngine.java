package models.engines;

import java.io.File;
import java.util.ArrayList;

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
    public ArrayList<Integer> getSettings(){return null;}
    public boolean setSettings(){return false;}
    public File getCredits(){return null;}
    public boolean quit(){return false;}
}
