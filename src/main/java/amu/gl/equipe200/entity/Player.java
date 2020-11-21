package amu.gl.equipe200.entity;

import amu.gl.equipe200.inputengine.IOInterface;
import amu.gl.equipe200.physicsengine.PhysicsInterface;
import amu.gl.equipe200.graphicsengine.RenderableInterface;

import amu.gl.equipe200.core.Entity;
import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.gameworld.Settings;
import amu.gl.equipe200.physicsengine.PhysicsComponent;

public class Player extends Entity implements PhysicsInterface, RenderableInterface, IOInterface {

    private double playerShipMinX;
    private double playerShipMaxX;
    private double playerShipMinY;
    private double playerShipMaxY;
    private String imagePath;
    private String layerName;

    private double speed;

    /**
     * Physics variables
     */
    private double x, y;
    private double xSpeed, ySpeed;
    private double r, rSpeed;                   // WARNING:it's an angle not a position
    private double width = 10, height = 10;



    public Player(double x, double y, double r, double dx, double dy, double dr, double health, double damage, double speed, GameWorld gameScene, String imagePath, String layerName) {

        super(x, y, r, dx, dy, dr, health, damage, gameScene);
        this.setTag(Settings.Tag.PLAYER);
        this.speed = speed;
        this.imagePath = imagePath;
        this.layerName = layerName;
        collisionsCheck.add(Settings.Tag.ENEMY);
    }

    public String getLayerName(){return this.layerName;}

    public double getSpeed() {
        return speed;
    }

//    public void initShip(Renderable component) {
//        // calculate movement bounds of the player ship
//        // allow half of the ship to be outside of the screen
//        playerShipMinX = 0 - component.getWidth() / 2.0;
//        playerShipMaxX = Settings.SCENE_WIDTH - component.getWidth() / 2.0;
//        playerShipMinY = 0 - component.getHeight() / 2.0;
//        playerShipMaxY = Settings.SCENE_HEIGHT -component.getHeight() / 2.0;
//    }

    public void reactToInput(String key) {
        key = key.toUpperCase();
        switch (key){
            case "Z":
                setDx(0);
                setDy(-Settings.PLAYER_SHIP_SPEED);
                setR(270);
                break;
            case "S":
                setDx(0);
                setDy(Settings.PLAYER_SHIP_SPEED);
                setR(90);
                break;
            case "Q":
                setDx(-Settings.PLAYER_SHIP_SPEED);
                setDy(0);
                setR(180);
                break;
            case "D":
                setDx(Settings.PLAYER_SHIP_SPEED);
                setDy(0);
                setR(0);
                break;
            default:
                break;
        }
    }

    @Override
    public void move() {
        super.move();

        // ensure the ship can't move outside of the screen
        checkBounds();
    }

    public void checkBounds() {

        // vertical
        if( Double.compare( getY(), playerShipMinY) < 0) {
            setY(playerShipMinY);
        } else if( Double.compare(getY(), playerShipMaxY) > 0) {
            setY(playerShipMaxY);
        }

        // horizontal
        if( Double.compare( getX(), playerShipMinX) < 0) {
            setX(playerShipMinX);
        } else if( Double.compare(getX(), playerShipMaxX) > 0) {
            setX(playerShipMaxX);
        }

    }

    public void setBounds(double minX, double maxX, double minY, double maxY){
        this.playerShipMinX = minX;
        this.playerShipMaxX = maxX;
        this.playerShipMinY = minY;
        this.playerShipMaxY = maxY;
    }

    @Override
    public void checkRemovability() {
        // TODO Auto-generated method stub
    }

    @Override
    public void onCollisionStay(PhysicsComponent physicsComponent) {
        System.err.println("Collision stay: " + physicsComponent.getTag());
    }

    @Override
    public void onCollide(PhysicsComponent physicsComponent) {
        System.err.println("Collided with: " + physicsComponent.getTag());
    }

    @Override
    public void onExit(PhysicsComponent physicsComponent) {}

    @Override
    public String getImageName() {
        return this.imagePath;
    }


    /**
     * Physics Behaviour overwrite
     */

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
    public void setXspeed(double xSpeed) { this.xSpeed = xSpeed; }
    @Override
    public double getYSpeed() { return this.ySpeed; }
    @Override
    public void setYspeed(double ySpeed) { this.ySpeed = ySpeed; }
    @Override
    public double getRSpeed() { return this.rSpeed; }
    @Override
    public void setRSpeed(double rSpeed) { this.rSpeed = rSpeed; }
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
        System.out.println(this.toString() + " has collide with " + others.toString());
    }
}