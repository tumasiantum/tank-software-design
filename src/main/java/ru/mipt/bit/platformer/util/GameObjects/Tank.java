package ru.mipt.bit.platformer.util.GameObjects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.GameObjects.Managers.CollisionManager;
import ru.mipt.bit.platformer.util.GameObjects.Managers.Direction;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.Graphics.GdxGameUtils.*;

public class Tank implements GameObject {
    private static final float MOVEMENT_SPEED = 0.4f;
    public static final float MOVEMENT_COMPLETED = 1f;
    public static final int MOVEMENT_STARTED = 0;

    public float movementProgress;
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

    @Override
    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public Boolean getRotateProgress() {
        return rotateProgress;
    }

    public void setRotateProgress(Boolean rotateProgress) {
        this.rotateProgress = rotateProgress;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public Direction getDirection() {
        return direction;
    }



    @Override
    public void move(Direction movingDirection, CollisionManager collisionManager, float deltaTime) {
        if (movingDirection != null & isEqual(movementProgress, MOVEMENT_COMPLETED)) {
            GridPoint2 tankTargetCoordinates = movingDirection.apply(coordinates);
            if (collisionManager.isFree(tankTargetCoordinates)) {
                destinationCoordinates = tankTargetCoordinates;
                movementProgress = MOVEMENT_STARTED;
            }
            this.rotate(movingDirection);
        }
        movementProgress = continueProgress(movementProgress, deltaTime, MOVEMENT_SPEED);
        if (isEqual(movementProgress, MOVEMENT_COMPLETED)) {
            if (this.coordinates != this.destinationCoordinates){
                collisionManager.endMovement(coordinates);
            }
            this.coordinates = this.destinationCoordinates;
        }
    }

    public void rotate(Direction direction) {
        this.direction = direction;
        this.rotateProgress = true;
    }


}
