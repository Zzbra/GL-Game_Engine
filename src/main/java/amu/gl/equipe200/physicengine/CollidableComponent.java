package amu.gl.equipe200.physicengine;

import amu.gl.equipe200.core.Entity;


public class CollidableComponent extends PhysicComponent {

    private CollisionHandler collisionHandler;
    private boolean impassable;

    public CollidableComponent(Double hitboxH, Double hitboxW,
                               CollisionHandler collisionHandler,
                               boolean isImpassable) {
        super(hitboxH, hitboxW);
        this.collisionHandler = collisionHandler;
        this.impassable = isImpassable;
    }

    @Override
    public void onAttach(Entity entity) {
        super.onAttach(entity);

        entity.setProperty("IMPASSABLE", this.impassable);
    }

    protected boolean isImpassable() { return (Boolean) this.getEntity().getProperty("IMPASSABLE"); }

    // TODO: je sais pas trop
    protected void onCollide(Entity other) { collisionHandler.onCollide(other); }
}
