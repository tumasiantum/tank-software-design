package ru.mipt.bit.platformer.util.Graphics.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.GameObjects.GameObject;
import ru.mipt.bit.platformer.util.GameObjects.Managers.Direction;
import ru.mipt.bit.platformer.util.Graphics.GdxLevelGraphics;
import ru.mipt.bit.platformer.util.GameObjects.Tank;

import static ru.mipt.bit.platformer.util.Graphics.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.Graphics.GdxGameUtils.drawTextureRegionUnscaled;

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
    public void render(Batch batch, GdxLevelGraphics levelGraphics) {
        Direction movingDirection = tank.getDirection();
        if (movingDirection != null & tank.getRotateProgress()) {
            this.direction = movingDirection;
            tank.setRotateProgress(Boolean.FALSE);
        }
        levelGraphics.getTileMovement().moveRectangleBetweenTileCenters(this.rectangle, tank.getCoordinates(), tank.getDestinationCoordinates(), tank.getMovementProgress());
        drawTextureRegionUnscaled(batch, textureRegion, rectangle, direction.getRotation());
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    @Override
    public GameObject getGameObject() {
        return this.tank;
    }
    @Override
    public Rectangle getRectangle() {
        return this.rectangle;
    }
}
