package ru.mipt.bit.platformer.util.Graphics.LevelGenerator.Parsers;

import ru.mipt.bit.platformer.util.Graphics.LevelGenerator.LevelPositions;

public interface Parser {
    LevelPositions parse(String pathToFile);
}
