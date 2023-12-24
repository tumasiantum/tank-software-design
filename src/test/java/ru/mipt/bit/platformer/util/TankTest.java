package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static ru.mipt.bit.platformer.util.Tank.MOVEMENT_COMPLETED;

public class TankTest {

    private Tank tank;

    @BeforeEach
    void init(){
        tank = new Tank(new GridPoint2(1, 1), Direction.UP);
    }

    @Test
    void moveTankTestWithObstacle(){
        tank.rotate(Direction.LEFT);
        Direction movingDirection = Direction.UP;
        GridPoint2 coordinatesBefore = tank.getCoordinates();
        Set<GridPoint2> obstacleSet = new HashSet<>();
        GridPoint2 obstacleCoordinates = movingDirection.apply(tank.getCoordinates());
        obstacleSet.add(obstacleCoordinates);
        tank.movementProgress = MOVEMENT_COMPLETED;
        tank.move(movingDirection, obstacleSet, Gdx.graphics.getDeltaTime());

        Assertions.assertEquals(tank.getDirection(), movingDirection);
        Assertions.assertEquals(tank.getDestinationCoordinates(), coordinatesBefore);

    }

    @Test
    void moveTankTestWithoutObstacle(){
        tank.rotate(Direction.LEFT);
        Direction movingDirection = Direction.UP;
        GridPoint2 coordinatesAfter = movingDirection.apply(tank.getCoordinates());
        Set<GridPoint2> obstacleSet = new HashSet<>();
        GridPoint2 obstacleCoordinates = movingDirection.apply(coordinatesAfter);
        obstacleSet.add(obstacleCoordinates);
        tank.movementProgress = MOVEMENT_COMPLETED;
        tank.move(movingDirection, obstacleSet, Gdx.graphics.getDeltaTime());

        Assertions.assertEquals(tank.getDirection(), movingDirection);
        Assertions.assertEquals(tank.getDestinationCoordinates(), coordinatesAfter);

    }

}
