package amu.gl.equipe200.IAEngine;

public class Cell {
    private int x;
    private int y;
    boolean isWalkable;

    public Cell(int x, int y, boolean isWalkable) {
        this.x = x;
        this.y = y;
        this.isWalkable = isWalkable;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x= " + x +
                "y= " + y +
                " : " + isWalkable +
                '}';
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return getX() == cell.getX() && getY() == cell.getY();
    }
    @Override
    public int hashCode() {
        return x * y;
    }
}
