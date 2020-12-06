package amu.gl.equipe200.entity;

import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.core.Settings;
import amu.gl.equipe200.graphicsengine.GraphicsInterface;
import amu.gl.equipe200.physicsengine.PhysicsInterface;

import java.util.Set;


public class PacGomme
        extends Entity
        implements PhysicsInterface, GraphicsInterface {

    /**
     * Graphics variables
     */
    private double r;
    private String imageName;
    private String layerName;

    private double width = 0.2, height = 0.2;

    public PacGomme(double x, double y,
                      String imageName,
                      String layerName) {
        setX(x + 0.4);
        setY(y + 0.4);
        this.imageName = imageName;
        this.layerName = layerName;
        setTag(Settings.Tag.PACGOMME);
    }

    /******************************************************************************************************************
     *    Getters and Setters                                                                                         *
     ******************************************************************************************************************/

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
    public void setXSpeed(double dx) {
    }

    @Override
    public void setYSpeed(double dy) {

    }

    @Override
    public String getImageName() { return this.imageName; }
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
        if (others.getTag() == Settings.Tag.valueOf("PLAYER")) {
            setRemovable(true);
            // TODO
        }
    }

    /******************************************************************************************************************
     *    Graphics Engine behaviour                                                                                   *
     ******************************************************************************************************************/
    @Override
    public boolean needRemoval(){ return false; }

    @Override
    public void checkRemovability(){
    }
}