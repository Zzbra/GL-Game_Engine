package amu.gl.equipe200.physicsengine;

public interface PhysicsInterface {
    // Getter and setter for the position
    double getX();
    void setX(double x);
    double getY();
    void setY(double y);

    // Getter and setter for the speed
    double getXSpeed();
    void setXspeed(double xSpeed);
    double getYSpeed();
    void setYspeed(double ySpeed);

    // Getter and setter for the rotation of the entity
    // TODO: does it goes here ?
    // TODO: and do we need a speed
    double getR();
    void setR(double r);
    double getRSpeed();
    void setRSpeed(double rSpeed);

    // Getter and setter for the hitbox of the entity
    double getWidth();
    double getHeight();

    // Getter and setter for the physics behaviour of the entity
    default boolean isMovable() { return false;};
    default boolean isBounded() { return false; };
    default boolean isCollidable() { return false; };
    default boolean isPermeable() { return true; };

    // Callback of the physics engine
    default void onWorldEnds() {};
    default void onCollide(PhysicsInterface others) {};
}
