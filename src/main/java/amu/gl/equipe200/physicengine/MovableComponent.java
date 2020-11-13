package amu.gl.equipe200.physicengine;

import amu.gl.equipe200.core.Component;
import amu.gl.equipe200.core.Entity;
import amu.gl.equipe200.utils.Pair;

public class MovableComponent extends Component {

    Pair<Float, Float> initialSpeed;

    public MovableComponent (float speedX, float speedY) {
        this.initialSpeed = Pair.create(speedX, speedY);
    }

    /***** Getter and Setter *****/
    protected float getX() { return (Float) this.getEntity().getProperty("X"); }
    protected float getY() { return (Float) this.getEntity().getProperty("X"); }
    protected float getSpeedX() { return (Float) this.getEntity().getProperty("SPEEDX"); }
    protected float getSpeedY() { return  (Float) this.getEntity().getProperty("SPEEDY"); }

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
        // ensure that all the required properties are defined in the entity
        entity.addProperty("X");
        entity.addProperty("Y");
        entity.addProperty("SPEEDX");
        entity.addProperty("SPEEDY");

        // set the value of the properties
        entity.setProperty("SPEEDX", this.initialSpeed.first);
        entity.setProperty("SPEEDY", this.initialSpeed.second);
    }
}
