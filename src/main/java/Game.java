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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Game extends Application {

    Random rnd = new Random();

    Pane playfieldLayer;
    Pane scoreLayer;

    Image playerImage;
    Image enemyImage;

    List<Player> players = new ArrayList<>();
    List<Enemy> enemies = new ArrayList<>();

    Text collisionText = new Text();
    boolean collision = false;

    public static enum Tag {PLAYER, ENEMY} ;

    Scene scene;

    @Override
    public void start(Stage primaryStage) {

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
                players.forEach(sprite -> sprite.processInput());

                // add random enemies
                spawnEnemies( true);

                // movement
                players.forEach(sprite -> sprite.move());
                enemies.forEach(sprite -> sprite.move());

                // update collisions
                updateCollisions();

                // update sprites in scene
                players.forEach(sprite -> sprite.updateUI());
                enemies.forEach(sprite -> sprite.updateUI());

                // check if sprite can be removed
                enemies.forEach(sprite -> sprite.checkRemovability());

                // remove removables from list, layer, etc
                removeSprites( enemies);

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
            System.err.println("Pas trouvé");
        }
        try {
            enemyImage = new Image( getClass().getResource("enemy.png").toExternalForm());
        }catch(Exception e){
            System.err.println("Pas trouvé");
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

        // player input
        Input input = new Input( scene);

        // register input listeners
        input.addListeners(); // TODO: remove listeners on game over

        Image image = playerImage;

        // center horizontally, position at 70% vertically
        double x = (Settings.SCENE_WIDTH - image.getWidth()) / 2.0;
        double y = Settings.SCENE_HEIGHT * 0.7;

        // create player
        Player player = new Player(playfieldLayer, image, x, y, 0, 0, 0, 0, Settings.PLAYER_SHIP_HEALTH, 0, Settings.PLAYER_SHIP_SPEED, input);

        // register player
        players.add( player);

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

    }

    private void removeSprites(  List<? extends BaseEntity> spriteList) {
        Iterator<? extends BaseEntity> iter = spriteList.iterator();
        while( iter.hasNext()) {
            BaseEntity sprite = iter.next();

            if( sprite.isRemovable()) {

                // remove from layer
                sprite.removeFromLayer();

                // remove from list
                iter.remove();
            }
        }
    }


    /**
     * Collision engine
     */
    private void updateCollisions() {

        for( BaseEntity entity1: players) {
            for( BaseEntity entity2: enemies) {
                if (collidesAB(entity1, entity2)) {
                    if (entity1.collisionStayed(entity2)) {
                        entity1.onCollisionStay(entity2);
                        continue;
                    }
                    entity1.addToCollisionManifold(entity2);
                    entity1.onCollide(entity2);

                }else
                    if (entity1.collideWithTags().contains(entity2.getTag())) {
                    entity1.removeCollision(entity2);
                }
            }
        }
    }

    public boolean collidesAB(BaseEntity A, BaseEntity B) {

        return ( B.x + B.w >= A.x && B.y + B.h >= A.y && B.x <= A.x + A.w && B.y <= A.y + A.h);

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