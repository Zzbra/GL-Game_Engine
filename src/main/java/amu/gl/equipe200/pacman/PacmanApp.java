package amu.gl.equipe200.pacman;

import amu.gl.equipe200.pacman.entities.*;
import amu.gl.equipe200.pacman.entities.pacman.Pacman;
import amu.gl.equipe200.pacman.menues.*;

import amu.gl.equipe200.core.GameApp;
import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.core.Settings;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class PacmanApp
    extends GameApp {

    private GameWorld pacmanWorld;
    private Pacman pacman;
    private Blinky blinky;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onInit() {
        System.out.println("Hello onInit");
        this.pacmanWorld = new GameWorld(Settings.WORLD_WIDTH, Settings.WORLD_HEIGHT);

        int[][] map = createMap("Map1.txt");
        getIaEngine().loadMap(map);
        createPlayers();
        createGhost();
        loadMainMenu();
    }

    @Override
    public void onGameIterBegin(double ellapsedTime) {
        System.out.println(blinky.getXSpeed() + " " + blinky.getYSpeed());
    }
    public void onGameIterEnd(long ellapsedTime) { }

    protected void loadMainMenu() { loadMenu(MainMenu.getInstance(this)); }
    public void loadGame() { loadGameWorld(pacmanWorld);}

    public GameWorld getPacmanWorld() { return pacmanWorld; }

    private void createPlayers() {
        // center horizontally, position at 70% vertically
        double x = 16 / 6.0;
        double y = 16 * 0.6;

        Pacman player1 = new Pacman();
        player1.setX(x);
        player1.setY(y);
        player1.setWidth(0.8);
        player1.setHeight(0.8);
        player1.setControls("Z", "S", "Q", "D");
        player1.setLayerName("FOREGROUND");

        pacmanWorld.addGraphicsEntity(player1);
        pacmanWorld.addPhysicsEntity(player1);
        pacmanWorld.addIOEntity(player1);
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

    private int[][] createMap(String mapName){
        int[][] mapGrid = getMapGrid(mapName);

        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                System.out.printf("%d", mapGrid[y][x]);
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
            }
            System.out.println();
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

        System.out.println(sc.length());
        for(int i = 0; i < sc.length(); i++){
            if(sc.charAt(i) != '\n')
                mapTab[i/16][i%16] = (int) sc.charAt(i) - (int) '0';
        }
        return mapTab;
    }

}
