package ru.mipt.bit.platformer.util.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.mipt.bit.platformer.util.GameObjects.GameObject;
import ru.mipt.bit.platformer.util.Graphics.Objects.GdxTankGraphics;
import ru.mipt.bit.platformer.util.Graphics.Objects.GdxTreeGraphics;
import ru.mipt.bit.platformer.util.Graphics.Objects.GraphicsObject;
import ru.mipt.bit.platformer.util.GameObjects.Level;
import ru.mipt.bit.platformer.util.GameObjects.Tank;
import ru.mipt.bit.platformer.util.GameObjects.Tree;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        this.levelGraphics = new GdxLevelGraphics(generateTmxFromTemplate(level.width, level.height).getPath(), batch);
        init();
    }



    private void init(){
        for (GameObject gameObject: level.getGameObjectList()) {
            if (gameObject instanceof Tree) {
                GdxTreeGraphics treeGraphics = new GdxTreeGraphics();
                this.graphicsObjectList.add(treeGraphics);
                moveRectangleAtTileCenter(levelGraphics.getGroundLayer(), treeGraphics.getRectangle(), gameObject.getCoordinates());
            } else if (gameObject instanceof Tank) {
                GdxTankGraphics tankGraphics = new GdxTankGraphics(gameObject.getDirection(), (Tank) gameObject);
                this.graphicsObjectList.add(tankGraphics);
            }
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

    public File generateTmxFromTemplate(Integer width, Integer height){
        File templateFile = new File("src/main/resources/leveltemplate.tmx");
        File resultFile = new File("src/main/resources/levelresult.tmx");
        try (BufferedReader reader = new BufferedReader(new FileReader(templateFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("CSV")) {
                    fillCsvField(width, height, writer);
                } else {
                    line = line.replaceAll("\\b" + "WIDTH" + "\\b", width.toString());
                    line = line.replaceAll("\\b" + "HEIGHT" + "\\b", height.toString());
                    writer.write(line);
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultFile;
    }

    private static void fillCsvField(Integer width, Integer height, BufferedWriter writer) throws IOException {
        Random random = new Random();
        for (int i = 0; i < height; i++) {
            StringBuilder lineBuilder = new StringBuilder();
            for (int j = 0; j < width; j++) {
                int randomNumber = random.nextInt(2) + 1;
                lineBuilder.append(randomNumber);
                if (j < width - 1 || i < height - 1) {
                    lineBuilder.append(",");
                }
            }
            writer.write(lineBuilder.toString());
            if (i < height - 1){
                writer.write("\n");
            }
        }
    }
}
