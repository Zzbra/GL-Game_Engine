package amu.gl.equipe200.core;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class GameWorldMaker {
    private Scene scene;

    public GameWorldMaker(){
        this.scene = new Scene(new Group());
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
        this.scene = new Scene(panel, 640, 480);
        scene.getStylesheets().add("MainMenuStyleSheet.css");
    }

    public static GameWorld MakeMenuScene(){
        return new MainMenuScene();
    }

    public static GameWorld MakeGameScene(){
        return new GameScene();
    }

    public Scene getScene() {
        return scene;
    }
}
