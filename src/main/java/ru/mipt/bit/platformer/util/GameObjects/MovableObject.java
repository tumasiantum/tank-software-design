package ru.mipt.bit.platformer.util.GameObjects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.GameObjects.Managers.CollisionManager;
import ru.mipt.bit.platformer.util.GameObjects.Managers.Direction;

public interface MovableObject {

    void move(CollisionManager collisionManager);
    void startMovement(Direction movingDirection, CollisionManager collisionManager);

    GridPoint2 getDestinationCoordinates();

    float getMovementProgress();
    Direction getDirection();

}
