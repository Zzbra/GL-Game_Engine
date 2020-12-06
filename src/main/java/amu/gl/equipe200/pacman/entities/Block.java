package amu.gl.equipe200.pacman.entities;

import amu.gl.equipe200.core.Settings;
import amu.gl.equipe200.graphicsengine.GraphicsInterface;
import amu.gl.equipe200.physicsengine.PhysicsInterface;

import java.util.Formatter;

public class Block
        extends Entity
        implements GraphicsInterface, PhysicsInterface {

    /***  Physics Flags  ***/
    private volatile boolean isSolid;

    public Block() {
        super(Settings.Tag.WALL);
        this.setImageName("Block.jpg");
        this.setLayerName("BACKGROUND");
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
    @Override
    public boolean needRemoval() { return false; }
}
