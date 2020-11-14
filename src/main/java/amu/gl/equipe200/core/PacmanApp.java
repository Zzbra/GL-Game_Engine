package amu.gl.equipe200.core;

import amu.gl.equipe200.entity.BaseEntity;
import amu.gl.equipe200.entity.Enemy;
import amu.gl.equipe200.entity.Player;
import amu.gl.equipe200.gameworld.Settings;

import amu.gl.equipe200.system.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

import java.util.*;

/*
    TODO: Réparer un bug qui à lieux lorsque qu'on entre en colision tout en
          bas de la map: le texte Collision ne disparait pas
 */
public class PacmanApp extends Application {

    private Random rnd = new Random();
    private HashMap<String, ASystem> systems;

    private GraphicalEngine graphicalEngine;
    private GameWorld mainMenuScene, gameScene;
    private PlayerController playerController;
    private AnimationTimer gameLoop;
    private boolean playerIsCreated;
    @Override
    public void start(Stage primaryStage) {
        /*** Création des moteurs ***/
        this.graphicalEngine = new GraphicalEngine(primaryStage);
        this.mainMenuScene = GameWorldMaker.MakeMenuScene();
        this.gameScene = GameWorldMaker.MakeGameScene();

        systems = new HashMap<String, ASystem>();
        systems.put("Collisions", new CollisionSystem());

        graphicalEngine.loadScene(mainMenuScene.getScene());
        playerIsCreated = false;


        gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {

                // player input
                //players.forEach(amu.gl.equipe200.entity -> amu.gl.equipe200.entity.processInput());

                // add random enemies
                spawnEnemies( true);

                // movement
                // Déplacé dans physicalEngine, la fonction est statique pour le moment
                //gameScene.getPlayers().forEach(entity -> entity.move());
                //gameScene.getEnemies().forEach(entity -> entity.move());
                PhysicalEngine.moveEntity(gameScene.getEntities());


                // update collisions
                //updateCollisions();
                /*
                 Déplacé la detection des collision dans PhysicalEngine
                 Il me semble que le prof à spécifié que le PhysicalEngine
                 ne devrait pas avoir de référence sur les entitées. Je les
                 passes donc en argument.
                 */
                //systems.get("Collisions").update();
                PhysicalEngine.checkCollision(gameScene.getEntities());

                // update amu.gl.equipe200.entity in scene
                // Bougé le updateUI dans Graphical engine
                //gameScene.getPlayers().forEach(entity -> entity.updateUI());
                //gameScene.getEnemies().forEach(entity -> entity.updateUI());
                graphicalEngine.updateUI(gameScene.getEntities());


                // check if amu.gl.equipe200.entity can be removed
                gameScene.getEnemies().forEach(entity -> entity.checkRemovability());

                // remove removables from list, layer, etc
                removeEntity( gameScene.getEnemies());

                // update score, health, etc
                updateScore();
            }

        };

        // Définition du callback de startGameButton
        Button startButton = (Button)mainMenuScene.getUIComponents().get("startGameButton");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                graphicalEngine.loadScene(gameScene.getScene());
                if(!playerIsCreated)
                    createPlayers();
                setControls();
                gameLoop.start();
            }
        });


    }


    private void setControls(){
        Player player = (Player)gameScene.getPlayers().get(0);
        this.playerController = new PlayerController(gameScene.getScene());
        playerController.addAction(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()== KeyCode.Z){
                player.setDx(0);
                player.setDy(-player.getSpeed());
                player.setR(270);
            }
        });
        playerController.addAction(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()== KeyCode.S){
                player.setDx(0);
                player.setDy(player.getSpeed());
                player.setR(90);
            }
        });
        playerController.addAction(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()== KeyCode.Q){
                player.setDx(-player.getSpeed());
                player.setDy(0);
                player.setR(180);
            }
        });
        playerController.addAction(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()== KeyCode.D){
                player.setDx(player.getSpeed());
                player.setDy(0);
                player.setR(0);
            }
        });

        playerController.addAction(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()== KeyCode.P){
                gameLoop.stop();
                graphicalEngine.loadScene(mainMenuScene.getScene());
            }
        });
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
        playerIsCreated = true;

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