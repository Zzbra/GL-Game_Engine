package amu.gl.equipe200.graphicsengine;

public interface GraphicsInterface {
    // Getter for the InGame coordinates
    double getX();
    double getY();
    double getR();

    // Getters for the InGame size
    double getHeight();
    double getWidth();

    // Getter for the graphical properties
    String getImageName(long ellapsedTime);
    String getLayerName();

    // Does the entity need to removed
    default boolean needRemoval() { return false; }

    // Does the render of entity need to be update
    default boolean hasMoved() { return false; }
    default boolean hasNewSprite() { return false; }

    // Trigger
    default void onProcessed(GraphicsEngine engine) { }
}
