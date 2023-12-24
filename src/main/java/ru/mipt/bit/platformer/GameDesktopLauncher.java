package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.util.GameObjects.Level;
import ru.mipt.bit.platformer.util.GameObjects.Managers.InputController;
import ru.mipt.bit.platformer.util.Graphics.GraphicsController;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.util.LevelGenerator.LevelGenerator;
import ru.mipt.bit.platformer.util.LevelGenerator.TxtLevelGenerator;

public class GameDesktopLauncher implements ApplicationListener {
    private Level level;
    private InputController inputController;
    private GraphicsController graphicsController;


    @Override
    public void create() {
        LevelGenerator generator = new TxtLevelGenerator("src/main/resources/pos.txt");
//        LevelGenerator generator = new RandomLevelGenerator(5 ,5 ,7, 3);
        level = generator.generate();
        graphicsController = new GraphicsController(level);
    }

    @Override
    public void render() {
        // clear the screen
        graphicsController.clearScreen();
        // get time passed since the last render
        level.updateState();
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

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 768);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}