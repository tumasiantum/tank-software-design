package ru.mipt.bit.platformer.util.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.Bullet;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.GameObject;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.Tank;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.Tree;
import ru.mipt.bit.platformer.util.Graphics.Factory.BulletGraphicsFactory;
import ru.mipt.bit.platformer.util.Graphics.Factory.GraphicsFactory;
import ru.mipt.bit.platformer.util.Graphics.Factory.TankGraphicsFactory;
import ru.mipt.bit.platformer.util.Graphics.Factory.TreeGraphicsFactory;
import ru.mipt.bit.platformer.util.Graphics.Objects.Decorator.HealthBarGraphicsDecorator;
import ru.mipt.bit.platformer.util.Graphics.Objects.GraphicsObject;
import ru.mipt.bit.platformer.util.Listeners.Event;
import ru.mipt.bit.platformer.util.Listeners.EventListener;

import java.io.*;
import java.util.HashMap;
import java.util.Random;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GraphicsController implements EventListener {
    private HashMap<GameObject, GraphicsObject> objectHashMap = new HashMap<>();
    private GdxLevelGraphics levelGraphics;
    private Batch batch;

    public GraphicsController(int width, int height) {
        this.batch = new SpriteBatch();
        this.levelGraphics = new GdxLevelGraphics(generateTmxFromTemplate(width, height).getPath(), batch);
    }

    @Override
    public void update(Event eventType, GameObject gameObject) {
        if (eventType == Event.ADD_GAME_OBJECT){
            objectHashMap.put(gameObject, getGraphicsFactory(gameObject).createGraphics(gameObject));
        }
        if (eventType == Event.REMOVE_GAME_OBJECT){
            objectHashMap.remove(gameObject).dispose();
        }
    }

    public void toggleLiveBar(){
        for (GraphicsObject graphicsObject: objectHashMap.values()){
            if (graphicsObject instanceof HealthBarGraphicsDecorator){
                ((HealthBarGraphicsDecorator) graphicsObject).toggleLiveBar();
            }
        }
    }

    private GraphicsFactory getGraphicsFactory(GameObject gameObject) {
        if (gameObject instanceof Bullet) {
            return new BulletGraphicsFactory();
        } else if (gameObject instanceof Tree) {
            return new TreeGraphicsFactory();
        } else if (gameObject instanceof Tank) {
            return new TankGraphicsFactory();
        }
        throw new RuntimeException("Не создан объект графики");
    }


    public void dispose(){
        for (GraphicsObject graphicsObject : objectHashMap.values()) {
            graphicsObject.dispose();
        }
        this.levelGraphics.getLevel().dispose();
        this.batch.dispose();
    }

    private static void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    public void render(){
        clearScreen();
        // render each tile of the level
        levelGraphics.getLevelRenderer().render();
        // start recording all drawing commands
        this.batch.begin();
        // render all objects
        for (GraphicsObject graphicsObject: objectHashMap.values()) {
            graphicsObject.render(batch, levelGraphics);
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
