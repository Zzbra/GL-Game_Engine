package amu.gl.equipe200.core.Component;

import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.entity.BaseEntity;
import amu.gl.equipe200.system.GraphicalEngine;
import amu.gl.equipe200.system.InputEngine;
import amu.gl.equipe200.system.PhysicalEngine;

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


    public void updateBy(GraphicalEngine engine){}

    public void updateBy(PhysicalEngine engine){}

    public void updateBy(InputEngine engine){}

    public abstract void remove();
}
