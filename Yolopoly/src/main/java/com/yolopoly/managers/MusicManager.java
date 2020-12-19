package com.yolopoly.managers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class MusicManager {
    private MediaPlayer mediaPlayer;
    private Media media;
    private static MusicManager instance;

    private MusicManager() {
        String path = new File("src/main/resources/sounds/background-music.wav").getAbsolutePath();
        media = new Media (new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.05);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        mediaPlayer.play();
    }

    public static synchronized MusicManager getInstance() {
        if (instance == null) {
            instance = new MusicManager();
        }
        return instance;
    }

    public static synchronized void clearInstance() {
        instance = new MusicManager();
    }

    public int getVolume() {
        return (int) (instance.mediaPlayer.getVolume() * 100);
    }

    public void setVolume(int volume) {
        instance.mediaPlayer.setVolume(( (double) (volume) / 100));
    }
}
