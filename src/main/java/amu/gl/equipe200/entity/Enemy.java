package amu.gl.equipe200.entity;

import amu.gl.equipe200.physicsengine.PhysicsComponent;
import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.gameworld.Settings;

public class Enemy extends BaseEntity {

    public Enemy(double x, double y, double r, double dx, double dy, double dr, double health, double damage, GameWorld gamescene) {
        super(x, y, r, dx, dy, dr, health, damage, gamescene);
        this.setTag(Settings.Tag.ENEMY);
    }

    @Override
    public void checkRemovability() {
        if( Double.compare( getY(), Settings.SCENE_HEIGHT) > 0) {
            setRemovable(true);
        }
    }


    /*** Colidable ***/
    @Override
    public void onCollide(PhysicsComponent physicsComponent) {}

    @Override
    public void onCollisionStay(PhysicsComponent physicsComponent) {}

    @Override
    public void onExit(PhysicsComponent physicsComponent) {}
}