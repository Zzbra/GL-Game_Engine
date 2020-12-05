package amu.gl.equipe200.physicsengine;

import amu.gl.equipe200.core.Settings;

public interface PhysicsInterface {
    // Getter and setter for the position
    double getX();
    void setX(double x);
    double getY();
    void setY(double y);

    // Getter for the speed
    double getXSpeed();
    double getYSpeed();

    // Getter for the hitbox size of the entity
    double getWidth();
    double getHeight();
    void setXSpeed(double dx);
    void setYSpeed(double dy);

    // Physics behaviour of the entity
    default boolean isMovable() { return false;}
    default boolean isWorldBounded() { return false; }
    default boolean isCollidable() { return false; }
    default boolean isSolid() { return false; }
    boolean isRemovable();

    Settings.Tag getTag();

    // Callback of the physics engine
    default void onWorldEnds() {}
    default void onCollide(PhysicsInterface others) {}
}
