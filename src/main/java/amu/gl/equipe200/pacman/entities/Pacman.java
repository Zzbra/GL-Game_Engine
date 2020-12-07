package amu.gl.equipe200.pacman.entities;

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
    private int lives;

    private boolean isInvincible;
    private final static int invincibleTotalDuration = 2;
    private double invincibleDuration;

    private boolean isPassWall;
    private final static double passWallTotalDuration = 5;
    private double passWallDuration;

    private String inputWaiting;

    public Pacman(){
        super(Settings.Tag.PLAYER);

        this.imageLastFrame = 0;
        this.animation = new ArrayList<>();
        this.animation.add("images/Pacman_1.png");
        this.animation.add("images/Pacman_2.png");
        this.animation.add("images/Pacman_3.png");
        this.animation.add("images/Pacman_2.png");

        this.lives = 4;
        this.inputWaiting = "";

        this.isInvincible = false;
        this.invincibleDuration = 0;
        this.isPassWall = false;
        this.passWallDuration = 0;
    }

    /******************************************************************************************************************
     *    Getters and Setters                                                                                         *
     ******************************************************************************************************************/
    @Override
    public void setX(double x) { super.setX(x); hasMoved = true; }
    @Override
    public void setY(double y) { super.setY(y); hasMoved = true; }

    public void setControls(String up, String down, String left, String right) {
        this.upKey = up.toUpperCase();
        this.downKey = down.toUpperCase();
        this.leftKey = left.toUpperCase();
        this.rightKey = right.toUpperCase();
    }

    public int getLives(){return lives;}
    public void setLives(int lives){
        if(lives >0) {
            this.lives = lives;
        }
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
        if(others.getTag() == Settings.Tag.POWERUP_INVINCIBLE) { activateInvincible(); }
        if(others.getTag() == Settings.Tag.POWERUP_WALLPASS) { activatePassWall(); }
        if (others.getTag() == Settings.Tag.ENEMY && !this.isInvincible) {
            this.lives--;
            System.out.println("collide with enemy: one life lost");
            activateInvincible();
        }
    }

    /******************************************************************************************************************
     *    Graphics Engine behaviour                                                                                   *
     ******************************************************************************************************************/
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
    public void onGraphicsProcessed() {
        this.hasMoved = false;
        this.hasNewSprite = false;
    }

    /******************************************************************************************************************
     *    Input Engine behaviour                                                                                      *
     ******************************************************************************************************************/
    public void reactToInput(String key) {
        this.inputWaiting = key.toUpperCase();
        //System.out.println(this + "recieved input " + key);


    }

    private void processInput() {
        System.out.println("Process input: " + this.inputWaiting);
        if (inputWaiting.isEmpty()) return;

        if(inputWaiting.equals("K")) {
            isPassWall = !isPassWall;
        }
        if (inputWaiting.equals(upKey)) {
            setXSpeed(0);
            setYSpeed(-Settings.PLAYER_SPEED);
            setR(270);
        }
        if (inputWaiting.equals(downKey)) {
            setXSpeed(0);
            setYSpeed(Settings.PLAYER_SPEED);
            setR(90);
        }
        if (inputWaiting.equals(leftKey)) {
            setXSpeed(-Settings.PLAYER_SPEED);
            setYSpeed(0);
            setR(180);
        }
        if (inputWaiting.equals(rightKey)) {
            setXSpeed(Settings.PLAYER_SPEED);
            setYSpeed(0);
            setR(0);
        }
        this.inputWaiting = "";
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
        if (this.isPassWall) {
            this.passWallDuration += ellapsedTime;
            if (this.passWallDuration >= passWallTotalDuration) {
                this.isPassWall = false;
                this.passWallDuration = 0;
            }
        }

//        System.out.println("Pacman :" + this.getX() + "," + this.getY() + " , " + (this.getX()+this.getWidth()) + "," + (this.getY()+this.getHeight()));
        System.out.println("Pacman :" + (int)this.getX() + "," + (int)this.getY() + " , " + (int)(this.getX()+this.getWidth()) + "," + (int)(this.getY()+this.getHeight()));
//        System.out.println("Pacman :" + (((int) this.getX()) == (int)(this.getX() + this.getWidth())) +" , " + (((int) this.getY()) == (int)(this.getY() + this.getHeight())));
        System.out.println("input waiting : " + this.inputWaiting);
        if (((inputWaiting == upKey || inputWaiting == downKey) && ((int) this.getX()) == (int)(this.getX() + this.getWidth()))
            || ((inputWaiting == rightKey || inputWaiting == leftKey) && ((int) this.getY()) == (int)(this.getY() + this.getHeight()))) {
            processInput();
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