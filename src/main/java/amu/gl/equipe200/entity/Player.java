package amu.gl.equipe200.entity;

import amu.gl.equipe200.physicsengine.PhysicsComponent;
import amu.gl.equipe200.graphicsengine.RenderableComponent;
import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.gameworld.Settings;

public class Player extends BaseEntity {

    private double playerShipMinX;
    private double playerShipMaxX;
    private double playerShipMinY;
    private double playerShipMaxY;


    private double speed;

    public Player(double x, double y, double r, double dx, double dy, double dr, double health, double damage, double speed, GameWorld gameScene) {

        super(x, y, r, dx, dy, dr, health, damage, gameScene);
        this.setTag(Settings.Tag.PLAYER);
        this.speed = speed;

        collisionsCheck.add(Settings.Tag.ENEMY);
    }

    public double getSpeed() {
        return speed;
    }

    public void initShip(RenderableComponent component) {
        // calculate movement bounds of the player ship
        // allow half of the ship to be outside of the screen
        playerShipMinX = 0 - component.getWidth() / 2.0;
        playerShipMaxX = Settings.SCENE_WIDTH - component.getWidth() / 2.0;
        playerShipMinY = 0 - component.getHeight() / 2.0;
        playerShipMaxY = Settings.SCENE_HEIGHT -component.getHeight() / 2.0;
    }

    @Override
    public void move() {
        super.move();

        // ensure the ship can't move outside of the screen
        checkBounds();
    }

    private void checkBounds() {

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
    public void onExit(PhysicsComponent physicsComponent2) {}

}