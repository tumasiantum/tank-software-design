package ru.mipt.bit.platformer.util;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class Object {

    private Texture objectTexture;
    private TextureRegion objectGraphics;
    private Rectangle objectRectangle;

    public Rectangle getObjectRectangle() {
        return objectRectangle;
    }

    public TextureRegion getObjectGraphics() {
        return objectGraphics;
    }

    public Texture getObjectTexture() {
        return objectTexture;
    }

    public Object (String texturePath) {
        objectTexture = new Texture(texturePath);
        objectGraphics = new TextureRegion(objectTexture);
        objectRectangle = createBoundingRectangle(objectGraphics);
    }
}
