package amu.gl.equipe200.pacman;

import amu.gl.equipe200.pacman.entities.*;
import amu.gl.equipe200.pacman.menues.*;

import amu.gl.equipe200.core.GameApp;
import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.core.Settings;
import javafx.print.PageLayout;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.Set;
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

        createMap("Map1.txt");
        createPlayers();
        createGhost();
        loadMainMenu();
    }
    public void onGameIterBegin(long ellapsedTime) { }
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
        blinky.setPacMan(pacman);
        pacmanWorld.addGraphicsEntity(blinky);
        pacmanWorld.addPhysicsEntity(blinky);
    }

    private void createMap(String mapName){
        int[][] mapGrid = getMapGrid(mapName);

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if(mapGrid[i][j] == 1) {
                    Block block = new Block();
                    block.setX(j);
                    block.setY(i);
                    block.setWidth(1);
                    block.setHeight(1);
                    block.setImageName(("images/Wall.png"));
                    block.setLayerName("FOREGROUND");
                    pacmanWorld.addGraphicsEntity(block);
                    pacmanWorld.addPhysicsEntity(block);

                }
                if(mapGrid[i][j] == 2){
                    SuperFruit superFruit = new SuperFruit();
                    superFruit.setX(j);
                    superFruit.setY(i);
                    superFruit.setWidth(0.75);
                    superFruit.setHeight(0.75);
                    superFruit.setImageName("images/Fruit_Cherry.png");
                    superFruit.setLayerName("FOREGROUND");
                    pacmanWorld.addPhysicsEntity(superFruit);
                    pacmanWorld.addGraphicsEntity(superFruit);
                }
                if(mapGrid[i][j] == 0){
                    PacGomme pacGomme = new PacGomme();
                    pacGomme.setX(j + 0.5-0.1);
                    pacGomme.setY(i + 0.5-0.1);
                    pacGomme.setWidth(0.2);
                    pacGomme.setHeight(0.2);
                    pacGomme.setImageName("images/PacGum.png");
                    pacGomme.setLayerName("BACKGROUND");
                    pacmanWorld.addPhysicsEntity(pacGomme);
                    pacmanWorld.addGraphicsEntity(pacGomme);
                }
            }
        }
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
