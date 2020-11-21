package amu.gl.equipe200.core.Component.Interfaces;

public interface Movable {
    public double getX();
    public void setX(double x);
    public double getY();
    public void setY(double y);
    public double getR();
    public void setR(double r);
    public double getDx();
    public double getDy();
    public double getDr();
    public boolean canMove();
    public void checkBounds();
}
