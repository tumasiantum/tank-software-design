package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class Player extends Object {

    public GridPoint2 getPlayerCoordinates() {
        return playerCoordinates;
    }

    public float getPlayerRotation(){
        return playerRotation;
    }

    public void setPlayerRotation(float playerRotation) {
        this.playerRotation = playerRotation;
    }

    public GridPoint2 getPlayerDestinationCoordinates() {
        return playerDestinationCoordinates;
    }

    public Rectangle getPlayerRectangle() {
        return playerRectangle;
    }

    public Player (String texturePath,
                   int playerCoordinatesX,
                   int playerCoordinatesY,
                   float playerRotation) {
        super(texturePath);
        playerDestinationCoordinates = new GridPoint2(playerCoordinatesX, playerCoordinatesY);
        playerCoordinates = new GridPoint2(playerDestinationCoordinates);
        playerRectangle = createBoundingRectangle(this.getObjectGraphics());
    }

    private float playerRotation;
    private GridPoint2 playerDestinationCoordinates;
    private GridPoint2 playerCoordinates;
    private Rectangle playerRectangle;
//    playerDestinationCoordinates = new GridPoint2(1, 1);
//    playerCoordinates = new GridPoint2(playerDestinationCoordinates);
//    playerRotation = 0f;
}
