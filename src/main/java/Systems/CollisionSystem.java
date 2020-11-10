package Systems;

import Entity.BaseEntity;

public class CollisionSystem extends ASystem {
    /**
     * Collision engine
     */
    public void update() {
        for( BaseEntity entity1: entities) {
            for( BaseEntity entity2: entities) {
                if(entity1.equals(entity2)) continue;
                if (entity1.getCollisionsCheck().contains(entity2.getTag())) {
                    if (collidesAB(entity1, entity2)) {
                        if (entity1.collisionStayed(entity2)) {
                            entity1.onCollisionStay(entity2);
                            continue;
                        }
                        entity1.addToCollisionManifold(entity2);
                        entity1.onCollide(entity2);
                    }else{
                        entity1.removeCollision(entity2);
                        entity1.onExit(entity2);
                    }
                }
            }
        }
    }

    public boolean collidesAB(BaseEntity A, BaseEntity B) {
        return ( B.x + B.w >= A.x && B.y + B.h >= A.y && B.x <= A.x + A.w && B.y <= A.y + A.h);

    }
}
