package org.example.Entity;

import org.example.Component.Component;
import org.example.GameWorld.GameWorld;

import java.util.ArrayList;

public class Entity {

    private GameWorld world;

    private ArrayList<Component> components;

    private boolean active;

    public boolean isActive() { return active; }

    public void update(long lastFrameTime) {
        for (Component c : this.components) {
            if (c.isEnable()) {
                c.update(lastFrameTime);
            }
        }
    }
}
