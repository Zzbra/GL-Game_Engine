package amu.gl.equipe200.entity;

import amu.gl.equipe200.graphicsengine.GraphicsEngine;
import amu.gl.equipe200.graphicsengine.GraphicsInterface;
import amu.gl.equipe200.inputengine.IOInterface;
import amu.gl.equipe200.physicsengine.PhysicsInterface;
import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.gameworld.Settings;

/**
 *  La super class Entity a des paramètres w et h qui sont aussi width et height. A changer du coup.
 */
public class Player
        extends Entity
        implements PhysicsInterface, GraphicsInterface, IOInterface {

    /**
     * Physics variables
     */
    private double x, y;
    private double xSpeed, ySpeed;
    private double width, height;
    private volatile boolean isSolid;

    /**
     * Graphics variables
     */
    private double r;
    private String imageName;
    private String layerName;
    private boolean hasMoved;
    private boolean hasNewSprite;

    /**
     * Input variables
     */
    private String upKey, downKey, leftKey, rightKey;

    public Player(){ }

    public Player(double x, double y, double r,
                  double dx, double dy, double dr,
                  double health, double damage,
                  double speed, GameWorld gameScene, String imageName, String layerName) {

        super(x, y, r, dx, dy, dr, health, damage, gameScene);
        this.setTag(Settings.Tag.PLAYER);
        this.imageName = imageName;
        this.layerName = layerName;
        collisionsCheck.add(Settings.Tag.ENEMY);
        isSolid=true;
    }


    /******************************************************************************************************************
     *    Getters and Setters                                                                                         *
     ******************************************************************************************************************/
    @Override
    public double getX() { return this.x; }
    @Override
    public double getY() { return this.y; }
    @Override
    public void setX(double x) { this.hasMoved = true; this.x = x; }
    @Override
    public void setY(double y) { this.hasMoved = true; this.y = y; }

    @Override
    public double getXSpeed() { return this.xSpeed; }
    public void setXSpeed(double xSpeed) { this.xSpeed = xSpeed; }
    @Override
    public double getYSpeed() { return this.ySpeed; }
    public void setYSpeed(double ySpeed) { this.ySpeed = ySpeed; }

    @Override
    public double getWidth() {
        return this.width;
    }
    public void setWidth(double width) { this.width = width; }
    @Override
    public double getHeight() {
        return this.height;
    }
    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String getImageName(long ellapsedTime) { return this.imageName; }
    @Override
    public String getLayerName(){ return this.layerName; }

    public void setControls(String up, String down, String left, String right) {
        this.upKey = up.toUpperCase();
        this.downKey = down.toUpperCase();
        this.leftKey = left.toUpperCase();
        this.rightKey = right.toUpperCase();
    }

    /******************************************************************************************************************
     *    Physics Engine behaviour                                                                                    *
     ******************************************************************************************************************/
    @Override
    public boolean isMovable() { return true; }
    @Override
    public boolean isWorldBounded() { return true; }
    @Override
    public boolean isCollidable() { return true;}
    @Override
    public boolean isSolid() { return isSolid; }
    public void setIsSolid(boolean isSolid) {this.isSolid=isSolid;}

    @Override
    public void onWorldEnds() {
        // TODO
        //System.out.println(this.toString() + " has reach the end of the world");
    }
    @Override
    public void onCollide(PhysicsInterface others) {
        // TODO
        //System.out.println(this.toString() + " has collided with " + others.toString());
        if(others.getTag() == Settings.Tag.valueOf("FRUIT")){
            superPowerActive();
        }
    }

    /******************************************************************************************************************
     *    Graphics Engine behaviour                                                                                   *
     ******************************************************************************************************************/
    @Override
    public boolean needRemoval() { return false; }

    @Override
    public boolean hasMoved() { return this.hasMoved; }
    @Override
    public boolean hasNewSprite() { return true; }

    @Override
    public void onProcessed(GraphicsEngine engine) {
        this.hasMoved = false;
        this.hasNewSprite = false;
    }

    /******************************************************************************************************************
     *    Input Engine behaviour                                                                                      *
     ******************************************************************************************************************/
    public void reactToInput(String key) {
        key = key.toUpperCase();
        //System.out.println(this + "recieved input " + key);
        if(key.equals("K")) {
            setIsSolid(!isSolid);
        }
        if (key.equals(upKey)) {
            setXSpeed(0);
            setYSpeed(-Settings.PLAYER_SHIP_SPEED);
            setR(270);
        }
        if (key.equals(downKey)) {
            setXSpeed(0);
            setYSpeed(Settings.PLAYER_SHIP_SPEED);
            setR(90);
        }
        if (key.equals(leftKey)) {
            setXSpeed(-Settings.PLAYER_SHIP_SPEED);
            setYSpeed(0);
            setR(180);
        }
        if (key.equals(rightKey)) {
            setXSpeed(Settings.PLAYER_SHIP_SPEED);
            setYSpeed(0);
            setR(0);
        }

    }

    /******************************************************************************************************************
     *    Other                                                                                                       *
     ******************************************************************************************************************/
    public void superPowerActive(){
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                    long startTime=System.currentTimeMillis();
                    long time=System.currentTimeMillis();
                    while (time<(startTime+5000)) {
                        isSolid = false;
                        time=System.currentTimeMillis();
                    }
                isSolid = true;
            }
        }).start();
    }

    /****************
     *    Others    *
     ****************/
    @Override
    public void checkRemovability() {
        // TODO Auto-generated method stub
    }



}