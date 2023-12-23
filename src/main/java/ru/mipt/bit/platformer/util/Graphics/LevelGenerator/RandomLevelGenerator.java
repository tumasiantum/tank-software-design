package ru.mipt.bit.platformer.util.Graphics.LevelGenerator;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.Level;

import java.util.*;

public class RandomLevelGenerator implements LevelGenerator {

    private Random random;
    private int height;
    private int width;
    private int numberOfTrees;
    private Set<GridPoint2> levelSet = new HashSet<>();

    public RandomLevelGenerator(int width, int height, int numberOfTrees){
        this.height = height;
        this.width = width;
        this.numberOfTrees = numberOfTrees;
        this.random = new Random();
    }
    @Override
    public Level generate() {
        Level resultLevel = new Level(this.width, this.height);
        GridPoint2 playerStartCoordinates = this.randomCoordinate();
        levelSet.add(playerStartCoordinates);
        resultLevel.setPlayerTank(playerStartCoordinates);
        resultLevel.addTrees(List.copyOf(this.randomSet(this.numberOfTrees)));
        return resultLevel;
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
