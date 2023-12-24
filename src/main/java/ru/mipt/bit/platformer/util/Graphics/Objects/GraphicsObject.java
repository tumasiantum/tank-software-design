package ru.mipt.bit.platformer.util.Graphics.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.Direction;
import ru.mipt.bit.platformer.util.Graphics.GdxLevelGraphics;

public interface GraphicsObject {


    Texture getTexture();

    TextureRegion getTextureRegion();

    Direction getDirection();

    Rectangle getRectangle();

    void renderGraphic(GdxLevelGraphics levelGraphics);

}
