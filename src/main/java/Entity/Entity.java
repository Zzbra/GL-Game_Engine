package Entity;

import Componant.Componant;
import GameWorld.GameWorld;

import java.util.ArrayList;

public class Entity {

    private GameWorld world;

    private ArrayList<Componant> componants;

    private boolean active;

    public boolean isActive() { return active; }

    public void update(long lastFrameTime) {
        for (Componant c : this.componants) {
            if (c.isEnable()) {
                c.update(lastFrameTime);
            }
        }
    }
}
