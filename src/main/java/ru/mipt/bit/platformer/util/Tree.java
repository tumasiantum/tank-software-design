package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Set;

public class Tree implements GameObject{

    private final GridPoint2 coordinates;

    public Tree (GridPoint2 coordinates) {
        this.coordinates = coordinates;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    @Override
    public void move(Direction movingDirection, Set<GridPoint2> obstacleSet, float deltaTime) {

    }
}
