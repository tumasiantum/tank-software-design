package ru.mipt.bit.platformer.util.Graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.util.Level;
import ru.mipt.bit.platformer.util.Tank;
import ru.mipt.bit.platformer.util.Tree;

import java.util.ArrayList;
import java.util.List;

import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;
import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class GraphicsController {
    private List<GraphicsObject> graphicsObjectList;

    private List<GdxTankGraphics> tankGraphicsObjectList;
    private Level level;

    public GraphicsController(Level level) {
        this.graphicsObjectList = new ArrayList<>();
        this.tankGraphicsObjectList = new ArrayList<>();
        this.level = level;
    }


    public void initTreeGraphics(){
        for (Tree tree: level.getTreeList()) {
            GdxTreeGraphics treeGraphics = new GdxTreeGraphics();
            this.graphicsObjectList.add(treeGraphics);
            moveRectangleAtTileCenter(level.getGroundLayer(), treeGraphics.getRectangle(), tree.getCoordinates());
        }
    }

    public void initTankGraphics(){
        for (Tank tank: level.getTankList()) {
            GdxTankGraphics tankGraphics = new GdxTankGraphics(tank.getDirection(), tank);
            graphicsObjectList.add(tankGraphics);
            tankGraphicsObjectList.add(tankGraphics);
        }
    }

    public List<GdxTankGraphics> getTankGraphicsObjectList() {
        return tankGraphicsObjectList;
    }

    public void init(){
        initTankGraphics();
        initTreeGraphics();
    }

    public void dispose(){
        for (GraphicsObject graphicsObject : graphicsObjectList) {
            graphicsObject.getTexture().dispose();
        }
    }

    public void drawAllTextureRegionUnscaled(Batch batch){
        for (GraphicsObject graphicsObject : graphicsObjectList) {
            drawTextureRegionUnscaled(batch, graphicsObject.getTextureRegion(), graphicsObject.getRectangle(), graphicsObject.getDirection().getRotation());
        }
    }

}
