package amu.gl.equipe200.entity;

import amu.gl.equipe200.physicsengine.Movable;
import amu.gl.equipe200.graphicsengine.RenderableInterface;
import amu.gl.equipe200.core.Entity;
import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.gameworld.Settings;
import amu.gl.equipe200.physicsengine.PhysicsComponent;

public class Enemy extends Entity implements Movable, RenderableInterface {

    private String imageName, layerName;
    public Enemy(double x, double y, double r, double dx, double dy, double dr, double health, double damage, GameWorld gamescene, String imageName, String layerName) {
        super(x, y, r, dx, dy, dr, health, damage, gamescene);
        this.setTag(Settings.Tag.ENEMY);
        this.imageName = imageName;
        this.layerName = layerName;
    }

    @Override
    public void checkRemovability() {
        if( Double.compare( getY(), Settings.SCENE_HEIGHT) > 0) {
            setRemovable(true);
        }
    }

    public String getLayerName(){return this.layerName;}

    /*** Colidable ***/
    @Override
    public void onCollide(PhysicsComponent physicsComponent) {}

    @Override
    public void onCollisionStay(PhysicsComponent physicsComponent) {}

    @Override
    public void onExit(PhysicsComponent physicsComponent) {}

    @Override
    public void checkBounds() {

    }

    @Override
    public String getImageName() {
        return this.imageName;
    }
}