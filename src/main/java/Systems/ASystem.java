package Systems;

import Entity.BaseEntity;

import java.util.*;

public abstract class ASystem {
    ArrayList<BaseEntity> entities;

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
}
