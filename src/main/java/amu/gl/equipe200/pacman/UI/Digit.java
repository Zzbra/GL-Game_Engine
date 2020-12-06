package amu.gl.equipe200.pacman.UI;

import amu.gl.equipe200.graphicsengine.GraphicsEngine;
import amu.gl.equipe200.graphicsengine.GraphicsInterface;

public class Digit implements GraphicsInterface {
    private double x, y, r, w, h;
    private String imageName;
    private boolean hasNewSprite = false;

    public Digit(int value){
        setValue(value);
    }

    public void setValue(int value){
        imageName = "images/digits_" + String.valueOf(value) +".png";
        hasNewSprite = true;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setW(double w) {
        this.w = w;
    }

    public void setH(double h) {
        this.h = h;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
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
        return imageName;
    }

    @Override
    public String getLayerName() {
        return "GUI";
    }

    @Override
    public boolean hasNewSprite() {
        return hasNewSprite;
    }

    @Override
    public void onGraphicsProcessed() {
        hasNewSprite = false;
    }
}
