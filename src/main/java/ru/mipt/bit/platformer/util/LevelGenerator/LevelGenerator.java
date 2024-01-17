package ru.mipt.bit.platformer.util.LevelGenerator;
import ru.mipt.bit.platformer.util.GameObjects.Level;

public interface LevelGenerator {
    Level generate();

    void fillLevel(Level level);

    int getWidth();
    int getHeight();

}
