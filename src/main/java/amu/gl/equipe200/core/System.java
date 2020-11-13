package amu.gl.equipe200.core;

/**
 * Abstract for a system
 * Must be able to register components and get tick update
 */

public abstract class System {

    public System() { };

    /**
     * A system must be able to register a new component
     * @param component
     */
    public void registerComponent(Component component) { };

    /**
     * Each system update method will be called once each game tick
     * Handle the update procedure of the system
     * @param elapsedTime : time elapsed since the last update
     */
    public void onUpdate (long elapsedTime) { };
}
