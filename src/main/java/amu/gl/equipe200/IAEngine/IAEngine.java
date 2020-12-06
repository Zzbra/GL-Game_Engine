package amu.gl.equipe200.IAEngine;

import amu.gl.equipe200.core.Settings;


import java.util.ArrayList;
import java.util.HashSet;

public class IAEngine {
    private int[][] grid;
    private HashSet<IAInterface> entities;
    private ShortestPath shortestPath;
    private AStar aStar;

    private double cell_width, cell_height;

    public IAEngine(){ }

    public void loadMap(int[][] grid, double cell_width, double cell_height){
        this.grid = grid;
        this.cell_width = cell_width;
        this.cell_height = cell_height;

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
        System.out.println("IA Update :");

        for(IAInterface entity : entities){
            System.out.println("Update Entity");
            Cell currentCell = new Cell((int)(entity.getX() / this.cell_width),
                                        (int)(entity.getY() / this.cell_height),
                               true);
            Cell currentCellOtherCorner = new Cell((int)((entity.getX()+ entity.getWidth()) / this.cell_width),
                                                   (int)((entity.getY() + entity.getHeight()) / this.cell_height),
                                          true);
            double dX = entity.getGoalX() - entity.getX();
            double dY = entity.getGoalY() - entity.getY();
            if(currentCell.getX() == currentCellOtherCorner.getX() && currentCell.getY() == currentCellOtherCorner.getY()) {
                Cell goal = new Cell((int) (entity.getGoalX() / this.cell_width),
                                     (int) (entity.getGoalY() / this.cell_width),
                                true);
                ArrayList<Cell> path = shortestPath.getShortestPath(currentCell, goal);
                if (path.size() > 1) {
                    adjustDirection(entity, path.get(1), currentCell);
                }
                System.out.println("Path :" + path);
            }
        }
    }

    private void adjustDirection(IAInterface entity, Cell direction, Cell currentCell){
        double dx = (direction.getX() - currentCell.getX()) * Settings.ENEMY_SPEED;
        double dy = (direction.getY() - currentCell.getY()) * Settings.ENEMY_SPEED;
        entity.setXSpeed(dx);
        entity.setYSpeed(dy);

    }

}
