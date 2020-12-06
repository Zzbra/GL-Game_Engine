package amu.gl.equipe200.pacman.entities;

import amu.gl.equipe200.graphicsengine.GraphicsInterface;
import amu.gl.equipe200.physicsengine.PhysicsInterface;


public abstract class Entity
    implements PhysicsInterface, GraphicsInterface {

    /***  Physics variables  ***/
    private double x, y;
    private double xSpeed, ySpeed;
    private double width, height;

    /***  Graphics variables   ***/
    private double r;
    private String imageName;
    private String layerName;

    public Entity() {}

    /******************************************************************************************************************
     *    Getters and Setters                                                                                         *
     ******************************************************************************************************************/
    @Override
    public double getX() { return this.x; }
    @Override
    public double getY() { return this.y; }
    @Override
    public void setX(double x) { this.x = x; }
    @Override
    public void setY(double y) { this.y = y; }
    @Override
    public double getR() { return this.r; }
    public void setR(double r) { this.r = r; }

    @Override
    public double getXSpeed() { return this.xSpeed; }
    public void setXSpeed(double xSpeed) { this.xSpeed = xSpeed; }
    @Override
    public double getYSpeed() { return this.ySpeed; }
    public void setYSpeed(double ySpeed) { this.ySpeed = ySpeed; }

    @Override
    public double getWidth() {
        return this.width;
    }
    public void setWidth(double width) { this.width = width; }
    @Override
    public double getHeight() {
        return this.height;
    }
    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String getImageName() { return this.imageName; }
    public void setImageName( String name ) { this.imageName = name; }
    @Override
    public String getLayerName() { return this.layerName; }
    public void setLayerName(String name) { this.layerName = name; }
}