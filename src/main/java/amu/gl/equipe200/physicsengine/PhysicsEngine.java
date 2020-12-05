package amu.gl.equipe200.physicsengine;

import amu.gl.equipe200.core.Engine;
import amu.gl.equipe200.utils.Pair;

import java.util.HashSet;

import static java.lang.Double.max;
import static java.lang.Double.min;

public class PhysicsEngine
        extends Engine {

    // List of the entity to update
    private HashSet<PhysicsInterface> physicsEntities;

    // Size of the world in which the entities move
    private double worldHeight;
    private double worldWidth;

    private static double eps = 0.000001d;

    public PhysicsEngine(double worldWidth, double worldHeight){
        this.physicsEntities = new HashSet<>();
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    // register or remove entities for updates
    public void registerEntity(PhysicsInterface entity){
        this.physicsEntities.add(entity);
    }
    public void removeEntity(PhysicsInterface entity) {
        this.physicsEntities.remove(entity);
    }

    public void update(long ellapsedTime){
        for(PhysicsInterface entity : this.physicsEntities) {
            if(entity.isRemovable()){
                physicsEntities.remove(entity);
                continue;
            }

            // Check if the entity is movable, as collidable only entities does not require any update
            if(!entity.isMovable()) continue;

            // Compute the new position of the entity
            Pair<Double, Double> newPosition = computeNewPosition(entity, ellapsedTime);

            // Check if the entity can go outside the game world, if not snap it back and notify it
            if (entity.isWorldBounded()) newPosition = adjustOnWorldBorder(entity, newPosition);

            // If the entity is collidable check for collisions
            if (entity.isCollidable()) newPosition = adjustOnCollision(entity, newPosition);

            // Finally move the entity where it should go
            move(entity, newPosition);
        }
    }

    /**
     * Compute the new position of the entity as if nothing block it, need to be corrected afterward
     * Does not include the rotation
     * @param entity : entity of which the position should be computed
     * @param elapsedTime : "in-game" time ellapsed since last update
     * @return the new position of the entity in world coordinates
     */
    private Pair<Double, Double> computeNewPosition (PhysicsInterface entity, long elapsedTime) {
        double newX = entity.getX() + elapsedTime * entity.getXSpeed();
        double newY = entity.getY() + elapsedTime * entity.getYSpeed();
        return Pair.create(newX, newY);
    }

    /**
     * Adjust the future position of the entity if it goes beyond the limit of the world
     * Will trigger the callback if the world border is reached
     * @param entity : entity of which the position should be computed
     * @param newPosition : where it should land if nothing is done
     * @return the adjusted position of the enity
     */
    private Pair<Double, Double> adjustOnWorldBorder (PhysicsInterface entity, Pair<Double, Double> newPosition) {
        double newX = newPosition.first, newY = newPosition.second;
        boolean triggered = false;

        // Check on X axis
        if (Double.compare(newX, eps) <= 0) {
            triggered = true;
            newX = 0;
        } else if (Double.compare(newX + entity.getWidth(), this.worldWidth - eps) >= 0) {
            triggered = true;
            newX = this.worldWidth - entity.getWidth();
        }

        // Check on Y axis
        if (Double.compare(newY, eps) <= 0) {
            triggered = true;
            newY = 0;
        } else if (Double.compare(newY + entity.getHeight(), this.worldHeight - eps) >= 0) {
            triggered = true;
            newY = this.worldHeight - entity.getHeight();
        }

        if (triggered) entity.onWorldEnds();
        return Pair.create(newX, newY);
    }

    /**
     * Adjust the future position of the entity if it collide with other entities
     * Will trigger the onCollision callback if triggered
     * @param entity
     * @param newPosition
     * @return
     */
    public Pair<Double, Double> adjustOnCollision(PhysicsInterface entity, Pair<Double, Double> newPosition) {
        for (PhysicsInterface toCheck : this.physicsEntities) {
            // Don't check entities with disabled collidable or yourself
            if (!toCheck.isCollidable()) continue;
            if (toCheck.equals(entity)) continue;

            // check if the two entities collide during the movement
            if (collide(entity, newPosition, toCheck)) {
                // trigger the collision call back
                entity.onCollide(toCheck);

                //En a t'on vraiment besoin ?
                toCheck.onCollide(entity);

                // check if both entities are solid and if so compute the final position of the entity
                if (entity.isSolid() && toCheck.isSolid()) newPosition = computeCollidedPosition(entity, newPosition, toCheck);
            }
        }
        return newPosition;
    }

    /**
     * Check if the new position of A collides with B
     * @param A : First entity
     * @param newPosition : new position of the entity A
     * @param B : Second entity to check against
     * @return : does the 2 entities collide with each other
     */
    private boolean collide(PhysicsInterface A,
                            Pair<Double, Double> newPosition,
                            PhysicsInterface B) {
        return (Double.compare(B.getX() + B.getWidth(), newPosition.first) >= 0
                && Double.compare(B.getX(), newPosition.first + A.getWidth()) <= 0
                && Double.compare(B.getY() + B.getHeight(), newPosition.second ) >=0
                && Double.compare(B.getY(), newPosition.second + A.getHeight()) <= 0);
    }

    /**
     * Compute the new position of the movable entity after collision
     * @param entity: entity of which the position should be computed
     * @param newPosition: where it should land if nothing is done
     * @param toCheck: entity which the movable entity collide with
     * @return: final position of the movable entity
     */



    private Pair<Double, Double> computeCollidedPosition(PhysicsInterface entity,
                                                        Pair<Double, Double> newPosition,
                                                        PhysicsInterface toCheck) {
        Double finalX = newPosition.first;
        Double finalY = newPosition.second;

        // the entity move on the X axis, compute the final X position
        if (Double.compare(entity.getXSpeed(), eps) > 0
            && Double.compare(entity.getX() + entity.getWidth(), toCheck.getX() + eps) > 0 ) {
            // the movable is on the right of the obstacle and try to go in (maybe)
//            finalX = min(finalX, toCheck.getX() - entity.getWidth());
            //finalX = entity.getX();
            stop(entity);
            finalX = toCheck.getX() - entity.getWidth() - eps;

        } else if (Double.compare(entity.getXSpeed(), -eps) < 0
            && Double.compare(entity.getX(),  toCheck.getX() + toCheck.getWidth() - eps) < 0) {
            // the movable is on the left of the obstacle and try to go in (maybe)
//            finalX = max(finalX, toCheck.getX() + toCheck.getWidth());
            //finalX = entity.getX();
            stop(entity);
            finalX = toCheck.getX() + toCheck.getWidth() + eps;
        }

        // the entity move on the Y axis, compute the final Y position
        if (Double.compare(entity.getYSpeed(), eps) > 0
            && Double.compare(entity.getY() + entity.getHeight(), toCheck.getY() + eps) > 0 ) {
            // the movable is on the top of the obstacle and try to go in (maybe)
//            finalY = min(finalY, toCheck.getY() - entity.getHeight());
//            finalY = entity.getY();
            stop(entity);
            finalY = toCheck.getY() - entity.getHeight() - eps;
        } else if (Double.compare(entity.getYSpeed(), -eps) < 0
            && Double.compare(entity.getY(),  toCheck.getY() + toCheck.getWidth() - eps) < 0) {
            // the movable is on the bottom of the obstacle and try to go in (maybe)
//            finalY = max(finalY, toCheck.getY() + toCheck.getHeight());
            //finalY = entity.getY();
            stop(entity);
            finalY = toCheck.getY() + toCheck.getHeight() + eps;
        }
        return Pair.create(finalX, finalY);
    }

    /**
     * Moves the entity to it's new position
     * @param entity
     * @param newPosition
     */
    private void move(PhysicsInterface entity, Pair<Double, Double> newPosition){
        entity.setX(newPosition.first);
        entity.setY(newPosition.second);
    }

    private void stop(PhysicsInterface entity){
        entity.setXSpeed(0);
        entity.setYSpeed(0);
    }
}
