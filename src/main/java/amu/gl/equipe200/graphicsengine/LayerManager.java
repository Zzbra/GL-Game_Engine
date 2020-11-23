package amu.gl.equipe200.graphicsengine;

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

    public void addLayer(String name, int depth) {
        RenderLayer layer = new RenderLayer(name);
        this.layersByName.put(name, layer);
        this.layersByDepth.add(depth, layer);

    }

    public RenderLayer getLayer(String name) { return this.layersByName.get(name); }
    public RenderLayer getLayer(int depth) { return this.layersByDepth.get(depth); }
    public RenderLayer getLayerDepth(String name) { for(int i=0; i < this.layerBy) }

}
