package amu.gl.equipe200.physicsengine;

import amu.gl.equipe200.core.Component.Component;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSystem {

    private ArrayList<PhysicsComponent> physicsComponents;

//    public void update(List<PhysicalComponent> componentList){
//       for(PhysicalComponent physicalComponent : componentList){
//           moveComponent(physicalComponent);
//       }
//       checkCollision(componentList);
//    }



    public void update(List<Component> componentList){
        physicsComponents = new ArrayList<>();
        for(Component component : componentList){
            component.updateBy(this);
        }
        System.out.println(physicsComponents.size()==componentList.size());
        checkCollision(physicsComponents);
    }

    public void update(PhysicsComponent physicsComponent){
        moveComponent(physicsComponent);
        physicsComponents.add(physicsComponent);
    }

    public void moveComponent(PhysicsComponent component){

        if( !component.canMove())
            return;
        component.setX(component.getX() + component.getDx());
        component.setY(component.getY() + component.getDy());
        component.setR(component.getR() + component.getDr());
        if(component.getIsBounded()){
            checkBounds(component);
        }
    }

    private void checkBounds(PhysicsComponent physicsComponent) {

        // vertical
        if( Double.compare( physicsComponent.getY(), physicsComponent.getMinY()) < 0) {
            physicsComponent.setY(physicsComponent.getMinY());
        } else if( Double.compare(physicsComponent.getY(), physicsComponent.getMaxY()) > 0) {
            physicsComponent.setY(physicsComponent.getMaxY());
        }

        // horizontal
        if( Double.compare( physicsComponent.getX(), physicsComponent.getMinX()) < 0) {
            physicsComponent.setX(physicsComponent.getMinX());
        } else if( Double.compare(physicsComponent.getX(), physicsComponent.getMaxX()) > 0) {
            physicsComponent.setX(physicsComponent.getMaxX());
        }

    }

    public void checkCollision(List<PhysicsComponent> componentList) {
        for (PhysicsComponent physicsComponent1 : componentList) {
            for (PhysicsComponent physicsComponent2 : componentList) {
                if (physicsComponent1.equals(physicsComponent2)) continue;
                if (physicsComponent1.getCollisionsCheck().contains(physicsComponent2.getTag())) {
                    if (collidesAB(physicsComponent1, physicsComponent2)) {
                        if (physicsComponent1.collisionStayed(physicsComponent2)) {
                            physicsComponent1.onCollisionStay(physicsComponent2);
                            continue;
                        }
                        physicsComponent1.addToCollisionManifold(physicsComponent2);
                        physicsComponent1.onCollide(physicsComponent2);
                    } else {
                        physicsComponent1.removeCollision(physicsComponent2);
                        physicsComponent1.onExit(physicsComponent2);
                    }
                }
            }

        }
    }

        public static boolean collidesAB(PhysicsComponent A, PhysicsComponent B) {
            return ( B.getX() + B.getWidth() >= A.getX() && B.getY() + B.getHeight() >= A.getY() && B.getX() <= A.getX() + A.getWidth() && B.getY() <= A.getY() + A.getHeight());

        }
}
