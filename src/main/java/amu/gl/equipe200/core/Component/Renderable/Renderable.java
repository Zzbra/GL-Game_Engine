package amu.gl.equipe200.core.Component.Renderable;

import amu.gl.equipe200.core.Component.Component;
import amu.gl.equipe200.entity.BaseEntity;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public abstract class Renderable extends Component {
    Pane layer;

    public Renderable(BaseEntity entity, String layerName) {
        super(entity);
        entity.addComponent(Renderable.class, this);
        layer = getGameWorld().getLayers().get(layerName);
    }

    public void addToLayer(Node node){
        layer.getChildren().add(node);
    }

    public abstract double getWidth();

    public abstract double getHeight();
}
