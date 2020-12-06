package amu.gl.equipe200.pacman.entities;

import amu.gl.equipe200.graphicsengine.GraphicsEngine;
import amu.gl.equipe200.graphicsengine.GraphicsInterface;
import amu.gl.equipe200.physicsengine.PhysicsInterface;
import amu.gl.equipe200.core.Settings;

import java.util.ArrayList;

public class Ghost
        extends Entity
        implements PhysicsInterface, GraphicsInterface {

    /***  Physics Flags  ***/

    /***  Graphics Flags ***/
    private ArrayList<String> animation;
    private int imageLastFrame;
    private boolean hasMoved;

    public Ghost() {
        this.animation = new ArrayList<>();
        this.animation.add("images/Ghost_Red_1.png");
        this.animation.add("images/Ghost_Red_2.png");
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
    public Settings.Tag getTag() { return null; }

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
    public boolean needRemoval() { return false; }
    @Override
    public boolean hasMoved() { return this.hasMoved; }

    @Override
    public boolean hasNewSprite() { return true; }
    @Override
    public String getImageName() {
        this.imageLastFrame++;
        int url = (imageLastFrame / 30) % this.animation.size();
        return this.animation.get(url);
    }

    @Override
    public void onProcessed(GraphicsEngine engine) {
        this.hasMoved = false;
    }
}