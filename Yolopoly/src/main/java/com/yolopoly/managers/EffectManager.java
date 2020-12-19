package com.yolopoly.managers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class EffectManager {
    private static EffectManager instance;
    private int volume;

    private EffectManager() {
        volume = 50;
    }

    public static synchronized EffectManager getInstance() {
        if (instance == null) {
            instance = new EffectManager();
        }
        return instance;
    }

    public static synchronized void clearInstance() {
        instance = new EffectManager();
    }

    public void playMoneyEffect() {
        MediaPlayer mediaPlayer;
        Media media;
        String path = new File("src/main/resources/sounds/money-effect.wav").getAbsolutePath();
        media = new Media (new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume((double) volume / 100);
        // Media View??
        mediaPlayer.play();
    }

    public void playRollEffect() {
        MediaPlayer mediaPlayer;
        Media media;
        String path = new File("src/main/resources/sounds/roll-dice-effect.wav").getAbsolutePath();
        media = new Media (new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume((double) volume / 100);
        // Media View??
        mediaPlayer.play();
    }

    public void playJailEffect() {
        MediaPlayer mediaPlayer;
        Media media;
        String path = new File("src/main/resources/sounds/go-to-jail-effect.wav").getAbsolutePath();
        media = new Media (new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume((double) volume / 100);
        // Media View??
        mediaPlayer.play();
    }

    public void playDrawCardEffect() {
        MediaPlayer mediaPlayer;
        Media media;
        String path = new File("src/main/resources/sounds/draw-card-effect.wav").getAbsolutePath();
        media = new Media (new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume((double) volume / 100);
        // Media View??
        mediaPlayer.play();
    }

    public void playFreeParkingEffect() {
        MediaPlayer mediaPlayer;
        Media media;
        String path = new File("src/main/resources/sounds/free-parking-effect.wav").getAbsolutePath();
        media = new Media (new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume((double) volume / 100);
        // Media View??
        mediaPlayer.play();
    }

    public void playClickEffect() {
        MediaPlayer mediaPlayer;
        Media media;
        String path = new File("src/main/resources/sounds/click-effect.wav").getAbsolutePath();
        media = new Media (new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume((double) volume / 100);
        // Media View??
        mediaPlayer.play();
    }

    public void playBankruptEffect() {
        MediaPlayer mediaPlayer;
        Media media;
        String path = new File("src/main/resources/sounds/bankrupt-effect.wav").getAbsolutePath();
        media = new Media (new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume((double) volume / 100);
        // Media View??
        mediaPlayer.play();
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
