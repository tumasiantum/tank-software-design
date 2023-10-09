package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

public class Tree{

    private final GridPoint2 coordinates;

    public Tree (GridPoint2 coordinates) {
        this.coordinates = coordinates;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

}
