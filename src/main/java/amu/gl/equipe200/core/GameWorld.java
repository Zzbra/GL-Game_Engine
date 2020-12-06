package amu.gl.equipe200.core;

import amu.gl.equipe200.graphicsengine.GraphicsInterface;
import amu.gl.equipe200.inputengine.IOInterface;
import amu.gl.equipe200.physicsengine.PhysicsInterface;

import java.util.HashSet;

public class GameWorld {
    private final double width;
    private final double height;

    private final HashSet<PhysicsInterface> physicsEntities = new HashSet<>();
    private final HashSet<GraphicsInterface> graphicsEntities = new HashSet<>();
    private final HashSet<IOInterface> ioEntities = new HashSet<>();


    public GameWorld(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() { return width; }
    public double getHeight() { return height; }

    public void addPhysicsEntity(PhysicsInterface e) { this.physicsEntities.add(e); }
    public void addGraphicsEntity(GraphicsInterface e) { this.graphicsEntities.add(e); }
    public void addIOEntity(IOInterface e) { this.ioEntities.add(e); }

    public HashSet<PhysicsInterface> getPhysicsEntities() { return this.physicsEntities; }
    public HashSet<GraphicsInterface> getGraphicsEntities() { return this.graphicsEntities; }
    public HashSet<IOInterface> getIOEntities() { return this.ioEntities; }

    public void removePhysicsEntity(PhysicsInterface e) { this.physicsEntities.remove(e); }
    public void removeGraphicsEntity(GraphicsInterface e) { this.physicsEntities.remove(e); }
    public void removeIOEntity(IOInterface e) { this.physicsEntities.remove(e); }
}
