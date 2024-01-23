package ru.mipt.bit.platformer.util.Graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;

import static ru.mipt.bit.platformer.util.Graphics.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.Graphics.GdxGameUtils.getSingleLayer;

public class GdxLevelGraphics {
    private final TiledMap level;
    private final MapRenderer levelRenderer;
    private final TileMovement tileMovement;
    private final TiledMapTileLayer groundLayer;

    public GdxLevelGraphics(String mapPath, Batch batch){
        this.level = new TmxMapLoader().load(mapPath);
        this.levelRenderer = createSingleLayerMapRenderer(level, batch);
        this.groundLayer = getSingleLayer(level);
        this.tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
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

    public TiledMapTileLayer getGroundLayer() {
        return groundLayer;
    }
}
