package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.Graphics.LevelGenerator.LevelPositions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Level {
    private List<Tree> treeList;
    private List<Tank> tankList;
    private Set<GridPoint2> obstacleSet;
    private Tank playerTank;


    public Level(){
        this.treeList = new ArrayList<>();
        this.tankList = new ArrayList<>();
        this.obstacleSet = new HashSet<>();
    }

    public void init(LevelPositions levelPositions){
        this.addTrees(levelPositions.getResults().get(Tree.class));
        this.setPlayerTank(levelPositions.getPlayerStartCoordinates(), Direction.DOWN);
    }

    public void setPlayerTank(GridPoint2 startPlayerCoordinations, Direction startPlayerDirection) {
        playerTank = this.addTank(startPlayerCoordinations, startPlayerDirection);
    }

    public void addTrees(List<GridPoint2> coordinatesList){
        for (GridPoint2 coordinates: coordinatesList) {
            Tree tree = new Tree(coordinates);
            treeList.add(tree);
            obstacleSet.add(coordinates);
        }
    }

    public Tank addTank(GridPoint2 coordinates, Direction direction){
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
