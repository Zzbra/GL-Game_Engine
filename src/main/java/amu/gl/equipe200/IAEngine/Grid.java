package amu.gl.equipe200.IAEngine;

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
        for (int y = 0; y < mat.length; y++){
            for (int x = 0; x < mat.length; x++){
                //System.out.printf("%d",mat[y][x]);
                Cell cell = new Cell(x, y, mat[y][x] != 1);
//                System.out.println(cell);
                addCell(x, y, cell);
            }
//            System.out.println();
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
        Cell up = getCell(cell.getX()+1, cell.getY());
        if (up != null)
            if (up.isWalkable) possibilities.add(up);

        Cell down = getCell(cell.getX()-1, cell.getY());
        if (down != null)
            if (down.isWalkable) possibilities.add(down);

        Cell right = getCell(cell.getX(), cell.getY()+1);
        if (right != null)
            if (right.isWalkable) possibilities.add(right);

        Cell left = getCell(cell.getX(), cell.getY()-1);
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
}
