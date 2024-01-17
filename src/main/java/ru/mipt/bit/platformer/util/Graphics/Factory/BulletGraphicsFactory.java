package ru.mipt.bit.platformer.util.Graphics.Factory;

import ru.mipt.bit.platformer.util.GameObjects.Bullet;
import ru.mipt.bit.platformer.util.GameObjects.GameObject;
import ru.mipt.bit.platformer.util.Graphics.Objects.GdxBulletGraphics;
import ru.mipt.bit.platformer.util.Graphics.Objects.GraphicsObject;


public class BulletGraphicsFactory implements GraphicsFactory{
    @Override
    public GraphicsObject createGraphics(GameObject gameObject) {
        return new GdxBulletGraphics(gameObject.getDirection(), (Bullet) gameObject);
    }
}
