package amu.gl.equipe200.pacman.menues;

import amu.gl.equipe200.core.Settings;
import amu.gl.equipe200.graphicsengine.MenuInterface;

import amu.gl.equipe200.pacman.PacmanApp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class LoseMenu
        implements MenuInterface {

    private static LoseMenu INSTANCE;
    private Scene loseMenuScene;
    private Pane root;
    private PacmanApp linkedApp;
    public final Button restartGame, score;

    private LoseMenu(PacmanApp app) {
        this.linkedApp = app;

        restartGame = new Button("Retart Game");
        restartGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                linkedApp.loadGame();
            }
        });

        score = new Button("Score");
        Image img = new Image("menuScreen.jpg");

        ImageView imgView = new ImageView(img);
        VBox vBox = new VBox(imgView,restartGame, score);
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(50,10,50,10));
        StackPane panel = new StackPane(vBox);
        panel.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        loseMenuScene = new Scene(panel, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        loseMenuScene.getStylesheets().add("MainMenuStyleSheet.css");

        this.root = panel;
        this.loseMenuScene = loseMenuScene;
    }

    public static LoseMenu getInstance(PacmanApp app) {
        if (INSTANCE == null) INSTANCE = new LoseMenu(app);
        return INSTANCE;
    }

    public Scene getScene() { return loseMenuScene; }

    @Override
    public Pane getRoot() { return this.root; }
}
