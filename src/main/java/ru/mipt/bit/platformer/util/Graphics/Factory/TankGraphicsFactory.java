package ru.mipt.bit.platformer.util.Graphics.Factory;

import ru.mipt.bit.platformer.util.GameObjects.GameObject;
import ru.mipt.bit.platformer.util.GameObjects.Tank;
import ru.mipt.bit.platformer.util.Graphics.Objects.Decorator.HealthBarGraphicsDecorator;
import ru.mipt.bit.platformer.util.Graphics.Objects.GdxTankGraphics;
import ru.mipt.bit.platformer.util.Graphics.Objects.GraphicsObject;

public class TankGraphicsFactory implements GraphicsFactory{
    @Override
    public GraphicsObject createGraphics(GameObject gameObject) {
        return new HealthBarGraphicsDecorator(new GdxTankGraphics(gameObject.getDirection(), (Tank) gameObject));
    }
}
