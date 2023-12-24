package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Level {
    private List<Tree> treeList;
    private List<Tank> tankList;
    private Set<GridPoint2> obstacleSet;
    private Tank playerTank;
    public Integer width;
    public Integer height;



    public Level(Integer width, Integer height) {
        this.treeList = new ArrayList<>();
        this.tankList = new ArrayList<>();
        this.obstacleSet = new HashSet<>();
        this.width = width;
        this.height = height;
    }

    public void setPlayerTank(GridPoint2 startPlayerCoordinations) {
        playerTank = this.addTank(startPlayerCoordinations, Direction.DOWN);
    }

    public void addTrees(List<GridPoint2> coordinatesList) {
        for (GridPoint2 coordinates : coordinatesList) {
            Tree tree = new Tree(coordinates);
            treeList.add(tree);
            obstacleSet.add(coordinates);
        }
    }

    private Tank addTank(GridPoint2 coordinates, Direction direction) {
        Tank tank = new Tank(coordinates, direction);
        tankList.add(tank);
        obstacleSet.add(coordinates);
        return tank;
    }

    public void updateState(Direction direction) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        playerTank.move(direction, obstacleSet, deltaTime);
    }

    public List<Tree> getTreeList() {
        return treeList;
    }

    public List<Tank> getTankList() {
        return tankList;
    }

}
