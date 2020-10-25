package models;

public class Dice {
    private int dice1;
    private int dice2;
    public Dice() {
        this.dice1 = 0;
        this.dice2 = 0;
    }

    public void roll(){
        this.dice1 = (int) (1 + Math.random() * 6);
        this.dice2 = (int) (1 + Math.random() * 6);
    }
}
