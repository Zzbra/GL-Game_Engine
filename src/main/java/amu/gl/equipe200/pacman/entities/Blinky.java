package amu.gl.equipe200.pacman.entities;

import amu.gl.equipe200.IAEngine.*;
import amu.gl.equipe200.core.Settings;
import amu.gl.equipe200.pacman.entities.pacman.Pacman;

import java.util.ArrayList;

public class Blinky extends Ghost implements IAInterface {
    Pacman pacMan;
    ShortestPath algo;
    ArrayList<Cell> path;
    Cell now;


    public Blinky() {
        super();
        this.path= new ArrayList<>();
        this.now = new Cell((int)getX(), (int)getY(), true);
        setImageName("Images/Ghost_blue_1");
        setLayerName("FOREGROUND");
    }

    public void setPacMan(Pacman pacMan) {
        this.pacMan = pacMan;
    }

    public void setPacMan(Pacman pacMan){ this.pacMan = pacMan;}

    public void update(){

        Cell start = new Cell((int)getX(), (int)getY(), true);
        Cell goal = new Cell((int)pacMan.getX(), (int)pacMan.getY(), true);
        System.out.println("db");
        if(path.size()<=1) path = algo.getShortestPath(start, goal);

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
