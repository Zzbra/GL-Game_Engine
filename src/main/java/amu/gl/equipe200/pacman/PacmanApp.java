package amu.gl.equipe200.pacman;

import amu.gl.equipe200.entity.Block;
import amu.gl.equipe200.entity.SuperFruit;
import amu.gl.equipe200.pacman.MainMenu;

import amu.gl.equipe200.core.GameApp;
import amu.gl.equipe200.core.GameWorld;
import amu.gl.equipe200.core.Settings;
import amu.gl.equipe200.entity.Player;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class PacmanApp
    extends GameApp {

    protected GameWorld pacmanWorld;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onInit() {
        System.out.println("Hello onInit");
        this.pacmanWorld = new GameWorld(Settings.WORLD_WIDTH, Settings.WORLD_HEIGHT);

        createMap("Map1.txt");
        createPlayers();
//        createGhost();
        loadMainMenu();
    }
    public void onGameIterBegin(long ellapsedTime) { }
    public void onGameIterEnd(long ellapsedTime) { }

    protected void loadMainMenu() { loadMenu(MainMenu.getInstance(this)); }
    protected void loadGame() { loadGameWorld(pacmanWorld);}


    private void createPlayers() {
        // center horizontally, position at 70% vertically
        double x = 16 / 6.0;
        double y = 16 * 0.6;

        Player player1 = new Player(x, y, 0, 0, 0, 0, Settings.PLAYER_SHIP_HEALTH, 0, Settings.PLAYER_SPEED,  "pacman.jpg", "FOREGROUND");
        player1.setX(x);
        player1.setY(y);
        player1.setWidth(0.8);
        player1.setHeight(0.8);
        player1.setControls("Z", "S", "Q", "D");
        pacmanWorld.addGraphicsEntity(player1);
        pacmanWorld.addPhysicsEntity(player1);
        pacmanWorld.addIOEntity(player1);
    }

    private void createMap(String mapName){
        int[][] mapGrid = getMapGrid(mapName);

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if(mapGrid[i][j] == 1) {
                    Block block = new Block(j , i , 1, 1, "Block.jpg", "BACKGROUND");
                    pacmanWorld.addGraphicsEntity(block);
                    pacmanWorld.addPhysicsEntity(block);

                }
                if(mapGrid[i][j] == 2){
                    SuperFruit superFruit = new SuperFruit(j , i,"SuperFruit.jpg", "BACKGROUND");
                    pacmanWorld.addPhysicsEntity(superFruit);
                    pacmanWorld.addGraphicsEntity(superFruit);
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
