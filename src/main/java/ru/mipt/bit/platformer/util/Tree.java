package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

public class Tree extends Object{

    private final GridPoint2 treeObstacleCoordinates;

    public GridPoint2 getTreeObstacleCoordinates() {
        return treeObstacleCoordinates;
    }

    public Tree(String texturePath, int treeCoordinatesX, int treeCoordinatesY) {
        super(texturePath);
        treeObstacleCoordinates = new GridPoint2(treeCoordinatesX, treeCoordinatesY);
    }
//
//    public void drawTree(TiledMapTileLayer groundLayer){
//        moveRectangleAtTileCenter(groundLayer, this.getObjectRectangle(), treeObstacleCoordinates);
//    }
}
