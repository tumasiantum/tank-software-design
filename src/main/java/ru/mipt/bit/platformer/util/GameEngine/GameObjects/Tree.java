package ru.mipt.bit.platformer.util.GameEngine.GameObjects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.GameEngine.Direction;

public class Tree implements GameObject {

    private final GridPoint2 coordinates;

    public Tree (GridPoint2 coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    @Override
    public Direction getDirection() {
        return Direction.UP;
    }

}
