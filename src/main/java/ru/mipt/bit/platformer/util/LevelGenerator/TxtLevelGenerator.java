package ru.mipt.bit.platformer.util.LevelGenerator;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.GameObjects.GameObject;
import ru.mipt.bit.platformer.util.GameObjects.Level;
import ru.mipt.bit.platformer.util.GameObjects.Managers.Direction;
import ru.mipt.bit.platformer.util.GameObjects.Tank;
import ru.mipt.bit.platformer.util.GameObjects.Tree;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TxtLevelGenerator implements LevelGenerator {
    private final String filePath;
    private List<GameObject> gameObjectList = new ArrayList<>();
    private List<GridPoint2> treePositions = new ArrayList<>();
    private List<GridPoint2> tankPositions = new ArrayList<>();
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
                    case 'A':
                        tankPositions.add(new GridPoint2(x, height));
                        break;
                }
            }
            height++;
        }
        treePositions.forEach(position -> position.y = height - position.y - 1);
        tankPositions.forEach(position -> position.y = height - position.y - 1);
        playerPosition.y = height - playerPosition.y - 1;
    }


    @Override
    public Level generate() {
        try {
            processingLine(new Scanner(Paths.get(filePath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Tank playerTank = new Tank(playerPosition, Direction.UP);
        gameObjectList.add(playerTank);
        generateGameObjects(Tree.class, treePositions);
        generateGameObjects(Tank.class, tankPositions);
        Level resultLevel = new Level(this.width, this.height, gameObjectList, playerTank);
        return resultLevel;
    }

    private void generateGameObjects(Class gameObjectType, List<GridPoint2> coordinatesList){
        if (gameObjectType == Tank.class){
            for (GridPoint2 coordinates : coordinatesList) {
                Tank tank = new Tank(coordinates, Direction.UP);
                gameObjectList.add(tank);
            }
        } else if (gameObjectType == Tree.class) {
            for (GridPoint2 coordinates : coordinatesList) {
                Tree tree = new Tree(coordinates);
                gameObjectList.add(tree);
            }
        }
    }
}