package amu.gl.equipe200.physicsengine;

public class Collision {
    PhysicsInterface entity1, entity2;

    public Collision(PhysicsInterface entity1, PhysicsInterface entity2) {
        this.entity1 = entity1;
        this.entity2 = entity2;
    }

    public PhysicsInterface getEntity1() {
        return entity1;
    }

    public PhysicsInterface getEntity2() {
        return entity2;
    }
}
