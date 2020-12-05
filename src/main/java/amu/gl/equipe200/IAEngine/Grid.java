package amu.gl.equipe200.IAEngine;

import amu.gl.equipe200.gameworld.Settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Grid {
    Map<Integer, Map<Integer, Cell>> cells;
    public Grid(int[][] mat) {
        this.cells = new HashMap<>();
        fillCells(mat);
    }

    private void fillCells(int[][] mat) {
        for (int x = 0; x < mat.length; x++){
            for (int y = 0; y < mat.length; y++){
                Cell cell = new Cell(x, y, mat[y][x] != 1);
                addCell(x, y, cell);
            }
        }
    }

    private void addCell(int x, int y, Cell cell) {
        if (!cells.containsKey(x)){
            cells.put(x, new HashMap<>());
        }
        cells.get(x).put(y, cell);
    }

    public Cell getCell(int x, int y) {
        if (!cells.containsKey(x)) return null;
        if (!cells.get(x).containsKey(y)) return null;
        return cells.get(x).get(y);
    }

    public ArrayList<Cell> getPossibilities(Cell cell){
        ArrayList<Cell> possibilities = new ArrayList<>();
        Cell up = getCell(cell.x+1, cell.y);
        if (up != null)
            if (up.isWalkable) possibilities.add(up);

        Cell down = getCell(cell.x-1, cell.y);
        if (down != null)
            if (down.isWalkable) possibilities.add(down);

        Cell right = getCell(cell.x, cell.y+1);
        if (right != null)
            if (right.isWalkable) possibilities.add(right);

        Cell left = getCell(cell.x, cell.y-1);
        if (left != null)
            if (left.isWalkable) possibilities.add(left);
        return possibilities;
    }

    @Override
    public String toString() {
        return "Grid{" +
                "cells=" + cells +
                '}';
    }

    public Cell pointToCell(double x, double y){
        return getCell((int)(x/(Settings.SCENE_HEIGHT/16)), (int)(y/(Settings.SCENE_HEIGHT/16)));
    }
}
