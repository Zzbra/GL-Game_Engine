package amu.gl.equipe200.physicengine;

import amu.gl.equipe200.core.Component;
import amu.gl.equipe200.core.Entity;
import amu.gl.equipe200.utils.Pair;


public class PhysicComponent extends Component {

    private Pair<Double, Double> hitboxSize;

    public PhysicComponent(Double H, Double W) {
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

    protected Double getX() { return (Double) this.getEntity().getProperty("X"); }
    protected Double getY() { return (Double) this.getEntity().getProperty("Y"); }
    protected Double getSpeedX() { return (Double) this.getEntity().getProperty("SPEEDX"); }
    protected Double getSpeedY() { return (Double) this.getEntity().getProperty("SPEEDY"); }
    protected Double getH() { return (Double) this.getEntity().getProperty("H"); }
    protected Double getW() { return (Double) this.getEntity().getProperty("W"); }
}
