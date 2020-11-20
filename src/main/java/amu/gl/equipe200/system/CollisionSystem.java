package amu.gl.equipe200.system;

import amu.gl.equipe200.core.Entity;

public class CollisionSystem extends ASystem {
    /**
     * Collision engine
     */
    public void update() {
//        for( BaseEntity entity1: getEntities()) {
//            for( BaseEntity entity2: getEntities()) {
//                if(entity1.equals(entity2)) continue;
//                if (entity1.getCollisionsCheck().contains(entity2.getTag())) {
//                    if (collidesAB(entity1, entity2)) {
//                        if (entity1.collisionStayed(entity2)) {
//                            entity1.onCollisionStay(entity2);
//                            continue;
//                        }
//                        entity1.addToCollisionManifold(entity2);
//                        entity1.onCollide(entity2);
//                    }else{
//                        entity1.removeCollision(entity2);
//                        entity1.onExit(entity2);
//                    }
//                }
//            }
//        }
    }

    public boolean collidesAB(Entity A, Entity B) {
        return ( B.getX() + B.getWidth() >= A.getX() && B.getY() + B.getHeight() >= A.getY() && B.getX() <= A.getX() + A.getWidth() && B.getY() <= A.getY() + A.getHeight());

    }
}
