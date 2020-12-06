package amu.gl.equipe200.pacman;

import amu.gl.equipe200.pacman.UI.Counter;
import amu.gl.equipe200.pacman.UI.Digit;
import amu.gl.equipe200.pacman.entities.*;
import amu.gl.equipe200.pacman.entities.Pacman;
import amu.gl.equipe200.pacman.menues.*;

import amu.gl.equipe200.core.GameApp;
import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.core.Settings;
import amu.gl.equipe200.physicsengine.PhysicsInterface;
import amu.gl.equipe200.utils.Pair;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.stream.Collectors;

public class PacmanApp
    extends GameApp {

    private GameWorld pacmanWorld;
    private Pacman pacman;
    private Blinky blinky;
    private Counter counter;
    private int score;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onInit() {
        System.out.println("Hello onInit");
        this.pacmanWorld = new GameWorld(Settings.WORLD_WIDTH, Settings.WORLD_HEIGHT);
        score = 0;
        int[][] map = createMap("Map1.txt");
        getIaEngine().loadMap(map);
        createPlayers();
        createGhost();
        loadMainMenu();
        createCounter();
    }
    @Override
    public void handleCollisions(HashSet<Pair<PhysicsInterface, PhysicsInterface>> collisions){
        super.handleCollisions(collisions);
        for(Pair<PhysicsInterface, PhysicsInterface> collision : collisions){
            if(collision.first.getTag() == Settings.Tag.PLAYER && collision.second.getTag() == Settings.Tag.PACGUM){
                score++;
                System.out.println("score: " + score);
                counter.setValue(score);
            }
        }
    }

    @Override
    public void onGameIterBegin(double ellapsedTime) {

    }
    public void onGameIterEnd(long ellapsedTime) { }

    protected void loadMainMenu() { loadMenu(MainMenu.getInstance(this)); }
    public void loadGame() { loadGameWorld(pacmanWorld);}

    public GameWorld getPacmanWorld() { return pacmanWorld; }

    private void createPlayers() {
        // center horizontally, position at 70% vertically
        double x = 16 / 6.0;
        double y = 16 * 0.6;

        pacman = new Pacman();
        pacman.setX(x);
        pacman.setY(y);
        pacman.setWidth(0.8);
        pacman.setHeight(0.8);
        pacman.setControls("Z", "S", "Q", "D");
        pacman.setLayerName("FOREGROUND");

        pacmanWorld.addGraphicsEntity(pacman);
        pacmanWorld.addPhysicsEntity(pacman);
        pacmanWorld.addIOEntity(pacman);
    }

    private void createGhost(){
        blinky = new Blinky();
        blinky.setX(6);
        blinky.setY(6);
        blinky.setPacMan(pacman);
        blinky.setWidth(0.8);
        blinky.setHeight(0.8);
        blinky.setImageName("images/ghostRed.jpg");
        blinky.setLayerName("FOREGROUND");
        pacmanWorld.addGraphicsEntity(blinky);
        pacmanWorld.addPhysicsEntity(blinky);
        pacmanWorld.addAIEntity(blinky);
    }

    private void createCounter(){
        double blockHeight = Settings.SCENE_HEIGHT / Settings.WORLD_HEIGHT;
        counter = new Counter();
        counter.setWidth(1);
        counter.setX(0);
        counter.setY(Settings.SCENE_HEIGHT/blockHeight);
        counter.setHeight(Settings.UISECTION_HEIGHT/blockHeight);
        for(Digit digit : counter.getDigits()){
            pacmanWorld.addGraphicsEntity(digit);
        }
    }

    private int[][] createMap(String mapName){
        int[][] mapGrid = getMapGrid(mapName);

        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                if(mapGrid[y][x] == 1) {
                    Block block = new Block();
                    block.setX(x);
                    block.setY(y);
                    block.setWidth(1);
                    block.setHeight(1);
                    block.setImageName(("images/Wall.png"));
                    block.setLayerName("FOREGROUND");
                    pacmanWorld.addGraphicsEntity(block);
                    pacmanWorld.addPhysicsEntity(block);

                }
                if(mapGrid[y][x] == 2){
                    SuperFruit superFruit = new SuperFruit();
                    superFruit.setX(x);
                    superFruit.setY(y);
                    superFruit.setWidth(0.75);
                    superFruit.setHeight(0.75);
                    superFruit.setImageName("images/Fruit_Cherry.png");
                    superFruit.setLayerName("FOREGROUND");
                    pacmanWorld.addPhysicsEntity(superFruit);
                    pacmanWorld.addGraphicsEntity(superFruit);
                }
                if(mapGrid[y][x] == 0){
                    PacGomme pacGomme = new PacGomme();
                    pacGomme.setX(x+0.3);
                    pacGomme.setY(y+0.3);
                    pacGomme.setWidth(0.2);
                    pacGomme.setHeight(0.2);
                    pacGomme.setImageName("images/pacGomme.jpg");
                    pacGomme.setLayerName("BACKGROUND");
                    pacmanWorld.addPhysicsEntity(pacGomme);
                    pacmanWorld.addGraphicsEntity(pacGomme);
                }
            }
        }
        return mapGrid;
    }

    private int[][] getMapGrid(String mapName){
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(mapName);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + mapName);
        }
        String sc = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining(""));
        int[][] mapTab = new int[16][16];

        for(int i = 0; i < sc.length(); i++){
            if(sc.charAt(i) != '\n')
                mapTab[i/16][i%16] = (int) sc.charAt(i) - (int) '0';
        }
        return mapTab;
    }

}
