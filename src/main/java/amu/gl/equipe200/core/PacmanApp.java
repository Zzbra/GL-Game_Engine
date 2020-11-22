package amu.gl.equipe200.core;

import amu.gl.equipe200.graphicsengine.GraphicsEngine;
import amu.gl.equipe200.graphicsengine.RenderableComponent;
import amu.gl.equipe200.physicsengine.PhysicsEngine;

import amu.gl.equipe200.entity.Enemy;
import amu.gl.equipe200.entity.Player;
import amu.gl.equipe200.gameworld.Settings;


import amu.gl.equipe200.physicsengine.PhysicsInterface;
import amu.gl.equipe200.system.InputEngine;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
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

    private InputEngine inputEngine;
    private PhysicsEngine physicsEngine;
    private GraphicsEngine graphicsEngine;

    private Random rnd = new Random();
    private GameWorld mainMenuScene, gameScene;
    private AnimationTimer gameLoop;

    private boolean playerIsCreated;

    @Override
    public void start(Stage primaryStage) {
        this.mainMenuScene = GameWorldMaker.MakeMenuScene();
        this.gameScene = GameWorldMaker.MakeGameScene();

        /*** Create the engines ***/
        //TODO: replace scene size by world size
        this.physicsEngine = new PhysicsEngine(Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        this.graphicsEngine = new GraphicsEngine(primaryStage, gameScene.getLayers());
        this.inputEngine = new InputEngine(gameScene.getScene());

        graphicsEngine.loadScene(mainMenuScene.getScene());

        playerIsCreated = false;
        createPlayers();
        playerIsCreated = true;


        gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {

                // player input
                //players.forEach(amu.gl.equipe200.entity -> amu.gl.equipe200.entity.processInput());

                //inputEngine.update(gameScene.getComponentsByType(InputComponent.class));
                inputEngine.update();
                // add random enemies
                spawnEnemies( true);

                // Ici l'engin physique se charge de déplacer les entitées et de détecter les collisions
                // TODO: compute the ellapsed time to send it to the engines
                physicsEngine.update(1);


                // update amu.gl.equipe200.entity in scene
                // Ici le moteur graphique se charge de réafficher les entitées avec leurs coordonnées actualisées
                // TODO: compute the ellapsed time to send it to the engines
                graphicsEngine.update(1);


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
                graphicsEngine.loadScene(gameScene.getScene());
                if(!playerIsCreated)
                    createPlayers();
                //setControls();

                gameLoop.start();
            }
        });


    }




    private void createPlayers() {
        // center horizontally, position at 70% vertically
        double x = Settings.SCENE_WIDTH / 2.0;
        double y = Settings.SCENE_HEIGHT * 0.7;

        // create player
        Player player = new Player(x, y, 0, 0, 0, 0, Settings.PLAYER_SHIP_HEALTH, 0, Settings.PLAYER_SHIP_SPEED, gameScene, "playerImage", "playerfieldLayer");
        physicsEngine.registerEntity(player);
        graphicsEngine.addRenderable(player);
        inputEngine.addIOEntity(player);
        //RenderableComponent sprite = new SpriteComponent(player, "playerImage", "playerfieldLayer");
        //player.initShip(sprite);
//        PhysicsComponent physicsComponent = new PhysicsComponent(player, true, 0 - sprite.getWidth() / 2.0,
//                0 - sprite.getHeight() / 2.0, Settings.SCENE_WIDTH - sprite.getWidth() / 2.0, Settings.SCENE_HEIGHT -sprite.getHeight() / 2.0);
//        InputComponent inputComponent = new PlayerInputComponent(player);
        // register player
        gameScene.getPlayers().add(player);
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
        Enemy enemy = new Enemy(x, y, 0, 0, speed, 0, 1,1, gameScene,"enemyImage", "playerfieldLayer" );
        physicsEngine.registerEntity(enemy);
        graphicsEngine.addRenderable(enemy);
//        RenderableComponent sprite = new SpriteComponent(enemy, "enemyImage", "playerfieldLayer");
//        enemy.setY(-sprite.getHeight());
        //enemy.addComponent(PhysicsComponent.class, new PhysicsComponent(enemy));
        // manage sprite
        gameScene.getEnemies().add( enemy);
    }

    private void removeEntity(List<? extends Entity> spriteList) {
        Iterator<? extends Entity> iter = spriteList.iterator();
        while( iter.hasNext()) {
            Entity sprite = iter.next();

            if( sprite.isRemovable()) {
                physicsEngine.removeEntity((PhysicsInterface) iter);
                // remove from layer
                sprite.getComponent(RenderableComponent.class).remove();
                // remove from list
                iter.remove();
            }
        }
    }


    private void updateScore() {
//        Text collisionText = (Text)gameScene.getUIComponents().get("collisionText");
//        if( gameScene.getPlayers().get(0).hasCollisions()) {
//            collisionText.setText("Collision");
//        } else {
//            collisionText.setText("");
//        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}