package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Player extends Object {

    public GridPoint2 getPlayerCoordinates() {
        return playerCoordinates;
    }

    public GridPoint2 getPlayerDestinationCoordinates() {
        return playerDestinationCoordinates;
    }

    public Player (String texturePath,
                   int playerCoordinatesX,
                   int playerCoordinatesY,
                   float playerRotation) {
        super(texturePath, playerRotation);
        this.playerDestinationCoordinates = new GridPoint2(playerCoordinatesX, playerCoordinatesY);
        this.playerCoordinates = new GridPoint2(playerDestinationCoordinates);
        //this.playerRectangle = createBoundingRectangle(this.getObjectGraphics());
    }

    private GridPoint2 playerDestinationCoordinates;
    private GridPoint2 playerCoordinates;

    private void playerChangeCoordinate(String way){
        switch (way){
            case "UP": playerDestinationCoordinates.y++; break;
            case "DOWN": playerDestinationCoordinates.y--; break;
            case "RIGHT": playerDestinationCoordinates.x++; break;
            case "LEFT": playerDestinationCoordinates.x--; break;
        }
    }

    private void playerTurn (String way){
        float rotation = 0f;
        switch (way) {
            case "UP":
                rotation = 90f;
                break;
            case "DOWN":
                rotation = -90f;
                break;
            case "RIGHT":
                rotation = 0f;
                break;
            case "LEFT":
                rotation = -180f;
                break;
        }
        this.setObjectRotation(rotation);
    }

    private boolean isThereCollision(Tree tree, String way) {
        return !tree.getTreeObstacleCoordinates().equals(futureWay(way));
    }

    private GridPoint2 futureWay (String way){
        switch (way){
            case "UP": return incrementedY(playerCoordinates);
            case "DOWN": return decrementedY(playerCoordinates);
            case "RIGHT": return incrementedX(playerCoordinates);
            case "LEFT": return decrementedX(playerCoordinates);
        }
        return playerCoordinates;
    }

    public float playerMovement(String way, Tree tree, float playerMovementProgress){
        if (isEqual(playerMovementProgress, 1f)) {
            if (isThereCollision(tree, way)) {
                playerChangeCoordinate(way);
                playerMovementProgress = 0f;
            }
            playerTurn(way);
        }
        return playerMovementProgress;
    }

}
