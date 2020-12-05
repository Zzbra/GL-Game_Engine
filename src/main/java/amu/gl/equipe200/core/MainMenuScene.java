package amu.gl.equipe200.core;

import amu.gl.equipe200.gameworld.Settings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MainMenuScene {

    public static Scene MainMenuScene(){
        System.out.println("Hello! ");
        Scene mainMenuScene;
        Button startGameButton = new Button("Start Game");
        Button controlButton = new Button("Controls");
        Button aboutButton = new Button("About");
        Image img = new Image("menuScreen.jpg");
        ImageView imgView = new ImageView(img);
        VBox vBox = new VBox(imgView,startGameButton, controlButton, aboutButton);
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(50,10,50,10));
        StackPane panel = new StackPane(vBox);
        panel.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        mainMenuScene = new Scene(panel, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        mainMenuScene.getStylesheets().add("MainMenuStyleSheet.css");
        return mainMenuScene;
    }

}
