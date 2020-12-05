package amu.gl.equipe200.graphicsengine;

import amu.gl.equipe200.utils.Pair;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.HashSet;

public class GraphicsEngine {

    // javafx component used for display
    private Stage stage;
    private Scene displayedScene;
    private Pane rootNode;

    // ressources used
    private ImagesManager images;
    private LayerManager layers;

    // entity to render
    private HashSet<GraphicsInterface> graphicsEntitiesToAdd;
    private HashSet<GraphicsInterface> graphicsEntities;
    private HashMap<GraphicsInterface, ImageView> views;

    // size of the window in game and in pixel
    private double windowWidthInGame, windowHeightInGame;
    private int windowWidthPixel, windowsHeightPixel;

    // Request update every frame
    private HashSet<GameLoopListener> gameLoopListeners;
    private boolean launchGameLoop = false;
    private AnimationTimer gameLoop;


    public GraphicsEngine(Stage stage, int windowWidthPixel, int windowsHeightPixel) {
        // set the window size
        this.windowWidthPixel = windowWidthPixel;
        this.windowsHeightPixel = windowsHeightPixel;
        this.windowWidthInGame = 0;
        this.windowHeightInGame = 0;

        // create the javafx component
        this.stage = stage;
        this.setErrorScene();
        this.gameLoop = new AnimationTimer() {
            private long lastUpdate = 0 ;
            @Override
            public void handle(long now) {
                // cap the time between frame to 1/60s * 10e9 nanosec
                if (now - lastUpdate >= 1_666_666) {
                    for (GameLoopListener l : gameLoopListeners) {
                        l.onNewFrame(now);
                    }
                    lastUpdate = now;
                }
            }
        };

        // Content holders
        this.graphicsEntities = new HashSet<>();
        this.graphicsEntitiesToAdd = new HashSet<>();
        this.views = new HashMap<>();

        // create the base layers for the graphics engine
        this.layers = new LayerManager(rootNode);

        // Manager for cached images
        this.images = new ImagesManager();

        // create the gameloopListener
        this.gameLoopListeners = new HashSet<>();
    }

    /******************************************
     *     entry point of the core engine     *
     ******************************************/
    public void update(long ellapsedTime) {
        // create and add the javafx node to the layers
        for (GraphicsInterface entity : graphicsEntitiesToAdd) {
            ImageView view = this.createNewNode(entity);
            this.layers.addNodeToLayer(entity.getLayerName(), view);
            this.graphicsEntities.add(entity);
            this.views.put(entity, view);
        }
        this.graphicsEntitiesToAdd.clear();

        // Update the scene
        for (GraphicsInterface entity : this.graphicsEntities) {
            // if the entity needs to be removed do it
            if (entity.needRemoval()) {
                this.removeEntity(entity);
                entity.onProcessed(this);
                continue;
            }
            // Else update the linked node
            if (entity.hasMoved()) this.moveEntity(entity);
            if (entity.hasNewSprite()) this.redrawEntity(entity, ellapsedTime);
            entity.onProcessed(this);
        }
    }

    /**********************************
     *     change displayed scene     *
     **********************************/
    public void loadMenu(MenuInterface menu) {
        // stop the app and clear the stage
        this.gameLoop.stop();
        this.graphicsEntitiesToAdd.clear();
        this.graphicsEntities.clear();
        this.views.clear();
        this.images.clearCache();

        // load the menu
        this.displayedScene = menu.getScene();
        this.rootNode = menu.getRoot();

        this.layers = new LayerManager(this.rootNode);

        this.launchGameLoop = false;
    }
    public void loadGameWorld(HashSet<GraphicsInterface> entitiesToAdd, double windowWidthInGame, double windowHeightInGame){
        // stop the app and clear the stage
        this.gameLoop.stop();
        this.graphicsEntitiesToAdd.clear();
        this.graphicsEntities.clear();
        this.views.clear();
        this.images.clearCache();

        // set the size of the new gameWorld
        this.windowWidthInGame = windowWidthInGame;
        this.windowHeightInGame = windowHeightInGame;

        // create the new scene
        Pane g = new Pane();
        this.displayedScene = new Scene(g, this.windowWidthPixel, this.windowsHeightPixel);
        this.rootNode = g;

        this.layers = new LayerManager(this.rootNode);

        // add the already existing entities to the engine
        for (GraphicsInterface entity : entitiesToAdd) {
            this.registerEntity(entity);
        }

        this.launchGameLoop = true;
    }
    public void display() {
        this.stage.setScene(this.displayedScene);
        if (this.launchGameLoop) this.gameLoop.start();
        this.stage.show();
    }
    public Scene getCurrentScene() { return this.displayedScene; }
    /**********************************
     *     add or remove elements     *
     **********************************/
    // register or remove entities for updates
    public void registerEntity(GraphicsInterface entity){ this.graphicsEntitiesToAdd.add(entity); }
    public void removeEntity(GraphicsInterface entity) {
        Node node = this.views.get(entity);
        this.layers.get(entity.getLayerName()).getChildren().remove(node);
        this.graphicsEntities.remove(entity);
        this.views.remove(entity);
    }

    // register or remove gameLoop listener
    public void registerGameLoopListener(GameLoopListener l) { this.gameLoopListeners.add(l); }
    public void removeGameLoopListener(GameLoopListener l) { this.gameLoopListeners.remove(l); }

    // add graphical layer to the engine
    public void addLayer(String name) { this.layers.add(name); }
    public void addLayer(int depth, String name) {this.layers.add(depth, name); }

    /**********************************
     *     Internal of the engine     *
     **********************************/

    // Convert coordinates from InGame to Screen space (and the other way)
    private Pair<Integer, Integer> fromGameSpaceToScreenSpace(Pair<Double, Double> toConvert) {
        double scale = (this.windowWidthPixel / this.windowWidthInGame);
        int x = (int) Math.round(toConvert.first * scale);
        int y = (int) Math.round(toConvert.second * scale);
        return Pair.create(x, y);
    }
    private Pair<Double, Double> fromScreenSpaceToGameSpace(Pair<Double, Double> toConvert) {
        double x = toConvert.first * this.windowWidthInGame / this.windowWidthPixel;
        double y = toConvert.second * this.windowHeightInGame / this.windowsHeightPixel;
        return Pair.create(x, y);
    }

    // Create a new node for the engine to use
    private ImageView createNewNode(GraphicsInterface entity) {
        ImageView view = new ImageView();

        // load and set the texture
        Image texture = this.images.getImage(entity.getImageName(0));
        view.setImage(texture);

        // relocate and rotate the view
        Pair<Integer, Integer> position = fromGameSpaceToScreenSpace(Pair.create(entity.getX(), entity.getY()));
        view.setX(position.first);
        view.setY(position.second);
        view.setRotate(entity.getR());

        // scale it to the right scale
        Pair<Integer, Integer> size = fromGameSpaceToScreenSpace(Pair.create(entity.getWidth(), entity.getHeight()));
        view.setFitWidth(size.first);
        view.setFitHeight(size.second);

        return view;
    }
    private void moveEntity(GraphicsInterface entity) {
        // get the view associated with the entity
        ImageView view = this.views.get(entity);

        // get the new postion
        Pair<Integer, Integer> position = fromGameSpaceToScreenSpace(Pair.create(entity.getWidth(), entity.getHeight()));

        // change the properties of the view
        view.relocate(position.first, position.second);
        view.setRotate(entity.getR());
    }
    private void redrawEntity(GraphicsInterface entity, long ellapsedTime) {
        // get the view associated with the entity
        ImageView view = this.views.get(entity);

        // get the texture and size
        Image texture = this.images.getImage(entity.getImageName(ellapsedTime));
        Pair<Integer, Integer> size = fromGameSpaceToScreenSpace(Pair.create(entity.getX(), entity.getY()));

        // change the properties of the view
        view.setImage(texture);
        view.setFitWidth(size.first);
        view.setFitWidth(size.second);
    }

    // Error scene
    private void setErrorScene() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Error using JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        Pane root = new StackPane(l);
        Scene scene = new Scene(root, this.windowWidthPixel, this.windowsHeightPixel);
        this.displayedScene = scene;
        this.rootNode = root;
    }
}
