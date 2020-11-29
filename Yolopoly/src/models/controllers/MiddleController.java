package models.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static sample.Main.changeScreen;


public class MiddleController {

    @FXML
    ImageView size2, size3, size4, size5, size6, size7, size8;
    @FXML
    ImageView mode_vanilla, mode_bankman;
    @FXML
    ImageView theme_vanilla, theme_bilkent, theme_ankara, theme_halloween;

    //Mode Buttons
    @FXML public void mode_vanilla() { setModeImage(0);}
    @FXML public void mode_bankman() { setModeImage(1);}

    private void setModeImage(int id){
        if (id == 0){
            mode_vanilla.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/vanilla-green.png")));
            mode_bankman.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/bankman-red.png")));
        }
        else if(id == 1){
            mode_vanilla.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/vanilla-red.png")));
            mode_bankman.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/bankman-green.png")));
        }
    }

    //Theme Buttons
    @FXML public void theme_vanilla() { setTehemeImage(0);}
    @FXML public void theme_bilkent() { setTehemeImage(1);}
    @FXML public void theme_ankara() { setTehemeImage(2);}
    @FXML public void theme_halloween() { setTehemeImage(3);}

    private void setTehemeImage(int id){
        if (id == 0){
            theme_vanilla.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/vanilla-green.png")));
            theme_bilkent.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/bilkent-red.png")));
            theme_ankara.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/ankara-red.png")));
            theme_halloween.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/halloween-red.png")));
        }
        else if(id == 1){
            theme_vanilla.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/vanilla-red.png")));
            theme_bilkent.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/bilkent-green.png")));
            theme_ankara.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/ankara-red.png")));
            theme_halloween.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/halloween-red.png")));
        }
        else if(id == 2){
            theme_vanilla.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/vanilla-red.png")));
            theme_bilkent.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/bilkent-red.png")));
            theme_ankara.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/ankara-green.png")));
            theme_halloween.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/halloween-red.png")));
        }
        else if(id == 3){
            theme_vanilla.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/vanilla-red.png")));
            theme_bilkent.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/bilkent-red.png")));
            theme_ankara.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/ankara-red.png")));
            theme_halloween.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/halloween-green.png")));
        }
    }

    //Lobby Size Buttons
    @FXML public void size_2(){ setSizeImages(2); }
    @FXML public void size_3(){ setSizeImages(3); }
    @FXML public void size_4(){ setSizeImages(4); }
    @FXML public void size_5(){ setSizeImages(5); }
    @FXML public void size_6(){ setSizeImages(6); }
    @FXML public void size_7(){ setSizeImages(7); }
    @FXML public void size_8(){ setSizeImages(8); }

    private void setSizeImages(int id){
        size2.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/2-red.png")));
        size3.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/3-red.png")));
        size4.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/4-red.png")));
        size5.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/5-red.png")));
        size6.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/6-red.png")));
        size7.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/7-red.png")));
        size8.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/8-red.png")));
        if(id == 2){
            size2.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/2-green.png")));
        }
        else if (id == 3){
            size3.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/3-green.png")));
        }
        else if (id == 4){
            size4.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/4-green.png")));
        }
        else if (id == 5){
            size5.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/5-green.png")));
        }
        else if (id == 6){
            size6.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/6-green.png")));
        }
        else if (id == 7){
            size7.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/7-green.png")));
        }
        else if (id == 8){
            size8.setImage(new Image(getClass().getResourceAsStream("sources/lobby-settings/8-green.png")));
        }
    }

    @FXML
    public void closeButtonPressed() throws Exception{
        changeScreen("../models/controllers/OuterController.fxml");
    }

    @FXML
    public void readyButtonPressed() throws Exception{
        changeScreen("../models/controllers/InnerController.fxml");
    }


}
