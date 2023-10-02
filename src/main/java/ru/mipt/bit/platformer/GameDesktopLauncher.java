package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.*;
import ru.mipt.bit.platformer.util.Graphics.GdxTankGraphics;
import ru.mipt.bit.platformer.util.Graphics.GraphicsController;
import ru.mipt.bit.platformer.util.Graphics.GraphicsObject;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {
    private Batch batch;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;
    private Level level;
    private Tank tank;
    private InputController inputController;
    private GdxTankGraphics tankGraphics;
    private GraphicsController graphicsController;

    @Override
    public void create() {
        batch = new SpriteBatch();
        level = new Level("level.tmx", batch);
        tileMovement = level.getTileMovement();
        levelRenderer = level.getLevelRenderer();

        graphicsController = new GraphicsController(level);

        tank = level.createTank(new GridPoint2(1, 3), Direction.RIGHT);

        List<GridPoint2> treeCoordinatesList = new ArrayList<>();
        treeCoordinatesList.add(new GridPoint2(3,4));
        treeCoordinatesList.add(new GridPoint2(1,2));
        level.createTrees(treeCoordinatesList);

        graphicsController.init();

        initKeyMappings();
        tankGraphics = graphicsController.getTankGraphicsObjectList().get(0);
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
        graphicsController.dispose();
        level.getLevel().dispose();
        batch.dispose();
    }

    private void renderGame() {
        // render each tile of the level
        levelRenderer.render();
        // start recording all drawing commands
        batch.begin();
        // render all objects
        graphicsController.drawAllTextureRegionUnscaled(batch);
        // submit all drawing requests
        batch.end();
    }



    private void updateGameState(float deltaTime, Direction movingDirection) {
        tankGraphics.moveTankPicture(movingDirection);
        tank.moveTank(movingDirection, level.getObstacleHashMap());
        // calculate interpolated player screen coordinates
        Tank tankForDrawing = tankGraphics.getTank();
        tileMovement.moveRectangleBetweenTileCenters(tankGraphics.getRectangle(), tankForDrawing.getCoordinates(), tankForDrawing.getDestinationCoordinates(), tankForDrawing.getMovementProgress());
        tank.updateState(deltaTime);
    }

    private static void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
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
