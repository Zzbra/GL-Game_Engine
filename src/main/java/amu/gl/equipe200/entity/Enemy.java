package amu.gl.equipe200.entity;

import amu.gl.equipe200.entity.BaseEntity;
import amu.gl.equipe200.gameworld.Settings;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Enemy extends BaseEntity {

    public Enemy(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health, double damage) {
        super(layer, image, x, y, r, dx, dy, dr, health, damage);
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
    public void onCollide(BaseEntity entity) {}

    @Override
    public void onCollisionStay(BaseEntity entity2) {}

    @Override
    public void onExit(BaseEntity entity2) {}
}