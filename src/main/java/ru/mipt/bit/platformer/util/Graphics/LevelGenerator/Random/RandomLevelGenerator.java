package ru.mipt.bit.platformer.util.Graphics.LevelGenerator.Random;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.Graphics.LevelGenerator.LevelPositions;
import ru.mipt.bit.platformer.util.Tree;

import java.util.*;

public class RandomLevelGenerator {

    private Random random;
    private int height;
    private int width;
    private Set<GridPoint2> levelSet = new HashSet<>();

    public RandomLevelGenerator(int width, int height){
        this.height = height;
        this.width = width;
        this.random = new Random();
    }

    public LevelPositions generate(int numberOfTrees) {
        LevelPositions result = new LevelPositions();
        result.width = this.width;
        result.height = this.height;
        GridPoint2 playerStartCoordinates = this.randomCoordinate();
        levelSet.add(playerStartCoordinates);
        result.setPlayerStartCoordinates(playerStartCoordinates);

        result.addList(Tree.class, List.copyOf(this.randomSet(numberOfTrees)));
        return result;
    }

    private GridPoint2 randomCoordinate(){
        int x = this.random.nextInt(this.width);
        int y = this.random.nextInt(this.height);
        return new GridPoint2(x, y);
    }


    private Set<GridPoint2> randomSet(int numberOfObstacles){
        Set<GridPoint2> res = new HashSet<>();
        while (res.size() < numberOfObstacles){
            GridPoint2 coordinates = this.randomCoordinate();
            if (!levelSet.contains(coordinates)){
                res.add(coordinates);
                levelSet.add(coordinates);
            }
        }
        return res;
    }


}
