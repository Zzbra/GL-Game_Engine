package amu.gl.equipe200.pacman.entities;

import amu.gl.equipe200.core.Settings;
import amu.gl.equipe200.graphicsengine.GraphicsInterface;
import amu.gl.equipe200.physicsengine.PhysicsInterface;

public class Wall
        extends Entity
        implements GraphicsInterface, PhysicsInterface {

    /***  Physics Flags  ***/
    private volatile boolean isSolid;

    public Wall() {
        super(Settings.Tag.WALL);
        setImageName(("images/Wall.png"));
        setLayerName("BACKGROUND");
    }


    /******************************************************************************************************************
     *    Physics Engine behaviour                                                                                    *
     ******************************************************************************************************************/

    public boolean isSolid() { return true; }

    @Override
    public boolean isRemovable() {
        return false;
    }

    @Override
    public Settings.Tag getTag() {
        return null;
    }

    public boolean isCollidable() { return true; }


    /******************************************************************************************************************
     *    Graphics Engine behaviour                                                                                   *
     ******************************************************************************************************************/
}
