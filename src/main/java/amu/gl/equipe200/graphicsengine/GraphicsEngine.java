package amu.gl.equipe200.graphicsengine;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;

public class GraphicsEngine {
    private Stage stage;
    private Scene currentScene;
    private static HashMap<String, Image> IMAGEMAP;
    private HashMap<RenderableInterface, ImageView> renderables;
    private HashMap<String, Pane> layers;
    public GraphicsEngine(Stage stage, HashMap<String, Pane> layers){
        this.stage = stage;
        this.IMAGEMAP = new HashMap<>();
        this.renderables = new HashMap<>();
        this.layers = layers;
        loadImages();
    }

    public void setLayers(HashMap<String, Pane> layers){
        this.layers = layers;
    }

    public void addLayer(String layerName){
        layers.put(layerName, new Pane());
    }


    public void addRenderable(RenderableInterface renderable){
        renderables.put(renderable, new ImageView(IMAGEMAP.get(renderable.getImageName())));
        layers.get(renderable.getLayerName()).getChildren().add(renderables.get(renderable));
        //updateRenderable(renderable);
    }

    public void loadScene(Scene scene){
        this.currentScene = scene;
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

//    public void update(List<BaseEntity> list){
//        for(BaseEntity entity : list){
//            Component component = entity.getComponent(Renderable.class);
//            if (component == null) continue;
//            component.updateBy(this);
//        }
//    }

//    public void update(List<Component> componentList){
//        for(Component component : componentList){
//            component.updateBy(this);
//        }
//
//    }

    public void update(){
        for(RenderableInterface renderable : renderables.keySet()){
            updateRenderable(renderable);
        }
    }

    private void updateRenderable(RenderableInterface renderable){
        renderables.get(renderable).relocate(renderable.getX(), renderable.getY());
        renderables.get(renderable).setRotate(renderable.getR());
    }

    // TODO : Déplacer les systems dans core
    public void update(SpriteComponent component) {
        ImageView imageView = component.getView();
        imageView.relocate(component.getX(), component.getY());
        imageView.setRotate(component.getR());
    }
}
