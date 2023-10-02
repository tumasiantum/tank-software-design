package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Level {

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;
    private TiledMapTileLayer groundLayer;
    private List<Tree> treeList = new ArrayList<>();
    private List<Tank> tankList = new ArrayList<>();

    private HashMap<Object, GridPoint2> obstacleHashMap = new HashMap<>();



    public Level(String mapPath, Batch batch){
        this.level = new TmxMapLoader().load(mapPath);
        this.levelRenderer = createSingleLayerMapRenderer(level, batch);
        this.groundLayer = getSingleLayer(level);
        this.tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
    }

    public TiledMapTileLayer getGroundLayer() {
        return groundLayer;
    }

    public TiledMap getLevel() {
        return level;
    }
    public MapRenderer getLevelRenderer() {
        return levelRenderer;
    }
    public TileMovement getTileMovement() {
        return tileMovement;
    }

    public void createTrees(List<GridPoint2> coordinatesList){
        for (GridPoint2 coordinates: coordinatesList) {
            Tree tree = new Tree(coordinates);
            treeList.add(tree);
            obstacleHashMap.put(tree, coordinates);
        }
    }

    public Tank createTank(GridPoint2 coordinates, Direction direction){
        Tank tank = new Tank(coordinates, direction);
        tankList.add(tank);
        obstacleHashMap.put(tank, coordinates);
        return tank;
    }
    public List<Tree> getTreeList() {
        return treeList;
    }

    public List<Tank> getTankList() {
        return tankList;
    }

    public HashMap<Object, GridPoint2> getObstacleHashMap() {
        return obstacleHashMap;
    }
}
