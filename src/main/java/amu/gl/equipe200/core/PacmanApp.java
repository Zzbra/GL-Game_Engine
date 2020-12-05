package amu.gl.equipe200.core;


import amu.gl.equipe200.entity.Player;
import amu.gl.equipe200.graphicsengine.GameLoopListener;
import amu.gl.equipe200.graphicsengine.GraphicsEngine;
import amu.gl.equipe200.pacman.menues.MainMenu;
import amu.gl.equipe200.physicsengine.PhysicsEngine;

import amu.gl.equipe200.gameworld.Settings;

import amu.gl.equipe200.system.InputEngine;

// TODO: remove the extends Application in the gameApp to remove these
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
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
public class PacmanApp
        extends Application
        implements GameLoopListener {

    private PhysicsEngine physicsEngine;
    private GraphicsEngine graphicsEngine;
    private InputEngine inputEngine;

    private Random rnd = new Random();

    private GameWorld gameWorld;

    private boolean playerIsCreated;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        /*** Create the engines ***/
        this.physicsEngine = new PhysicsEngine(Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);  //TODO: replace scene size by world size
        this.graphicsEngine = new GraphicsEngine(primaryStage, (int) Settings.SCENE_WIDTH, (int) Settings.SCENE_HEIGHT);
        this.inputEngine = new InputEngine(graphicsEngine.getCurrentScene());

        /*** create the gameworld  ***/
        this.gameWorld = new GameWorld(Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        this.createPlayers();

        /***  Set up the graphics engine  ***/
        graphicsEngine.registerGameLoopListener(this);
        graphicsEngine.loadMenu(MainMenu.getInstance());
        Button startButton = (Button) MainMenu.getInstance().startGameButton;
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                graphicsEngine.loadGameWorld(gameWorld.getGraphicsEntities(),
                                             Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
                graphicsEngine.display();
            }
        });

        /***  Set up the input engine  ***/
        //TODO




        /***  Display the main scene to launch the game  ***/
        graphicsEngine.display();


//        graphicsEngine.loadMenu(GameWorldMaker.MakeMenuScene());
//        graphicsEngine.display();
//
//        playerIsCreated = false;
//        createPlayers();
//        playerIsCreated = true;
//        createMap("src\\main\\resources\\Map1.txt");
//
        // Définition du callback de startGameButton

//

    }

    private void createPlayers() {
        // center horizontally, position at 70% vertically
        double x = Settings.SCENE_WIDTH / 6.0;
        double y = Settings.SCENE_HEIGHT * 0.7;

        // create player1
        Player player1 = new Player(x, y, 0, 0, 0, 0, Settings.PLAYER_SHIP_HEALTH, 0, Settings.PLAYER_SHIP_SPEED, gameWorld, "pacman.jpg", "FOREGROUND");
        player1.setX(x);
        player1.setY(y);
        player1.setWidth(50);
        player1.setHeight(50);
        player1.setControls("Z", "S", "Q", "D");
        gameWorld.addGraphicsEntity(player1);
        gameWorld.addPhysicsEntity(player1);


        Player player2 = new Player(x, y, 0, 0, 0, 0, Settings.PLAYER_SHIP_HEALTH, 0, Settings.PLAYER_SHIP_SPEED, gameWorld, "pacman.jpg", "FOREGROUND");
        player2.setX(5 * x);
        player2.setY(y);
        player2.setWidth(50);
        player2.setHeight(50);
        player2.setControls("NUMPAD8", "NUMPAD5", "NUMPAD4", "NUMPAD6");
        gameWorld.addGraphicsEntity(player2);
        gameWorld.addPhysicsEntity(player2);
    }
//
//    private void createMap(String mapName){
//        int[][] mapGrid = getMapGrid(mapName);
//        double offset = Settings.SCENE_HEIGHT/16.0;
//        for (int i = 0; i < 16; i++) {
//            for (int j = 0; j < 16; j++) {
//                if(mapGrid[i][j] == 1) {
//                    Block block = new Block(j * offset, i * offset, offset, offset, "blockImage", "playerfieldLayer");
//                    graphicsEngine.registerEntity(block);
//                    physicsEngine.registerEntity(block);
//                }
//                if(mapGrid[i][j] == 2){
//                    SuperFruit superFruit = new SuperFruit(j * offset, i * offset,gameScene,"SuperFruit", "playerfieldLayer");
//                    physicsEngine.registerEntity(superFruit);
//                    graphicsEngine.registerEntity(superFruit);
//                }
//            }
//        }
//    }
//
//    private int[][] getMapGrid(String mapName){
//        File file = new File(mapName);
//        Scanner sc = null;
//        try {
//            sc = new Scanner(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        int[][] mapTab = new int[16][16];
//        int i = 0;
//        while (sc.hasNextLine()) {
//            String line = sc.nextLine();
//            for (int j = 0; j < 16; j++) {
//                mapTab[i][j] = (int) line.charAt(j) - (int)'0' ;
//            }
//            i++;
//        }
//        return mapTab;
//    }
//
//    private void spawnEnemies( boolean random) {
//
//        if( random && rnd.nextInt(Settings.ENEMY_SPAWN_RANDOMNESS) != 0) {
//            return;
//        }
//
//        // random speed
//        //double speed = rnd.nextDouble() * 1.0 + 2.0;
//        double speed = 1.0;
//
//        // x position range: enemy is always fully inside the screen, no part of it is outside
//        // y position: right on top of the view, so that it becomes visible with the next game iteration
//        double x = rnd.nextDouble() * Settings.SCENE_WIDTH;
//        double y = 0;
//
//        // create a sprite
//        Enemy enemy = new Enemy(x, y, 0, 0, speed, 0, 1,1, gameScene,"enemyImage", "playerfieldLayer" );
//        enemy.setX(x);
//        enemy.setY(y);
//        physicsEngine.registerEntity(enemy);
//        graphicsEngine.registerEntity(enemy);
////        RenderableComponent sprite = new SpriteComponent(enemy, "enemyImage", "playerfieldLayer");
////        enemy.setY(-sprite.getHeight());
//        //enemy.addComponent(PhysicsComponent.class, new PhysicsComponent(enemy));
//        // manage sprite
//        gameScene.getEnemies().add( enemy);
//    }
//
//    private void spawnSuperFruit(boolean random){
//        if( random && rnd.nextInt(Settings.SUPERFRUIT_SPAWN_RANDOMNESS) != 0) {
//            return;
//        }
//
//        double x = rnd.nextDouble() * Settings.SCENE_WIDTH;
//        double y = rnd.nextDouble() * Settings.SCENE_HEIGHT;
//
//        SuperFruit superFruit = new SuperFruit(x,y,gameScene,"SuperFruit", "playerfieldLayer");
//        physicsEngine.registerEntity(superFruit);
//        graphicsEngine.registerEntity(superFruit);
//    }
//
//    private void removeEntity(List<? extends Entity> spriteList) {
//        Iterator<? extends Entity> iter = spriteList.iterator();
//        while( iter.hasNext()) {
//            Entity sprite = iter.next();
//
//            if( sprite.isRemovable()) {
//                physicsEngine.removeEntity((PhysicsInterface) iter);
//                // remove from layer
////                sprite.getComponent(RenderableComponent.class).remove();
//                // remove from list
//                iter.remove();
//            }
//        }
//    }
//
//
//    private void updateScore() {
////        Text collisionText = (Text)gameScene.getUIComponents().get("collisionText");
////        if( gameScene.getPlayers().get(0).hasCollisions()) {
////            collisionText.setText("Collision");
////        } else {
////            collisionText.setText("");
////        }
//    }
//
    @Override
    public void onNewFrame(long now) {
//        System.out.println("New Frame");
//        // player input
//        //players.forEach(amu.gl.equipe200.entity -> amu.gl.equipe200.entity.processInput());
//
//        //inputEngine.update(gameScene.getComponentsByType(InputComponent.class));
        inputEngine.update();
//        // add random enemies
//        //spawnEnemies( true);
//        //  spawnSuperFruit(true);
//
//        // Ici l'engin physique se charge de déplacer les entitées et de détecter les collisions
//        // TODO: compute the ellapsed time to send it to the engines
        physicsEngine.update(1);
//
//
//        // update amu.gl.equipe200.entity in scene
//        // Ici le moteur graphique se charge de réafficher les entitées avec leurs coordonnées actualisées
//        // TODO: compute the ellapsed time to send it to the engines
        graphicsEngine.update(1);
//
//
//        // check if amu.gl.equipe200.entity can be removed
//        //gameScene.getEnemies().forEach(entity -> entity.checkRemovability());
//
//        // remove removables from list, layer, etc
//        removeEntity( gameScene.getEnemies());
//
//        // update score, health, etc
//        updateScore();
    }
}