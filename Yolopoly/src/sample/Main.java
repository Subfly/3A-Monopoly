package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import models.engines.InnerEngine;
import models.engines.MiddleEngine;
import models.engines.OuterEngine;

public class Main extends Application {

    private static int width = 1920;
    private static int height = 1080;

    static Stage primaryStage;

    static OuterEngine oe;
    static MiddleEngine me;
    static InnerEngine ie;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Main.oe = new OuterEngine();
        Main.me = new MiddleEngine();
        Main.ie = new InnerEngine();

        Main.primaryStage = primaryStage;
        changeScreen("../models/controllers/OuterController.fxml");
    }

    public static OuterEngine getOuterEngine() {
        return oe;
    }

    public static MiddleEngine getMiddleEngine() {
        return me;
    }

    public static InnerEngine getInnerEngine() {
        return ie;
    }

    public static void changeScreen(String source) throws Exception{
        Parent root = FXMLLoader.load(Main.class.getResource(source));
        primaryStage.setScene(new Scene(root, width , height));
        primaryStage.setTitle("Yolopoly");
        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}