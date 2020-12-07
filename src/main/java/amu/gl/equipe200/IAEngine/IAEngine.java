package amu.gl.equipe200.IAEngine;

import amu.gl.equipe200.core.Settings;


import java.util.ArrayList;
import java.util.HashSet;

public class IAEngine {
    private HashSet<IAInterface> entities;
    private ShortestPath shortestPath;

    private double cell_width, cell_height;

    public IAEngine(){ }

    public void loadMap(int[][] grid, double cell_width, double cell_height){
        this.cell_width = cell_width;
        this.cell_height = cell_height;

        this.shortestPath = new ShortestPath(grid);
    }

    public void loadGameWorld(HashSet<IAInterface> entities){
        this.entities = entities;
    }

    public void registerEntity(IAInterface entity){
        entities.add(entity);
    }

    public void update(){

        for(IAInterface entity : entities){
            Cell currentCell = new Cell((int)(entity.getX() / this.cell_width),
                                        (int)(entity.getY() / this.cell_height),
                               true);
            Cell currentCellOtherCorner = new Cell((int)((entity.getX()+ entity.getWidth()) / this.cell_width),
                                                   (int)((entity.getY() + entity.getHeight()) / this.cell_height),
                                          true);
            if(currentCell.getX() == currentCellOtherCorner.getX() && currentCell.getY() == currentCellOtherCorner.getY()) {
                Cell goal = new Cell((int) (entity.getGoalX() / this.cell_width),
                                     (int) (entity.getGoalY() / this.cell_width),
                                true);
                ArrayList<Cell> path = shortestPath.getShortestPath(currentCell, goal);
                if (path.size() > 1) {
                    if(path.get(1).isWalkable)
                        adjustDirection(entity, path.get(1), currentCell);
                    else
                        adjustDirection(entity, path.get(0), currentCell);
                }
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
