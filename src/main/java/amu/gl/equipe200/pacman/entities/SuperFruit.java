package amu.gl.equipe200.pacman.entities;

import amu.gl.equipe200.core.Settings;
import amu.gl.equipe200.graphicsengine.GraphicsEngine;
import amu.gl.equipe200.graphicsengine.GraphicsInterface;
import amu.gl.equipe200.physicsengine.PhysicsInterface;


public class SuperFruit
        extends Entity
        implements PhysicsInterface, GraphicsInterface {

    /*** Graphics Flags  ***/


    public SuperFruit() {
        super(Settings.Tag.POWERUP_WALLPASS);
        setImageName("SuperFruit.jpg");
        setLayerName("BACKGROUND");
    }


    /******************************************************************************************************************
     *    Getters and Setters                                                                                         *
     ******************************************************************************************************************/


    /******************************************************************************************************************
     *    Physics Engine behaviour                                                                                    *
     ******************************************************************************************************************/
    @Override
    public boolean isCollidable() { return true; }

    @Override
    public boolean isRemovable() {
        return false;
    }

    @Override
    public Settings.Tag getTag() {
        return null;
    }

    public void onCollide(PhysicsInterface others) {
        // TODO
       // System.out.println(this.toString() + " has collided with " + others.toString());

        if(others.getTag() == Settings.Tag.valueOf("PLAYER")){
//            setRemovable(true);
        }
    }

    /******************************************************************************************************************
     *    Graphics Engine behaviour                                                                                   *
     ******************************************************************************************************************/
    @Override
    public boolean needRemoval() { return false; }
}