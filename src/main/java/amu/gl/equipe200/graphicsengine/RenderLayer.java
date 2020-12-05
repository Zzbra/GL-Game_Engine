package amu.gl.equipe200.graphicsengine;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class RenderLayer {
    public String name;
    public int depth;
    public Pane pane;

    public RenderLayer(String name, int depth) {
        this.name = name;
        this.depth = depth;
        this.pane = new Pane();
    }

    public String getName() { return this.name; }

    public int getDepth() { return this.depth; }
    public void setDepth(int newDepth) {this.depth = newDepth; }

    public Pane getPane() { return this.pane; }
    public ObservableList<Node> getChildren() { return this.pane.getChildren(); }
}
