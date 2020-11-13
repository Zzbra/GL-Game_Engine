package amu.gl.equipe200.core;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public abstract class EntityOld {

    protected Image image;
    protected ArrayList<GameSettings.Tag> collisionsCheck;
    ImageView imageView;

    Pane layer;

    SystemOld collisionLister;


    public double x;
    public double y;
    public double r;

    public double dx;
    public double dy;
    public double dr;

    double health;
    double damage;

    boolean removable = false;

    public double w;
    public double h;

    boolean canMove = true;
    GameSettings.Tag tag;

    /*** ColiderComponent ***/
    private ArrayList<EntityOld> collisionManifold;
    //private HashMap<String, Component> components;

    public EntityOld(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health, double damage) {

        this.layer = layer;
        this.image = image;
        this.x = x;
        this.y = y;
        this.r = r;
        this.dx = dx;
        this.dy = dy;
        this.dr = dr;

        this.health = health;
        this.damage = damage;

        this.imageView = new ImageView(image);
        this.imageView.relocate(x, y);
        this.imageView.setRotate(r);

        this.w = image.getWidth(); // imageView.getBoundsInParent().getWidth();
        this.h = image.getHeight(); // imageView.getBoundsInParent().getHeight();

        /*** Collidable ***/
        collisionManifold = new ArrayList<>();
        this.collisionsCheck = new ArrayList<>();

        addToLayer();

    }

    public void addToLayer() {
        this.layer.getChildren().add(this.imageView);
    }

    public void removeFromLayer() {
        this.layer.getChildren().remove(this.imageView);
    }

    public Pane getLayer() {
        return layer;
    }

    public void setLayer(Pane layer) {
        this.layer = layer;
    }

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

    public GameSettings.Tag getTag(){
        return this.tag;
    }

    public void move() {

        if( !canMove)
            return;

        x += dx;
        y += dy;
        r += dr;

    }

    public boolean isAlive() {
        return Double.compare(health, 0) > 0;
    }

    public ImageView getView() {
        return imageView;
    }

    public void updateUI() {

        imageView.relocate(x, y);
        imageView.setRotate(r);

    }

    public double getWidth() {
        return w;
    }

    public double getHeight() {
        return h;
    }

    public double getCenterX() {
        return x + w * 0.5;
    }

    public double getCenterY() {
        return y + h * 0.5;
    }

    // TODO: per-pixel-collision
    public boolean collidesWith( EntityOld otherSprite) {

        return ( otherSprite.x + otherSprite.w >= x && otherSprite.y + otherSprite.h >= y && otherSprite.x <= x + w && otherSprite.y <= y + h);

    }

    /**
     * Reduce health by the amount of damage that the given sprite can inflict
     * @param sprite
     */
    public void getDamagedBy( EntityOld sprite) {
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

    public ArrayList<GameSettings.Tag> getCollisionsCheck(){
        return this.collisionsCheck;
    }


    /***  Collidable   ***/
    public void setCollisionLister(SystemOld listener){
        this.collisionLister = listener;
    }

    public void addToCollisionManifold(EntityOld entityOld){
        this.collisionManifold.add(entityOld);
    }

    public void clearCollisionManifold(){
        this.collisionManifold.clear();
    }

    public boolean collisionStayed(EntityOld entityOld){
        return this.collisionManifold.contains(entityOld);
    }

    public abstract void onCollide(EntityOld entityOld);

    public boolean hasCollisions(){
        return !this.collisionManifold.isEmpty();
    }


    public void removeCollision(EntityOld entityOld){
        this.collisionManifold.remove(entityOld);
    }

    public abstract void onCollisionStay(EntityOld entityOld2);

    public void setTag(GameSettings.Tag tag){
        this.tag = tag;
    }

    public abstract void onExit(EntityOld entityOld2);
}