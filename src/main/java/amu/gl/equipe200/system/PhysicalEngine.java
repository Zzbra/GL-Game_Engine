package amu.gl.equipe200.system;

import amu.gl.equipe200.core.Component.Component;
import amu.gl.equipe200.core.Component.PhysicalComponent;
import amu.gl.equipe200.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public class PhysicalEngine {

    private ArrayList<PhysicalComponent> physicalComponents;

//    public void update(List<PhysicalComponent> componentList){
//       for(PhysicalComponent physicalComponent : componentList){
//           moveComponent(physicalComponent);
//       }
//       checkCollision(componentList);
//    }



    public void update(List<Component> componentList){
        physicalComponents = new ArrayList<>();
        for(Component component : componentList){
            component.updateBy(this);
        }
        System.out.println(physicalComponents.size()==componentList.size());
        checkCollision(physicalComponents);
    }

    public void update(PhysicalComponent physicalComponent){
        moveComponent(physicalComponent);
        physicalComponents.add(physicalComponent);
    }

    public void moveComponent(PhysicalComponent component){

        if( !component.canMove())
            return;
        component.setX(component.getX() + component.getDx());
        component.setY(component.getY() + component.getDy());
        component.setR(component.getR() + component.getDr());
        if(component.getIsBounded()){
            checkBounds(component);
        }
    }

    private void checkBounds(PhysicalComponent physicalComponent) {

        // vertical
        if( Double.compare( physicalComponent.getY(), physicalComponent.getMinY()) < 0) {
            physicalComponent.setY(physicalComponent.getMinY());
        } else if( Double.compare(physicalComponent.getY(), physicalComponent.getMaxY()) > 0) {
            physicalComponent.setY(physicalComponent.getMaxY());
        }

        // horizontal
        if( Double.compare( physicalComponent.getX(), physicalComponent.getMinX()) < 0) {
            physicalComponent.setX(physicalComponent.getMinX());
        } else if( Double.compare(physicalComponent.getX(), physicalComponent.getMaxX()) > 0) {
            physicalComponent.setX(physicalComponent.getMaxX());
        }

    }

    public void checkCollision(List<PhysicalComponent> componentList) {
        for (PhysicalComponent physicalComponent1 : componentList) {
            for (PhysicalComponent physicalComponent2 : componentList) {
                if (physicalComponent1.equals(physicalComponent2)) continue;
                if (physicalComponent1.getCollisionsCheck().contains(physicalComponent2.getTag())) {
                    if (collidesAB(physicalComponent1, physicalComponent2)) {
                        if (physicalComponent1.collisionStayed(physicalComponent2)) {
                            physicalComponent1.onCollisionStay(physicalComponent2);
                            continue;
                        }
                        physicalComponent1.addToCollisionManifold(physicalComponent2);
                        physicalComponent1.onCollide(physicalComponent2);
                    } else {
                        physicalComponent1.removeCollision(physicalComponent2);
                        physicalComponent1.onExit(physicalComponent2);
                    }
                }
            }

        }
    }

        public static boolean collidesAB(PhysicalComponent A, PhysicalComponent B) {
            return ( B.getX() + B.getWidth() >= A.getX() && B.getY() + B.getHeight() >= A.getY() && B.getX() <= A.getX() + A.getWidth() && B.getY() <= A.getY() + A.getHeight());

        }
}
