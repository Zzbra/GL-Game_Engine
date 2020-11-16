package amu.gl.equipe200.core;

import amu.gl.equipe200.physicengine.PhysicSystem;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.HashMap;

public class GameApp extends Application {

    private GameSettings settings;

    private HashMap<String, GameWorld> gameWorlds;
    private HashMap<String, System> systems;

    private long lastupdateTime;
    private GameWorld activeWorld;

    /******************************************************************************************************************/
    /***** Can be overwritten *****/
    /** Initilization fonction **/
    public void initSettings(GameSettings settings) { };
    public void initApp(Stage primaryStage) { };

    /** Game loop call **/
    public void spawnEntity() { };

    /******************************************************************************************************************/
    /***** Definition of the game loop *****/
    private AnimationTimer gameLoop = new AnimationTimer() {
        @Override
        public void handle(long now) {
            long elapsedTime = now - lastupdateTime;
            lastupdateTime = now;

            // spawnEntity
            // AI movement
            // player input

            // update the physic
            systems.get("Physic").onUpdate(elapsedTime, activeWorld);

            // update graphic
            systems.get("Graphic").onUpdate(elapsedTime, activeWorld);

            // check if amu.gl.equipe200.entity can be removed
            // enemies.forEach(entity -> entity.checkRemovability());

            // remove removables from list, layer, etc
            // removeEntity(enemies);

            // update score, health, etc
            // updateScore();

            // Custom system ?
        }

    };
    /******************************************************************************************************************/
    /***** Other *****/

    /**
     * Change the current to a new one
     * @param worldName : name of the world to load
     */
    public void loadWorld(String worldName) { this.activeWorld = this.gameWorlds.get(worldName); }


    /******************************************************************************************************************/
    /***** Entry point for JavaFX *****/
    @Override
    public void start(Stage primaryStage) throws Exception {
        /** Initilize the settings **/
        this.settings = new GameSettings();
        this.initSettings(this.settings);

        /** Initialize the systems **/
        this.systems = new HashMap<>();
        this.systems.put("Collisions", new PhysicSystem(GameSettings.SCENE_WIDTH, GameSettings.SCENE_WIDTH));
        this.systems.put("Collisions", new PhysicSystem(GameSettings.SCENE_WIDTH, GameSettings.SCENE_WIDTH));

        /** Custom initialisation of the app **/
        this.initApp(primaryStage);

        /** load a game world if none is active **/
        if (this.activeWorld == null) { this.activeWorld = this.gameWorlds.getOrDefault("Main", new GameWorld()); }

        /** Start  the game loop **/
        this.gameLoop.start();

    }



    public static void main(String[] args) {
        launch(args);
    }

}
