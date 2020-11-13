package amu.gl.equipe200.physicengine;

import amu.gl.equipe200.core.Component;
import amu.gl.equipe200.core.Entity;
import amu.gl.equipe200.utils.Pair;


public class CollidableComponent extends PhysicComponent {

    private CollisionHandler collisionHandler;
    private boolean impassable;

    public CollidableComponent(float hitboxH, float hitboxW,
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
    protected void onCollide(CollidableComponent other) { collisionHandler.onCollide(other); }
}
