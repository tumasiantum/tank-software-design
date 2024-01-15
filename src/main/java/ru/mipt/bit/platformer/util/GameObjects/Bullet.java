package ru.mipt.bit.platformer.util.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.GameObjects.Managers.CollisionManager;
import ru.mipt.bit.platformer.util.GameObjects.Managers.Direction;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.Graphics.GdxGameUtils.continueProgress;

public class Bullet implements GameObject, MovableObject {
    private static final float BULLET_MOVEMENT_SPEED = 0.2f;
    public static final float MOVEMENT_COMPLETED = 1f;
    public static final int MOVEMENT_STARTED = 0;

    public float movementProgress;
    private GridPoint2 coordinates;
    private GridPoint2 destinationCoordinates;
    private Direction direction;
    private Boolean rotateProgress = false;

    public Bullet(GridPoint2 coordinates, Direction direction) {
        this.movementProgress = MOVEMENT_STARTED;
        this.direction = direction;
        this.coordinates = coordinates;
        this.destinationCoordinates = direction.apply(coordinates);
    }

    @Override
    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }


    public float getMovementProgress() {
        return movementProgress;
    }

    public Direction getDirection() {
        return direction;
    }



    @Override
    public void move(CollisionManager collisionManager) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        movementProgress = continueProgress(movementProgress, deltaTime, BULLET_MOVEMENT_SPEED);

        if (isEqual(movementProgress, MOVEMENT_COMPLETED)) {
            this.coordinates = this.destinationCoordinates;
            movementProgress = MOVEMENT_STARTED;
            this.destinationCoordinates = direction.apply(coordinates);
        }
    }

    @Override
    public void startMovement(Direction movingDirection, CollisionManager collisionManager){}

}
