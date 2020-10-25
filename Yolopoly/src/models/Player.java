package models;

public class Player {
    private String name;
    private Pawn pawn;
    private int money;
    private boolean isInJail;
    private boolean isThreeTimesDoubled;

    public Player(String name) {
        this.name = name;
        this.isInJail = false;
        this.isThreeTimesDoubled = false;
        this.money = 0;
    }
}
