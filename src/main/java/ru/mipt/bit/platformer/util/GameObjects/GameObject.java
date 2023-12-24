package ru.mipt.bit.platformer.util.GameObjects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.GameObjects.Managers.CollisionManager;
import ru.mipt.bit.platformer.util.GameObjects.Managers.Direction;

public interface GameObject {

    GridPoint2 getCoordinates();
    default void move(Direction movingDirection, CollisionManager collisionManager, float deltaTime){}
    default Direction getDirection(){
        return Direction.UP;
    };

}
