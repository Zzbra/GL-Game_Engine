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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

public class GameScene {
    private Image playerImage;
    private Image enemyImage;
    private Text collisionText;

    public GameScene(){
//        super();
//        Group root = new Group();
//        collisionText = new Text();
//        getUIComponents().put("collisionText", collisionText);
//
//        // create layers
//        Pane playfieldLayer = new Pane();
//        Pane scoreLayer = new Pane();
//        getLayers().put("playerfieldLayer", playfieldLayer);
//        getLayers().put("scoreLayer", scoreLayer);
//        createScoreLayer();
//        root.getChildren().add( playfieldLayer);
//        root.getChildren().add( scoreLayer);
//        this.setScene(new Scene( root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT));


    }


    private void createScoreLayer() {
//        collisionText.setFont( Font.font( null, FontWeight.BOLD, 64));
//        collisionText.setStroke(Color.BLACK);
//        collisionText.setFill(Color.RED);
//
//        getLayers().get("playerfieldLayer").getChildren().add( collisionText);
//
//        // TODO: quick-hack to ensure the text is centered; usually you don't have that; instead you have a health bar on top
//        collisionText.setText("Collision");
//        double x = (Settings.SCENE_WIDTH - collisionText.getBoundsInLocal().getWidth()) / 2;
//        double y = (Settings.SCENE_HEIGHT - collisionText.getBoundsInLocal().getHeight()) / 2;
//        collisionText.relocate(x, y);
//        collisionText.setText("");
//
//        collisionText.setBoundsType(TextBoundsType.VISUAL);
    }


}
