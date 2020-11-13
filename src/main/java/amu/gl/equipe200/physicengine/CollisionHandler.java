package amu.gl.equipe200.physicengine;

import amu.gl.equipe200.core.Entity;

import java.util.function.Function;

public abstract class CollisionHandler {
    private CollidableComponent collidableComponent;

    CollisionHandler (CollidableComponent c) { this.collidableComponent =c; }

    protected abstract void onCollide(CollidableComponent other);
}
