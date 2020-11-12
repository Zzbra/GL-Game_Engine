package amu.gl.equipe200.core;

import amu.gl.equipe200.gameworld.Settings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class GameScene extends GameWorld {
    private Image playerImage;
    private Image enemyImage;

    public GameScene(){
        super();
        Group root = new Group();

        // create layers
        Pane playfieldLayer = new Pane();
        Pane scoreLayer = new Pane();
        getLayers().put("playerfieldLayer", playfieldLayer);
        getLayers().put("scoreLayer", scoreLayer);

        root.getChildren().add( playfieldLayer);
        root.getChildren().add( scoreLayer);
        loadGame();
        this.setScene(new Scene( root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT));


    }
    private void loadGame() {
        try {
            playerImage = new Image(getClass().getResource("pacman.jpg").toExternalForm());
        }catch(Exception e){
            java.lang.System.err.println("Pas trouve");
        }
        try {
            enemyImage = new Image( getClass().getResource("ghostRed.jpg").toExternalForm());
        }catch(Exception e){
            java.lang.System.err.println("Pas trouve");
        }
    }



    public void setPlayerImage(Image playerImage) {
        this.playerImage = playerImage;
    }

    public void setEnemyImage(Image enemyImage) {
        this.enemyImage = enemyImage;
    }

    public Image getPlayerImage() {
        return playerImage;
    }

    public Image getEnemyImage() {
        return enemyImage;
    }
}
