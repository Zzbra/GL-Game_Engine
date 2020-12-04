package amu.gl.equipe200.IAEngine;

public class Cell {
    int x, y;
    boolean isWalkable;

    public Cell(int x, int y, boolean isWalkable) {
        this.x = x;
        this.y = y;
        this.isWalkable = isWalkable;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x= " + x +
                "y= " + y +
                " : " + isWalkable +
                '}';
    }
}
