package ru.mipt.bit.platformer.util.LevelGenerator;
import ru.mipt.bit.platformer.util.GameEngine.Level;
import ru.mipt.bit.platformer.util.Graphics.GraphicsController;

public interface LevelGenerator {
    Level generate(GraphicsController graphicsController);

    void fillLevel(Level level);

    int getWidth();
    int getHeight();

}
