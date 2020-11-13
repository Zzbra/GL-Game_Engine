package amu.gl.equipe200.core;

import amu.gl.equipe200.entity.BaseEntity;
import amu.gl.equipe200.entity.Enemy;
import amu.gl.equipe200.entity.Player;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class GameWorld {
    private Scene scene;
    // Liste des composant UI du GameWorld
    private final HashMap<String, Node> UIComponents;
    // Liste des layers du GameWorld
    private final HashMap<String, Pane> layers;
    // Liste des entitées joueur
    private final List<BaseEntity> players;
    // Liste des entitées ennemies
    private final List<BaseEntity> enemies;


    public GameWorld() {
        this.UIComponents = new HashMap<>();
        this.layers = new HashMap<>();
        this.players = new ArrayList<>();
        this.enemies = new ArrayList<>();
    }

    public HashMap<String, Pane> getLayers() {
        return layers;
    }
    public Scene getScene(){return this.scene;}
    public HashMap<String, Node> getUIComponents(){return this.UIComponents;}
    public List<BaseEntity> getPlayers() {
        return players;
    }
    public List<BaseEntity> getEnemies() {
        return enemies;
    }
    public List<BaseEntity> getEntities() {
        return Stream.concat(players.stream(), enemies.stream()).collect(Collectors.toList());
    }

    // Set la scène du GameWorld
    public void setScene(Scene scene){this.scene = scene;}



}
