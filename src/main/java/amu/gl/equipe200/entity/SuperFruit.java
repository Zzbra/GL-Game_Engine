package amu.gl.equipe200.entity;

import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.gameworld.Settings;
import amu.gl.equipe200.physicsengine.PhysicsInterface;
import amu.gl.equipe200.graphicsengine.RenderableInterface;


public class SuperFruit extends Entity implements PhysicsInterface, RenderableInterface{

    private String imageName;
    private String layerName;
    private double width = 50, height = 50;

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
    @Override
    public void checkRemovability(){
    }

    @Override
    public String getImageName() {
        return this.imageName;
    }

    @Override
    public String getLayerName() {
        return this.layerName;
    }

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
    public boolean isWorldBounded() { return true; }
    @Override
    public boolean isCollidable() { return true; }
    @Override
    public boolean isSolid() { return false; }

    public void onCollide(PhysicsInterface others) {
        // TODO
        System.out.println(this.toString() + " has collided with " + others.toString());

        if(others.getTag() == Settings.Tag.valueOf("PLAYER")){
            setRemovable(true);
        }
    }

}