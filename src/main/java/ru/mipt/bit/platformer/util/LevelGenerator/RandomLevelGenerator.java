package ru.mipt.bit.platformer.util.LevelGenerator;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.GameObject;
import ru.mipt.bit.platformer.util.GameEngine.Level;
import ru.mipt.bit.platformer.util.GameEngine.Direction;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.Tank;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.Tree;
import ru.mipt.bit.platformer.util.Graphics.GraphicsController;
import ru.mipt.bit.platformer.util.Listeners.Event;

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
    public Level generate(GraphicsController graphicsController) {
        Tank playerTank = new Tank(randomCoordinate(), Direction.UP);
        gameObjectList.add(playerTank);
        Level level = new Level(this.width, this.height, playerTank);
        level.events.subscribe(Event.ADD_GAME_OBJECT, graphicsController);
        level.events.subscribe(Event.REMOVE_GAME_OBJECT, graphicsController);
        return level;
    }

    @Override
    public void fillLevel(Level resultLevel) {
        generateGameObjects(Tree.class, numberOfTrees);
        generateGameObjects(Tank.class, numberOfTanks);
        for (GameObject gameObject: gameObjectList){
            resultLevel.addObject(gameObject);
        }
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

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }
}
