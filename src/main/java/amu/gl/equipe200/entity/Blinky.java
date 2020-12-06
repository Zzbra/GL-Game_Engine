package amu.gl.equipe200.entity;

import amu.gl.equipe200.IAEngine.AStar;
import amu.gl.equipe200.IAEngine.Cell;
import amu.gl.equipe200.IAEngine.Grid;
import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.gameworld.Settings;

import java.util.ArrayList;

public class Blinky extends Enemy {
    Player pacMan;
    AStar algo;
    ArrayList<Cell> path;
    Cell next;
    Cell now;

    public Blinky(double x, double y, double r, double xSpeed, double ySpeed, double dr, double health, double damage, GameWorld gamescene, String imageName, String layerName, Player pacMan, AStar algo) {
        super(x, y, r, xSpeed, ySpeed, dr, health, damage, gamescene, imageName, layerName);
        this.pacMan=pacMan;
        this.algo=algo;
        this.path= new ArrayList<>();
        now=algo.getGrid().pointToCell(this.getX(), this.getY());
    }

    public void update(){

        //ArrayList<Cell> path = algo.start(grid.getCell(0, 5), grid.getCell(4,11));
//        System.out.println((int)(pacMan.getX()/(Settings.SCENE_HEIGHT/16))+" "+(int)(pacMan.getY()/(Settings.SCENE_HEIGHT/16)));
//        int gridX = (int)(pacMan.getX()/(Settings.SCENE_HEIGHT/16));
//        int gridY = (int)(pacMan.getY()/(Settings.SCENE_HEIGHT/16));

        Cell start = algo.getGrid().pointToCell(this.getX(), this.getY());
        Cell goal = algo.getGrid().pointToCell(pacMan.getX(),pacMan.getY());

        if(path.size()<=1) path = algo.start(start, goal);

        int offset=4;
        Cell c1 = algo.getGrid().pointToCell(this.getX()-offset, this.getY()-offset);
        Cell c2 = algo.getGrid().pointToCell(this.getX()+offset+this.getWidth(), this.getY()+offset+this.getHeight());
        if(c1.getX() == c2.getX() && c1.getY() == c2.getY()){
            now = algo.getGrid().pointToCell(this.getX(), this.getY());
            System.out.println(now);
        }
        Cell next = path.get(1);

        if(now.getX()==next.getX() && now.getY()==next.getY()){
            path.remove(0);
            next = path.get(1);
            System.out.println(path);
        }

        int Xd, Yd;
        Xd = now.getX() - next.getX();
        Yd = now.getY() - next.getY();

        if (Math.abs(Xd) >= Math.abs(Yd)) {
            if (Xd < 0) {
                setXSpeed(Settings.ENEMY_SHIP_SPEED);
                setYSpeed(0);
            } else {
                setXSpeed(-Settings.ENEMY_SHIP_SPEED);
                setYSpeed(0);
            }
        } else {
            if (Yd < 0) {
                setXSpeed(0);
                setYSpeed(Settings.ENEMY_SHIP_SPEED);
            } else {
                setXSpeed(0);
                setYSpeed(-Settings.ENEMY_SHIP_SPEED);
            }
        }
    }
}
