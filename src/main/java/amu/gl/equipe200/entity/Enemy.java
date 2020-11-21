package amu.gl.equipe200.entity;

import amu.gl.equipe200.Interfaces.Movable;
import amu.gl.equipe200.Interfaces.RenderableInterface;
import amu.gl.equipe200.core.Component.PhysicalComponent;
import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.gameworld.Settings;

public class Enemy extends BaseEntity implements Movable, RenderableInterface {

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
    public void onCollide(PhysicalComponent physicalComponent) {}

    @Override
    public void onCollisionStay(PhysicalComponent physicalComponent) {}

    @Override
    public void onExit(PhysicalComponent physicalComponent) {}

    @Override
    public void checkBounds() {

    }

    @Override
    public String getImageName() {
        return this.imageName;
    }
}