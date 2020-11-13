package amu.gl.equipe200.physicengine;

import amu.gl.equipe200.core.Entity;
import amu.gl.equipe200.utils.Pair;

public class MovableComponent extends PhysicComponent {

    Pair<Double, Double> initialSpeed;

    public MovableComponent (Double speedX, Double speedY,
                             Double hitboxW, Double hitboxH) {
        super(hitboxW, hitboxH);
        this.initialSpeed = Pair.create(speedX, speedY);
    }

    /***** Getter and Setter *****/
    protected void moveTo(Double X, Double Y) {
        this.getEntity().setProperty("X", X);
        this.getEntity().setProperty("Y", Y);
    }
    protected void setSpeed(Double speedX, Double speedY) {
        this.getEntity().setProperty("SPEEDX", speedX);
        this.getEntity().setProperty("SPEEDY", speedY);
    }

    @Override
    public void onAttach(Entity entity) {
        // call the general procedure for physical component
        super.onAttach(entity);

        // set the value of the properties
        entity.setProperty("SPEEDX", this.initialSpeed.first);
        entity.setProperty("SPEEDY", this.initialSpeed.second);
    }
}
