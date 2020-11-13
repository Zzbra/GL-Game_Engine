package amu.gl.equipe200.physicengine;

public abstract class CollisionHandler {
    private CollidableComponent collidableComponent;

    public CollisionHandler (CollidableComponent c) { this.collidableComponent =c; }

    public abstract void onCollide(CollidableComponent other);
}
