package amu.gl.equipe200.pacman.Entities;

import amu.gl.equipe200.core.Entity;
import amu.gl.equipe200.physicengine.CollidableComponent;
import amu.gl.equipe200.physicengine.CollisionHandler;

class PacmanCollisionHandler extends CollisionHandler {

    PacmanCollisionHandler (CollidableComponent c) {
        super(c);
    }

    @Override
    public void onCollide(Entity other) {
        System.out.println("Collide with: " + other.toString());
    }
}

public class PacmanFactory { };
