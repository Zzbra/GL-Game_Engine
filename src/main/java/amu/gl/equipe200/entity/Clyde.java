package amu.gl.equipe200.entity;
import static java.lang.Math.abs;

import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.gameworld.Settings;

import java.util.Random;

public class Clyde extends Enemy {
    Player pacMan;

    public Clyde(double x, double y, double r, double xSpeed, double ySpeed, double dr, double health, double damage, GameWorld gamescene, String imageName, String layerName, Player pacMan) {
        super(x, y, r, xSpeed, ySpeed, dr, health, damage, gamescene, imageName, layerName);
        this.pacMan = pacMan;
    }

    public void update() {
        double Xd, Yd;
        Xd = getX() - pacMan.getX();
        Yd = getY() - pacMan.getY();

        double rng = Math.random() % 100;
        if (rng < 50) {
            if (Math.abs(Xd) >= Math.abs(Yd)) {
                if (Xd < 0) {
                    setXSpeed(Settings.ENEMY_SHIP_SPEED);
                    setYSpeed(0);
                } else {
                    setXSpeed(-Settings.ENEMY_SHIP_SPEED);
                    setYSpeed(0);
                }
            } else {
                if (Yd < 0) {
                    setXSpeed(0);
                    setYSpeed(Settings.ENEMY_SHIP_SPEED);
                } else {
                    setXSpeed(0);
                    setYSpeed(-Settings.ENEMY_SHIP_SPEED);
                }
            }
        }
    }
}
