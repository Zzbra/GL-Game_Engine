package amu.gl.equipe200.entity;

import amu.gl.equipe200.IAEngine.*;
import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.core.Settings;

import java.util.ArrayList;

public class Blinky extends Enemy implements IAInterface {
    Player pacMan;
    ShortestPath algo;
    ArrayList<Cell> path;
    Cell next;
    Cell now;
    ShortestPath shortestPath;

    public Blinky(double x, double y, double r, double xSpeed, double ySpeed, double dr, double health, double damage, String imageName, String layerName, Player pacMan) {
        super(x, y, r, xSpeed, ySpeed, dr, health, damage, imageName, layerName);
        this.pacMan=pacMan;
        this.path= new ArrayList<>();
        this.now = new Cell((int)x, (int)y, true);
//        now=algo.getGrid().pointToCell(this.getX(), this.getY());
    }

    public void update(){

        //ArrayList<Cell> path = algo.start(grid.getCell(0, 5), grid.getCell(4,11));
//        System.out.println((int)(pacMan.getX()/(Settings.SCENE_HEIGHT/16))+" "+(int)(pacMan.getY()/(Settings.SCENE_HEIGHT/16)));
//        int gridX = (int)(pacMan.getX()/(Settings.SCENE_HEIGHT/16));
//        int gridY = (int)(pacMan.getY()/(Settings.SCENE_HEIGHT/16));

//        Cell start = algo.getGrid().pointToCell(this.getX(), this.getY());
//        Cell goal = algo.getGrid().pointToCell(pacMan.getX(),pacMan.getY());
        Cell start = new Cell((int)getX(), (int)getY(), true);
        Cell goal = new Cell((int)pacMan.getX(), (int)pacMan.getY(), true);
        System.out.println("db");
        if(path.size()<=1) path = algo.getShortestPath(start, goal);

//        int offset=2;
//        if(algo.getGrid().pointToCell(this.getX()-offset, this.getY()-offset) == algo.getGrid().pointToCell(this.getX()+this.getWidth()+offset, this.getY()+this.getHeight()+offset)){
//            now = algo.getGrid().pointToCell(this.getX(), this.getY());
//        }
        Cell next = path.get(1);
        System.out.println(now);

        if(now.getX()==next.getX() && now.getY()==next.getY()){
            path.remove(0);
            System.out.println(path);
        }

        int Xd, Yd;
        Xd = now.getX() - next.getX();
        Yd = now.getY() - next.getY();

        if (Math.abs(Xd) >= Math.abs(Yd)) {
            if (Xd < 0) {
                setXSpeed(Settings.ENEMY_SPEED);
                setYSpeed(0);
            } else {
                setXSpeed(-Settings.ENEMY_SPEED);
                setYSpeed(0);
            }
        } else {
            if (Yd < 0) {
                setXSpeed(0);
                setYSpeed(Settings.ENEMY_SPEED);
            } else {
                setXSpeed(0);
                setYSpeed(-Settings.ENEMY_SPEED);
            }
        }
    }

    @Override
    public double getGoalX() {
        return pacMan.getX();
    }

    @Override
    public double getGoalY() {
        return pacMan.getY();
    }
}
