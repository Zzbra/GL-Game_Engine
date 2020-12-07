package amu.gl.equipe200.pacman.entities;

import amu.gl.equipe200.IAEngine.IAInterface;

public class Clide extends Ghost implements IAInterface {

    private double changeTime;
    private double cycleLength;
    private double goalX, goalY;

    public Clide() {
        super();
        setImageName("Images/Ghost_orange_1");
        setLayerName("FOREGROUND");
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
}
