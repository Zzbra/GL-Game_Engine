package amu.gl.equipe200.physicengine;

import amu.gl.equipe200.core.Component.Component;
import amu.gl.equipe200.entity.BaseEntity;
import amu.gl.equipe200.gameworld.Settings;

import java.util.ArrayList;

public class PhysicalComponent extends Component {
    private boolean isBounded = false;
    private double minX, minY, maxX, maxY;
    public PhysicalComponent(BaseEntity entity, Boolean isBounded, double minX, double minY, double maxX, double maxY){
        super(entity);
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        this.isBounded = isBounded;
        entity.addComponent(PhysicalComponent.class, this);
    }

    public PhysicalComponent(BaseEntity entity){
        super(entity);
        entity.addComponent(PhysicalComponent.class, this);
    }

    @Override
    public void remove() {

    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public boolean getIsBounded(){ return  isBounded;}

    public boolean canMove(){ return getEntity().canMove();}

    public void updateBy(PhysicalEngine engine){engine.update(this);}

    public void setX(double x){ getEntity().setX(x);}
    public void setY(double y){ getEntity().setY(y);}
    public void setR(double r){ getEntity().setR(r);}

    public void setDx(double dx){ getEntity().setDx(dx);}
    public void setDy(double dy){ getEntity().setDy(dy);}
    public void setDr(double dr){ getEntity().setDr(dr);}

    public double getDx(){return  getEntity().getDx();}
    public double getDy(){return  getEntity().getDy();}
    public double getDr(){return  getEntity().getDr();}

    public double getWidth(){return  getEntity().getWidth();}
    public double getHeight(){return getEntity().getHeight();}

    public ArrayList<Settings.Tag> getCollisionsCheck(){return getEntity().getCollisionsCheck();}
    public Settings.Tag getTag(){return getEntity().getTag();}
    public boolean collisionStayed(PhysicalComponent physicalComponent){
        return getEntity().collisionStayed(physicalComponent);
    }
    public void onCollisionStay(PhysicalComponent physicalComponent2){
        getEntity().onCollisionStay(physicalComponent2);
    }

    public void addToCollisionManifold(PhysicalComponent physicalComponent2){
        getEntity().addToCollisionManifold(physicalComponent2);
    }

    public void onCollide(PhysicalComponent physicalComponent2){
        getEntity().onCollide(physicalComponent2);
    }

    public void removeCollision(PhysicalComponent physicalComponent2){
        getEntity().removeCollision(physicalComponent2);
    }

    public void onExit(PhysicalComponent physicalComponent2){
        getEntity().onExit(physicalComponent2);
    }


}
