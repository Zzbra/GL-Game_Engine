package amu.gl.equipe200.core;

import amu.gl.equipe200.entity.BaseEntity;
import amu.gl.equipe200.entity.Enemy;
import amu.gl.equipe200.entity.Player;
import amu.gl.equipe200.gameworld.Settings;

import amu.gl.equipe200.system.CollisionSystem;
import amu.gl.equipe200.system.ASystem;
import amu.gl.equipe200.system.GraphicalEngine;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

import java.util.*;

public class PacmanApp extends Application {

    private Random rnd = new Random();

    // Scene manager ?
//    private Pane playfieldLayer;
//    private Pane scoreLayer;

    // Peut aller dans une factory
    private Image playerImage;
    private Image enemyImage;

    // Une seule liste d'entité
    //private List<Player> players = new ArrayList<>();
    //private List<Enemy> enemies = new ArrayList<>();

    // Controller d'UI ?


    private Scene scene;
    private HashMap<String, ASystem> systems;

    private GraphicalEngine graphicalEngine;
    private GameWorld mainMenuScene, gameScene;
    @Override
    public void start(Stage primaryStage) {
        /*** Création des moteurs ***/
        this.graphicalEngine = new GraphicalEngine(primaryStage);
        this.mainMenuScene = GameWorldMaker.MakeMenuScene();
        this.gameScene = GameWorldMaker.MakeGameScene();

        systems = new HashMap<String, ASystem>();
        systems.put("Collisions", new CollisionSystem());

        graphicalEngine.loadScene(mainMenuScene.getScene());

        // Définition du callback de startGameButton
        Button startButton = (Button)mainMenuScene.getUIComponents().get("startGameButton");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                graphicalEngine.loadScene(gameScene.getScene());
            }
        });


        createPlayers();


        AnimationTimer gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {

                // player input
                //players.forEach(amu.gl.equipe200.entity -> amu.gl.equipe200.entity.processInput());

                // add random enemies
                spawnEnemies( true);

                // movement
                gameScene.getPlayers().forEach(entity -> entity.move());
                gameScene.getEnemies().forEach(entity -> entity.move());

                // update collisions
                //updateCollisions();
                systems.get("Collisions").update();

                // update amu.gl.equipe200.entity in scene
                gameScene.getPlayers().forEach(entity -> entity.updateUI());
                gameScene.getEnemies().forEach(entity -> entity.updateUI());

                // check if amu.gl.equipe200.entity can be removed
                gameScene.getEnemies().forEach(entity -> entity.checkRemovability());

                // remove removables from list, layer, etc
                removeEntity( gameScene.getEnemies());

                // update score, health, etc
                updateScore();
            }

        };
        gameLoop.start();

    }




    private void createPlayers() {

        Image image = graphicalEngine.getImages().get("playerImage");

        // center horizontally, position at 70% vertically
        double x = (Settings.SCENE_WIDTH - image.getWidth()) / 2.0;
        double y = Settings.SCENE_HEIGHT * 0.7;

        // create player
        Player player = new Player(gameScene.getLayers().get("playerfieldLayer"), image, x, y, 0, 0, 0, 0, Settings.PLAYER_SHIP_HEALTH, 0, Settings.PLAYER_SHIP_SPEED);

        // register player
        gameScene.getPlayers().add( player);
        systems.get("Collisions").addEntity(player);

    }

    private void spawnEnemies( boolean random) {

        if( random && rnd.nextInt(Settings.ENEMY_SPAWN_RANDOMNESS) != 0) {
            return;
        }

        // image
        Image image = graphicalEngine.getImages().get("enemyImage");

        // random speed
        double speed = rnd.nextDouble() * 1.0 + 2.0;

        // x position range: enemy is always fully inside the screen, no part of it is outside
        // y position: right on top of the view, so that it becomes visible with the next game iteration
        double x = rnd.nextDouble() * (Settings.SCENE_WIDTH - image.getWidth());
        double y = -image.getHeight();

        // create a sprite
        Enemy enemy = new Enemy( gameScene.getLayers().get("playerfieldLayer"), image, x, y, 0, 0, speed, 0, 1,1);

        // manage sprite
        gameScene.getEnemies().add( enemy);
        systems.get("Collisions").addEntity(enemy);
    }

    private void removeEntity(List<? extends BaseEntity> spriteList) {
        Iterator<? extends BaseEntity> iter = spriteList.iterator();
        while( iter.hasNext()) {
            BaseEntity sprite = iter.next();

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
        Text collisionText = (Text)gameScene.getUIComponents().get("collisionText");
        if( gameScene.getPlayers().get(0).hasCollisions()) {
            collisionText.setText("Collision");
        } else {
            collisionText.setText("");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}