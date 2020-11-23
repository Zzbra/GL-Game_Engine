package amu.gl.equipe200.graphicsengine;

public interface GraphicsInterface {
    // Getter for the coordiantes in the game world
    double getX();
    double getY();
    double getR();

    // Getter the properties for the identification and rendering
    String getID();
    String getImageName(long ellapsedTime);
    String getLayerName();

    // Does the reder of entity need to be update
    default boolean hasMoved() { return false; }
    default boolean hasNewSprite() { return false; }
}
