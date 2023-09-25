package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {
    private Batch batch;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;
    Level level;
    Tree tree;
    Tree tree2;
    Tank tank;
    private InputController inputController;
    private Graphics tankGraphics;
    private Graphics treeGraphics;
    private Graphics treeGraphics2;

    List<Graphics> graphicsList = new ArrayList<>();
    private HashMap<Object, GridPoint2> obstacleHashMap = new HashMap<>();
    @Override
    public void create() {
        batch = new SpriteBatch();
        level = new Level("level.tmx", batch);
        tileMovement = level.getTileMovement();
        levelRenderer = level.getLevelRenderer();
        TiledMapTileLayer groundLayer = level.getGroundLayer();
        tank = new Tank(new GridPoint2(1, 3), Direction.RIGHT);
        tankGraphics = new Graphics("images/tank_blue.png", tank.getDirection());
        tree = new Tree(new GridPoint2(3, 4));
        tree2 = new Tree(new GridPoint2(1, 2));
//        treeList.add(tree);
        treeGraphics = new Graphics("images/greenTree.png", Direction.UP);
        treeGraphics2 = new Graphics("images/greenTree.png", Direction.UP);
        graphicsList.add(tankGraphics);
        graphicsList.add(treeGraphics);
        graphicsList.add(treeGraphics2);
        obstacleHashMap.put(tank, tank.getCoordinates());
        obstacleHashMap.put(tree, tree.getCoordinates());
        obstacleHashMap.put(tree2, tree2.getCoordinates());
        moveRectangleAtTileCenter(groundLayer, treeGraphics.getRectangle(), tree.getCoordinates());
        moveRectangleAtTileCenter(groundLayer, treeGraphics2.getRectangle(), tree2.getCoordinates());
        initKeyMappings();
    }

    @Override
    public void render() {
        // clear the screen
        clearScreen();
        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();
        Direction direction = inputController.getDirection();
        updateGameState(deltaTime, direction);
        renderGame();
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        for (Graphics graphics : graphicsList) {
            graphics.getTexture().dispose();
        }
        level.getLevel().dispose();
        batch.dispose();
    }

    private void renderGame() {
        // render each tile of the level
        levelRenderer.render();
        // start recording all drawing commands
        batch.begin();
        // render all objects
        drawAllTextureRegionUnscaled(graphicsList);
        // submit all drawing requests
        batch.end();
    }



    private void updateGameState(float deltaTime, Direction movingDirection) {
        tank.moveTank(movingDirection, tankGraphics, obstacleHashMap);
        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(tankGraphics.getRectangle(), tank.getCoordinates(), tank.getDestinationCoordinates(), tank.getMovementProgress());
        tank.updateState(deltaTime);
    }

    private static void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private void drawAllTextureRegionUnscaled(List<Graphics> graphicsList){
        for (Graphics graphics: graphicsList) {
            drawTextureRegionUnscaled(batch, graphics.getTextureRegion(), graphics.getRectangle(), graphics.getDirection().getRotation());
        }
    }
    private void initKeyMappings() {
        inputController = new InputController();
        inputController.addMapping(UP, Direction.UP);
        inputController.addMapping(W, Direction.UP);
        inputController.addMapping(LEFT, Direction.LEFT);
        inputController.addMapping(A, Direction.LEFT);
        inputController.addMapping(DOWN, Direction.DOWN);
        inputController.addMapping(S, Direction.DOWN);
        inputController.addMapping(RIGHT, Direction.RIGHT);
        inputController.addMapping(D, Direction.RIGHT);
    }
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
