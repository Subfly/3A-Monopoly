package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static int width = 1920;
    private static int height = 1080;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../models/controllers/OuterController.fxml"));
        primaryStage.setTitle("Yolopoly");
        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, width , height));
        primaryStage.setFullScreenExitHint("");
        //primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);        disable esc
        primaryStage.show();
    }

    public static int getWidth(){
        return width;
    }

    public static int getHeight(){
        return height;
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }

}
