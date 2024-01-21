package ru.mipt.bit.platformer.util.Graphics.Objects.Decorator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import ru.mipt.bit.platformer.util.GameObjects.GameObject;
import ru.mipt.bit.platformer.util.GameObjects.LiveableObject;
import ru.mipt.bit.platformer.util.Graphics.GdxGameUtils;
import ru.mipt.bit.platformer.util.Graphics.GdxLevelGraphics;
import ru.mipt.bit.platformer.util.Graphics.Objects.GraphicsObject;

import static ru.mipt.bit.platformer.util.Graphics.GdxGameUtils.drawTextureRegionUnscaled;


public class HealthBarGraphicsDecorator extends GraphicsObjectDecorator{

    public HealthBarGraphicsDecorator(GraphicsObject wrappee) {
        super(wrappee);
    }
    private boolean lifebar = false;

    @Override
    public void render(Batch batch, GdxLevelGraphics levelGraphics) {
        super.render(batch, levelGraphics);
        if (lifebar) {
            GameObject gameObject = super.getGameObject();
            if (gameObject instanceof LiveableObject) {
                LiveableObject liveableObject = (LiveableObject) gameObject;
                drawTextureRegionUnscaled(batch, getHealthbarTexture(liveableObject.getHealth()), createRectangle(), 0f);
            }
        }
    }

    public void toggleLiveBar(){
        lifebar = !lifebar;
    }


    private TextureRegion getHealthbarTexture(float relativeHealth) {
        var pixmap = new Pixmap(70, 3, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fillRectangle(0, 0, 70, 3);
        pixmap.setColor(Color.GREEN);
        pixmap.fillRectangle(0, 0, (int) (70 * relativeHealth), 3);
        var texture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegion(texture);
    }

    private Rectangle createRectangle() {
        Rectangle rectangle = graphicsObject.getRectangle();
        rectangle.y += 90;
        rectangle.x += 12;
        return rectangle;
    }
}

