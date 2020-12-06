package amu.gl.equipe200.pacman;

import amu.gl.equipe200.graphicsengine.GraphicsInterface;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class PacmanApp
    extends GameApp {

    private GameWorld pacmanWorld;
    private Pacman pacman;
    private Blinky blinky;
    private Counter counter;
    private int score;
    private ArrayList<Life> lifeCounter;

    private int grid_size;
    private double cell_height, cell_width;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onInit() {
        System.out.println("Hello onInit");

        score = 0;
        this.pacmanWorld = new GameWorld(Settings.WORLD_WIDTH, Settings.WORLD_HEIGHT);

        int[][] map = createMap("Map.txt");

        blinky.setPacMan(pacman);

        getIaEngine().loadMap(map, this.cell_width, this.cell_height);
        loadMainMenu();
        createCounter();
        initLife();
    }
    @Override
    public void handleCollisions(HashSet<Pair<PhysicsInterface, PhysicsInterface>> collisions){
        super.handleCollisions(collisions);
        for(Pair<PhysicsInterface, PhysicsInterface> collision : collisions){
            if(collision.first.getTag() == Settings.Tag.PLAYER && collision.second.getTag() == Settings.Tag.PACGUM){
                score++;
                counter.setValue(score);
            }

            if(collision.first.getTag() == Settings.Tag.PLAYER && collision.second.getTag() == Settings.Tag.ENEMY){
                if(lifeCounter.size()> 0) {
                   lifeCounter.get(pacman.getLives()).toRemove();
                }if (pacman.getLives() == 0){
                    loadMainMenu();
                }
                pacman.activateInvincible();
            }
        }

    }


    @Override
    public void onGameIterBegin(double ellapsedTime) {
        /*** update the entities ***/
       pacman.update(ellapsedTime);
    }
    public void onGameIterEnd(long ellapsedTime) { }

    protected void loadMainMenu() { loadMenu(MainMenu.getInstance(this)); }
    public void loadGame() { loadGameWorld(pacmanWorld);}

    public GameWorld getPacmanWorld() { return pacmanWorld; }

    private void initLife(){
        lifeCounter = new ArrayList<>();
        for (int i = 0; i < pacman.getLives(); i++) {
            Life life = new Life();
            life.setX(Settings.WORLD_WIDTH-i-1);
            life.setY(Settings.WORLD_HEIGHT);
            life.setHeight(1);
            life.setWidth(1);
            pacmanWorld.addGraphicsEntity(life);
            lifeCounter.add(life);
        }

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

        for (int y = 0; y < grid_size; y++) {
            for (int x = 0; x < grid_size; x++) {
                System.out.printf("%d", mapGrid[y][x]);
                switch (mapGrid[y][x]) {
                    case 0: break; // Blank space
                    case 1: {
                        double wall_scale = 1;
                        double width = cell_width * wall_scale;
                        double height = cell_height * wall_scale;
                        Block wall = new Block();
                        wall.setX((x * cell_width) + (cell_width / 2) - (width / 2));
                        wall.setY((y * cell_height) + (cell_height / 2) - (height / 2));
                        wall.setWidth(width);
                        wall.setHeight(height);
                        wall.setImageName(("images/Wall.png"));
                        wall.setLayerName("BACKGROUND");
                        pacmanWorld.addGraphicsEntity(wall);
                        pacmanWorld.addPhysicsEntity(wall);
                        break;
                    }
                    case 2: {
                        double pacgum_scale = 0.25;
                        double width = cell_width * pacgum_scale;
                        double height = cell_height * pacgum_scale;
                        PacGomme PacGomme = new PacGomme();
                        PacGomme.setX((x * cell_width) + (cell_width / 2) - (width / 2));
                        PacGomme.setY((y * cell_height) + (cell_height / 2) - (height / 2));
                        PacGomme.setWidth(width);
                        PacGomme.setHeight(height);
                        PacGomme.setImageName("images/PacGum.png");
                        PacGomme.setLayerName("BACKGROUND");
                        pacmanWorld.addPhysicsEntity(PacGomme);
                        pacmanWorld.addGraphicsEntity(PacGomme);
                        break;
                    }
                    case 3: {
                        double fruit_scale = 0.5;
                        double width = cell_width * fruit_scale;
                        double height = cell_height * fruit_scale;
                        SuperFruit sp = new SuperFruit();
                        sp.setX((x * cell_width) + (cell_width / 2) - (width / 2));
                        sp.setY((y * cell_height) + (cell_height / 2) - (height / 2));
                        sp.setWidth(width);
                        sp.setHeight(height);
                        sp.setImageName("images/Fruit_Cherry.png");
                        sp.setLayerName("BACKGROUND");
                        pacmanWorld.addPhysicsEntity(sp);
                        pacmanWorld.addGraphicsEntity(sp);
                        break;
                    }
                    case 4: {
                        break;
                    }
                    case 5: {
                        double pacman_scale = 0.99;
                        double width = cell_width * pacman_scale;
                        double height = cell_height * pacman_scale;
                        pacman = new Pacman();
                        pacman.setX((x * cell_width) + (cell_width / 2) - (width / 2));
                        pacman.setY((y * cell_height) + (cell_height / 2) - (height / 2));
                        pacman.setWidth(width);
                        pacman.setHeight(height);
                        pacman.setLayerName("FOREGROUND");
                        pacman.setControls("Z", "S", "Q", "D");
                        pacmanWorld.addGraphicsEntity(pacman);
                        pacmanWorld.addPhysicsEntity(pacman);
                        pacmanWorld.addIOEntity(pacman);
                        break;
                    }
                    case 6: {
                        double blincky_scale = 0.9;
                        double width = cell_width * blincky_scale;
                        double height = cell_height * blincky_scale;
                        blinky = new Blinky();
                        blinky.setX((x * cell_width) + (cell_width / 2) - (width / 2));
                        blinky.setY((y * cell_height) + (cell_height / 2) - (height / 2));
                        blinky.setWidth(width);
                        blinky.setHeight(width);
                        blinky.setImageName("images/Ghost_Red_1.png");
                        blinky.setLayerName("FOREGROUND");
                        pacmanWorld.addGraphicsEntity(blinky);
                        pacmanWorld.addPhysicsEntity(blinky);
                        pacmanWorld.addAIEntity(blinky);
                        break;
                    }
//                    case 7: {
//                        break;
//                    }
//                    case 8: {
//                        break;
//                    }
//                    case 9: {
//                        break;
//                    }
                    default: {
                        System.out.println("Parsing Error");
                    }
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
        // Map must be square
        this.grid_size = (int) Math.sqrt(sc.length());
        this.cell_width = Settings.WORLD_WIDTH / grid_size;
        this.cell_height = Settings.WORLD_HEIGHT / grid_size;

        System.out.println("Grid len= " + sc.length());
        System.out.println("Grid size= " + grid_size);

        int[][] mapTab = new int[grid_size][grid_size];

        for(int i = 0; i < sc.length(); i++){
            if(sc.charAt(i) != '\n')
                mapTab[i/grid_size][i%grid_size] = (int) sc.charAt(i) - (int) '0';
        }

        return mapTab;
    }

}
