package ru.mipt.bit.platformer.util.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.mipt.bit.platformer.util.Graphics.Objects.GdxTankGraphics;
import ru.mipt.bit.platformer.util.Graphics.Objects.GdxTreeGraphics;
import ru.mipt.bit.platformer.util.Graphics.Objects.GraphicsObject;
import ru.mipt.bit.platformer.util.Level;
import ru.mipt.bit.platformer.util.Tank;
import ru.mipt.bit.platformer.util.Tree;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.Graphics.GdxGameUtils.drawTextureRegionUnscaled;
import static ru.mipt.bit.platformer.util.Graphics.GdxGameUtils.moveRectangleAtTileCenter;

public class GraphicsController {
    private List<GraphicsObject> graphicsObjectList;
    private Level level;
    private GdxLevelGraphics levelGraphics;
    private Batch batch;

    public GraphicsController(Level level) {
        this.graphicsObjectList = new ArrayList<>();
        this.level = level;
        this.batch = new SpriteBatch();
        this.levelGraphics = new GdxLevelGraphics("level.tmx", batch);
    }


    public void init(){
        // TODO with gameObject Interface
        for (Tank tank: level.getTankList()) {
            GdxTankGraphics tankGraphics = new GdxTankGraphics(tank.getDirection(), tank);
            this.graphicsObjectList.add(tankGraphics);
        }

        for (Tree tree: level.getTreeList()) {
            GdxTreeGraphics treeGraphics = new GdxTreeGraphics();
            this.graphicsObjectList.add(treeGraphics);
            moveRectangleAtTileCenter(levelGraphics.getGroundLayer(), treeGraphics.getRectangle(), tree.getCoordinates());
        }
    }

    public void dispose(){
        for (GraphicsObject graphicsObject : graphicsObjectList) {
            graphicsObject.getTexture().dispose();
        }
        this.levelGraphics.getLevel().dispose();
        this.batch.dispose();
    }

    public static void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    public void render(){
        // render each tile of the level
        levelGraphics.getLevelRenderer().render();
        // start recording all drawing commands
        this.batch.begin();
        // render all objects
        for (GraphicsObject graphicsObject: graphicsObjectList) {
            graphicsObject.renderGraphic(levelGraphics);
            drawTextureRegionUnscaled(this.batch, graphicsObject.getTextureRegion(), graphicsObject.getRectangle(), graphicsObject.getDirection().getRotation());
        }
        // submit all drawing requests
        this.batch.end();
    }

    public GdxLevelGraphics getLevelGraphics() {
        return levelGraphics;
    }
}
