package amu.gl.equipe200.entity;

import amu.gl.equipe200.physicsengine.PhysicsInterface;
import amu.gl.equipe200.graphicsengine.RenderableInterface;
import amu.gl.equipe200.core.Entity;
import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.gameworld.Settings;

public class Enemy
        extends Entity
        implements PhysicsInterface, RenderableInterface {

    private String imageName, layerName;

    /**
     * Physics variables
     */
    private double x, y;
    private double xSpeed, ySpeed;
    private double r, rSpeed;                   // WARNING:it's an angle not a position
    private double width = 10, height = 10;



    public Enemy(double x, double y, double r,
                 double xSpeed, double ySpeed, double dr,
                 double health, double damage,
                 GameWorld gamescene,
                 String imageName,
                 String layerName) {
        super(x, y, r, xSpeed, ySpeed, dr, health, damage, gamescene);

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

    @Override
    public void checkRemovability() {
        if( Double.compare( getY(), Settings.SCENE_HEIGHT) > 0) {
            setRemovable(true);
        }
    }

    public String getLayerName(){return this.layerName;}

    /*** Colidable ***/
//    @Override
//    public void onCollide(PhysicsComponent physicsComponent) { }
//
//    @Override
//    public void onCollisionStay(PhysicsComponent physicsComponent) { }

//    @Override
//    public void onExit(PhysicsComponent physicsComponent) {}

    @Override
    public String getImageName() {
        return this.imageName;
    }


    /****************************
     *   Getters and Setters    *
     ****************************/
    @Override
    public double getX() { return this.x; }
    @Override
    public void setX(double x) { this.x = x; }
    @Override
    public double getY() { return this.y; }
    @Override
    public void setY(double y) { this.y = y; }
    @Override
    public double getXSpeed() { return this.xSpeed; }
    @Override
    public void setXSpeed(double xSpeed) { this.xSpeed = xSpeed; }
    @Override
    public double getYSpeed() { return this.ySpeed; }
    @Override
    public void setYSpeed(double ySpeed) { this.ySpeed = ySpeed; }
    @Override
    public double getWidth() {
        return this.width;
    }
    @Override
    public void setWidth(double width) { this.width = width; }
    @Override
    public double getHeight() {
        return this.height;
    }
    @Override
    public void setHeight(double height) {
        this.height = height;
    }


    /**********************************
     *    Physics Engine behaviour    *
     **********************************/
    @Override
    public boolean isWorldBounded() { return true; }
    @Override
    public boolean isCollidable() { return true; }
    @Override
    public boolean isSolid() { return false; }
    @Override
    public void onWorldEnds() {
        // TODO
        System.out.println(this.toString() + " has reach the end of the world");
    }
    @Override
    public void onCollide(PhysicsInterface others) {
        // TODO
        System.out.println(this.toString() + " has collided with " + others.toString());
    }




}