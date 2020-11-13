package amu.gl.equipe200.physicengine;

import amu.gl.equipe200.core.Component;
import amu.gl.equipe200.core.System;
import amu.gl.equipe200.utils.Pair;

import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;


/**
 * Physical Engine System
 * Handle movement and collision between entities
 */
public class PhysicSystem
        extends System {

    /** Components linked to the physical system **/
    private ArrayList<MovableComponent> movables;
    private ArrayList<CollidableComponent> collidables;

    /** InGame size of the world **/
    private Double worldWidth;
    private Double worldHeight;

    public PhysicSystem(Double worldWidth, Double worldHeight) {
        this.movables = new ArrayList<>();
        this.collidables = new ArrayList<>();
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    @Override
    public void registerComponent(Component component) {
        // register the component on the right list
        if (component instanceof MovableComponent) {
            this.movables.add((MovableComponent) component);
        }
        if (component instanceof CollidableComponent) {
            this.collidables.add((CollidableComponent) component);
        }
    }

    /**
     * Main loop for the physic engine
     * Will update the position of all movable entities and trigger the collision if needed
     * the collision behavior will be handle in the component with the collision handler
     * @param elapsedTime : time elapsed since the last update
     */
    @Override
    public void onUpdate(long elapsedTime) {
        for( MovableComponent currentMovable : this.movables) {
            // update the position if the component is enable
            if (! currentMovable.isEnable()) continue;

            // Compute the new position of the entity
            Double newX = currentMovable.getX() + elapsedTime * currentMovable.getSpeedX();
            Double newY = currentMovable.getY() + elapsedTime * currentMovable.getSpeedY();

            // If go futher than the windows snap it back
            if (newX + currentMovable.getW() > this.worldWidth) newX -= newX + currentMovable.getW() - this.worldWidth;
            if (newY + currentMovable.getH() > this.worldHeight) newY -= newY + currentMovable.getH() - this.worldHeight;

            Pair<Double, Double> newPosition = Pair.create(newX, newY);

            // check if the entities is also collidable
            if (currentMovable.getEntity().hasComponent(MovableComponent.class.getSimpleName())) {
                // Get the collidable component of the entity and continue if it is enable
                CollidableComponent currentCollidable = (CollidableComponent) currentMovable.getEntity().getComponent(MovableComponent.class.getSimpleName());

                if (currentCollidable.isEnable()) {
                    // The collide component is enable, Check for collisions
                    for( CollidableComponent toCheck : this.collidables) {
                        // Don't check disable component or yourself
                        if(! toCheck.isEnable()) continue;
                        if(currentCollidable.equals(toCheck)) continue;

                        // check if the two entities collide during the movement
                        if (this.collide(currentCollidable, newPosition, toCheck)) {
                            // proc the collision trigger
                            currentCollidable.onCollide(toCheck);
                            toCheck.onCollide(currentCollidable);

                            // check if both entities are solid
                            if (  currentCollidable.isImpassable() && toCheck.isImpassable()) {
                                // compute the final position of the movable entity
                                newPosition = this.computeMovablePosition(currentCollidable, newPosition, toCheck);
                            }
                        }
                    }
                }
            }
            // move the entity
            currentMovable.moveTo(newPosition.first, newPosition.second);
        }
    }

    /**
     * Check if the new position of A collides with B
     * @param A : Collidable component of the first entity
     * @param newPosition : new position of the entity A
     * @param B : Collidable component of the second entity
     * @return : does the 2 entities collide with each other
     */
    private boolean collide(CollidableComponent A,
                            Pair<Double, Double> newPosition,
                            CollidableComponent B) {
        return (B.getX() + B.getW() >= newPosition.first
                && B.getX() <= newPosition.first + A.getW()
                && B.getY() + B.getH() >= newPosition.second
                && B.getY() <= newPosition.second + A.getH());
    }

    /**
     * Compute the new position of the movable entity after collision
     * @param currentCollidable: movable entity
     * @param newPosition: new position of the movable entity
     * @param toCheck: entity which the movable entity collide with
     * @return: final position of the movable entity
     */
    private Pair<Double, Double> computeMovablePosition(CollidableComponent currentCollidable,
                                                      Pair<Double, Double> newPosition,
                                                      CollidableComponent toCheck) {
        Double finalX = newPosition.first;
        Double finalY = newPosition.second;

        // the entity move on the X axis, compute the final X position
        if (currentCollidable.getSpeedX() > 0
            && currentCollidable.getX() + currentCollidable.getW() < toCheck.getX()) {
            // the movable is on the right of the obstacle and try to go in (maybe)
            finalX = min(finalX, toCheck.getX() - currentCollidable.getW());
        }
        if (currentCollidable.getSpeedX() < 0
            && toCheck.getX() + toCheck.getW() < currentCollidable.getX()) {
            // the movable is on the left of the obstacle and try to go in (maybe)
            finalX = max(finalX, toCheck.getX() + toCheck.getW());
        }

        // the entity move on the Y axis, compute the final Y position
        if (currentCollidable.getSpeedY() > 0
            && currentCollidable.getY() + currentCollidable.getH() < toCheck.getY()) {
                // the movable is on the top of the obstacle and try to go in (maybe)
                finalY = min(finalY, toCheck.getY() - currentCollidable.getH());
            }
        if (currentCollidable.getSpeedY() < 0
            && toCheck.getY() + toCheck.getH() < currentCollidable.getY()) {
            // the movable is on the bottom of the obstacle and try to go in (maybe)
            finalY = max(finalY, toCheck.getY() + toCheck.getH());
        }
        return Pair.create(finalX, finalY);
    }
}
