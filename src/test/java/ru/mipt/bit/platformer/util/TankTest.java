//package ru.mipt.bit.platformer.util;
//
//import com.badlogic.gdx.math.GridPoint2;
//import org.junit.jupiter.api.BeforeEach;
//import ru.mipt.bit.platformer.util.GameObjects.GameObject;
//import ru.mipt.bit.platformer.util.GameObjects.Level;
//import ru.mipt.bit.platformer.util.GameObjects.Managers.CollisionManager;
//import ru.mipt.bit.platformer.util.GameObjects.Managers.Direction;
//import ru.mipt.bit.platformer.util.GameObjects.Tank;
//import ru.mipt.bit.platformer.util.GameObjects.Tree;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//
//public class TankTest {
//
//    private Tank tank;
//
//    @BeforeEach
//    void init(){
//        Tree tree = new Tree(1,1);
//        List<GameObject> gameObjectList = new ArrayList<>();
//        gameObjectList.add(tree);
//        tank = new Tank(new GridPoint2(0, 1), Direction.UP);
//        gameObjectList.add(tank)
//        Level level = new Level(2,2, gameObjectList, tank);
//        CollisionManager collisionManager = new CollisionManager(level);
//    }
//
////    @Test
////    void moveTankTestWithObstacle(){
////        tank.rotate(Direction.LEFT);
////        Direction movingDirection = Direction.UP;
////        GridPoint2 coordinatesBefore = tank.getCoordinates();
////        Set<GridPoint2> obstacleSet = new HashSet<>();
////        GridPoint2 obstacleCoordinates = movingDirection.apply(tank.getCoordinates());
////        obstacleSet.add(obstacleCoordinates);
////        tank.movementProgress = MOVEMENT_COMPLETED;
////        tank.move(movingDirection, obstacleSet, Gdx.graphics.getDeltaTime());
////
////        Assertions.assertEquals(tank.getDirection(), movingDirection);
////        Assertions.assertEquals(tank.getDestinationCoordinates(), coordinatesBefore);
////
////    }
//
////    @Test
////    void moveTankTestWithoutObstacle(){
////        tank.rotate(Direction.LEFT);
////        Direction movingDirection = Direction.UP;
////        GridPoint2 coordinatesAfter = movingDirection.apply(tank.getCoordinates());
////        CollisionManager collisionManager = new CollisionManager(new Level(2,2, List<GameObject> list.add(this)))
////        Set<GridPoint2> obstacleSet = new HashSet<>();
////        GridPoint2 obstacleCoordinates = movingDirection.apply(coordinatesAfter);
////        obstacleSet.add(obstacleCoordinates);
////        tank.movementProgress = MOVEMENT_COMPLETED;
////        tank.move(movingDirection, obstacleSet, Gdx.graphics.getDeltaTime());
////
////        Assertions.assertEquals(tank.getDirection(), movingDirection);
////        Assertions.assertEquals(tank.getDestinationCoordinates(), coordinatesAfter);
////
////    }
//
//}
