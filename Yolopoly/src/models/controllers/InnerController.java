package models.controllers;

import javafx.stage.Stage;

public class InnerController {

    Stage primaryStage;

    public InnerController() {
        this.primaryStage = null;
    }

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

}
