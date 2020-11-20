package amu.gl.equipe200.core.Component;

import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.entity.BaseEntity;
import amu.gl.equipe200.graphicsengine.GraphicsSystem;
import amu.gl.equipe200.system.InputEngine;
import amu.gl.equipe200.physicsengine.PhysicsSystem;

public abstract class Component {
    private BaseEntity entity;

    public Component(BaseEntity entity){
        this.entity = entity;
    }

    public BaseEntity getEntity(){
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



    public GameWorld getGameWorld(){
        return entity.getGameWorld();
    }


    public void updateBy(GraphicsSystem engine){
        System.err.println(engine.getClass().toString() + " Not implemented");
    }

    public void updateBy(PhysicsSystem engine){
        System.err.println(engine.getClass().toString() + " Not implemented");
    }

    public void updateBy(InputEngine engine){
        System.err.println(engine.getClass().toString() + " Not implemented");
    }

    public abstract void remove();
}
