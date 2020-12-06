package amu.gl.equipe200.pacman.entities.factory;

import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.pacman.PacmanApp;
import amu.gl.equipe200.pacman.entities.pacman.Pacman;

public class PacmanFactory {

    PacmanApp app;
    boolean sizeSet, controlSet;
    private double width, height;
    private String up, down, left, right;

    public PacmanFactory(PacmanApp app) {
        this.app = app;
    }

    public PacmanFactory setSize (double width, double height) {
        this.width = width;
        this.height = height;
        this.sizeSet = true;
        return this;
    }

    public PacmanFactory setControls(String up, String down, String left, String right) {
        this.up = up;
        this.down = down;
        this.right = right;
        this.down = down;
        this.controlSet = true;
        return this;
    }
    public Pacman createAt(double x, double y) {
        Pacman p = new Pacman();
        if (sizeSet) {
            p.setWidth(width);
            p.setHeight(height);
        }
        if (controlSet) {
            p.setControls(up, down, right, left);
        }
        return p;
    }

    public Pacman createAtAndRegister(double x, double y, GameWorld world) {
        Pacman p = this.createAt(x, y);
        world.addPhysicsEntity(p);
        world.addGraphicsEntity(p);
        world.addIOEntity(p);
        return p;
    }

    public Pacman createATAndRegisterToCurrent(double x, double y) {
        Pacman p = this.createAt(x, y);
        app.addPhysicsEntityToCurrent(p);
        app.addGraphicsEntityToCurrent(p);
        app.addIOEntityToCurrent(p);
        return p;
    }
}
