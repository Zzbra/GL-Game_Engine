package amu.gl.equipe200.graphicsengine;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;

public class LayerManager {

    private HashMap<String, RenderLayer> layersByName;
    private ArrayList<RenderLayer> layersByDepth;

    public LayerManager() {
        this.layersByName = new HashMap<>();
        this.layersByDepth = new ArrayList<>();
    }

    public void add(int depth, String name) {
        RenderLayer layer = new RenderLayer(name, depth);
        this.layersByName.put(name, layer);
        this.layersByDepth.add(depth, layer);
        for (int i = depth + 1; i < this.layersByDepth.size(); i++){
            this.layersByDepth.get(i).setDepth(i);
        }
    }
    public void add(String name) { add(this.layersByDepth.size(),name); }

    public void clear() {
        this.layersByName.clear();
        this.layersByDepth.clear();
    }

    public RenderLayer get(String name) { return this.layersByName.get(name); }
    public RenderLayer get(int depth) { return this.layersByDepth.get(depth); }

    public void addNodeToLayer(String name, Node node) {
        this.layersByName.get(name).getChildren().add(node);
    }
    public void addNodeAtDepth(int depth, Node node) {
        this.layersByName.get(depth).getChildren().add(node);
    }
}
