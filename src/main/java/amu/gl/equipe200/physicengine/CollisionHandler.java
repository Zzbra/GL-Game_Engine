package amu.gl.equipe200.physicengine;

import amu.gl.equipe200.core.Entity;

public abstract class CollisionHandler {
    private CollidableComponent collidableComponent;

    public CollisionHandler (CollidableComponent c) { this.collidableComponent =c; }

    public abstract void onCollide(Entity other);
}
