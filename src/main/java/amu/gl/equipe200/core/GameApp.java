package amu.gl.equipe200.core;


import amu.gl.equipe200.IAEngine.IAEngine;
import amu.gl.equipe200.graphicsengine.GameLoopListener;
import amu.gl.equipe200.graphicsengine.GraphicsEngine;
import amu.gl.equipe200.graphicsengine.GraphicsInterface;
import amu.gl.equipe200.graphicsengine.MenuInterface;
import amu.gl.equipe200.inputengine.IOInterface;
import amu.gl.equipe200.inputengine.InputEngine;
import amu.gl.equipe200.physicsengine.PhysicsEngine;


// TODO: remove the extends Application in the gameApp to remove these
import amu.gl.equipe200.physicsengine.PhysicsInterface;
import amu.gl.equipe200.utils.Pair;
import javafx.application.Application;
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
public class GameApp
        extends Application
        implements GameLoopListener {

    private PhysicsEngine physicsEngine;
    private GraphicsEngine graphicsEngine;
    private InputEngine inputEngine;
    private IAEngine iaEngine;

    private Random rnd = new Random();
    private long lastGameUpdate;

    private GameWorld currentGameWorld;


    public void onInit() { }
    public void onGameIterBegin(double ellapsedTime) { }
    public void handleCollisions(HashSet<Pair<PhysicsInterface, PhysicsInterface>> collisions) {
        for (Pair<PhysicsInterface, PhysicsInterface> p : collisions ) {
            p.first.onCollide(p.second);
            p.second.onCollide(p.first);
        }
    }
    public void onGameIterEnd(double ellapsedTime) { }

    public PhysicsEngine getPhysicsEngine() { return physicsEngine; }
    public GraphicsEngine getGraphicsEngine() { return graphicsEngine; }
    public InputEngine getInputEngine() { return inputEngine; }
    public IAEngine getIaEngine(){ return iaEngine;}



    @Override
    public void start(Stage primaryStage) {
        /***  Create the engines  ***/
        this.physicsEngine = new PhysicsEngine(100, 100);  //TODO: replace scene size by world size
        this.graphicsEngine = new GraphicsEngine(primaryStage, (int) Settings.SCENE_WIDTH, (int) Settings.SCENE_HEIGHT);
        this.inputEngine = new InputEngine();
        this.iaEngine = new IAEngine();
        /***  Configure the engines  ***/
        graphicsEngine.registerGameLoopListener(this);
        inputEngine.attachToScene(graphicsEngine.getCurrentScene());

        /***  Zero init the variables  ***/
        this.lastGameUpdate = java.time.Instant.now().getNano();
        this.currentGameWorld = null;

        /***  Call the GameDev designed initialisation  ***/
        onInit();

        /***  Start the game  ***/
         graphicsEngine.display();
    }

    @Override
    public void onNewFrame(long now) {
        double ellapsedTime = (now - this.lastGameUpdate) / 1000000000d;
        this.lastGameUpdate = now;

        this.onGameIterBegin(ellapsedTime);

        inputEngine.update();

        physicsEngine.update(ellapsedTime);
        handleCollisions(physicsEngine.getCollisionPair());
        graphicsEngine.update();

        iaEngine.update();

        this.onGameIterEnd(ellapsedTime);
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

    public void loadGameWorld(GameWorld world) {
        this.currentGameWorld = world;

        physicsEngine.loadGameWorld(currentGameWorld.getPhysicsEntities(), currentGameWorld.getWidth(), currentGameWorld.getHeight());

        graphicsEngine.loadGameWorld(currentGameWorld.getGraphicsEntities(), currentGameWorld.getWidth(), currentGameWorld.getHeight());
        graphicsEngine.display();

        inputEngine.loadGameWorld(currentGameWorld.getIOEntities());
        inputEngine.attachToScene(graphicsEngine.getCurrentScene());

        iaEngine.loadGameWorld(currentGameWorld.getIAEntities());
    }

    public void loadMenu(MenuInterface menu) {
        this.currentGameWorld = null;

        graphicsEngine.loadMenu(menu);
        graphicsEngine.display();

        inputEngine.attachToScene(graphicsEngine.getCurrentScene());
    }

    public void addPhysicsEntityToCurrent(PhysicsInterface p) {
        this.currentGameWorld.addPhysicsEntity(p);
        this.physicsEngine.registerEntity(p);
    }
    public void addGraphicsEntityToCurrent(GraphicsInterface g) {
        this.currentGameWorld.addGraphicsEntity(g);
        this.graphicsEngine.registerEntity(g);
    }
    public void addIOEntityToCurrent(IOInterface i) {
        this.currentGameWorld.addIOEntity(i);
        this.inputEngine.registerEntity(i);
    }

}