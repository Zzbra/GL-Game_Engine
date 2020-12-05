package amu.gl.equipe200.graphicsengine;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PrimitiveIterator;

public class LayerManager {

    private HashMap<String, RenderLayer> layersByName;
    private ArrayList<RenderLayer> layersByDepth;
    private Pane rootNode;
    public LayerManager(Pane rootNode) {
        this.layersByName = new HashMap<>();
        this.layersByDepth = new ArrayList<>();
        this.rootNode = rootNode;

        // add the default layers
        this.add("POPUPS");
        this.add("GUI");
        this.add("FOREGROUND");
        this.add("BACKGROUND");
    }

    public void add(int depth, String name) {
        RenderLayer layer = new RenderLayer(name, depth);
        this.layersByName.put(name, layer);
        this.layersByDepth.add(depth, layer);
        this.rootNode.getChildren().add(depth, layer.pane);
        for (int i = depth + 1; i < this.layersByDepth.size(); i++){
            this.layersByDepth.get(i).setDepth(i);
        }

    }
    public void add(String name) { add(this.layersByDepth.size(),name); }

    public RenderLayer get(String name) { return this.layersByName.get(name); }
    public RenderLayer get(int depth) { return this.layersByDepth.get(depth); }

    public void addNodeToLayer(String name, Node node) {
        this.layersByName.get(name).getChildren().add(node);
    }
    public void addNodeAtDepth(int depth, Node node) {
        this.layersByName.get(depth).getChildren().add(node);
    }
}
