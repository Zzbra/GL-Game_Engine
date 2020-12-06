package amu.gl.equipe200.pacman.entities;

import amu.gl.equipe200.IAEngine.*;
import amu.gl.equipe200.core.Settings;

import java.util.ArrayList;

public class Blinky extends Ghost implements IAInterface {
    Pacman pacMan;
    ArrayList<Cell> path;
    Cell now;


    public Blinky() {
        super();
        this.path= new ArrayList<>();
        this.now = new Cell((int)getX(), (int)getY(), true);
        setImageName("Images/Ghost_blue_1");
        setLayerName("FOREGROUND");
    }


    public void setPacMan(Pacman pacMan){ this.pacMan = pacMan;}

    @Override
    public double getGoalX() {
        return pacMan.getX();
    }

    @Override
    public double getGoalY() {
        return pacMan.getY();
    }
}
