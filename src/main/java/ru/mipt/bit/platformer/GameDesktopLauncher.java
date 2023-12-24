package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.util.*;
import ru.mipt.bit.platformer.util.Graphics.GraphicsController;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.util.Graphics.LevelGenerator.LevelGenerator;
import ru.mipt.bit.platformer.util.Graphics.LevelGenerator.RandomLevelGenerator;
import ru.mipt.bit.platformer.util.Graphics.LevelGenerator.TxtLevelGenerator;

import static com.badlogic.gdx.Input.Keys.*;

public class GameDesktopLauncher implements ApplicationListener {
    private Level level;
    private InputController inputController;
    private GraphicsController graphicsController;


    @Override
    public void create() {
//        LevelGenerator generator = new TxtLevelGenerator("src/main/resources/pos.txt");
        LevelGenerator generator = new RandomLevelGenerator(10 ,6 ,25);
        level = generator.generate();
        graphicsController = new GraphicsController(level);
        initKeyMappings();
    }

    @Override
    public void render() {
        // clear the screen
        graphicsController.clearScreen();
        // get time passed since the last render
        level.updateState(inputController.getDirection());
        graphicsController.render();
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
        config.setWindowedMode(1280, 768);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}