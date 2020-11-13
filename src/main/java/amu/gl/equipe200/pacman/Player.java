package amu.gl.equipe200.pacman;

import amu.gl.equipe200.core.EntityOld;
import amu.gl.equipe200.core.GameSettings;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Player extends EntityOld {

    double playerShipMinX;
    double playerShipMaxX;
    double playerShipMinY;
    double playerShipMaxY;

    private GameSettings.Tag tag = GameSettings.Tag.PLAYER;

    double speed;

    public Player(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health, double damage, double speed) {

        super(layer, image, x, y, r, dx, dy, dr, health, damage);
        this.speed = speed;

        collisionsCheck.add(GameSettings.Tag.ENEMY);
        init();
    }


    private void init() {
        // calculate movement bounds of the player ship
        // allow half of the ship to be outside of the screen
        playerShipMinX = 0 - image.getWidth() / 2.0;
        playerShipMaxX = GameSettings.SCENE_WIDTH - image.getWidth() / 2.0;
        playerShipMinY = 0 - image.getHeight() / 2.0;
        playerShipMaxY = GameSettings.SCENE_HEIGHT -image.getHeight() / 2.0;

    }

    @Override
    public void move() {
        super.move();

        // ensure the ship can't move outside of the screen
        checkBounds();
    }

    private void checkBounds() {

        // vertical
        if( Double.compare( y, playerShipMinY) < 0) {
            y = playerShipMinY;
        } else if( Double.compare(y, playerShipMaxY) > 0) {
            y = playerShipMaxY;
        }

        // horizontal
        if( Double.compare( x, playerShipMinX) < 0) {
            x = playerShipMinX;
        } else if( Double.compare(x, playerShipMaxX) > 0) {
            x = playerShipMaxX;
        }

    }


    @Override
    public void checkRemovability() {
        // TODO Auto-generated method stub
    }

    @Override
    public void onCollisionStay(EntityOld entityOld) {
        System.err.println("Collision stay: " + entityOld.getTag());
    }

    @Override
    public void onCollide(EntityOld entityOld) {
        System.err.println("Collided with: " + entityOld.getTag());
    }

    @Override
    public void onExit(EntityOld entityOld2) {}

}