package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.Level;
import ru.mipt.bit.platformer.util.Player;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.util.Tree;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private Texture blueTankTexture;
    private TextureRegion playerGraphics;
    private Rectangle playerRectangle;
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    private GridPoint2 playerCoordinates;
    // which tile the player want to go next
    private GridPoint2 playerDestinationCoordinates;
    private float playerMovementProgress = 1f;
    private float playerRotation;

    //private Texture greenTreeTexture;
    //private TextureRegion treeObstacleGraphics;
    //private GridPoint2 treeObstacleCoordinates = new GridPoint2();
    //private Rectangle treeObstacleRectangle = new Rectangle();
    Level level2;
    Tree tree2;
    Player player2;

    @Override
    public void create() {
        batch = new SpriteBatch();
        // load level tiles
        //level = new TmxMapLoader().load("level.tmx");
        //levelRenderer = createSingleLayerMapRenderer(level, batch);
        //TiledMapTileLayer groundLayer = getSingleLayer(level);
        //tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        level2 = new Level("level.tmx", batch);
        tileMovement = level2.getTileMovement();
        levelRenderer = level2.getLevelRenderer();
        TiledMapTileLayer groundLayer = level2.getGroundLayer();
        player2 = new Player("images/tank_blue.png", 1, 1,0f);
        // Texture decodes an image file and loads it into GPU memory, it represents a native resource
        //blueTankTexture = new Texture("images/tank_blue.png");
        // TextureRegion represents Texture portion, there may be many TextureRegion instances of the same Texture
        playerGraphics = player2.getObjectGraphics();
        playerRectangle = player2.getPlayerRectangle();
        // set player initial position
        playerDestinationCoordinates = player2.getPlayerDestinationCoordinates();
        playerCoordinates = player2.getPlayerCoordinates();
        playerRotation = player2.getPlayerRotation();

//        greenTreeTexture = new Texture("images/greenTree.png");
//        treeObstacleGraphics = new TextureRegion(greenTreeTexture);
//        treeObstacleCoordinates = new GridPoint2(1, 3);
//        treeObstacleRectangle = createBoundingRectangle(treeObstacleGraphics);
        tree2 = new Tree("images/greenTree.png", 1, 4);
        //moveRectangleAtTileCenter(groundLayer, treeObstacleRectangle, treeObstacleCoordinates);
        moveRectangleAtTileCenter(groundLayer, tree2.getObjectRectangle(), tree2.getTreeObstacleCoordinates());
    }

    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            if (isEqual(playerMovementProgress, 1f)) {
                // check potential player destination for collision with obstacles
                if (isThereCollision(true, true)) {
                    playerDestinationCoordinates.y++;
                    playerMovementProgress = 0f;
                }
                playerRotation = 90f;
            }
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            if (isEqual(playerMovementProgress, 1f)) {
                if (isThereCollision(false, false)) {
                    playerDestinationCoordinates.x--;
                    playerMovementProgress = 0f;
                }
                playerRotation = -180f;
            }
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            if (isEqual(playerMovementProgress, 1f)) {
                if (isThereCollision(true, false)) {
                    playerDestinationCoordinates.y--;
                    playerMovementProgress = 0f;
                }
                playerRotation = -90f;
            }
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            if (isEqual(playerMovementProgress, 1f)) {
                if (isThereCollision(false, true)) {
                    playerDestinationCoordinates.x++;
                    playerMovementProgress = 0f;
                }
                playerRotation = 0f;
            }
        }

        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(playerRectangle, playerCoordinates, playerDestinationCoordinates, playerMovementProgress);

        playerMovementProgress = continueProgress(playerMovementProgress, deltaTime, MOVEMENT_SPEED);
        if (isEqual(playerMovementProgress, 1f)) {
            // record that the player has reached his/her destination
            playerCoordinates.set(playerDestinationCoordinates);
        }

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render player
        drawTextureRegionUnscaled(batch, playerGraphics, playerRectangle, playerRotation);

        // render tree obstacle
        //drawTextureRegionUnscaled(batch, treeObstacleGraphics, treeObstacleRectangle, 0f);
        drawTextureRegionUnscaled(batch, tree2.getObjectGraphics(), tree2.getObjectRectangle(), 0f);

        // submit all drawing requests
        batch.end();
    }

    private boolean isThereCollision(boolean direction, boolean sign) {
        if (direction)
            if (sign)
                return !tree2.getTreeObstacleCoordinates().equals(incrementedY(player2.getPlayerCoordinates()));
            else
                return !tree2.getTreeObstacleCoordinates().equals(decrementedY(player2.getPlayerCoordinates()));
        else
        if (sign)
            return !tree2.getTreeObstacleCoordinates().equals(incrementedX(player2.getPlayerCoordinates()));
        else
            return !tree2.getTreeObstacleCoordinates().equals(decrementedX(player2.getPlayerCoordinates()));
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
