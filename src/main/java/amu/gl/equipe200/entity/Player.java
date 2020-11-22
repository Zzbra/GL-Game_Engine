package amu.gl.equipe200.entity;

import amu.gl.equipe200.inputengine.IOInterface;
import amu.gl.equipe200.physicsengine.PhysicsInterface;
import amu.gl.equipe200.graphicsengine.RenderableInterface;

import amu.gl.equipe200.core.Entity;
import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.gameworld.Settings;

public class Player
        extends Entity
        implements PhysicsInterface, RenderableInterface, IOInterface {

    private String imagePath;
    private String layerName;

    private double speed;

    /**
     * Physics variables
     */
    private double x, y;
    private double xSpeed, ySpeed;
    private double width = 50, height = 50;



    public Player(double x, double y, double r,
                  double dx, double dy, double dr,
                  double health, double damage,
                  double speed, GameWorld gameScene, String imagePath, String layerName) {

        super(x, y, r, dx, dy, dr, health, damage, gameScene);
        this.setTag(Settings.Tag.PLAYER);
        this.speed = speed;
        this.imagePath = imagePath;
        this.layerName = layerName;
        collisionsCheck.add(Settings.Tag.ENEMY);
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

    public String getLayerName(){return this.layerName;}


    /**********************************
     *    Physics Engine behaviour    *
     **********************************/
    @Override
    public boolean isWorldBounded() { return true; }
    @Override
    public boolean isCollidable() { return true; }
    @Override
    public boolean isSolid() { return true; }
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


    /********************************
     *    Input Engine Behaviour    *
     ********************************/
    public void reactToInput(String key) {
        key = key.toUpperCase();
        switch (key){
            case "Z":
                setXSpeed(0);
                setYSpeed(-Settings.PLAYER_SHIP_SPEED);
                setR(270);
                break;
            case "S":
                setXSpeed(0);
                setYSpeed(Settings.PLAYER_SHIP_SPEED);
                setR(90);
                break;
            case "Q":
                setXSpeed(-Settings.PLAYER_SHIP_SPEED);
                setYSpeed(0);
                setR(180);
                break;
            case "D":
                setXSpeed(Settings.PLAYER_SHIP_SPEED);
                setYSpeed(0);
                setR(0);
                break;
            default:
                break;
        }
    }

    /****************
     *    Others    *
     ****************/
    @Override
    public void checkRemovability() {
        // TODO Auto-generated method stub
    }

//    @Override
//    public void onExit(PhysicsComponent physicsComponent) { }

    @Override
    public String getImageName() {
        return this.imagePath;
    }



}