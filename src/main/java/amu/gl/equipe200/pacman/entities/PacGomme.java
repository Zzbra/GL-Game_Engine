package amu.gl.equipe200.pacman.entities;

import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.core.Settings;
import amu.gl.equipe200.graphicsengine.GraphicsInterface;
import amu.gl.equipe200.pacman.entities.Entity;
import amu.gl.equipe200.physicsengine.PhysicsInterface;

import java.util.Set;


public class PacGomme
        extends Entity
        implements PhysicsInterface, GraphicsInterface {


    public PacGomme(){
    }

    /******************************************************************************************************************
     *    Physics Engine behaviour                                                                                    *
     ******************************************************************************************************************/
    @Override
    public boolean isCollidable() { return true; }
    @Override
    public boolean isRemovable() { return false; }

    @Override
    public Settings.Tag getTag() {
        return null;
    }

    /******************************************************************************************************************
     *    Graphics Engine behaviour                                                                                   *
     ******************************************************************************************************************/
    @Override
    public boolean needRemoval(){ return false; }
}