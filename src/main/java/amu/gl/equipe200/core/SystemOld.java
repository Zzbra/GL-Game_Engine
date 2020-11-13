package amu.gl.equipe200.core;


import java.util.*;

public abstract class SystemOld {
    ArrayList<EntityOld> entities;

    public SystemOld(){
        this.entities = new ArrayList<>();
    }

    public abstract void update();

    public void addEntity(EntityOld entityOld){
        this.entities.add(entityOld);
        entityOld.setCollisionLister(this);
        java.lang.System.out.println(entityOld.getTag());
        java.lang.System.out.println(entities.size());
    }

    public void removeEntity(EntityOld entityOld){
        if (!this.entities.contains(entityOld)) return;
        this.entities.remove(entityOld);
    }
}
