package amu.gl.equipe200.pacman.entities;

import amu.gl.equipe200.graphicsengine.GraphicsInterface;

public class Life implements GraphicsInterface {
    double x, y, r, w, h;
    private boolean toRemove = false;
    public Life(){}
    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setWidth(double w) {
        this.w = w;
    }

    public void setHeight(double h) {
        this.h = h;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public double getR() {
        return r;
    }

    @Override
    public double getHeight() {
        return h;
    }

    @Override
    public double getWidth() {
        return w;
    }

    @Override
    public String getImageName() {
        return "images/Pacman_2.png";
    }

    @Override
    public String getLayerName() {
        return "GUI";
    }

    @Override
    public boolean isRemovable() {
        return toRemove;
    }

    public void toRemove(){
        toRemove = true;
    }
}
