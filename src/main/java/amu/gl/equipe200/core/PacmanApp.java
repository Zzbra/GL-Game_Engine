package amu.gl.equipe200.core;

import amu.gl.equipe200.core.Component.InputComponent;
import amu.gl.equipe200.core.Component.PhysicalComponent;
import amu.gl.equipe200.core.Component.PlayerInputComponent;
import amu.gl.equipe200.core.Component.Renderable.Renderable;
import amu.gl.equipe200.core.Component.Renderable.Sprite;
import amu.gl.equipe200.entity.BaseEntity;
import amu.gl.equipe200.entity.Enemy;
import amu.gl.equipe200.entity.Player;
import amu.gl.equipe200.gameworld.Settings;

import amu.gl.equipe200.system.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

/*
    TODO: Réparer un bug qui à lieux lorsque qu'on entre en colision tout en
          bas de la map: le texte Collision ne disparait pas

    Pour l'instant, tout ce qui est collision est géré au niveau de l'entité.
    On pourrait peut-être declarer PhysicalComponent comme classe abstraite et demander
    à l'utilisateur de créer des custom PhysicalComponent qui redéfinissent les
    méthodes liées à la collision.
 */
public class PacmanApp extends Application {

    private Random rnd = new Random();
    private HashMap<String, ASystem> systems;

    private GraphicalEngine graphicalEngine;
    private GameWorld mainMenuScene, gameScene;
    private PlayerController playerController;
    private AnimationTimer gameLoop;
    private boolean playerIsCreated;
    private PhysicalEngine physicalEngine;
    private InputEngine inputEngine;
    @Override
    public void start(Stage primaryStage) {
        /*** Création des moteurs ***/
        this.graphicalEngine = new GraphicalEngine(primaryStage);
        this.physicalEngine = new PhysicalEngine();
        this.mainMenuScene = GameWorldMaker.MakeMenuScene();
        this.gameScene = GameWorldMaker.MakeGameScene();
        this.inputEngine = new InputEngine(gameScene.getScene());
        systems = new HashMap<String, ASystem>();
        systems.put("Collisions", new CollisionSystem());

        graphicalEngine.loadScene(mainMenuScene.getScene());
        playerIsCreated = false;

        createPlayers();



        gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {

                // player input
                //players.forEach(amu.gl.equipe200.entity -> amu.gl.equipe200.entity.processInput());

                inputEngine.update(gameScene.getComponentsByType(InputComponent.class));

                // add random enemies
                spawnEnemies( true);

                // Ici l'engin physique se charge de déplacer les entitées et de détecter les collisions
                // PhysicalEngine.update(gameScene.getEntities());
                physicalEngine.update(gameScene.getComponentsByType(PhysicalComponent.class));
                //System.out.println(gameScene.getPhysicalComponents().size() == gameScene.getComponentsByType(PhysicalComponent.class).size());


                // update amu.gl.equipe200.entity in scene
                // Ici le moteur graphique se charge de réafficher les entitées avec leurs coordonnées actualisées
                graphicalEngine.update(gameScene.getComponentsByType(Renderable.class));


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
                //setControls();

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
        // center horizontally, position at 70% vertically
        double x = Settings.SCENE_WIDTH / 2.0;
        double y = Settings.SCENE_HEIGHT * 0.7;

        // create player
        Player player = new Player(x, y, 0, 0, 0, 0, Settings.PLAYER_SHIP_HEALTH, 0, Settings.PLAYER_SHIP_SPEED, gameScene);
        Renderable sprite = new Sprite(player, "playerImage", "playerfieldLayer");
        player.initShip(sprite);
        PhysicalComponent physicalComponent = new PhysicalComponent(player, true, 0 - sprite.getWidth() / 2.0,
                0 - sprite.getHeight() / 2.0, Settings.SCENE_WIDTH - sprite.getWidth() / 2.0, Settings.SCENE_HEIGHT -sprite.getHeight() / 2.0);
        InputComponent inputComponent = new PlayerInputComponent(player);
        // register player
        gameScene.getPlayers().add( player);
        systems.get("Collisions").addEntity(player);
        playerIsCreated = true;

    }

    private void spawnEnemies( boolean random) {

        if( random && rnd.nextInt(Settings.ENEMY_SPAWN_RANDOMNESS) != 0) {
            return;
        }

        // random speed
        double speed = rnd.nextDouble() * 1.0 + 2.0;

        // x position range: enemy is always fully inside the screen, no part of it is outside
        // y position: right on top of the view, so that it becomes visible with the next game iteration
        double x = rnd.nextDouble() * Settings.SCENE_WIDTH;
        double y = 0;

        // create a sprite
        Enemy enemy = new Enemy(x, y, 0, 0, speed, 0, 1,1, gameScene);
        Renderable sprite = new Sprite(enemy, "enemyImage", "playerfieldLayer");
        enemy.setY(-sprite.getHeight());
        enemy.addComponent(PhysicalComponent.class, new PhysicalComponent(enemy));
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
                sprite.getComponent(Renderable.class).remove();
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