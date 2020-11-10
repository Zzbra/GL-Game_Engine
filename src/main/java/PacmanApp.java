import Entity.BaseEntity;
import Entity.Enemy;
import Entity.Player;
import GameWorld.Settings;
import Systems.CollisionSystem;
import Systems.ASystem;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
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

public class PacmanApp extends Application {

    Random rnd = new Random();

    Pane playfieldLayer;
    Pane scoreLayer;

    Image playerImage;
    Image enemyImage;

    List<Player> players = new ArrayList<>();
    List<Enemy> enemies = new ArrayList<>();

    Text collisionText = new Text();

    Scene scene;
    HashMap<String, ASystem> systems;

    @Override
    public void start(Stage primaryStage) {
        /*** Création des moteurs ***/
        systems = new HashMap<String, ASystem>();
        systems.put("Collisions", new CollisionSystem());

        Group root = new Group();

        // create layers
        playfieldLayer = new Pane();
        scoreLayer = new Pane();

        root.getChildren().add( playfieldLayer);
        root.getChildren().add( scoreLayer);

        scene = new Scene( root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);

        primaryStage.setScene( scene);
        primaryStage.show();

        loadGame();

        createScoreLayer();
        createPlayers();


        AnimationTimer gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {

                // player input
                //players.forEach(entity -> entity.processInput());

                // add random enemies
                spawnEnemies( true);

                // movement
                players.forEach(entity -> entity.move());
                enemies.forEach(entity -> entity.move());

                // update collisions
                //updateCollisions();
                systems.get("Collisions").update();

                // update entity in scene
                players.forEach(entity -> entity.updateUI());
                enemies.forEach(entity -> entity.updateUI());

                // check if entity can be removed
                enemies.forEach(entity -> entity.checkRemovability());

                // remove removables from list, layer, etc
                removeEntity( enemies);

                // update score, health, etc
                updateScore();
            }

        };
        gameLoop.start();

    }

    private void loadGame() {
        try {
            playerImage = new Image(getClass().getResource("player.png").toExternalForm());
        }catch(Exception e){
            java.lang.System.err.println("Pas trouvé");
        }
        try {
            enemyImage = new Image( getClass().getResource("enemy.png").toExternalForm());
        }catch(Exception e){
            java.lang.System.err.println("Pas trouvé");
        }
    }

    private void createScoreLayer() {


        collisionText.setFont( Font.font( null, FontWeight.BOLD, 64));
        collisionText.setStroke(Color.BLACK);
        collisionText.setFill(Color.RED);

        scoreLayer.getChildren().add( collisionText);

        // TODO: quick-hack to ensure the text is centered; usually you don't have that; instead you have a health bar on top
        collisionText.setText("Collision");
        double x = (Settings.SCENE_WIDTH - collisionText.getBoundsInLocal().getWidth()) / 2;
        double y = (Settings.SCENE_HEIGHT - collisionText.getBoundsInLocal().getHeight()) / 2;
        collisionText.relocate(x, y);
        collisionText.setText("");

        collisionText.setBoundsType(TextBoundsType.VISUAL);
    }
    private void createPlayers() {

        Image image = playerImage;

        // center horizontally, position at 70% vertically
        double x = (Settings.SCENE_WIDTH - image.getWidth()) / 2.0;
        double y = Settings.SCENE_HEIGHT * 0.7;

        // create player
        Player player = new Player(playfieldLayer, image, x, y, 0, 0, 0, 0, Settings.PLAYER_SHIP_HEALTH, 0, Settings.PLAYER_SHIP_SPEED);

        // register player
        players.add( player);
        systems.get("Collisions").addEntity(player);

    }

    private void spawnEnemies( boolean random) {

        if( random && rnd.nextInt(Settings.ENEMY_SPAWN_RANDOMNESS) != 0) {
            return;
        }

        // image
        Image image = enemyImage;

        // random speed
        double speed = rnd.nextDouble() * 1.0 + 2.0;

        // x position range: enemy is always fully inside the screen, no part of it is outside
        // y position: right on top of the view, so that it becomes visible with the next game iteration
        double x = rnd.nextDouble() * (Settings.SCENE_WIDTH - image.getWidth());
        double y = -image.getHeight();

        // create a sprite
        Enemy enemy = new Enemy( playfieldLayer, image, x, y, 0, 0, speed, 0, 1,1);

        // manage sprite
        enemies.add( enemy);
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
        if( players.get(0).hasCollisions()) {
            collisionText.setText("Collision");
        } else {
            collisionText.setText("");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}