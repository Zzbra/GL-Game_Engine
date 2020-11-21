package amu.gl.equipe200.core;

import amu.gl.equipe200.graphicsengine.GraphicsEngine;
import amu.gl.equipe200.system.InputEngine;
import amu.gl.equipe200.physicsengine.PhysicsEngine;

public abstract class Component {
    private Entity entity;

    public Component(Entity entity){
        this.entity = entity;
    }

    public Entity getEntity(){
        return this.entity;
    }

    public double getX() {
        return entity.getX();
    }

    public double getY() {
        return entity.getY();
    }

    public double getR() {
        return entity.getR();
    }


    // Les entitées doivent elles référencer le gameWorld()?
    public GameWorld getGameWorld(){
        return entity.getGameWorld();
    }


    public void updateBy(GraphicsEngine engine){
        System.err.println(engine.getClass().toString() + " Not implemented");
    }

    public void updateBy(PhysicsEngine engine){
        System.err.println(engine.getClass().toString() + " Not implemented");
    }

    public void updateBy(InputEngine engine){
        System.err.println(engine.getClass().toString() + " Not implemented");
    }

    public abstract void remove();
}
