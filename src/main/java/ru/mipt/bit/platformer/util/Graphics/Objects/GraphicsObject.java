package ru.mipt.bit.platformer.util.Graphics.Objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.GameObject;
import ru.mipt.bit.platformer.util.Graphics.GdxLevelGraphics;

public interface GraphicsObject extends Disposable {


    void dispose();
    void render(Batch batch, GdxLevelGraphics levelGraphics);

    GameObject getGameObject();
    Rectangle getRectangle();

}
