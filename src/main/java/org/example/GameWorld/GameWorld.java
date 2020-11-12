package org.example.GameWorld;


import org.example.Entity.Entity;
import org.example.Systems.SystemClass;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class GameWorld {

    private SystemClass core;

    private Scene scene;

    private ArrayList<Entity> entities;
    private ArrayList<Entity> entitiesToAdd;

    private boolean isActive;

    public GameWorld(SystemClass core) {
        this.core = core;

        this.scene = new Scene(new StackPane(), 640, 480);

        this.entities = new ArrayList<>(); // list of all the entities in the world
        this.entitiesToAdd = new ArrayList<>(); // buffer for the entities to be added

        this.isActive = false;
    }

    public void addEntity(Entity e) {
        this.entitiesToAdd.add(e);
    }

    public void removeEntity(Entity e) throws Exception {
        if (! this.entities.contains(e)) throw new Exception("This world does not cotaint this entities");
        this.entities.remove(e);
    }

    public void onLoad() {
        // Describe what should be done in the game world on load
    }

    public void onUpdate(long lastFrameTime) {
        // add the entity added since last frame
        this.entities.addAll(this.entitiesToAdd);
        this.entitiesToAdd.clear();


        for (Entity e : this.entities) {
            // update all the entities of the World
            if (e.isActive()) {
                e.update(lastFrameTime);
            }
        }
    }

}
