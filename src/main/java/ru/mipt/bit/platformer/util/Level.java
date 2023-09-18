package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class Level {

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;
    private TiledMapTileLayer groundLayer;

    public Level(String mapPath, Batch batch){
        level = new TmxMapLoader().load(mapPath);
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
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
}
