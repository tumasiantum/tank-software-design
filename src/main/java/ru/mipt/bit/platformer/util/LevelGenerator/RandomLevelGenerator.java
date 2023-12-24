package ru.mipt.bit.platformer.util.LevelGenerator;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.GameObjects.GameObject;
import ru.mipt.bit.platformer.util.GameObjects.Level;
import ru.mipt.bit.platformer.util.GameObjects.Managers.Direction;
import ru.mipt.bit.platformer.util.GameObjects.Tank;
import ru.mipt.bit.platformer.util.GameObjects.Tree;

import java.util.*;

public class RandomLevelGenerator implements LevelGenerator {

    private Random random;
    private int height;
    private int width;
    private int numberOfTrees;
    private int numberOfTanks;
    private List<GameObject> gameObjectList = new ArrayList<>();

    private Set<GridPoint2> levelSet = new HashSet<>();

    public RandomLevelGenerator(int width, int height, int numberOfTrees, int numberOfTanks){
        this.height = height;
        this.width = width;
        this.numberOfTrees = numberOfTrees;
        this.numberOfTanks = numberOfTanks;
        this.random = new Random();
    }
    @Override
    public Level generate() {
        Tank playerTank = new Tank(randomCoordinate(), Direction.UP);
        gameObjectList.add(playerTank);
        generateGameObjects(Tree.class, numberOfTrees);
        generateGameObjects(Tank.class, numberOfTanks);
        Level resultLevel = new Level(this.width, this.height, gameObjectList, playerTank);
        return resultLevel;
    }

    private GridPoint2 randomCoordinate(){
        while (true) {
            int x = this.random.nextInt(this.width);
            int y = this.random.nextInt(this.height);
            GridPoint2 coordinates = new GridPoint2(x, y);
            if (!levelSet.contains(coordinates)){
                levelSet.add(coordinates);
                return coordinates;
            }
        }
    }


    private Set<GridPoint2> randomSet(int numberOfObstacles){
        Set<GridPoint2> res = new HashSet<>();
        for (int i = 0; i < numberOfObstacles; i++) {
            GridPoint2 coordinates = this.randomCoordinate();
            res.add(coordinates);
        }
        return res;
    }

    private void generateGameObjects(Class gameObjectType, int numberOfGameObjectType){
        List<GridPoint2> coordinatesList = List.copyOf(this.randomSet(numberOfGameObjectType));
        if (gameObjectType == Tank.class){
            for (GridPoint2 coordinates : coordinatesList) {
                Tank tank = new Tank(coordinates, Direction.UP);
                gameObjectList.add(tank);
            }
        } else if (gameObjectType == Tree.class) {
            for (GridPoint2 coordinates : coordinatesList) {
                Tree tree = new Tree(coordinates);
                gameObjectList.add(tree);
            }
        }
    }
}
