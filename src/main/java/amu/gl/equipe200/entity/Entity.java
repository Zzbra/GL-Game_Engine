package amu.gl.equipe200.entity;

import amu.gl.equipe200.core.Engine;
import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.gameworld.Settings;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public abstract class Entity {
    protected ArrayList<Settings.Tag> collisionsCheck;


    private double x;
    private double y;
    private double r;

    private double dx;
    private double dy;
    private double dr;

    private double health;
    private double damage;

    private boolean removable = false;

    private boolean canMove = true;
    private Settings.Tag tag;




    public Entity(double x, double y, double r, double dx, double dy, double dr, double health, double damage) {

        this.x = x;
        this.y = y;
        this.r = r;
        this.dx = dx;
        this.dy = dy;
        this.dr = dr;

        this.health = health;
        this.damage = damage;

        /*** Collidable ***/
//        collisionManifold = new ArrayList<>();
        this.collisionsCheck = new ArrayList<>();


    }

    public Entity(){};






    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }


    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public double getDr() {
        return dr;
    }

    public void setDr(double dr) {
        this.dr = dr;
    }

    public double getHealth() {
        return health;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public boolean isRemovable() {
        return removable;
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
    }

    public Settings.Tag getTag(){
        return this.tag;
    }

    public void move() {

        if( !canMove)
            return;

        x += dx;
        y += dy;
        r += dr;

    }

    public boolean isMovable(){ return canMove;}

    public boolean isAlive() {
        return Double.compare(health, 0) > 0;
    }

    /**
     * Reduce health by the amount of damage that the given sprite can inflict
     * @param sprite
     */
    public void getDamagedBy( Entity sprite) {
        health -= sprite.getDamage();
    }

    /**
     * Set health to 0
     */
    public void kill() {
        setHealth( 0);
    }

    /**
     * Set flag that the sprite can be removed from the UI.
     */
    public void remove() {
        setRemovable(true);
    }

    /**
     * Set flag that the sprite can't move anymore.
     */
    public void stopMovement() {
        this.canMove = false;
    }

    public abstract void checkRemovability();

    public ArrayList<Settings.Tag> getCollisionsCheck(){
        return this.collisionsCheck;
    }


    public void setTag(Settings.Tag tag){
        this.tag = tag;
    }

}