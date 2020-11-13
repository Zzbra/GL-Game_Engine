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

public class MainMenuScene extends GameWorld {


    public MainMenuScene(){
        super();
        Scene mainMenuScene;
        Button startGameButton = new Button("Start Game");
        this.getUIComponents().put("startGameButton", startGameButton);
        Button controlButton = new Button("Controls");
        this.getUIComponents().put("controlButton", controlButton);
        Button aboutButton = new Button("About");
        this.getUIComponents().put("aboutButton", aboutButton);
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
        super.setScene(mainMenuScene);
    }

}
