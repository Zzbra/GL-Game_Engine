package amu.gl.equipe200.entity;

import amu.gl.equipe200.graphicsengine.RenderableInterface;
import amu.gl.equipe200.physicsengine.PhysicsInterface;

public class Block extends Entity implements RenderableInterface, PhysicsInterface {

    private String imageName, layerName;
    private double width, height;

    public Block(double x, double y, String imageName, String layerName, double width, double height) {
        this.setX(x);
        this.setY(y);
        this.imageName = imageName;
        this.layerName = layerName;
        this.width = width;
        this.height = height;
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
    public double getWidth() { return width; }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public void setXSpeed(double dx) {

    }

    @Override
    public void setYSpeed(double dy) {

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
