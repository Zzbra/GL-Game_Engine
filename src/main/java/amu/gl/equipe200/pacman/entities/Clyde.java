package amu.gl.equipe200.pacman.entities;

import amu.gl.equipe200.IAEngine.IAInterface;

import java.util.ArrayList;

public class Clyde extends Ghost implements IAInterface {

    private double changeTime;
    private double cycleLength;
    private double goalX, goalY;

    private ArrayList<String> animation;
    private int imageLastFrame;

    public Clyde() {
        super();
        setLayerName("FOREGROUND");

        this.animation = new ArrayList<>();
        this.animation.add("images/Ghost_Orange_1.png");
        this.animation.add("images/Ghost_Orange_2.png");

        changeTime = 0;
        cycleLength = 3;
    }
    public void initGoal(){
        goalX = getX();
        goalY = getY();
        actualizeGoal();
    }

    @Override
    public double getGoalX() {
        return goalX;
    }

    @Override
    public double getGoalY() {
        return goalY;
    }

    public void update(double ellapsedTime){
        changeTime += ellapsedTime;
        if(changeTime > cycleLength){
            actualizeGoal();
            changeTime = 0;
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
        int url = (imageLastFrame / 10) % this.animation.size();
        return this.animation.get(url);
    }
}
