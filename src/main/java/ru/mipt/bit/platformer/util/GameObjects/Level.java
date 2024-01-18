package ru.mipt.bit.platformer.util.GameObjects;

import ru.mipt.bit.platformer.util.GameObjects.Managers.CollisionManager;
import ru.mipt.bit.platformer.util.GameObjects.Managers.Direction;
import ru.mipt.bit.platformer.util.GameObjects.Managers.InputController;
import ru.mipt.bit.platformer.util.GameObjects.Managers.RandomController;
import ru.mipt.bit.platformer.util.GameObjects.Mover.Command;
import ru.mipt.bit.platformer.util.GameObjects.Mover.Commands.*;
import ru.mipt.bit.platformer.util.Listeners.Event;
import ru.mipt.bit.platformer.util.Listeners.EventListener;
import ru.mipt.bit.platformer.util.Listeners.EventManager;

import java.util.*;

public class Level {
    private static Level level;
    private List<GameObject> gameObjectList = new ArrayList<>();
    private Tank playerTank;
    public Integer width;
    public Integer height;
    private CollisionManager collisionManager;
    private final List<Command> commandList = new ArrayList<>();
    private InputController inputController = new InputController();
    private RandomController randomController = new RandomController();
    public EventManager events = new EventManager(Event.ADD_GAME_OBJECT, Event.REMOVE_GAME_OBJECT);


    public Level(Integer width, Integer height, Tank playerTank) {
        this.width = width;
        this.height = height;
        this.playerTank = playerTank;
        this.collisionManager = new CollisionManager(this);
        level = this;
    }

    public static Level getInstance() {
        return level;
    }

    public void addObject(GameObject gameObject){
        gameObjectList.add(gameObject);
        if (!(gameObject instanceof Bullet)){
            collisionManager.addObstacle(gameObject.getCoordinates());
        }
        events.notify(Event.ADD_GAME_OBJECT, gameObject);
    }

    public void removeObject(GameObject gameObject){
        gameObjectList.remove(gameObject);
        events.notify(Event.REMOVE_GAME_OBJECT, gameObject);
    }


    public void updateState() {
        for (GameObject gameObject: gameObjectList) {
            if (gameObject instanceof Tank){
                if (gameObject != playerTank){
                    produceCommands(randomController.getDirection(), randomController.getShoot(), (Tank) gameObject);
                }
                else {
                    produceCommands(inputController.getDirection(), inputController.getShoot(), (Tank) gameObject);
                }
            }
        }
        for (Command command: commandList) {
            command.execute();
        }
        for (GameObject gameObject: gameObjectList) {
            if (gameObject instanceof MovableObject){
                ((MovableObject) gameObject).move(collisionManager);
            }
        }
        commandList.clear();
        collisionManager.updateState();
    }

    private void produceCommands(Direction direction, Boolean shoot, Tank tank) {
        if (direction != null){
            commandList.add(new MovementCommand(collisionManager, direction, tank));
        }
        if (shoot) {
            commandList.add(new ShootCommand(tank));
        }
    }

    public List<GameObject> getGameObjectList() {
        return gameObjectList;
    }
}
