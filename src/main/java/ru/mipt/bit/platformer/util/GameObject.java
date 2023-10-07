package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Set;

public interface GameObject {

    void move(Direction movingDirection, Set<GridPoint2> obstacleSet, float deltaTime);
}
