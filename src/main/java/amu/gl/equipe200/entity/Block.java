package amu.gl.equipe200.entity;

import amu.gl.equipe200.graphicsengine.GraphicsInterface;
import amu.gl.equipe200.physicsengine.PhysicsInterface;

public class Block
        extends Entity
        implements GraphicsInterface, PhysicsInterface {

    /**
     * Physics variables
     */
    private double x, y;
    private double width, height;
    private volatile boolean isSolid;

    /**
     * Graphics variables
     */
    private String imageName;
    private String layerName;

    public Block(double x, double y,
                 double width, double height,
                 String imageName, String layerName) {
        this.setX(x);
        this.setY(y);
        this.width = width;
        this.height = height;
        this.imageName = imageName;
        this.layerName = layerName;
    }


    /******************************************************************************************************************
     *    Getters and Setters                                                                                         *
     ******************************************************************************************************************/
    @Override
    public double getX() { return this.x; }
    @Override
    public double getY() { return this.y; }
    @Override
    public void setX(double x) { this.x = x; }
    @Override
    public void setY(double y) { this.y = y; }

    @Override
    public void checkRemovability() {
        //TODO: remove !
    }

    @Override
    public double getXSpeed() { return 0d; }
    @Override
    public double getYSpeed() { return 0d; }

    @Override
    public double getWidth() {
        return this.width;
    }
    public void setWidth(double width) { this.width = width; }
    @Override
    public double getHeight() {
        return this.height;
    }
    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String getImageName() { return this.imageName; }
    @Override
    public String getLayerName(){ return this.layerName; }


    /******************************************************************************************************************
     *    Physics Engine behaviour                                                                                    *
     ******************************************************************************************************************/

    public boolean isSolid() { return true; }
    public boolean isCollidable() { return true; }


    /******************************************************************************************************************
     *    Graphics Engine behaviour                                                                                   *
     ******************************************************************************************************************/
    @Override
    public boolean needRemoval() { return false; }
}
