package com.yolopoly;

import com.yolopoly.managers.MusicManager;
import com.yolopoly.models.bases.GameListData;
import com.yolopoly.storage.FirebaseUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Main extends Application {

    static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        changeScreen("src/main/resources/scenes/OuterController.fxml");
    }

    public static void changeScreen(String source) throws Exception{
        URL url = new File(source).toURI().toURL();
        Parent root = FXMLLoader.load(url);
        int width = 1920;
        int height = 1080;
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.setTitle("Yolopoly");
        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.show();
    }

    public static void main(String[] args) {
        MusicManager.getInstance();
        launch(args);
    }

}