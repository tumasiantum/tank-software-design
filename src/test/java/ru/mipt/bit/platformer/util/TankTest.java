package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static ru.mipt.bit.platformer.util.Tank.MOVEMENT_COMPLETED;

public class TankTest {

    private Tank tank;

    @BeforeEach
    void init(){
        tank = new Tank(new GridPoint2(1, 1), Direction.UP);
    }

    @Test
    void moveToTest() {
        GridPoint2 tankTargetCoordinates = tank.getCoordinates().add(0, 1);
        tank.moveTo(tankTargetCoordinates);
        assertEquals(tank.getDestinationCoordinates(), tankTargetCoordinates);
        assertEquals(tank.isMoving(), false);
    }

    @Test
    void moveTankTestWithObstacle(){
        tank.rotate(Direction.LEFT);
        Direction movingDirection = Direction.UP;
        GridPoint2 coordinatesBefore = tank.getCoordinates();
        HashMap<Object, GridPoint2> obstacleHashMap = new HashMap<>();
        GridPoint2 treeCoordinates = movingDirection.apply(tank.getCoordinates());
        obstacleHashMap.put(new Tree(treeCoordinates), treeCoordinates);
        tank.movementProgress = MOVEMENT_COMPLETED;
        tank.moveTank(movingDirection, obstacleHashMap);

        assertEquals(tank.getDirection(), movingDirection);
        assertEquals(tank.getDestinationCoordinates(), coordinatesBefore);

    }

    @Test
    void moveTankTestWithoutObstacle(){
        tank.rotate(Direction.LEFT);
        Direction movingDirection = Direction.UP;
        GridPoint2 coordinatesAfter = movingDirection.apply(tank.getCoordinates());
        HashMap<Object, GridPoint2> obstacleHashMap = new HashMap<>();
        GridPoint2 treeCoordinates = new GridPoint2(3,3);
        obstacleHashMap.put(new Tree(treeCoordinates), treeCoordinates);
        tank.movementProgress = MOVEMENT_COMPLETED;
        tank.moveTank(movingDirection, obstacleHashMap);

        assertEquals(tank.getDirection(), movingDirection);
        assertEquals(tank.getDestinationCoordinates(), coordinatesAfter);

    }

//    public void moveTank(Direction movingDirection, HashMap<Object, GridPoint2> obstacleHashMap) {
//        if (movingDirection != null & this.isMoving()) {
//            GridPoint2 tankTargetCoordinates = movingDirection.apply(this.getCoordinates());
//            if (!collides(obstacleHashMap, tankTargetCoordinates)) {
//                this.moveTo(tankTargetCoordinates);
//                obstacleHashMap.put(this, tankTargetCoordinates);
//            }
//            this.rotate(movingDirection);
//        }
//    }
}
