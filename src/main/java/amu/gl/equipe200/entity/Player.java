package amu.gl.equipe200.entity;

import amu.gl.equipe200.Interfaces.IOInterface;
import amu.gl.equipe200.Interfaces.Movable;
import amu.gl.equipe200.Interfaces.RenderableInterface;

import amu.gl.equipe200.core.Entity;
import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.gameworld.Settings;
import amu.gl.equipe200.physicsengine.PhysicsComponent;

public class Player extends Entity implements Movable, RenderableInterface, IOInterface {

    private double playerShipMinX;
    private double playerShipMaxX;
    private double playerShipMinY;
    private double playerShipMaxY;
    private String imagePath;
    private String layerName;

    private double speed;

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

    public void initShip(Renderable component) {
        // calculate movement bounds of the player ship
        // allow half of the ship to be outside of the screen
        playerShipMinX = 0 - component.getWidth() / 2.0;
        playerShipMaxX = Settings.SCENE_WIDTH - component.getWidth() / 2.0;
        playerShipMinY = 0 - component.getHeight() / 2.0;
        playerShipMaxY = Settings.SCENE_HEIGHT -component.getHeight() / 2.0;
    }

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
}