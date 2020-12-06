package amu.gl.equipe200.pacman.entities;

import amu.gl.equipe200.graphicsengine.GraphicsEngine;
import amu.gl.equipe200.graphicsengine.GraphicsInterface;
import amu.gl.equipe200.inputengine.IOInterface;
import amu.gl.equipe200.physicsengine.PhysicsInterface;
import amu.gl.equipe200.core.Settings;

import java.util.ArrayList;

/**
 *  La super class Entity a des paramètres w et h qui sont aussi width et height. A changer du coup.
 */
public class Pacman
    extends Entity
    implements PhysicsInterface, GraphicsInterface, IOInterface {

    /***  Physics Flag  ***/
//    private volatile boolean isSolid = true;

    /***  Graphics variables  ***/
    private ArrayList<String> animation;
    private int imageLastFrame;
    private boolean hasMoved;
    private boolean hasNewSprite;

    /***  Input variables  ***/
    private String upKey, downKey, leftKey, rightKey;

    /***  Others  ***/
    private int lifes;

    private boolean isInvincible;
    private final static int invincibleTotalDuration = 5;
    private double invincibleDuration;

    private boolean isPassWall;
    private final static int passWallTotalDuration = 5;
    private double passWallDuration;


    public Pacman(){
        super(Settings.Tag.PLAYER);

        this.imageLastFrame = 0;
        this.animation = new ArrayList<>();
        this.animation.add("images/Pacman_1.png");
        this.animation.add("images/Pacman_2.png");
        this.animation.add("images/Pacman_3.png");
        this.animation.add("images/Pacman_2.png");

        this.lifes = 4;

        this.isInvincible = false;
        this.invincibleDuration = 0;
        this.isPassWall = false;
        this.passWallDuration = 0;}

    /******************************************************************************************************************
     *    Getters and Setters                                                                                         *
     ******************************************************************************************************************/
    @Override
    public void setX(double x) { super.setX(x); hasMoved = true; }
    @Override
    public void setY(double y) { super.setY(y); hasMoved = true; }
    @Override
    public Settings.Tag getTag() {
        return null;
    }

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
    public boolean isCollidable() { return true; }
    @Override
    public boolean isSolid() { return !isPassWall; }

    @Override
    public boolean isRemovable() {
        return false;
    }

    @Override
    public void onWorldEnds() {
        // TODO
        //System.out.println(this.toString() + " has reach the end of the world");
    }
    @Override
    public void onCollide(PhysicsInterface others) {
        if(others.getTag() == Settings.Tag.POWERUP_INVINCIBLE) { this.isInvincible = true; }
        if(others.getTag() == Settings.Tag.POWERUP_WALLPASS) { this.isPassWall = true; }

        if (others.getTag() == Settings.Tag.ENEMY && !this.isInvincible) {
            this.lifes--;
            System.out.println("collide with enemy: one life lost");
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
    public String getImageName() {
        this.imageLastFrame++;
        int url = (imageLastFrame / 30) % this.animation.size();
        return this.animation.get(url);
    }

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
            isPassWall = !isPassWall;
        }
        if (key.equals(upKey)) {
            setXSpeed(0);
            setYSpeed(-Settings.PLAYER_SPEED);
            setR(270);
        }
        if (key.equals(downKey)) {
            setXSpeed(0);
            setYSpeed(Settings.PLAYER_SPEED);
            setR(90);
        }
        if (key.equals(leftKey)) {
            setXSpeed(-Settings.PLAYER_SPEED);
            setYSpeed(0);
            setR(180);
        }
        if (key.equals(rightKey)) {
            setXSpeed(Settings.PLAYER_SPEED);
            setYSpeed(0);
            setR(0);
        }

    }

    /******************************************************************************************************************
     *    Other                                                                                                       *
     ******************************************************************************************************************/
    public void update(double ellapsedTime) {
        if (isInvincible) {
            invincibleDuration += ellapsedTime;
            if (invincibleDuration >= invincibleTotalDuration) {
                this.invincibleDuration = 0;
                this.isInvincible = false;
            }
        }

        if (isPassWall) {
            passWallDuration += ellapsedTime;
            if (passWallDuration >= passWallTotalDuration) {
                this.passWallDuration = 0;
                this.isPassWall = false;
            }
        }
    }

    public void activateInvincible() { this.isInvincible = true; }
    public void activatePassWall() { this.isPassWall = true; }

//    public void superPowerActive(){
//        new Thread(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                    long startTime=System.currentTimeMillis();
//                    long time=System.currentTimeMillis();
//                    while (time<(startTime+5000)) {
//                        isSolid = false;
//                        time=System.currentTimeMillis();
//                    }
//                isSolid = true;
//            }
//        }).start();
//    }
}