package amu.gl.equipe200.physicengine;

import amu.gl.equipe200.core.Component;
import amu.gl.equipe200.core.Entity;
import amu.gl.equipe200.utils.Pair;


public abstract class PhysicComponent
        extends Component {

    private Pair<Double, Double> hitboxSize;

    public PhysicComponent(Double Height, Double Width) {
        super();
        this.hitboxSize = Pair.create(Height,Width);
    }

    @Override
    public void onAttach(Entity entity) {
        // ensure that all the required properties are defined in the entity
        entity.addProperty("X");
        entity.addProperty("Y");
        entity.addProperty("SPEEDX");
        entity.addProperty("SPEEDY");

        // set the value of the hitbox
        entity.setProperty("HEIGHT", this.hitboxSize.first);
        entity.setProperty("WIDTH", this.hitboxSize.second);
    }

    protected double getX() { return (double) this.getEntity().getProperty("X"); }
    protected double getY() { return (double) this.getEntity().getProperty("Y"); }
    protected double getSpeedX() { return (double) this.getEntity().getProperty("SPEEDX"); }
    protected double getSpeedY() { return (double) this.getEntity().getProperty("SPEEDY"); }
    protected double getWidth() { return (double) this.getEntity().getProperty("WIDTH"); }
    protected double getHeight() { return (double) this.getEntity().getProperty("HEIGHT"); }
}
