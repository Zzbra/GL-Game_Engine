package amu.gl.equipe200.graphicengine.old;

import javafx.scene.Node;
import javafx.scene.Scene;

import java.util.HashMap;

public abstract class SceneWrapper {
    private Scene scene;
    private HashMap<String, Node> components;
    public Scene getScene(){return this.scene;}
    public void setScene(Scene scene){this.scene = scene;}
    public HashMap<String, Node> getComponents(){return this.components;}

    public SceneWrapper() {
        this.components = new HashMap<>();
    }
}
