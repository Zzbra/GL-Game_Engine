package amu.gl.equipe200.entity;

import amu.gl.equipe200.core.Entity;
import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.graphicsengine.RenderableInterface;
import amu.gl.equipe200.physicsengine.PhysicsInterface;

public class Block extends Entity implements RenderableInterface, PhysicsInterface {

    private String imageName, layerName;

    public Block(double x, double y, String imageName, String layerName) {
        this.setX(x);
        this.setY(y);
        this.imageName = imageName;
        this.layerName = layerName;

    }




    public boolean isSolid() { return true; }
    public boolean isCollidable() { return true; }

    @Override
    public double getXSpeed() {
        return 0;
    }

    @Override
    public double getYSpeed() {
        return 0;
    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }

    @Override
    public double getR() {
        return 0;
    }

    @Override
    public void checkRemovability() {

    }

    @Override
    public String getImageName() {
        return this.imageName;
    }

    @Override
    public String getLayerName() {
        return this.layerName;
    }
}
