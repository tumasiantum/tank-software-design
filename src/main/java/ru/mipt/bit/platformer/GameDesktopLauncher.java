package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.util.*;
import ru.mipt.bit.platformer.util.Graphics.GraphicsController;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.util.Graphics.LevelGenerator.LevelPositions;
import ru.mipt.bit.platformer.util.Graphics.LevelGenerator.Parsers.Parser;
import ru.mipt.bit.platformer.util.Graphics.LevelGenerator.Parsers.TxtParser;
import ru.mipt.bit.platformer.util.Graphics.LevelGenerator.Random.RandomLevelGenerator;

import static com.badlogic.gdx.Input.Keys.*;

public class GameDesktopLauncher implements ApplicationListener {
    private Level level;
    private InputController inputController;
    private GraphicsController graphicsController;


    @Override
    public void create() {
        level = new Level();
        graphicsController = new GraphicsController(level);

//        Parser parser = new TxtParser(graphicsController.getLevelGraphics().getLevelWidth(), graphicsController.getLevelGraphics().getLevelHeight());
//        LevelPositions levelPositions = parser.parse("src/main/resources/pos.txt");
        RandomLevelGenerator randomLevelGenerator = new RandomLevelGenerator(graphicsController.getLevelGraphics().getLevelWidth(), graphicsController.getLevelGraphics().getLevelHeight());
        LevelPositions levelPositions = randomLevelGenerator.generate(20);

        level.init(levelPositions);
        graphicsController.init();

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
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}