package sample;

import enumerations.GameMode;
import enumerations.GameTheme;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Square;
import models.cards.PropertyCard;
import storage.StorageUtil;

import java.io.IOException;

public class Main extends Application {

    private static int width = 1920;
    private static int height = 1080;

    static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        changeScreen("../models/controllers/OuterController.fxml");
    }

    public static void changeScreen(String source) throws Exception{
        Parent root = FXMLLoader.load(Main.class.getResource(source));
        primaryStage.setScene(new Scene(root, width , height));
        primaryStage.setTitle("Yolopoly");
        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);
        primaryStage.setFullScreenExitHint("");
        //primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        StorageUtil util = new StorageUtil();
        var data = util.getPropertyCards(GameMode.vanilla, GameTheme.vanilla);
        for(PropertyCard p : data){
            System.out.println(p.getId());
        }
        launch(args);
    }

}