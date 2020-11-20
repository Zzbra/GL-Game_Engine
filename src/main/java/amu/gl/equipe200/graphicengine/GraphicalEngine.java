package amu.gl.equipe200.graphicengine;

import amu.gl.equipe200.core.Component.Component;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;

public class GraphicalEngine {
    private Stage stage;
    private Scene currentScene;
    private static HashMap<String, Image> IMAGEMAP;
    public GraphicalEngine(Stage stage){
        this.stage = stage;
        this.IMAGEMAP = new HashMap<>();
        loadImages();
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

    public void update(List<Component> componentList){
        for(Component component : componentList){
            component.updateBy(this);
        }

    }

    // TODO : Déplacer les systems dans core
    public void update(Sprite component) {
        ImageView imageView = component.getView();
        imageView.relocate(component.getX(), component.getY());
        imageView.setRotate(component.getR());
    }
}
