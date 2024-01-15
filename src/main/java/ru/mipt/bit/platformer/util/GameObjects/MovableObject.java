package ru.mipt.bit.platformer.util.GameObjects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.GameObjects.Managers.CollisionManager;
import ru.mipt.bit.platformer.util.GameObjects.Managers.Direction;

public interface MovableObject {

    default void move(CollisionManager collisionManager){}
    default void startMovement(Direction movingDirection, CollisionManager collisionManager){}

}
