package amu.gl.equipe200.pacman.entities;

import amu.gl.equipe200.IAEngine.*;
import amu.gl.equipe200.core.Settings;
import amu.gl.equipe200.utils.Pair;

import java.util.ArrayList;

public class Inky
        extends Ghost
        implements IAInterface {

    Pacman pacMan;
    private ArrayList<String> animation;
    private ArrayList<String> animationFeared;

    private int imageLastFrame;

    public Inky() {
        super();
        this.animation = new ArrayList<>();
        this.animation.add("images/Ghost_Blue_1.png");
        this.animation.add("images/Ghost_Blue_2.png");

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
