package amu.gl.equipe200.physicengine;

import amu.gl.equipe200.core.Component;
import amu.gl.equipe200.core.Entity;
import amu.gl.equipe200.utils.Pair;


public class PhysicComponent extends Component {

    private Pair<Float, Float> hitboxSize;

    public PhysicComponent(float H, float W) {
        super();
        this.hitboxSize = Pair.create(H,W);
    }

    @Override
    public void onAttach(Entity entity) {
        // ensure that all the required properties are defined in the entity
        entity.addProperty("X");
        entity.addProperty("Y");
        entity.addProperty("SPEEDX");
        entity.addProperty("SPEEDY");
        entity.addProperty("H");
        entity.addProperty("W");
    }

    protected float getX() { return (Float) this.getEntity().getProperty("X"); }
    protected float getY() { return (Float) this.getEntity().getProperty("Y"); }
    protected float getSpeedX() { return (Float) this.getEntity().getProperty("SPEEDX"); }
    protected float getSpeedY() { return (Float) this.getEntity().getProperty("SPEEDY"); }
    protected float getH() { return (Float) this.getEntity().getProperty("H"); }
    protected float getW() { return (Float) this.getEntity().getProperty("W"); }
}
