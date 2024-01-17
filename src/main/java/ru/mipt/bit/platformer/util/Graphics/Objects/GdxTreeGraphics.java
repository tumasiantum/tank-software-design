package ru.mipt.bit.platformer.util.Graphics.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.GameObjects.Managers.Direction;
import ru.mipt.bit.platformer.util.GameObjects.Tree;
import ru.mipt.bit.platformer.util.Graphics.GdxLevelGraphics;

import static ru.mipt.bit.platformer.util.Graphics.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.Graphics.GdxGameUtils.moveRectangleAtTileCenter;

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
    public Texture getTexture() {
        return this.texture;
    }

    @Override
    public TextureRegion getTextureRegion() {
        return this.textureRegion;
    }

    @Override
    public Direction getDirection() {
        return Direction.UP;
    }

    @Override
    public Rectangle getRectangle() {
        return this.rectangle;
    }


    @Override
    public void renderGraphic(GdxLevelGraphics levelGraphics) {
        moveRectangleAtTileCenter(levelGraphics.getGroundLayer(), rectangle, tree.getCoordinates());
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
