package amu.gl.equipe200.core;

import amu.gl.equipe200.graphicsengine.GraphicsEngine;
import amu.gl.equipe200.graphicsengine.RenderableComponent;
import amu.gl.equipe200.entity.Enemy;
import amu.gl.equipe200.entity.Player;
import amu.gl.equipe200.gameworld.Settings;


import amu.gl.equipe200.physicsengine.PhysicsEngine;
import amu.gl.equipe200.system.*;

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

    private Random rnd = new Random();
    private HashMap<String, ASystem> systems;

    private GraphicsEngine graphicsEngine;
    private GameWorld mainMenuScene, gameScene;

    private AnimationTimer gameLoop;
    private boolean playerIsCreated;
    private PhysicsEngine physicsEngine;
    private InputEngine inputEngine;
    @Override
    public void start(Stage primaryStage) {
        /*** Création des moteurs ***/

        this.physicsEngine = new PhysicsEngine(100, 100); //TODO: world size
        this.mainMenuScene = GameWorldMaker.MakeMenuScene();
        this.gameScene = GameWorldMaker.MakeGameScene();
        this.graphicsEngine = new GraphicsEngine(primaryStage, gameScene.getLayers());
        this.inputEngine = new InputEngine(gameScene.getScene());
        systems = new HashMap<String, ASystem>();
        systems.put("Collisions", new CollisionSystem());

        graphicsEngine.loadScene(mainMenuScene.getScene());
        playerIsCreated = false;

        createPlayers();



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
                // PhysicalEngine.update(gameScene.getEntities());
                //physicsSystem.update(gameScene.getComponentsByType(PhysicsComponent.class));
                //System.out.println(gameScene.getPhysicalComponents().size() == gameScene.getComponentsByType(PhysicalComponent.class).size());


                // update amu.gl.equipe200.entity in scene
                // Ici le moteur graphique se charge de réafficher les entitées avec leurs coordonnées actualisées
                graphicsEngine.update();


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
        graphicsEngine.addRenderable(player);
        inputEngine.addIOEntity(player);
        //RenderableComponent sprite = new SpriteComponent(player, "playerImage", "playerfieldLayer");
        //player.initShip(sprite);
//        PhysicsComponent physicsComponent = new PhysicsComponent(player, true, 0 - sprite.getWidth() / 2.0,
//                0 - sprite.getHeight() / 2.0, Settings.SCENE_WIDTH - sprite.getWidth() / 2.0, Settings.SCENE_HEIGHT -sprite.getHeight() / 2.0);
//        InputComponent inputComponent = new PlayerInputComponent(player);
        // register player
        gameScene.getPlayers().add(player);
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
        Enemy enemy = new Enemy(x, y, 0, 0, speed, 0, 1,1, gameScene,"enemyImage", "playerfieldLayer" );
        graphicsEngine.addRenderable(enemy);
//        RenderableComponent sprite = new SpriteComponent(enemy, "enemyImage", "playerfieldLayer");
//        enemy.setY(-sprite.getHeight());
        //enemy.addComponent(PhysicsComponent.class, new PhysicsComponent(enemy));
        // manage sprite
        gameScene.getEnemies().add( enemy);
        systems.get("Collisions").addEntity(enemy);
    }

    private void removeEntity(List<? extends Entity> spriteList) {
        Iterator<? extends Entity> iter = spriteList.iterator();
        while( iter.hasNext()) {
            Entity sprite = iter.next();

            if( sprite.isRemovable()) {

                // remove from layer
                sprite.getComponent(RenderableComponent.class).remove();
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