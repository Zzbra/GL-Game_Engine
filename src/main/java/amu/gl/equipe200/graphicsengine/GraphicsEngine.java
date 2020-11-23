package amu.gl.equipe200.graphicsengine;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class GraphicsEngine extends Application {

    // javafx component used for display
    private Stage stage;
    private Scene displayedScene;

    // ressources used
    private static ImagesManager images;


    // entity to render
    private HashSet<GraphicsInterface> renderableEntities;

    // size of the window in game and in pixel
    private double windowWidthInGame, windowHeightInGame;
    private int windowWidthPixel, windowsHeightPixel;

    public GraphicsEngine(int windowWidthPixel, int windowsHeightPixel,
                          double windowWidthInGame, double windowHeightInGame) {
        // set the window size
        this.windowWidthPixel = windowWidthPixel;
        this.windowsHeightPixel = windowsHeightPixel;
        this.windowWidthInGame = windowWidthInGame;
        this.windowHeightInGame = windowHeightInGame;

        // create the javafx component
        this.stage = null;         // stage will set on start
        this.displayedScene = null; //TODO

        this.images = new ImagesManager();
        this.renderableEntities = new HashSet<>();

        // create the base layers for the graphics engine
        this.layers = new HashMap<>();
        this.layers.put("POPUPS", new RenderLayer("POPUPS", 0));
        this.layers.put("GUI", new RenderLayer("GUI", 1));
        this.layers.put("FOREGROUND", new RenderLayer("FOREGROUND", 3));
        this.layers.put("BACKGROUND", new RenderLayer("BACKGROUND", 4));
    }

    public void update(long ellapsedTime){
        for(GraphicsInterface renderable : renderables.keySet()){
            updateRenderable(renderable);
        }
    }

    public void setLayers(HashMap<String, Pane> layers){
        this.layers = layers;
    }

    public void addLayer(String layerName){
        layers.put(layerName, new Pane());
    }


    public void addRenderable(GraphicsInterface renderable){
        renderables.put(renderable, new ImageView(IMAGEMAP.get(renderable.getImageName())));
        layers.get(renderable.getLayerName()).getChildren().add(renderables.get(renderable));
        //updateRenderable(renderable);
    }

    public void loadScene(Scene scene){
        this.displayedScene = scene;
        this.stage.setScene(scene);
        this.stage.show();
    }

    public static HashMap<String, Image> getImages() {
        return IMAGEMAP;
    }

    private void loadImages() {
        try {
            IMAGEMAP.put("playerImage", new Image("pacman.jpg", 50, 50, true, true));
        }catch(Exception e){
            java.lang.System.err.println("Pas trouve");
        }
        try {
            IMAGEMAP.put("enemyImage", new Image( "ghostRed.jpg", 50, 50, true, true));
        }catch(Exception e){
            java.lang.System.err.println("Pas trouve");
        }
    }



    private void updateRenderable(GraphicsInterface renderable){
        renderables.get(renderable).relocate(renderable.getX(), renderable.getY());
        renderables.get(renderable).setRotate(renderable.getR());
    }

    // TODO : Déplacer les systems dans core
    public void update(SpriteComponent component) {
        ImageView imageView = component.getView();
        imageView.relocate(component.getX(), component.getY());
        imageView.setRotate(component.getR());
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
