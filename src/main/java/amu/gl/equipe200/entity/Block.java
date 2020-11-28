package amu.gl.equipe200.entity;

import amu.gl.equipe200.graphicsengine.RenderableInterface;
import amu.gl.equipe200.physicsengine.PhysicsInterface;

public class Block extends Entity implements RenderableInterface, PhysicsInterface {

    public Block(double x, double y) {
        this.setX(x);
        this.setY(y);
    }

    @Override
    public double getX() {
        return 0;
    }

    @Override
    public void setX(double x) {

    }

    @Override
    public double getY() {
        return 0;
    }

    @Override
    public void setY(double y) {

    }

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
        return null;
    }

    @Override
    public String getLayerName() {
        return null;
    }
}
