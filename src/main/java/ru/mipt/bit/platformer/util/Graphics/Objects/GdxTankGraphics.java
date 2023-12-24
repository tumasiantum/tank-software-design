package ru.mipt.bit.platformer.util.Graphics.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.Direction;
import ru.mipt.bit.platformer.util.Graphics.GdxLevelGraphics;
import ru.mipt.bit.platformer.util.Tank;

import static ru.mipt.bit.platformer.util.Graphics.GdxGameUtils.createBoundingRectangle;

public class GdxTankGraphics implements GraphicsObject {

    private Tank tank;
    private Texture texture;
    private TextureRegion textureRegion;
    private Rectangle rectangle;
    private Direction direction;

    public GdxTankGraphics(Direction direction, Tank tank) {
        this.texture = new Texture("images/tank_blue.png");
        this.textureRegion = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(textureRegion);
        this.direction = direction;
        this.tank = tank;
    }

    @Override
    public void renderGraphic(GdxLevelGraphics levelGraphics) {
        Direction movingDirection = tank.getDirection();
        if (movingDirection != null & tank.getRotateProgress()) {
            this.direction = movingDirection;
            tank.setRotateProgress(Boolean.FALSE);
        }
        levelGraphics.getTileMovement().moveRectangleBetweenTileCenters(this.rectangle, tank.getCoordinates(), tank.getDestinationCoordinates(), tank.getMovementProgress());
    }

    @Override
    public Texture getTexture() {
        return this.texture;
    }

    @Override
    public TextureRegion getTextureRegion() {
        return this.textureRegion;
    }

    @Override
    public Direction getDirection() {
        return this.direction;
    }

    @Override
    public Rectangle getRectangle() {
        return this.rectangle;
    }
}