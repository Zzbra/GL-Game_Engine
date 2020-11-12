package amu.gl.equipe200.core;

import amu.gl.equipe200.entity.Enemy;
import amu.gl.equipe200.entity.Player;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class GameWorld {
    private Scene scene;
    private HashMap<String, Node> components;
    private HashMap<String, Pane> layers;

    public HashMap<String, Pane> getLayers() {
        return layers;
    }

    public Scene getScene(){return this.scene;}
    public void setScene(Scene scene){this.scene = scene;}
    public HashMap<String, Node> getUIComponents(){return this.components;}
    private List<Player> players = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();


    public GameWorld() {
        this.components = new HashMap<>();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }


}
