package amu.gl.equipe200.core;


import amu.gl.equipe200.IAEngine.IAEngine;
import amu.gl.equipe200.IAEngine.ShortestPath;
import amu.gl.equipe200.entity.Blinky;
import amu.gl.equipe200.entity.Block;
import amu.gl.equipe200.entity.Player;
import amu.gl.equipe200.entity.SuperFruit;
import amu.gl.equipe200.graphicsengine.GameLoopListener;
import amu.gl.equipe200.graphicsengine.GraphicsEngine;
import amu.gl.equipe200.inputengine.InputEngine;
import amu.gl.equipe200.pacman.menues.MainMenu;
import amu.gl.equipe200.physicsengine.PhysicsEngine;


// TODO: remove the extends Application in the gameApp to remove these
import amu.gl.equipe200.physicsengine.PhysicsInterface;
import amu.gl.equipe200.utils.Pair;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

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
    private IAEngine iaEngine;
    private int[][] currentMap;
    private Random rnd = new Random();
    private Player player;
    private Blinky blinky;
    private GameWorld gameWorld;

    private boolean playerIsCreated;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        /*** Create the engines ***/
        this.physicsEngine = new PhysicsEngine(16, 16);  //TODO: replace scene size by world size
        this.graphicsEngine = new GraphicsEngine(primaryStage, (int) Settings.SCENE_WIDTH, (int) Settings.SCENE_HEIGHT);
        this.inputEngine = new InputEngine();
        this.iaEngine = new IAEngine();

        /*** create the gameworld  ***/
        this.gameWorld = new GameWorld(Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        this.currentMap = createMap("Map1.txt");
        this.createPlayers();

        /*** Give map to iaEngine ***/
        iaEngine.loadMap(currentMap);

        /***  Set up the graphics engine  ***/
        graphicsEngine.registerGameLoopListener(this);
        graphicsEngine.loadMenu(MainMenu.getInstance());
        Button startButton = (Button) MainMenu.getInstance().startGameButton;
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                graphicsEngine.loadGameWorld(gameWorld.getGraphicsEntities(),
                                             16, 16);
                graphicsEngine.display();
                inputEngine.loadGameWorld(gameWorld.getIOEntities());
                inputEngine.attachToScene(graphicsEngine.getCurrentScene());
                iaEngine.loadGameWorld(gameWorld.getIAEntities());
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

//
        // Définition du callback de startGameButton

//

    }

    private void createPlayers() {
        // center horizontally, position at 70% vertically
        double x = 16 / 6.0;
        double y = 16 * 0.6;

        // create player1
        player = new Player(x, y, 0, 0, 0, 0, Settings.PLAYER_SHIP_HEALTH, 0, Settings.PLAYER_SPEED,  "pacman.jpg", "FOREGROUND");
        player.setX(x);
        player.setY(y);
        player.setWidth(0.8);
        player.setHeight(0.8);
        player.setControls("Z", "S", "Q", "D");
        gameWorld.addGraphicsEntity(player);
        gameWorld.addPhysicsEntity(player);
        physicsEngine.registerEntity(player);
        gameWorld.addIOEntity(player);

//        ShortestPath shortestPath = new ShortestPath(currentMap);
        //TODO: avoir une fonction qui créé les ennemies et du coup une référence du player dans pacmanapp?
        blinky = new Blinky(0, 0, 0, 0, 0, 0, 1, 0, "ghostRed.jpg", "FOREGROUND", player);
        blinky.setWidth(0.8);
        blinky.setHeight(0.8);
        gameWorld.addPhysicsEntity(blinky);
        physicsEngine.registerEntity(blinky);
        gameWorld.addGraphicsEntity(blinky);
        gameWorld.addAIEntity(blinky);

//        Player player2 = new Player(x, y, 0, 0, 0, 0, Settings.PLAYER_SHIP_HEALTH, 0, Settings.PLAYER_SPEED,  "pacman.jpg", "FOREGROUND");
//        player2.setX(5 * x);
//        player2.setY(y);
//        player2.setYSpeed(-0.1);
//        player2.setWidth(1);
//        player2.setHeight(1);
//        player2.setControls("NUMPAD8", "NUMPAD5", "NUMPAD4", "NUMPAD6");
//        gameWorld.addGraphicsEntity(player2);
//        gameWorld.addPhysicsEntity(player2);
//        physicsEngine.registerEntity(player2);
//        gameWorld.addIOEntity(player2);

    }

    private int[][] createMap(String mapName){
        int[][] mapGrid = getMapGrid(mapName);
        //double offset = Settings.SCENE_HEIGHT/16.0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if(mapGrid[i][j] == 1) {
                    Block block = new Block(j , i , 1, 1, "Block.jpg", "BACKGROUND");
                    gameWorld.addGraphicsEntity(block);
                    gameWorld.addPhysicsEntity(block);

                }
                if(mapGrid[i][j] == 2){
                    SuperFruit superFruit = new SuperFruit(j , i,"SuperFruit.jpg", "BACKGROUND");
                    gameWorld.addPhysicsEntity(superFruit);
                    gameWorld.addGraphicsEntity(superFruit);
                }
            }
        }
        return mapGrid;
    }

    private int[][] getMapGrid(String mapName){
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(mapName);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + mapName);
        }
        String sc = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining(""));
        int[][] mapTab = new int[16][16];

        System.out.println(sc.length());
        for(int i = 0; i < sc.length(); i++){
            if(sc.charAt(i) != '\n')
                mapTab[i/16][i%16] = (int) sc.charAt(i) - (int) '0';
        }
        return mapTab;
    }
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
    private void handleCollision(HashSet<Pair<PhysicsInterface, PhysicsInterface>> collisions){

    }

    @Override
    public void onNewFrame(long now) {
        //System.out.println("New Frame");
//        // player input
//        //players.forEach(amu.gl.equipe200.entity -> amu.gl.equipe200.entity.processInput());
//
        //inputEngine.update(gameScene.getComponentsByType(InputComponent.class));
        inputEngine.update();
//        // add random enemies
//        //spawnEnemies( true);
//        //  spawnSuperFruit(true);
//
//        // Ici l'engin physique se charge de déplacer les entitées et de détecter les collisions
        iaEngine.update();
//        // TODO: compute the ellapsed time to send it to the engines
        physicsEngine.update(1);

        HashSet<Pair<PhysicsInterface, PhysicsInterface>> collisions = physicsEngine.getCollisionPair();
        handleCollision(collisions);
//
//        // update amu.gl.equipe200.entity in scene
//        // Ici le moteur graphique se charge de réafficher les entitées avec leurs coordonnées actualisées
//        // TODO: compute the ellapsed time to send it to the engines
        graphicsEngine.update(1);
        System.out.println(blinky.getXSpeed() + " " + blinky.getYSpeed());
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