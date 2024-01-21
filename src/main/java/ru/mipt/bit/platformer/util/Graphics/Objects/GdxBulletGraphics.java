package ru.mipt.bit.platformer.util.Graphics.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.GameObjects.GameObject;
import ru.mipt.bit.platformer.util.GameObjects.Managers.Direction;
import ru.mipt.bit.platformer.util.GameObjects.Bullet;
import ru.mipt.bit.platformer.util.Graphics.GdxLevelGraphics;

import static ru.mipt.bit.platformer.util.Graphics.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.Graphics.GdxGameUtils.drawTextureRegionUnscaled;

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
    public void render(Batch batch, GdxLevelGraphics levelGraphics) {
        levelGraphics.getTileMovement().moveRectangleBetweenTileCenters(this.rectangle, bullet.getCoordinates(), bullet.getDestinationCoordinates(), bullet.getMovementProgress());
        drawTextureRegionUnscaled(batch, textureRegion, rectangle, direction.getRotation());
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    @Override
    public GameObject getGameObject() {
        return this.bullet;
    }
    @Override
    public Rectangle getRectangle() {
        return this.rectangle;
    }
}
