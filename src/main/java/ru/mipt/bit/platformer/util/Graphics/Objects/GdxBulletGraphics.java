package ru.mipt.bit.platformer.util.Graphics.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.GameObjects.Managers.Direction;
import ru.mipt.bit.platformer.util.GameObjects.Bullet;
import ru.mipt.bit.platformer.util.Graphics.GdxLevelGraphics;

import static ru.mipt.bit.platformer.util.Graphics.GdxGameUtils.createBoundingRectangle;

public class GdxBulletGraphics implements GraphicsObject {

    private Bullet bullet;
    private Texture texture;
    private TextureRegion textureRegion;
    private Rectangle rectangle;
    private Direction direction;

    public GdxBulletGraphics(Direction direction, Bullet bullet) {
        this.texture = new Texture("images/bullet.png");
        this.textureRegion = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(textureRegion);
        this.direction = direction;
        this.bullet = bullet;
    }

    @Override
    public void renderGraphic(GdxLevelGraphics levelGraphics) {
        levelGraphics.getTileMovement().moveRectangleBetweenTileCenters(this.rectangle, bullet.getCoordinates(), bullet.getDestinationCoordinates(), bullet.getMovementProgress());
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

    @Override
    public void dispose() {
        texture.dispose();
    }
}
