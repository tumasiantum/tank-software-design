package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class Graphics {
    private Texture texture;
    private TextureRegion textureRegion;
    private Rectangle rectangle;
    private Direction direction;

    public Graphics (String texturePath, Direction direction) {
        this.texture = new Texture(texturePath);
        this.textureRegion = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(textureRegion);
        this.direction = direction;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public Direction getDirection() {
        return direction;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
