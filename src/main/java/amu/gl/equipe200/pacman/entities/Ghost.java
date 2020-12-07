package amu.gl.equipe200.pacman.entities;

import amu.gl.equipe200.graphicsengine.GraphicsEngine;
import amu.gl.equipe200.graphicsengine.GraphicsInterface;
import amu.gl.equipe200.physicsengine.PhysicsInterface;
import amu.gl.equipe200.core.Settings;

import java.util.ArrayList;

public class Ghost
        extends Entity
        implements PhysicsInterface, GraphicsInterface {

    /***  Graphics Flags ***/
    private boolean hasMoved;

    public Ghost() {
        super(Settings.Tag.ENEMY);
        setLayerName("FOREGROUND");

    }

    /******************************************************************************************************************
     *    Getters and Setters                                                                                         *
     ******************************************************************************************************************/
    @Override
    public void setX(double x) { super.setX(x); this.hasMoved = true; }
    @Override
    public void setY(double y) { super.setY(y); this.hasMoved = true; }

    /******************************************************************************************************************
     *    Physics Engine behaviour                                                                                    *
     ******************************************************************************************************************/
    @Override
    public boolean isMovable() { return true; }
    @Override
    public boolean isWorldBounded() { return true; }
    @Override
    public boolean isCollidable() { return true; }
    @Override
    public boolean isSolid() { return true; }

    @Override
    public boolean isRemovable() { return false; }

    @Override
    public Settings.Tag getTag() { return Settings.Tag.ENEMY; }

    @Override
    public void onWorldEnds() {
        // TODO
        //System.out.println(this.toString() + " has reach the end of the world");
    }
    @Override
    public void onCollide(PhysicsInterface others) {
        // TODO
        //System.out.println(this.toString() + " has collided with " + others.toString());
    }


    /******************************************************************************************************************
     *    Graphics Engine behaviour                                                                                   *
     ******************************************************************************************************************/
    @Override
    public boolean hasMoved() { return this.hasMoved; }

    @Override
    public boolean hasNewSprite() { return true; }


    @Override
    public void onGraphicsProcessed() {
        this.hasMoved = false;
    }
}