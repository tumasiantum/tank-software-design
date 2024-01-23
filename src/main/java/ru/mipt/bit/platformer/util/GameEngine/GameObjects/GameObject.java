package ru.mipt.bit.platformer.util.GameEngine.GameObjects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.GameEngine.Direction;

public interface GameObject {

    GridPoint2 getCoordinates();
    Direction getDirection();

}
