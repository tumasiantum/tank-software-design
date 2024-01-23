package ru.mipt.bit.platformer.util.Graphics.Factory;

import ru.mipt.bit.platformer.util.GameEngine.GameObjects.GameObject;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.Tree;
import ru.mipt.bit.platformer.util.Graphics.Objects.GdxTreeGraphics;
import ru.mipt.bit.platformer.util.Graphics.Objects.GraphicsObject;

public class TreeGraphicsFactory implements GraphicsFactory{
    @Override
    public GraphicsObject createGraphics(GameObject gameObject) {
        return new GdxTreeGraphics((Tree) gameObject);
    }
}
