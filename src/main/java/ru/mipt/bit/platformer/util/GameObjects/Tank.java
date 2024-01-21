package ru.mipt.bit.platformer.util.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.GameObjects.Managers.CollisionManager;
import ru.mipt.bit.platformer.util.GameObjects.Managers.Direction;
import ru.mipt.bit.platformer.util.GameObjects.TankStates.LightTank;
import ru.mipt.bit.platformer.util.GameObjects.TankStates.TankState;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.Graphics.GdxGameUtils.*;

public class Tank implements GameObject, MovableObject, LiveableObject {
    private static final float MOVEMENT_SPEED = 0.4f;
    public static final float MOVEMENT_COMPLETED = 1f;
    public static final int MOVEMENT_STARTED = 0;
    private TankState state;

    public float movementProgress;
    private GridPoint2 coordinates;
    private GridPoint2 destinationCoordinates;
    private Direction direction;
    private Boolean rotateProgress = false;
    private Long lastCallTime;

    public Tank(GridPoint2 coordinates, Direction direction) {
        this.movementProgress = MOVEMENT_COMPLETED;
        this.coordinates = coordinates;
        this.destinationCoordinates = coordinates;
        this.direction = direction;
        this.state = new LightTank(this);
        lastCallTime = System.currentTimeMillis();
    }

    @Override
    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    @Override
    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public Boolean getRotateProgress() {
        return rotateProgress;
    }

    public void setRotateProgress(Boolean rotateProgress) {
        this.rotateProgress = rotateProgress;
    }

    @Override
    public float getMovementProgress() {
        return movementProgress;
    }
    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void move(CollisionManager collisionManager) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        movementProgress = continueProgress(movementProgress, deltaTime, state.getMovementSpeed());
        if (isEqual(movementProgress, MOVEMENT_COMPLETED)) {
            if (this.coordinates != this.destinationCoordinates) {
                collisionManager.removeObstacle(coordinates);
            }
            this.coordinates = this.destinationCoordinates;
        }
    }

    @Override
    public void startMovement(Direction movingDirection, CollisionManager collisionManager){
        if (isEqual(movementProgress, MOVEMENT_COMPLETED)) {
            GridPoint2 tankTargetCoordinates = movingDirection.apply(coordinates);
            if (collisionManager.isFree(tankTargetCoordinates)) {
                collisionManager.addObstacle(tankTargetCoordinates);
                destinationCoordinates = tankTargetCoordinates;
                movementProgress = MOVEMENT_STARTED;
            }
            this.rotate(movingDirection);
        }
    }

    public void rotate(Direction direction) {
        this.direction = direction;
        this.rotateProgress = true;
    }

    public void setState(TankState state) {
        this.state = state;
    }

    public void shoot() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastCallTime >= state.getRechargeTime() && state.getShootingAbility()) {
            Bullet bullet = new Bullet(direction.apply(coordinates), direction);
            Level.getInstance().addObject(bullet);
            lastCallTime = currentTime;
        }
    }

    @Override
    public void damage(Float damage) {
        state.damage(damage);
    }

    @Override
    public Boolean isAlive() {
        return (state.getHealth() > 0);
    }

    @Override
    public Float getHealth() {
        return state.getHealth();
    }
}

