package ru.mipt.bit.platformer.util.Graphics.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.GameObject;
import ru.mipt.bit.platformer.util.GameEngine.Direction;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.Tree;
import ru.mipt.bit.platformer.util.Graphics.GdxLevelGraphics;

import static ru.mipt.bit.platformer.util.Graphics.GdxGameUtils.*;

public class GdxTreeGraphics implements GraphicsObject {

    private Texture texture;
    private TextureRegion textureRegion;
    private Rectangle rectangle;
    private Tree tree;
    public GdxTreeGraphics(Tree tree) {
        this.texture = new Texture("images/greenTree.png");
        this.textureRegion = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(textureRegion);
        this.tree = tree;

    }

    @Override
    public void render(Batch batch, GdxLevelGraphics levelGraphics) {
        moveRectangleAtTileCenter(levelGraphics.getGroundLayer(), rectangle, tree.getCoordinates());
        drawTextureRegionUnscaled(batch, textureRegion, rectangle, Direction.UP.getRotation());
    }

    @Override
    public GameObject getGameObject() {
        return this.tree;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    @Override
    public Rectangle getRectangle() {
        return this.rectangle;
    }
}
