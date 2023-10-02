package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

import java.util.HashMap;
import java.util.Set;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tank {
    private static final float MOVEMENT_SPEED = 0.4f;
    public static final float MOVEMENT_COMPLETED = 1f;
    public static final int MOVEMENT_STARTED = 0;

    private float movementProgress;
    private GridPoint2 coordinates;
    private GridPoint2 destinationCoordinates;
    private Direction direction;
    private Boolean rotateProgress = false;

    public Tank(GridPoint2 coordinates, Direction direction) {
        this.movementProgress = 1;
        this.coordinates = coordinates;
        this.destinationCoordinates = coordinates;
        this.direction = direction;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public boolean isMoving() {
        return isEqual(movementProgress, MOVEMENT_COMPLETED);
    }

    public void moveTo(GridPoint2 tankTargetCoordinates) {
        destinationCoordinates = tankTargetCoordinates;
        movementProgress = MOVEMENT_STARTED;
    }

    public void rotate(Direction direction) {
        this.direction = direction;
        this.rotateProgress = true;
    }

    public Boolean getRotateProgress() {
        return rotateProgress;
    }

    public void setRotateProgress(Boolean rotateProgress) {
        this.rotateProgress = rotateProgress;
    }

    public void updateState(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, MOVEMENT_SPEED);
        if (isMoving()) {
            // record that the player has reached his/her destination
            coordinates = destinationCoordinates;
        }
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public Direction getDirection() {
        return direction;
    }

    public void moveTank(Direction movingDirection, HashMap<Object, GridPoint2> obstacleHashMap) {
        if (movingDirection != null & this.isMoving()) {
            GridPoint2 tankTargetCoordinates = movingDirection.apply(this.getCoordinates());
            if (!collides(obstacleHashMap, tankTargetCoordinates)) {
                this.moveTo(tankTargetCoordinates);
                obstacleHashMap.put(this, tankTargetCoordinates);
            }
            this.rotate(movingDirection);
        }
    }

    private boolean collides(HashMap<Object, GridPoint2> obstacleHashMap, GridPoint2 object2) {
        Set<Object> objectSet = obstacleHashMap.keySet();
        for (Object object: objectSet) {
            if (obstacleHashMap.get(object).equals(object2)) {
                return true;
            }
        }
        return false;
    }

}
