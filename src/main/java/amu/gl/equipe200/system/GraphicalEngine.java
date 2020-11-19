package amu.gl.equipe200.system;

import amu.gl.equipe200.entity.BaseEntity;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.ArrayList;
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

    public void updateUI(List<BaseEntity> list){
        for(BaseEntity entity : list){
            ImageView imageView = entity.getView();
            imageView.relocate(entity.getX(), entity.getY());
            imageView.setRotate(entity.getR());
        }
    }
}
