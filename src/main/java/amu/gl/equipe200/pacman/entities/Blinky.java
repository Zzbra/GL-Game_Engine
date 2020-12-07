package amu.gl.equipe200.pacman.entities;

import amu.gl.equipe200.IAEngine.*;

import java.util.ArrayList;

public class Blinky
        extends Ghost
        implements IAInterface {

    Pacman pacMan;
    private ArrayList<String> animation;
    private ArrayList<String> animationFeared;

    private int imageLastFrame;

    public Blinky() {
        super();
        this.animation = new ArrayList<>();
        this.animation.add("images/Ghost_Red_1.png");
        this.animation.add("images/Ghost_Red_2.png");

        this.animationFeared = new ArrayList<>();
        this.animationFeared.add("images/Ghost_Scared_1.png");
        this.animationFeared.add("images/Ghost_Scared_2.png");
    }



    @Override
    public double getGoalX() {
        if(isFeared()){
            return getFearedGoal().first;
        }else {
            return getPacMan().getX();
        }
    }
    @Override
    public double getGoalY() {
        if(isFeared()){
            return getFearedGoal().second;
        }else {
            return getPacMan().getY();
        }
    }

    @Override
    public String getImageName() {
        this.imageLastFrame++;
        int url;
        if (this.isFeared()) {
            url = (imageLastFrame / 10) % this.animationFeared.size();
            return this.animationFeared.get(url);
        } else {
            url = (imageLastFrame / 10) % this.animation.size();
            return this.animation.get(url);
        }

    }

}
