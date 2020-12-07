package amu.gl.equipe200.pacman.entities;

import amu.gl.equipe200.IAEngine.*;
import amu.gl.equipe200.core.Settings;

import java.util.ArrayList;

public class Blinky
        extends Ghost
        implements IAInterface {

    Pacman pacMan;
    private ArrayList<String> animation;
    private int imageLastFrame;

    public Blinky() {
        super();
        this.animation = new ArrayList<>();
        this.animation.add("images/Ghost_Red_1.png");
        this.animation.add("images/Ghost_Red_2.png");
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

    @Override
    public String getImageName() {
        this.imageLastFrame++;
        int url = (imageLastFrame / 10) % this.animation.size();
        return this.animation.get(url);
    }
}
