package ru.mipt.bit.platformer.util.GameObjects;

import ru.mipt.bit.platformer.util.GameObjects.Managers.CollisionManager;
import ru.mipt.bit.platformer.util.GameObjects.Managers.Direction;

public interface LiveableObject {

    void damage(Float damage);
    Boolean isAlive();

}
