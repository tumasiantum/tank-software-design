package ru.mipt.bit.platformer.util.Graphics.LevelGenerator;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.Level;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TxtLevelGenerator implements LevelGenerator {
    private final String filePath;
    private List<GridPoint2> treePositions = new ArrayList<>();
    private GridPoint2 playerPosition;
    private Integer height = 0;
    private Integer width = 0;


    public TxtLevelGenerator(String filePath) {
        this.filePath = filePath;
    }

    private void processingLine(Scanner sc) throws IOException {
        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            width = line.length();
            for (int x = 0; x < width; x++) {
                char symbol = line.charAt(x);
                switch (symbol) {
                    case '_':
                        break;
                    case 'T':
                        treePositions.add(new GridPoint2(x, height));
                        break;
                    case 'P':
                        playerPosition = new GridPoint2(x, height);
                        break;
                }
            }
            height++;
        }
        treePositions.forEach(position -> position.y = height - position.y - 1);
        playerPosition.y = height - playerPosition.y - 1;
    }


    @Override
    public Level generate() {
        try {
            processingLine(new Scanner(Paths.get(filePath)));
            Level resultLevel = new Level(width, height);
            resultLevel.addTrees(treePositions);
            resultLevel.setPlayerTank(playerPosition);
            return resultLevel;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}