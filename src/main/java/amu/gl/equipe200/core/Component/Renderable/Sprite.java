package amu.gl.equipe200.core.Component.Renderable;

import amu.gl.equipe200.entity.BaseEntity;
import amu.gl.equipe200.system.GraphicalEngine;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite extends Renderable {
    private Image image;
    private ImageView imageView;

    public Sprite(BaseEntity entity, String imagePath, String layerName) {
        super(entity, layerName);

        this.image = GraphicalEngine.getImages().get(imagePath);
        this.imageView = new ImageView(image);
        super.addToLayer(imageView);
        entity.setWidth(image.getWidth());
        entity.setHeight(image.getWidth());


    }

    public void updateBy(GraphicalEngine engine){
        engine.update(this);
    }

    @Override
    public void remove() {
        super.layer.getChildren().remove(this.imageView);
    }

    public ImageView getView() {
        return imageView;
    }


    @Override
    public double getWidth() {
        return image.getWidth();
    }

    @Override
    public double getHeight() {
        return image.getHeight();
    }
}
