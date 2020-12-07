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
    private Scene winMenuScene;
    private Pane root;
    private PacmanApp linkedApp;
    public final Button gotoMainMenu;

    private LoseMenu(PacmanApp app) {
        this.linkedApp = app;

        gotoMainMenu = new Button("Return To the Menu");
        gotoMainMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                linkedApp.loadMenu(MainMenu.getInstance(app));
            }
        });

        Image img = new Image("LoseLabel.png");
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(Settings.SCENE_WIDTH * 2 / 3);
        imgView.setFitHeight(Settings.SCENE_HEIGHT * 20 / 100);
        VBox vBox = new VBox(imgView, gotoMainMenu);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(50,10,50,10));
        StackPane panel = new StackPane(vBox);
        panel.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        winMenuScene = new Scene(panel, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        winMenuScene.getStylesheets().add("MainMenuStyleSheet.css");

        this.root = panel;
        this.winMenuScene = winMenuScene;
    }

    public static LoseMenu getInstance(PacmanApp app) {
        if (INSTANCE == null) INSTANCE = new LoseMenu(app);
        return INSTANCE;
    }

    public Scene getScene() { return winMenuScene; }

    @Override
    public Pane getRoot() { return this.root; }
}
