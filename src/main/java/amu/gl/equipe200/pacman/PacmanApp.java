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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class PacmanApp
    extends GameApp {

    private GameWorld pacmanWorld;

    private Pacman pacman;
    private Blinky blinky;
    private Clyde clyde;

    private Counter counter;
    private int score;
    private int nbGum;

    private ArrayList<Life> lifeCounter;

    private Pair<Double,Double> pacmanInit;
    private Pair<Double,Double> blinkyInit;
    private Pair<Double,Double> clydeInit;


    private int grid_size;
    private double cell_height = 1, cell_width = 1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onInit() {
        loadMainMenu();
    }
    @Override
    public void handleCollisions(HashSet<Pair<PhysicsInterface, PhysicsInterface>> collisions){
        super.handleCollisions(collisions);
        for(Pair<PhysicsInterface, PhysicsInterface> collision : collisions){
            if(collision.first.getTag() == Settings.Tag.PLAYER && collision.second.getTag() == Settings.Tag.PACGUM){
                if(score<999)
                    score++;
                nbGum--;
                System.out.println("nbGum: " + nbGum);
                counter.setValue(score);
            }
            if(collision.first.getTag() == Settings.Tag.PLAYER && collision.second.getTag() == Settings.Tag.ENEMY && !pacman.isPoweredUp()){
                if(lifeCounter.size()> 0) {
                   lifeCounter.get(pacman.getLives()).toRemove();
                   pacman.setX(pacmanInit.first);
                   pacman.setY(pacmanInit.second);
                   blinky.setX(blinkyInit.first);
                   blinky.setY(blinkyInit.second);
                   clyde.setX(clydeInit.first);
                   clyde.setY(clydeInit.second);
                   pacman.desactivateAllPower();
                }if (pacman.getLives() == 0){
                    loadMainMenu();
                }
            }
            if(collision.first.getTag() == Settings.Tag.PLAYER && collision.second.getTag() == Settings.Tag.ENEMY && pacman.isPoweredUp()){
                if(collision.second == blinky){
                    collision.second.setX(blinkyInit.first);
                    collision.second.setY(blinkyInit.second);
                }
                if(collision.second == clyde) {
                    collision.second.setX(clydeInit.first);
                    collision.second.setY(clydeInit.second);
                }
                score+=20;
                counter.setValue(score);
            }
        }
    }


    @Override
    public void onGameIterBegin(double ellapsedTime) {
        /*** update the entities ***/
       pacman.update(ellapsedTime);
       clyde.update(ellapsedTime);
       if(nbGum == 0){
//           winGame();
       }
       if(pacman.isPoweredUp()){
           clyde.fear(clydeInit);
           blinky.fear(blinkyInit);
       }else{
           clyde.fearEnd();
           blinky.fearEnd();
       }
    }
    public void onGameIterEnd(double ellapsedTime) {
        if (nbGum == 0) loadMenu(WinMenu.getInstance(this));
        if (pacman.getLives() == 0) loadMenu(LoseMenu.getInstance(this));
    }

    protected void loadMainMenu() { loadMenu(MainMenu.getInstance(this)); }
    public void loadGame() {
        score = 0;
        nbGum = 0;
        int[][] map = loadMap("Map.txt");
        loadNewGameWorld(map);
        getIaEngine().loadMap(map, this.cell_width, this.cell_height);
        loadGameWorld(pacmanWorld);
        this.getGraphicsEngine().setBackgroundColorToBlack();
    }

    public GameWorld getPacmanWorld() { return pacmanWorld; }

    private void initLife(){
        double blockHeight = Settings.SCENE_HEIGHT / this.pacmanWorld.getHeight();
        double size = Settings.UISECTION_HEIGHT / blockHeight;
        lifeCounter = new ArrayList<>();
        for (int i = 0; i < pacman.getLives(); i++) {
            Life life = new Life();
            life.setX(this.pacmanWorld.getWidth()- i * size - size);
            life.setY(this.pacmanWorld.getHeight());
            life.setHeight(size);
            life.setWidth(size);
            pacmanWorld.addGraphicsEntity(life);
            lifeCounter.add(life);
        }
    }

    private void createCounter(){
        double blockHeight = Settings.SCENE_HEIGHT / this.pacmanWorld.getHeight();
        double size = Settings.UISECTION_HEIGHT / blockHeight;
        counter = new Counter();
        counter.setWidth(size);
        counter.setHeight(size);
        counter.setX(0);
        counter.setY(this.pacmanWorld.getHeight());

        for(Digit digit : counter.getDigits()){
            pacmanWorld.addGraphicsEntity(digit);
        }
    }


    private void loadNewGameWorld(int[][] map) {
        this.pacmanWorld = new GameWorld(this.grid_size * cell_width, this.grid_size * cell_height);

        for (int y = 0; y < grid_size; y++) {
            for (int x = 0; x < grid_size; x++) {
                System.out.printf("%d", map[y][x]);
                switch (map[y][x]) {
                    case 0: break; // Blank space
                    case 1: {
                        double wall_scale = 1;
                        double width = cell_width * wall_scale;
                        double height = cell_width * wall_scale;
                        Wall wall = new Wall();
                        wall.setX((x * cell_width) + (cell_width / 2) - (width / 2));
                        wall.setY((y * cell_height) + (cell_height / 2) - (height / 2));
                        wall.setWidth(width);
                        wall.setHeight(height);
                        wall.setImageName(("images/Wall.png"));
                        wall.setLayerName("BACKGROUND");
                        this.pacmanWorld.addGraphicsEntity(wall);
                        this.pacmanWorld.addPhysicsEntity(wall);
                        break;
                    }
                    case 2: {
                        double pacgum_scale = 0.25;
                        double width = cell_width * pacgum_scale;
                        double height = cell_width * pacgum_scale;
                        PacGum PacGum = new PacGum();
                        PacGum.setX((x * cell_width) + (cell_width / 2) - (width / 2));
                        PacGum.setY((y * cell_height) + (cell_height / 2) - (height / 2));
                        PacGum.setWidth(width);
                        PacGum.setHeight(height);
                        this.pacmanWorld.addPhysicsEntity(PacGum);
                        this.pacmanWorld.addGraphicsEntity(PacGum);
                        this.nbGum++;
                        break;
                    }
                    case 3: {
                        double fruit_scale = 0.75;
                        double width = cell_width * fruit_scale;
                        double height = cell_height * fruit_scale;
                        PowerUp pu = new PowerUp();
                        pu.setX((x * cell_width) + (cell_width / 2) - (width / 2));
                        pu.setY((y * cell_height) + (cell_height / 2) - (height / 2));
                        pu.setWidth(width);
                        pu.setHeight(height);
                        this.pacmanWorld.addPhysicsEntity(pu);
                        this.pacmanWorld.addGraphicsEntity(pu);
                        break;
                    }
                    case 4: {
                        double tunnel_scale = 0.75;
                        double width = cell_width * tunnel_scale;
                        double height = cell_height * tunnel_scale;
                        TunnelUp tu = new TunnelUp();
                        tu.setX((x * cell_width) + (cell_width / 2) - (width / 2));
                        tu.setY((y * cell_height) + (cell_height / 2) - (height / 2));
                        tu.setWidth(width);
                        tu.setHeight(height);
                        this.pacmanWorld.addPhysicsEntity(tu);
                        this.pacmanWorld.addGraphicsEntity(tu);
                        break;
                    }
                    case 5: {
                        double pacman_scale = 0.90;
                        double width = cell_width * pacman_scale;
                        double height = cell_height * pacman_scale;
                        pacman = new Pacman();
                        pacman.setX((x * cell_width) + (cell_width / 2) - (width / 2));
                        pacman.setY((y * cell_height) + (cell_height / 2) - (height / 2));
                        pacmanInit=Pair.create(pacman.getX(), pacman.getY());
                        pacman.setWidth(width);
                        pacman.setHeight(height);
                        pacman.setLayerName("FOREGROUND");
                        pacman.setControls("Z", "S", "Q", "D");
                        this.pacmanWorld.addGraphicsEntity(pacman);
                        this.pacmanWorld.addPhysicsEntity(pacman);
                        this.pacmanWorld.addIOEntity(pacman);

                        break;
                    }
                    case 6: {
                        double blincky_scale = 0.9;
                        double width = cell_width * blincky_scale;
                        double height = cell_height * blincky_scale;
                        blinky = new Blinky();
                        blinky.setX((x * cell_width) + (cell_width / 2) - (width / 2));
                        blinky.setY((y * cell_height) + (cell_height / 2) - (height / 2));
                        blinkyInit=Pair.create(blinky.getX(), blinky.getY());
                        blinky.setWidth(width);
                        blinky.setHeight(width);
                        blinky.setImageName("images/Ghost_Red_1.png");
                        blinky.setLayerName("FOREGROUND");
                        this.pacmanWorld.addGraphicsEntity(blinky);
                        this.pacmanWorld.addPhysicsEntity(blinky);
                        this.pacmanWorld.addAIEntity(blinky);
                        break;
                    }
                    case 7: {
                        double clyde_scale = 0.9;
                        double width = cell_width * clyde_scale;
                        double height = cell_height * clyde_scale;
                        clyde = new Clyde();
                        clyde.setX((x * cell_width) + (cell_width / 2) - (width / 2));
                        clyde.setY((y * cell_height) + (cell_height / 2) - (height / 2));
                        clydeInit=Pair.create(clyde.getX(), clyde.getY());
                        clyde.setWidth(width);
                        clyde.setHeight(width);
                        clyde.setImageName("images/Ghost_Pink_1.png");
                        clyde.setLayerName("FOREGROUND");
                        this.pacmanWorld.addGraphicsEntity(clyde);
                        this.pacmanWorld.addPhysicsEntity(clyde);
                        this.pacmanWorld.addAIEntity(clyde);
                        clyde.initGoal();
                        break;
                    }
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
        this.blinky.setPacMan(this.pacman);
        this.clyde.setPacMan(this.pacman);
        createCounter();
        initLife();
    }

    private int[][] loadMap(String mapName){
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(mapName);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + mapName);
        }
        String sc = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining(""));
        // Map must be square
        this.grid_size = (int) Math.sqrt(sc.length());

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
