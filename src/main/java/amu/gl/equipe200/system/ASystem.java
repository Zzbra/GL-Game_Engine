package amu.gl.equipe200.system;

import amu.gl.equipe200.core.Entity;

import java.util.*;

public abstract class ASystem {
    private ArrayList<Entity> entities;

    ASystem(){
        this.entities = new ArrayList<>();
    }

    public abstract void update();

    public void addEntity(Entity entity){
        this.entities.add(entity);
        entity.setCollisionLister(this);
        System.out.println(entity.getTag());
        System.out.println(entities.size());
    }

    public void removeEntity(Entity entity){
        if (!this.entities.contains(entity)) return;
        this.entities.remove(entity);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }
}
