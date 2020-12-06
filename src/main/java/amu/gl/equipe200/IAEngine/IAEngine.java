package amu.gl.equipe200.IAEngine;

import amu.gl.equipe200.core.Settings;


import java.util.ArrayList;
import java.util.HashSet;

public class IAEngine {
    private int[][] grid;
    private HashSet<IAInterface> entities;
    private ShortestPath shortestPath;
    private AStar aStar;

    public IAEngine(){

    }

    public void loadMap(int[][] grid){
        this.grid = grid;
        this.shortestPath = new ShortestPath(grid);
        this.aStar = new AStar(new Grid(grid));
    }

    public void loadGameWorld(HashSet<IAInterface> entities){
        this.entities = entities;
    }

    public void registerEntity(IAInterface entity){
        entities.add(entity);
    }


    public void update(){

        for(IAInterface entity : entities){
            Cell currentCell = new Cell((int)(entity.getX()), (int)(entity.getY()), true);
            Cell currentCellOtherCorner = new Cell((int)(entity.getX()+ entity.getWidth()), (int)(entity.getY() + entity.getHeight()), true);
            double dX = entity.getGoalX() - entity.getX();
            double dY = entity.getGoalY() - entity.getY();
            System.out.println(dX + " " + dY);
            if(currentCell.getX() == currentCellOtherCorner.getX() && currentCell.getY() == currentCellOtherCorner.getY()) {
                Cell goal = new Cell((int) entity.getGoalX(), (int) entity.getGoalY(), true);
                ArrayList<Cell> path = shortestPath.getShortestPath(currentCell, goal);
                System.out.println(path);
                if (path.size() > 1) {
                    System.out.println(currentCell + " " + path.get(1));
                    adjustDirection(entity, path.get(1));
                }
            }
        }
    }

    private void adjustDirection(IAInterface entity, Cell direction){
        System.out.println(entity.getX() + " " + entity.getY() + " " +  direction);

        if(direction.getX() > entity.getX()){
            entity.setXSpeed(Settings.ENEMY_SPEED);
            entity.setYSpeed(0);
        }else if(direction.getX() < entity.getX()){
            entity.setXSpeed(-Settings.ENEMY_SPEED);
            entity.setYSpeed(0);
        }if(direction.getY() > entity.getY()){
            entity.setYSpeed(Settings.ENEMY_SPEED);
            entity.setXSpeed(0);
        }else if (direction.getY() < entity.getY()){
            entity.setYSpeed(-Settings.ENEMY_SPEED);
            entity.setXSpeed(0);
        }

    }

}
