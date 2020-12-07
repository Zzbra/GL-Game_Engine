package amu.gl.equipe200.pacman.entities;

import amu.gl.equipe200.core.Settings;
import amu.gl.equipe200.graphicsengine.GraphicsEngine;
import amu.gl.equipe200.graphicsengine.GraphicsInterface;
import amu.gl.equipe200.physicsengine.PhysicsInterface;

import java.util.ArrayList;


public class PowerUp
        extends Entity
        implements PhysicsInterface, GraphicsInterface {

    public PowerUp() {
        super(Settings.Tag.POWERUP_INVINCIBLE);
        setImageName("images/Fruit_Cherry.png");
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