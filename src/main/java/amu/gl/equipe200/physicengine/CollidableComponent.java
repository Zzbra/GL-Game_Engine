package amu.gl.equipe200.physicengine;

import amu.gl.equipe200.core.Component;
import amu.gl.equipe200.core.Entity;
import amu.gl.equipe200.utils.Pair;


public class CollidableComponent extends Component {

    private CollisionHandler collisionHandler;
    private Pair<Float, Float> hitboxSize;
    private boolean impassable;

    public CollidableComponent(CollisionHandler collisionHandler,
                               float H, float W,
                               boolean isImpassable) {
        super();
        this.collisionHandler = collisionHandler;
        this.hitboxSize = Pair.create(H,W);
        this.impassable = isImpassable;
    }

    @Override
    public void onAttach(Entity entity) {
        // ensure that all the required properties are defined in the entity
        entity.addProperty("X");
        entity.addProperty("Y");
        entity.addProperty("H");
        entity.addProperty("W");
        entity.addProperty("IMPASSABLE");

        // set the value of the properties
        entity.setProperty("H", this.hitboxSize.first);
        entity.setProperty("W", this.hitboxSize.second);
        entity.setProperty("IMPASSABLE", this.impassable);
    }

    protected float getX() { return (Float) this.getEntity().getProperty("X"); }
    protected float getY() { return (Float) this.getEntity().getProperty("Y"); }
    protected float getH() { return (Float) this.getEntity().getProperty("H"); }
    protected float getW() { return (Float) this.getEntity().getProperty("W"); }
    protected boolean isImpassable() { return (Boolean) this.getEntity().getProperty("IMPASSABLE"); }

    // TODO: je sais pas trop
    protected void onCollide(CollidableComponent other) { collisionHandler.onCollide(other); }
}
