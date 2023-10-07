package ru.mipt.bit.platformer.util.Graphics.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.Direction;
import ru.mipt.bit.platformer.util.Graphics.GdxLevelGraphics;

import static ru.mipt.bit.platformer.util.Graphics.GdxGameUtils.createBoundingRectangle;

public class GdxTreeGraphics implements GraphicsObject {

    private Texture texture;
    private TextureRegion textureRegion;
    private Rectangle rectangle;
    public GdxTreeGraphics() {
        this.texture = new Texture("images/greenTree.png");
        this.textureRegion = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(textureRegion);
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
    public Rectangle getRectangle() {
        return this.rectangle;
    }


    @Override
    public void renderGraphic(GdxLevelGraphics levelGraphics) {}

    @Override
    public Direction getDirection() {
        return Direction.UP;
    }

}
