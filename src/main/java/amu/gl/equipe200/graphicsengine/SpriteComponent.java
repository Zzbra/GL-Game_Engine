package amu.gl.equipe200.graphicsengine;

import amu.gl.equipe200.core.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SpriteComponent extends RenderableComponent {
    private Image image;
    private ImageView imageView;

    public SpriteComponent(Entity entity, String imagePath, String layerName) {
        super(entity, layerName);

        this.image = GraphicsEngine.getImages().get(imagePath);
        this.imageView = new ImageView(image);
        super.addToLayer(imageView);
        entity.setWidth(image.getWidth());
        entity.setHeight(image.getWidth());


    }

    public void updateBy(GraphicsEngine engine){
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
