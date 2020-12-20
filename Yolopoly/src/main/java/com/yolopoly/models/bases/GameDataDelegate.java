package com.yolopoly.models.bases;

import com.yolopoly.enumerations.GameMode;
import com.yolopoly.enumerations.GameState;
import com.yolopoly.enumerations.GameTheme;

import java.util.ArrayList;
import java.util.HashMap;

public class GameDataDelegate {

    private ArrayList<String> chat;
    private ArrayList<String> log;
    private ArrayList<Player> players;
    private Dice dice;
    private Board board;
    private Bank bank;
    private int currentPlayerId;
    private GameState state;
    private GameMode gameMode;

    //Auction Related
    private String currentHighestBidName;
    private int currentBid;
    private int auctionPropertyIndex;
    private int currentPlayerAuctioning;
    private ArrayList<Player> participants;
    private GameTheme theme;


    private HashMap<Integer, HashMap<Integer, Integer>> brokenPlayersMoneyHash;

    public GameDataDelegate(){}

    public ArrayList<String> getChat() {
        return chat;
    }

    public void setChat(ArrayList<String> chat) {
        this.chat = chat;
    }

    public ArrayList<String> getLog() {
        return log;
    }

    public void setLog(ArrayList<String> log) {
        this.log = log;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(int currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public String getCurrentHighestBidName() {
        return currentHighestBidName;
    }

    public void setCurrentHighestBidName(String currentHighestBidName) {
        this.currentHighestBidName = currentHighestBidName;
    }

    public int getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(int currentBid) {
        this.currentBid = currentBid;
    }

    public int getAuctionPropertyIndex() {
        return auctionPropertyIndex;
    }

    public void setAuctionPropertyIndex(int auctionPropertyIndex) {
        this.auctionPropertyIndex = auctionPropertyIndex;
    }

    public int getCurrentPlayerAuctioning() {
        return currentPlayerAuctioning;
    }

    public void setCurrentPlayerAuctioning(int currentPlayerAuctioning) {
        this.currentPlayerAuctioning = currentPlayerAuctioning;
    }

    public ArrayList<Player> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Player> participants) {
        this.participants = participants;
    }

    public GameTheme getTheme() {
        return theme;
    }

    public void setTheme(GameTheme theme) {
        this.theme = theme;
    }

    public HashMap<Integer, HashMap<Integer, Integer>> getBrokenPlayersMoneyHash() {
        return brokenPlayersMoneyHash;
    }

    public void setBrokenPlayersMoneyHash(HashMap<Integer, HashMap<Integer, Integer>> brokenPlayersMoneyHash) {
        this.brokenPlayersMoneyHash = brokenPlayersMoneyHash;
    }
}
