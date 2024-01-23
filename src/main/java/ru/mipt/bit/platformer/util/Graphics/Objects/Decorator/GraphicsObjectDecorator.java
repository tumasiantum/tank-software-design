package ru.mipt.bit.platformer.util.Graphics.Objects.Decorator;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.GameObject;
import ru.mipt.bit.platformer.util.Graphics.GdxLevelGraphics;
import ru.mipt.bit.platformer.util.Graphics.Objects.GraphicsObject;

public class GraphicsObjectDecorator implements GraphicsObject {
    protected GraphicsObject graphicsObject;

    public GraphicsObjectDecorator(GraphicsObject wrappee) {
        this.graphicsObject = wrappee;
    }

    @Override
    public void dispose() {
        graphicsObject.dispose();
    }

    @Override
    public void render(Batch batch, GdxLevelGraphics levelGraphics) {
        graphicsObject.render(batch, levelGraphics);
    }

    @Override
    public GameObject getGameObject() {
        return graphicsObject.getGameObject();
    }

    @Override
    public Rectangle getRectangle() {
        return graphicsObject.getRectangle();
    }
}
