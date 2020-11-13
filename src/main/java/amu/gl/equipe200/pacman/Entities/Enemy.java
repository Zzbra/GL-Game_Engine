package amu.gl.equipe200.pacman.Entities;

import amu.gl.equipe200.core.EntityOld;
import amu.gl.equipe200.core.GameSettings;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Enemy extends EntityOld {

    private GameSettings.Tag tag = GameSettings.Tag.ENEMY;

    public Enemy(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health, double damage) {
        super(layer, image, x, y, r, dx, dy, dr, health, damage);
    }

    @Override
    public void checkRemovability() {
        if( Double.compare( getY(), GameSettings.SCENE_HEIGHT) > 0) {
            setRemovable(true);
        }
    }


    /*** Colidable ***/
    @Override
    public void onCollide(EntityOld entityOld) {}

    @Override
    public void onCollisionStay(EntityOld entityOld2) {}

    @Override
    public void onExit(EntityOld entityOld2) {}
}