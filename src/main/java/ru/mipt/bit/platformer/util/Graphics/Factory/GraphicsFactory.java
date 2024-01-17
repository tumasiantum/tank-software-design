package ru.mipt.bit.platformer.util.Graphics.Factory;

import ru.mipt.bit.platformer.util.GameObjects.GameObject;
import ru.mipt.bit.platformer.util.Graphics.Objects.GraphicsObject;

public interface GraphicsFactory {
    GraphicsObject createGraphics(GameObject gameObject);
}
