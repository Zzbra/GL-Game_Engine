package amu.gl.equipe200.pacman.entities;

import amu.gl.equipe200.graphicsengine.GraphicsInterface;
import amu.gl.equipe200.physicsengine.PhysicsInterface;
import amu.gl.equipe200.core.Settings;
import amu.gl.equipe200.utils.Pair;

import java.util.ArrayList;

public class Ghost
        extends Entity
        implements PhysicsInterface, GraphicsInterface {

    /***Physics Flags  ***/

    /***  Graphics Flags ***/
    private ArrayList<String> animation;
    private int imageLastFrame;
    private boolean hasMoved;
    private Pair<Double, Double> fearedGoal;
    private boolean isFeared;
    private Pacman pacMan;
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

    public void setPacMan(Pacman pacMan){ this.pacMan = pacMan;}

    public Pacman getPacMan() {
        return pacMan;
    }

    public boolean isFeared() {
        return isFeared;
    }

    public Pair<Double, Double> getFearedGoal() {
        return fearedGoal;
    }

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
    public boolean isSolid() { return false; }

    @Override
    public boolean isRemovable() { return false; }

    @Override
    public Settings.Tag getTag() { return Settings.Tag.ENEMY; }

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



    public void fear(Pair<Double, Double> point){
        isFeared = true;
        fearedGoal = point;
    }

    public void fearEnd(){ isFeared = false;}

}