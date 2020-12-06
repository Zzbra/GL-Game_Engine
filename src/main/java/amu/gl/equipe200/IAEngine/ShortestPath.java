package amu.gl.equipe200.IAEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShortestPath {
    private int[][] grid;
    private Grid mapGrid;

    public ShortestPath(int[][] grid) {
        this.grid = grid;
        mapGrid = new Grid(grid);

    }

    public Grid getGrid(){ return this.mapGrid;}

    public ArrayList<Cell> getShortestPath(Cell startCell, Cell endCell){
        Map<Cell, Cell> parents = new HashMap<>();

        ArrayList<Cell> temp = new ArrayList<>();
        temp.add(startCell);
        parents.put(startCell, null);

        boolean reachDestination = false;
        while (temp.size() > 0 && !reachDestination){
            Cell currentCell = temp.remove(0);
            ArrayList<Cell> children = getChildren(currentCell);
            for(Cell child : children){
                if (!parents.containsKey(child)) {
                    parents.put(child, currentCell);

                    //int value = child.getValue();
                    if (child.isWalkable) {
                        temp.add(child);
                    } else if (child.equals(endCell)) {
                        temp.add(child);
                        reachDestination = true;
                        endCell = child;
                        break;
                    }
                }
            }
        }

        Cell cell = endCell;
        ArrayList<Cell> shortestPath = new ArrayList<>();
        while(cell != null){
            shortestPath.add(0, cell);
            cell = parents.get(cell);
        }
        return shortestPath;
    }

    private ArrayList<Cell> getChildren(Cell parent){
        ArrayList<Cell> children = new ArrayList<>();
        int x = parent.getX();
        int y = parent.getY();
        if (x - 1 >= 0) {
            Cell child = new Cell(x - 1, y, mapGrid.getCell(x - 1, y).isWalkable);
            children.add(child);
        }
        if (y - 1 >= 0) {
            Cell child = new Cell(x, y - 1,  mapGrid.getCell(x, y - 1).isWalkable);
            children.add(child);
        }
        if (x + 1 < grid.length) {
            Cell child = new Cell(x + 1, y,  mapGrid.getCell(x + 1, y).isWalkable);
            children.add(child);
        }
        if (y + 1 < grid[0].length) {
            Cell child = new Cell(x, y + 1,  mapGrid.getCell(x, y + 1).isWalkable);
            children.add(child);
        }
        return children;
    }
}
