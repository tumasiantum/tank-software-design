package ru.mipt.bit.platformer.util.GameObjects;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.util.GameObjects.Managers.CollisionManager;
import ru.mipt.bit.platformer.util.GameObjects.Managers.Direction;
import ru.mipt.bit.platformer.util.GameObjects.Managers.InputController;
import ru.mipt.bit.platformer.util.GameObjects.Mover.Mover;
import ru.mipt.bit.platformer.util.GameObjects.Mover.Movements.PlayerMovement;
import ru.mipt.bit.platformer.util.GameObjects.Mover.Movements.SelfMovement;

import java.util.*;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;

public class Level {
    private List<GameObject> gameObjectList;
    private Tank playerTank;
    public Integer width;
    public Integer height;
    private CollisionManager collisionManager;


    private InputController inputController;
    private Mover mover;

    public Level(Integer width, Integer height, List<GameObject> gameObjectList, Tank playerTank) {
        this.gameObjectList = gameObjectList;
        this.width = width;
        this.height = height;
        this.playerTank = playerTank;
        this.collisionManager = new CollisionManager(this);
        this.inputController = new InputController();
        inputController.addMapping(UP, Direction.UP);
        inputController.addMapping(W, Direction.UP);
        inputController.addMapping(LEFT, Direction.LEFT);
        inputController.addMapping(A, Direction.LEFT);
        inputController.addMapping(DOWN, Direction.DOWN);
        inputController.addMapping(S, Direction.DOWN);
        inputController.addMapping(RIGHT, Direction.RIGHT);
        inputController.addMapping(D, Direction.RIGHT);
        this.mover = new Mover();
        for (GameObject gameObject: gameObjectList) {
            if (gameObject instanceof Tank){
                if (gameObject == playerTank){
                    mover.register((Tank) gameObject, new PlayerMovement(collisionManager, inputController, (Tank) gameObject));
                }
                else {
                    mover.register((Tank) gameObject, new SelfMovement(collisionManager, (Tank) gameObject));
                }
            }
        }
    }

    public void updateState() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        for (GameObject gameObject: gameObjectList) {
            if (gameObject instanceof Tank ){
                this.mover.execute((Tank) gameObject, deltaTime);
            }
        }
    }

    public List<GameObject> getGameObjectList() {
        return gameObjectList;
    }
}
