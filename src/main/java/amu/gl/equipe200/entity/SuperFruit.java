package amu.gl.equipe200.entity;

import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.gameworld.Settings;
import amu.gl.equipe200.graphicsengine.GraphicsEngine;
import amu.gl.equipe200.graphicsengine.GraphicsInterface;
import amu.gl.equipe200.physicsengine.PhysicsInterface;


public class SuperFruit
        extends Entity
        implements PhysicsInterface, GraphicsInterface {

    /**
     * Graphics variables
     */
    private double r;
    private String imageName;
    private String layerName;

    private double width = 35, height = 35;

    public SuperFruit(double x, double y,
                      GameWorld gamescene,
                      String imageName,
                      String layerName) {
        setX(x);
        setY(y);
        // TODO: add width and height in the constructor ?

        this.setTag(Settings.Tag.ENEMY);
        this.imageName = imageName;
        this.layerName = layerName;
        setTag(Settings.Tag.FRUIT);
    }


    /******************************************************************************************************************
     *    Getters and Setters                                                                                         *
     ******************************************************************************************************************/
    @Override
    public double getX() { return this.getX(); }
    @Override
    public double getY() { return this.getY(); }
    @Override
    public void setX(double x) { this.setX(x); }
    @Override
    public void setY(double y) { this.setY(y); }

    @Override
    public double getXSpeed() {
        return 0;
    }
    @Override
    public double getYSpeed() {
        return 0;
    }

    @Override
    public double getWidth() {
        return this.width;
    }
    @Override
    public double getHeight() {
        return this.height;
    }

    @Override
    public String getImageName(long ellapsedTime) { return this.imageName; }
    @Override
    public String getLayerName(){ return this.layerName; }


    /******************************************************************************************************************
     *    Physics Engine behaviour                                                                                    *
     ******************************************************************************************************************/
    @Override
    public boolean isCollidable() { return true; }
    @Override
    public boolean isSolid() { return false; }

    public void onCollide(PhysicsInterface others) {
        // TODO
       // System.out.println(this.toString() + " has collided with " + others.toString());

        if(others.getTag() == Settings.Tag.valueOf("PLAYER")){
            setRemovable(true);
        }
    }

    /******************************************************************************************************************
     *    Graphics Engine behaviour                                                                                   *
     ******************************************************************************************************************/
    @Override
    public boolean needRemoval() { return false; }

    @Override
    public void checkRemovability(){
    }
}