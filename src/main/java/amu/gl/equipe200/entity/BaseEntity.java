package amu.gl.equipe200.entity;

import amu.gl.equipe200.core.Component.Component;
import amu.gl.equipe200.physicsengine.PhysicsComponent;
import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.gameworld.Settings;
import amu.gl.equipe200.system.ASystem;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class BaseEntity {
    protected ArrayList<Settings.Tag> collisionsCheck;
    private GameWorld gameWorld;

    private Pane layer;

    private ASystem collisionLister;


    private double x;
    private double y;
    private double r;

    private double dx;
    private double dy;
    private double dr;

    private double health;
    private double damage;

    private boolean removable = false;

    private double w;
    private double h;

    private boolean canMove = true;
    private Settings.Tag tag;

    /*** ColiderComponent ***/
    private ArrayList<PhysicsComponent> collisionManifold;
    private HashMap<Class<? extends Component>, Component> components;

    public BaseEntity(double x, double y, double r, double dx, double dy, double dr, double health, double damage, GameWorld gameWorld) {

        this.x = x;
        this.y = y;
        this.r = r;
        this.dx = dx;
        this.dy = dy;
        this.dr = dr;

        this.health = health;
        this.damage = damage;

        //this.imageView = new ImageView(image);
        //this.imageView.relocate(x, y);
        //this.imageView.setRotate(r);

        //this.w = image.getWidth(); // imageView.getBoundsInParent().getWidth();
        //this.h = image.getHeight(); // imageView.getBoundsInParent().getHeight();

        this.components = new HashMap<>();
        /*** Collidable ***/
        collisionManifold = new ArrayList<>();
        this.collisionsCheck = new ArrayList<>();

        this.gameWorld = gameWorld;

        //addToLayer();

    }



    public void addToLayer(Node node) {
        this.layer.getChildren().add(node);
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

    public boolean canMove(){ return canMove;}

    public boolean isAlive() {
        return Double.compare(health, 0) > 0;
    }





    public double getWidth() {
        return w;
    }

    public double getHeight() {
        return h;
    }

    public void setWidth(double w) { this.w = w; }

    public void setHeight(double h) {
        this.h = h;
    }

    public double getCenterX() {
        return x + w * 0.5;
    }

    public double getCenterY() {
        return y + h * 0.5;
    }

    public Component getComponent(Class<? extends Component> type){
        return this.components.get(type);
    }

    public void addComponent(Class<? extends Component> type, Component component){
        this.components.put(type, component);
    }
    // TODO: per-pixel-collision
    public boolean collidesWith( BaseEntity otherSprite) {

        return ( otherSprite.getX() + otherSprite.getWidth() >= x && otherSprite.getY() + otherSprite.getHeight() >= y && otherSprite.getX() <= x + w && otherSprite.getY() <= y + h);

    }

    /**
     * Reduce health by the amount of damage that the given sprite can inflict
     * @param sprite
     */
    public void getDamagedBy( BaseEntity sprite) {
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


    /***  Collidable   ***/
    public void setCollisionLister(ASystem listener){
        this.collisionLister = listener;
    }

    public void addToCollisionManifold(PhysicsComponent physicsComponent){
        this.collisionManifold.add(physicsComponent);
    }

    public void clearCollisionManifold(){
        this.collisionManifold.clear();
    }

    public boolean collisionStayed(PhysicsComponent physicsComponent){
        return this.collisionManifold.contains(physicsComponent);
    }

    public abstract void onCollide(PhysicsComponent physicsComponent);

    public boolean hasCollisions(){
        return !this.collisionManifold.isEmpty();
    }


    public void removeCollision(PhysicsComponent physicsComponent){
        this.collisionManifold.remove(physicsComponent);
    }

    public abstract void onCollisionStay(PhysicsComponent physicsComponent2);

    public void setTag(Settings.Tag tag){
        this.tag = tag;
    }

    public abstract void onExit(PhysicsComponent physicsComponent2);

    public GameWorld getGameWorld(){
        return this.gameWorld;
    }
}