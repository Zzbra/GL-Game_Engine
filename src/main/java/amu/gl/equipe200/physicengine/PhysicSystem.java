package amu.gl.equipe200.physicengine;

import amu.gl.equipe200.core.Entity;
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

    /** InGame size of the world **/
    private Double worldWidth;
    private Double worldHeight;

    public PhysicSystem(Double worldWidth, Double worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    /**
     * Main loop for the physic engine
     * Will update the position of all movable entities and trigger the collision if needed
     * the collision behavior will be handle in the component with the collision handler
     * @param elapsedTime : time elapsed since the last update
     */
    @Override
    public void onUpdate(long elapsedTime, ArrayList<Entity> affectedEntity) {
        for( Entity currentEntity : affectedEntity) {
            // Check if the entity is movable, as collidable only entities does not require any update
            if (! currentEntity.hasComponent(MovableComponent.class)) continue;
            MovableComponent currentMovable = (MovableComponent) currentEntity.getComponent(MovableComponent.class);
            // update the position if the component is enable
            if (! currentMovable.isEnable()) continue;

            // Compute the new position of the entity
            double newX = currentMovable.getX() + elapsedTime * currentMovable.getSpeedX();
            double newY = currentMovable.getY() + elapsedTime * currentMovable.getSpeedY();
            // If go further than the windows snap it back
            if (newX + currentMovable.getWidth() > this.worldWidth) newX -= newX + currentMovable.getWidth() - this.worldWidth;
            if (newY + currentMovable.getHeight() > this.worldHeight) newY -= newY + currentMovable.getHeight() - this.worldHeight;

            Pair<Double, Double> newPosition = Pair.create(newX, newY);

            // check if the entities is also collidable
            if (currentEntity.hasComponent(CollidableComponent.class)) {
                // Get the collidable component of the entity and continue if it is enable
                CollidableComponent currentCollidable =
                        (CollidableComponent) currentEntity.getComponent(CollidableComponent.class);
                if (currentCollidable.isEnable()) {
                    // The collide component is enable, Check for collisions
                    for( Entity toCheckEntity : affectedEntity) {
                        // Don't entity without/wtih disabled CollidableComponant or yourself
                        if (affectedEntity.equals(toCheckEntity)) continue;
                        if (! toCheckEntity.hasComponent(CollidableComponent.class)) continue;
                        CollidableComponent toCheckCollidable =
                                (CollidableComponent) toCheckEntity.getComponent(CollidableComponent.class);
                        if (! toCheckCollidable.isEnable()) continue;

                        // check if the two entities collide during the movement
                        if (this.collide(currentCollidable, newPosition, toCheckCollidable)) {
                            // proc the collision trigger
                            currentCollidable.onCollide(toCheckEntity);
                            toCheckCollidable.onCollide(currentEntity);

                            // check if both entities are solid
                            if (  currentCollidable.isImpassable() && toCheckCollidable.isImpassable()) {
                                // compute the final position of the movable entity
                                newPosition = this.computeMovablePosition(currentCollidable, newPosition, toCheckCollidable);
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
        return (B.getX() + B.getWidth() >= newPosition.first
                && B.getX() <= newPosition.first + A.getWidth()
                && B.getY() + B.getHeight() >= newPosition.second
                && B.getY() <= newPosition.second + A.getHeight());
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
            && currentCollidable.getX() + currentCollidable.getWidth() < toCheck.getX()) {
            // the movable is on the right of the obstacle and try to go in (maybe)
            finalX = min(finalX, toCheck.getX() - currentCollidable.getWidth());
        }
        if (currentCollidable.getSpeedX() < 0
            && toCheck.getX() + toCheck.getWidth() < currentCollidable.getX()) {
            // the movable is on the left of the obstacle and try to go in (maybe)
            finalX = max(finalX, toCheck.getX() + toCheck.getWidth());
        }

        // the entity move on the Y axis, compute the final Y position
        if (currentCollidable.getSpeedY() > 0
            && currentCollidable.getY() + currentCollidable.getHeight() < toCheck.getY()) {
                // the movable is on the top of the obstacle and try to go in (maybe)
                finalY = min(finalY, toCheck.getY() - currentCollidable.getHeight());
            }
        if (currentCollidable.getSpeedY() < 0
            && toCheck.getY() + toCheck.getHeight() < currentCollidable.getY()) {
            // the movable is on the bottom of the obstacle and try to go in (maybe)
            finalY = max(finalY, toCheck.getY() + toCheck.getHeight());
        }
        return Pair.create(finalX, finalY);
    }
}
