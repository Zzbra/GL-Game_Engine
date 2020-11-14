package amu.gl.equipe200.core;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Abstract for a system
 * Must be able to register components and get tick update
 */

public abstract class System {

    public System() { };

    /**
     * Each system update method will be called once each game tick
     * It handles the update procedure of the system
     * @param elapsedTime : time elapsed since the last update
     * @param affectedEntity : list of the enntiy affected by the system (should have the component managed by the system)
     */
    public abstract void onUpdate (long elapsedTime, ArrayList<Entity> affectedEntity);
}
