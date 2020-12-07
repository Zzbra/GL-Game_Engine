package amu.gl.equipe200.pacman.entities;

import amu.gl.equipe200.IAEngine.IAInterface;

import java.util.ArrayList;

public class Pinky extends Ghost implements IAInterface {


    private double aStarChangeTime;
    private double aStarCycleLength;
    private double changeTime;
    private double cycleLength;
    private boolean isAstar;
    private double goalX, goalY;
    private Pacman pacman;

    private ArrayList<String> animation;
    private ArrayList<String> animationFeared;
    private int imageLastFrame;

    public Pinky() {
        super();
        setLayerName("FOREGROUND");

        this.animation = new ArrayList<>();
        this.animation.add("Ghost_Pink_1.png");
        this.animation.add("Ghost_Pink_2.png");

        this.animationFeared = new ArrayList<>();
        this.animationFeared.add("Ghost_Scared_1.png");
        this.animationFeared.add("Ghost_Scared_2.png");

        changeTime = 0;
        aStarChangeTime = 0;
        cycleLength = 3;
        aStarCycleLength = 6;
        isAstar = true;
    }
    public void initGoal(){
        goalX = getX();
        goalY = getY();
        actualizeGoal();
    }

    public void setPacMan(Pacman pacman){ this.pacman = pacman;}

    @Override
    public double getGoalX() {
        if(!isFeared()) {
            if (!isAstar) {
                return pacman.getX();
            } else {
                return goalX;
            }
        }else {
            return getFearedGoal().first;
        }
    }

    @Override
    public double getGoalY() {
        if(!isFeared()) {
            if (!isAstar) {
                return pacman.getY();
            } else {
                return goalY;
            }
        }else{
            return getFearedGoal().second;
        }
    }

    public void update(double ellapsedTime){
        changeTime += ellapsedTime;
        aStarChangeTime += ellapsedTime;
        if(changeTime > cycleLength){
            actualizeGoal();
            changeTime = 0;
        }
        if(aStarChangeTime > aStarCycleLength){
            isAstar = !isAstar;
            aStarChangeTime = 0;
        }
    }
    private void actualizeGoal(){
        if(Math.random() < 0.5){
            if(Math.random() < 0.5){
                goalX = getX() + 1;
            }else{
                goalX = getX() - 1;
            }
        }else{
            if(Math.random() < 0.5){
                goalY = getY() + 1;
            }else{
                goalY = getY() - 1;
            }

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
