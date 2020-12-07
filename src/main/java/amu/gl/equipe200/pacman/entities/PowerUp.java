package amu.gl.equipe200.pacman.entities;

import amu.gl.equipe200.core.Settings;
import amu.gl.equipe200.graphicsengine.GraphicsInterface;
import amu.gl.equipe200.physicsengine.PhysicsInterface;


public class PowerUp
        extends Entity
        implements PhysicsInterface, GraphicsInterface {

    public PowerUp() {
        super(Settings.Tag.POWERUP_INVINCIBLE);
        setImageName("Fruit_Cherry.png");
        setLayerName("BACKGROUND");
    }

    /******************************************************************************************************************
     *    Physics Engine behaviour                                                                                    *
     ******************************************************************************************************************/
    @Override
    public boolean isCollidable() { return true; }

    public void onCollide(PhysicsInterface others){
        if(others.getTag()==Settings.Tag.PLAYER){
            toRemove();
        }
    }
}