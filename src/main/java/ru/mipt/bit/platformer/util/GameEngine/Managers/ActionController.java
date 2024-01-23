package ru.mipt.bit.platformer.util.GameEngine.Managers;

import ru.mipt.bit.platformer.util.GameEngine.Direction;

public interface ActionController {
    Direction getDirection();
    boolean getShoot();
}
