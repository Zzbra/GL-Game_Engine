package amu.gl.equipe200.core;

import amu.gl.equipe200.utils.Pair;

public class Settings {
    public static double SCENE_WIDTH = 800;
    public static double SCENE_HEIGHT = 800;

    public static double WORLD_WIDTH = 16;
    public static double WORLD_HEIGHT = 16;

    public static double PLAYER_SPEED = 5;
    public static double ENEMY_SPEED = 4;

    public static enum Tag { PLAYER, ENEMY,
                             POWERUP_INVINCIBLE, POWERUP_WALLPASS,
                             PACGUM, WALL }

    // Size of the physical world in which the entities are
    private Pair<Double, Double> worldSize = Pair.create(100d, 100d);
    public void setWorldSize(double width, double height) { this.worldSize = Pair.create(width, height); }
    public Pair<Double, Double> getWorldSize() { return this.worldSize; }
}