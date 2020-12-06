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


public class MainMenu
        implements MenuInterface {

    private static MainMenu INSTANCE;
    private Scene mainMenuScene;
    private Pane root;
    private PacmanApp linkedApp;
    public final Button startGameButton, controlButton, aboutButton;

    private MainMenu(PacmanApp app) {
        this.linkedApp = app;

        startGameButton = new Button("Start Game");
        startGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                linkedApp.loadGame();
            }
        });

        controlButton = new Button("Controls");
        aboutButton = new Button("About");

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

        this.root = panel;
        this.mainMenuScene = mainMenuScene;
    }

    public static MainMenu getInstance(PacmanApp app) {
        if (INSTANCE == null) INSTANCE = new MainMenu(app);
        return INSTANCE;
    }

    public Scene getScene() { return mainMenuScene; }

    @Override
    public Pane getRoot() { return this.root; }
}
