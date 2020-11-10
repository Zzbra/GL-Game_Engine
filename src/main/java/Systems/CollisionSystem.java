package Systems;

import Entity.BaseEntity;

import java.util.ArrayList;

public class CollisionService extends ISystem {
    ArrayList<BaseEntity> entities;
    /**
     * Collision engine
     */
    private void update() {

        for( BaseEntity entity1: entities) {
            for( BaseEntity entity2: entities) {
                if (collidesAB(entity1, entity2)) {
                    if (entity1.collisionStayed(entity2)) {
                        entity1.onCollisionStay(entity2);
                        continue;
                    }
                    entity1.addToCollisionManifold(entity2);
                    entity1.onCollide(entity2);

                }else
                if (entity1.collideWithTags().contains(entity2.getTag())) {
                    entity1.removeCollision(entity2);
                }
            }
        }
    }

    public boolean collidesAB(BaseEntity A, BaseEntity B) {
        return ( B.x + B.w >= A.x && B.y + B.h >= A.y && B.x <= A.x + A.w && B.y <= A.y + A.h);

    }
}
