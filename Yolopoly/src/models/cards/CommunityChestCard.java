package models.cards;

public class CommunityChestCard {
    //Variables
    private DrawableCard type;
    private String message;
    private int getMoney;
    private int looseMoney;
    private int goTo;
    private int shouldSave;
    private boolean affectAll;

    //Variables for bankman
    private double multiplier;

    //Constructor
    public CommunityChestCard(DrawableCard type, String message, int getMoney, int looseMoney, int goTo, int shouldSave, boolean affectAll, double multiplier) {
        this.type = type;
        this.message = message;
        this.getMoney = getMoney;
        this.looseMoney = looseMoney;
        this.goTo = goTo;
        this.shouldSave = shouldSave;
        this.multiplier = multiplier;
        this.affectAll = affectAll;
    }

    //Getters and Setters
    public DrawableCard getType() {
        return type;
    }

    public void setType(DrawableCard type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getGetMoney() {
        return getMoney;
    }

    public void setGetMoney(int getMoney) {
        this.getMoney = getMoney;
    }

    public int getLooseMoney() {
        return looseMoney;
    }

    public void setLooseMoney(int looseMoney) {
        this.looseMoney = looseMoney;
    }

    public int getGoTo() {
        return goTo;
    }

    public void setGoTo(int goTo) {
        this.goTo = goTo;
    }

    public int getShouldSave() {
        return shouldSave;
    }

    public void setShouldSave(int shouldSave) {
        this.shouldSave = shouldSave;
    }
}
