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

    public int getTotal(){
        return this.dice1 + this.dice2;
    }

    public int getDice1() {
        return dice1;
    }

    public void setDice1(int dice1) {
        this.dice1 = dice1;
    }

    public int getDice2() {
        return dice2;
    }

    public void setDice2(int dice2) {
        this.dice2 = dice2;
    }
}
