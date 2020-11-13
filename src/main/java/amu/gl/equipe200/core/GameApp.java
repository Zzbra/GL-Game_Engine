package amu.gl.equipe200.core;

import amu.gl.equipe200.physicengine.PhysicSystem;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.HashMap;

public class GameApp extends Application {

    private GameSettings settings;
    private GameWorld gameWorld;

    private HashMap<String, System> systems;
    private long lastupdateTime;

    /***** Can be overwritten *****/
    /** Initilization fonction **/
    public void initSettings(GameSettings settings) { };
    public void initApp(Stage primaryStage) { };

    /** Game loop call **/
    public void spawnEntity() { };


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
            systems.get("Physic").onUpdate(elapsedTime);

            // update graphic
            systems.get("Graphic").onUpdate(elapsedTime);

            // check if amu.gl.equipe200.entity can be removed
            // enemies.forEach(entity -> entity.checkRemovability());

            // remove removables from list, layer, etc
            // removeEntity(enemies);

            // update score, health, etc
            // updateScore();

            // Custom system ?
        }

    };


    /***** Entry point for JavaFX *****/
    @Override
    public void start(Stage primaryStage) throws Exception {
        /** Initilize the settings **/
        this.settings = new GameSettings();
        this.initSettings(this.settings);

        /** Initialize the systems **/
        this.systems = new HashMap<>();
        this.systems.put("Collisions", new PhysicSystem(GameSettings.SCENE_WIDTH, GameSettings.SCENE_WIDTH));

        /** Custom initialisation of the app **/
        this.initApp(primaryStage);

        /** Start  the game loop **/
        this.gameLoop.start();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
