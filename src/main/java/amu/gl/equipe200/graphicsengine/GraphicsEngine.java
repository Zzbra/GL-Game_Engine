package amu.gl.equipe200.graphicsengine;

import amu.gl.equipe200.utils.Pair;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.HashSet;

public class GraphicsEngine extends Application {

    // javafx component used for display
    private Stage stage;
    private Scene displayedScene;

    // ressources used
    private ImagesManager images;
    private LayerManager layers;


    // entity to render
    private HashSet<GraphicsInterface> graphicsEntities;
    private HashMap<GraphicsInterface, ImageView> views;

    // size of the window in game and in pixel
    private double windowWidthInGame, windowHeightInGame;
    private int windowWidthPixel, windowsHeightPixel;

    // Request update every frame
    private HashSet<GameLoopListener> gameLoopListeners;
    private AnimationTimer gameLoop = new AnimationTimer() {
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

    public GraphicsEngine(int windowWidthPixel, int windowsHeightPixel,
                          double windowWidthInGame, double windowHeightInGame) {
        // set the window size
        this.windowWidthPixel = windowWidthPixel;
        this.windowsHeightPixel = windowsHeightPixel;
        this.windowWidthInGame = windowWidthInGame;
        this.windowHeightInGame = windowHeightInGame;

        // create the javafx component
        this.stage = null;          // stage will set on start
        this.displayedScene = null; // TODO

        this.images = new ImagesManager();
        this.graphicsEntities = new HashSet<>();

        // create the base layers for the graphics engine
        this.layers = new LayerManager();
        this.layers.add("POPUPS");
        this.layers.add("GUI");
        this.layers.add("FOREGROUND");
        this.layers.add("BACKGROUND");

        // create the gameloopListener
        this.gameLoopListeners = new HashSet<>();
    }

    public void update(long ellapsedTime) {
        for (GraphicsInterface entity : this.graphicsEntities) {
            if (entity.needRemoval()) {
                this.removeEntity(entity);
                entity.onProcessed(this);
                continue;
            }
            if (entity.hasMoved()) this.moveEntity(entity);
            if (entity.hasNewSprite()) this.redrawEntity(entity, ellapsedTime);
            entity.onProcessed(this);
        }
    }

    // register or remove entities for updates
    public void registerEntity(GraphicsInterface entity){
        ImageView view = this.createNewNode(entity);
        this.graphicsEntities.add(entity);
        this.views.put(entity, view);
    }
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

    // Convert coordinates from InGame to Screen space (and the other way)
    private Pair<Integer, Integer> fromGameSpaceToScreenSpace(Pair<Double, Double> toConvert) {
        int x = (int) Math.round(toConvert.first * this.windowWidthPixel / this.windowWidthInGame);
        int y = (int) Math.round(toConvert.second * this.windowsHeightPixel / this.windowHeightInGame);
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
        Pair<Integer, Integer> position = fromGameSpaceToScreenSpace(Pair.create(entity.getWidth(), entity.getHeight()));
        view.setX(position.first);
        view.setY(position.second);
        view.setRotate(entity.getR());

        // scale it to the right scale
        Pair<Integer, Integer> size = fromGameSpaceToScreenSpace(Pair.create(entity.getX(), entity.getY()));
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

    public void loadScene(Scene scene){
        this.displayedScene = scene;
        this.stage.setScene(scene);
        this.stage.show();
    }

//    // TODO : Déplacer les systems dans core
//    public void update(SpriteComponent component) {
//        ImageView imageView = component.getView();
//        imageView.relocate(component.getX(), component.getY());
//        imageView.setRotate(component.getR());
//    }

    @Override
    public void start(Stage stage) throws Exception {
        this.gameLoop.start();
    }
}
