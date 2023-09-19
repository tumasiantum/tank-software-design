package ru.mipt.bit.platformer.util;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class Object {

    private float objectRotation;
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

    public float getObjectRotation() {
        return objectRotation;
    }

    public void setObjectRotation(float objectRotation) {
        this.objectRotation = objectRotation;
    }

    public void setObjectRectangle(Rectangle objectRectangle) {
        this.objectRectangle = objectRectangle;
    }

    public Object (String texturePath, float objectRotation) {
        this.objectTexture = new Texture(texturePath);
        this.objectGraphics = new TextureRegion(objectTexture);
        this.objectRectangle = createBoundingRectangle(objectGraphics);
        this.objectRotation = objectRotation;
    }
}
