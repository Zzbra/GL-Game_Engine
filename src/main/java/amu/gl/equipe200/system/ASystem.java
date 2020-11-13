package amu.gl.equipe200.system;

import amu.gl.equipe200.entity.BaseEntity;

import java.util.*;

public abstract class ASystem {
    private ArrayList<BaseEntity> entities;

    ASystem(){
        this.entities = new ArrayList<>();
    }

    public abstract void update();

    public void addEntity(BaseEntity entity){
        this.entities.add(entity);
        entity.setCollisionLister(this);
        System.out.println(entity.getTag());
        System.out.println(entities.size());
    }

    public void removeEntity(BaseEntity entity){
        if (!this.entities.contains(entity)) return;
        this.entities.remove(entity);
    }

    public ArrayList<BaseEntity> getEntities() {
        return entities;
    }
}
