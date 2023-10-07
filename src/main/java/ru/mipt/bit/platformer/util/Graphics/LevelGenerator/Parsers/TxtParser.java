package ru.mipt.bit.platformer.util.Graphics.LevelGenerator.Parsers;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.Graphics.LevelGenerator.LevelPositions;
import ru.mipt.bit.platformer.util.Tree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TxtParser implements Parser {
    private List<GridPoint2> treePositions = new ArrayList<>();
    private GridPoint2 playerPosition;

    private int height;
    private int width;

    public TxtParser(int width, int height){
        this.height = height;
        this.width = width;
    }

    @Override
    public LevelPositions parse(String pathToFile) {
        LevelPositions result = new LevelPositions();
        result.width = this.width;
        result.height = this.height;
        try {
            FileReader fileReader = new FileReader(pathToFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            processingLine(bufferedReader);
            result.addList(Tree.class, this.treePositions);
            result.setPlayerStartCoordinates(this.playerPosition);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    private void processingLine(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        GridPoint2 coordinates;

        for (int y = this.height - 1; y >= 0; y--) {
            for (int x = 0; x < this.width; x++) {
                char symbol = line.charAt(x);
                switch (symbol) {
                    case '_':
                        break;
                    case 'T':
                        coordinates = new GridPoint2(x, y);
                        treePositions.add(coordinates);
                        break;
                    case 'P':
                        playerPosition = new GridPoint2(x, y);
                        break;
                }
            }
            line = reader.readLine();
        }
    }

}