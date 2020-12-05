package amu.gl.equipe200.entity;

import amu.gl.equipe200.graphicsengine.GraphicsEngine;
import amu.gl.equipe200.graphicsengine.GraphicsInterface;
import amu.gl.equipe200.physicsengine.PhysicsInterface;
import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.core.Settings;

public class Enemy
        extends Entity
        implements PhysicsInterface, GraphicsInterface {

    /**
     * Physics variables
     */
    private double x, y;
    private double xSpeed, ySpeed;
    private double width, height;

    /**
     * Graphics variables
     */
    private double r;
    private String imageName;
    private String layerName;
    private boolean hasMoved;
    private boolean hasNewSprite;


    public Enemy(double x, double y, double r,
                 double xSpeed, double ySpeed, double dr,
                 double health, double damage,
                 String imageName,
                 String layerName) {
        super(x, y, r, xSpeed, ySpeed, dr, health, damage);

        this.x = x;
        this.y = y;
        this.r = r;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        // TODO: add width and height in the constructor ?

        this.setTag(Settings.Tag.ENEMY);
        this.imageName = imageName;
        this.layerName = layerName;
    }


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
    public String getImageName(long ellapsedTime) { return this.imageName; }
    @Override
    public String getLayerName(){ return this.layerName; }


    /******************************************************************************************************************
     *    Physics Engine behaviour                                                                                    *
     ******************************************************************************************************************/
    @Override
    public boolean isWorldBounded() { return true; }
    @Override
    public boolean isCollidable() { return true; }
    @Override
    public boolean isSolid() { return true; }

    @Override
    public void onWorldEnds() {
        // TODO
        //System.out.println(this.toString() + " has reach the end of the world");
    }
    @Override
    public void onCollide(PhysicsInterface others) {
        // TODO
        //System.out.println(this.toString() + " has collided with " + others.toString());
    }


    /******************************************************************************************************************
     *    Graphics Engine behaviour                                                                                   *
     ******************************************************************************************************************/
    @Override
    public boolean needRemoval() { return false; }

    @Override
    public boolean hasMoved() { return this.hasMoved; }
    @Override
    public boolean hasNewSprite() { return this.hasMoved; }

    @Override
    public void onProcessed(GraphicsEngine engine) {
        this.hasMoved = false;
        this.hasNewSprite = false;
    }


    @Override
    public void checkRemovability() {
        if( Double.compare( getY(), Settings.SCENE_HEIGHT) > 0) {
            setRemovable(true);
        }
    }
}