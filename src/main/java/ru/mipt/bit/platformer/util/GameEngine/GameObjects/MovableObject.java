package ru.mipt.bit.platformer.util.GameEngine.GameObjects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.GameEngine.Direction;
import ru.mipt.bit.platformer.util.GameEngine.Managers.CollisionManager;

public interface MovableObject {

    void move(CollisionManager collisionManager);
    void startMovement(Direction movingDirection, CollisionManager collisionManager);

    GridPoint2 getDestinationCoordinates();

    float getMovementProgress();
    Direction getDirection();

}
