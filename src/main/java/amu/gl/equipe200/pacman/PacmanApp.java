package amu.gl.equipe200.pacman;

import amu.gl.equipe200.core.GameApp;
import amu.gl.equipe200.core.GameSettings;

import amu.gl.equipe200.pacman.Entities.Enemy;
import amu.gl.equipe200.pacman.Entities.Player;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

import java.util.*;

public class PacmanApp extends GameApp {

    Random rnd = new Random();

    // Scene manager ?
    Pane playfieldLayer;
    Pane scoreLayer;

    // Peut aller dans une factory
    Image playerImage;
    Image enemyImage;

    // Une seule liste d'entité
    List<Player> players = new ArrayList<>();
    List<Enemy> enemies = new ArrayList<>();

    // Controller d'UI ?
    Text collisionText = new Text();

    Scene scene;
    HashMap<String, SystemOld> systems;


    @Override
    public void initApp(Stage primaryStage) {
        Group root = new Group();

        // create layers
        playfieldLayer = new Pane();
        scoreLayer = new Pane();

        root.getChildren().add( playfieldLayer);
        root.getChildren().add( scoreLayer);

        scene = new Scene( root, GameSettings.SCENE_WIDTH, GameSettings.SCENE_HEIGHT);

        primaryStage.setScene( scene);
        primaryStage.show();

        loadGame();

        createScoreLayer();
        createPlayers();

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

    private void createScoreLayer() {
        collisionText.setFont( Font.font( null, FontWeight.BOLD, 64));
        collisionText.setStroke(Color.BLACK);
        collisionText.setFill(Color.RED);

        scoreLayer.getChildren().add( collisionText);

        // TODO: quick-hack to ensure the text is centered; usually you don't have that; instead you have a health bar on top
        collisionText.setText("Collision");
        double x = (GameSettings.SCENE_WIDTH - collisionText.getBoundsInLocal().getWidth()) / 2;
        double y = (GameSettings.SCENE_HEIGHT - collisionText.getBoundsInLocal().getHeight()) / 2;
        collisionText.relocate(x, y);
        collisionText.setText("");

        collisionText.setBoundsType(TextBoundsType.VISUAL);
    }
    private void createPlayers() {
        Image image = playerImage;

        // center horizontally, position at 70% vertically
        double x = (GameSettings.SCENE_WIDTH - image.getWidth()) / 2.0;
        double y = GameSettings.SCENE_HEIGHT * 0.7;

        // create player
        Player player = new Player(playfieldLayer, image, x, y, 0, 0, 0, 0, GameSettings.PLAYER_SHIP_HEALTH, 0, GameSettings.PLAYER_SHIP_SPEED);

        // register player
        players.add( player);
        systems.get("Collisions").addEntity(player);

    }

    private void spawnEnemies( boolean random) {

        if( random && rnd.nextInt(GameSettings.ENEMY_SPAWN_RANDOMNESS) != 0) {
            return;
        }

        // image
        Image image = enemyImage;

        // random speed
        double speed = rnd.nextDouble() * 1.0 + 2.0;

        // x position range: enemy is always fully inside the screen, no part of it is outside
        // y position: right on top of the view, so that it becomes visible with the next game iteration
        double x = rnd.nextDouble() * (GameSettings.SCENE_WIDTH - image.getWidth());
        double y = -image.getHeight();

        // create a sprite
        Enemy enemy = new Enemy( playfieldLayer, image, x, y, 0, 0, speed, 0, 1,1);

        // manage sprite
        enemies.add( enemy);
        systems.get("Collisions").addEntity(enemy);
    }

    private void removeEntity(List<? extends EntityOld> spriteList) {
        Iterator<? extends EntityOld> iter = spriteList.iterator();
        while( iter.hasNext()) {
            EntityOld sprite = iter.next();

            if( sprite.isRemovable()) {

                // remove from layer
                sprite.removeFromLayer();
                systems.get("Collisions").removeEntity(sprite);

                // remove from list
                iter.remove();
            }
        }
    }


    private void updateScore() {
        if( players.get(0).hasCollisions()) {
            collisionText.setText("Collision");
        } else {
            collisionText.setText("");
        }
    }
}