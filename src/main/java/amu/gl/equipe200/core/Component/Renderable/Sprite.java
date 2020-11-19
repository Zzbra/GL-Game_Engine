package amu.gl.equipe200.core.Component.Renderable;

import amu.gl.equipe200.entity.BaseEntity;
import amu.gl.equipe200.system.GraphicalEngine;
import javafx.scene.image.Image;

public class View extends Renderable {
    private Image image;
    public View(BaseEntity entity, String imagePath) {
        super(entity);
        image = GraphicalEngine.getImages().get(imagePath);
    }

    public void updateBy(GraphicalEngine engine){
        engine.update(this);
    }

    public Image getImage(){
        return this.image;
    }
}
