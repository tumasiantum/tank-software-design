package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.util.Level;
import ru.mipt.bit.platformer.util.Object;
import ru.mipt.bit.platformer.util.Player;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.util.Tree;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {
    private static final float MOVEMENT_SPEED = 0.4f;
    private Batch batch;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;
    Level level2;
    private float playerMovementProgress = 1f;
    Tree tree2;
    Player player2;
    List<Object> objectList = new ArrayList<>();

    @Override
    public void create() {
        batch = new SpriteBatch();
        level2 = new Level("level.tmx", batch);
        tileMovement = level2.getTileMovement();
        levelRenderer = level2.getLevelRenderer();
        TiledMapTileLayer groundLayer = level2.getGroundLayer();
        player2 = new Player("images/tank_blue.png", 1, 1,0f);
        tree2 = new Tree("images/greenTree.png", 1, 4);
        objectList.add(player2);
        objectList.add(tree2);
        moveRectangleAtTileCenter(groundLayer, tree2.getObjectRectangle(), tree2.getTreeObstacleCoordinates());
    }

    @Override
    public void render() {
        // clear the screen
        clearScreen();

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        movePlayer(player2);

        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(player2.getObjectRectangle(), player2.getPlayerCoordinates(), player2.getPlayerDestinationCoordinates(), playerMovementProgress);

        playerMovementProgress = continueProgress(playerMovementProgress, deltaTime, MOVEMENT_SPEED);
        if (isEqual(playerMovementProgress, 1f)) {
            // record that the player has reached his/her destination
            player2.getPlayerCoordinates().set(player2.getPlayerDestinationCoordinates());
        }

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render player

        drawAllTextureRegionUnscaled(objectList);
        // render tree obstacle
//        drawTextureRegionUnscaled(batch, tree2.getObjectGraphics(), tree2.getObjectRectangle(), tree2.getObjectRotation());

        // submit all drawing requests
        batch.end();
    }

    private void movePlayer(Player player) {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            playerMovementProgress = player.playerMovement("UP", tree2, playerMovementProgress);
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            playerMovementProgress = player.playerMovement("LEFT", tree2, playerMovementProgress);
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            playerMovementProgress = player.playerMovement("DOWN", tree2, playerMovementProgress);
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            playerMovementProgress = player.playerMovement("RIGHT", tree2, playerMovementProgress);
        }
    }

    private static void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private void drawAllTextureRegionUnscaled(List<Object> objectList){
        for (Object object: objectList) {
            drawTextureRegionUnscaled(batch, object.getObjectGraphics(), object.getObjectRectangle(), object.getObjectRotation());
        }
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
        tree2.getObjectTexture().dispose();
        player2.getObjectTexture().dispose();
        level2.getLevel().dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
