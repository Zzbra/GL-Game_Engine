package amu.gl.equipe200.physicengine;

import amu.gl.equipe200.core.Component;
import amu.gl.equipe200.core.Entity;
import amu.gl.equipe200.utils.Pair;

public class MovableComponent extends PhysicComponent {

    Pair<Float, Float> initialSpeed;

    public MovableComponent (float speedX, float speedY,
                             float hitboxW, float hitboxH) {
        super(hitboxW, hitboxH);
        this.initialSpeed = Pair.create(speedX, speedY);
    }


    /***** Getter and Setter *****/
    protected void moveTo(float X, float Y) {
        this.getEntity().setProperty("X", X);
        this.getEntity().setProperty("Y", Y);
    }
    protected void setSpeed(float speedX, float speedY) {
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
