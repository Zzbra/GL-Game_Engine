package amu.gl.equipe200.physicsengine;

import amu.gl.equipe200.core.Component;
import amu.gl.equipe200.core.Entity;
import amu.gl.equipe200.gameworld.Settings;

import java.util.ArrayList;

public class PhysicsComponent extends Component {
    private boolean isBounded = false;
    private double minX, minY, maxX, maxY;
    public PhysicsComponent(Entity entity, Boolean isBounded, double minX, double minY, double maxX, double maxY){
        super(entity);
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        this.isBounded = isBounded;
        entity.addComponent(PhysicsComponent.class, this);
    }

    public PhysicsComponent(Entity entity){
        super(entity);
        entity.addComponent(PhysicsComponent.class, this);
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

    public boolean canMove(){ return getEntity().isMovable();}

//    public void updateBy(PhysicsEngine engine){engine.update(this);}

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
    public boolean collisionStayed(PhysicsComponent physicsComponent){
        return getEntity().collisionStayed(physicsComponent);
    }
    public void onCollisionStay(PhysicsComponent physicsComponent2){
        getEntity().onCollisionStay(physicsComponent2);
    }

    public void addToCollisionManifold(PhysicsComponent physicsComponent2){
        getEntity().addToCollisionManifold(physicsComponent2);
    }

    public void onCollide(PhysicsComponent physicsComponent2){
        getEntity().onCollide(physicsComponent2);
    }

    public void removeCollision(PhysicsComponent physicsComponent2){
        getEntity().removeCollision(physicsComponent2);
    }

    public void onExit(PhysicsComponent physicsComponent2){
        getEntity().onExit(physicsComponent2);
    }


}
