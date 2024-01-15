package ru.mipt.bit.platformer.util.Graphics.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.util.GameObjects.Managers.Direction;
import ru.mipt.bit.platformer.util.Graphics.GdxLevelGraphics;

public interface GraphicsObject extends Disposable {


    Texture getTexture();

    TextureRegion getTextureRegion();

    Direction getDirection();

    Rectangle getRectangle();

    void renderGraphic(GdxLevelGraphics levelGraphics);

}
