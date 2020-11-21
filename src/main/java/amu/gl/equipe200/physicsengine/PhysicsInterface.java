package amu.gl.equipe200.physicsengine;

public interface PhysicsInterface {
    // Getter and setter for the position
    double getX();
    void setX(double x);
    double getY();
    void setY(double y);

    // Getter and setter for the speed
    double getXSpeed();
    void setXSpeed(double xSpeed);
    double getYSpeed();
    void setYSpeed(double ySpeed);

    // Getter and setter for the hitbox of the entity
    double getWidth();
    double getHeight();

    // Getter and setter for the physics behaviour of the entity
    default boolean isMovable() { return false;};
    default boolean isWorldBounded() { return false; };
    default boolean isCollidable() { return false; };
    default boolean isSolid() { return false; };

    // Callback of the physics engine
    default void onWorldEnds() {};
    default void onCollide(PhysicsInterface others) {};
}
