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
    private List<GameObject> gameObjectList;
    private Tank playerTank;
    public Integer width;
    public Integer height;
    private CollisionManager collisionManager;
    private final List<Command> commandList = new ArrayList<>();
    private InputController inputController;
    private RandomController randomController;
    public EventManager events;


    public Level(Integer width, Integer height, List<GameObject> gameObjectList, Tank playerTank) {
        this.gameObjectList = gameObjectList;
        this.width = width;
        this.height = height;
        this.playerTank = playerTank;
        this.collisionManager = new CollisionManager(this);
        this.inputController = new InputController();
        this.randomController = new RandomController();
        this.events = new EventManager(Event.ADD_GAME_OBJECT, Event.REMOVE_GAME_OBJECT);
        level = this;
    }

    public static Level getInstance() {
        return level;
    }

    public void addObject(GameObject gameObject){
        gameObjectList.add(gameObject);
        events.notify(Event.ADD_GAME_OBJECT, gameObject);
    }

    public void removeObject(GameObject gameObject){
        gameObjectList.remove(gameObject);
        events.notify(Event.REMOVE_GAME_OBJECT, gameObject);
        System.out.printf("объект удален");
    }

    public ArrayList<Bullet> getBulletsArray(){
        ArrayList<Bullet> levelBullets = new ArrayList<>();
        for (GameObject gameObject: gameObjectList) {
            if (gameObject instanceof Bullet){
                levelBullets.add((Bullet) gameObject);
            }
        }
        return levelBullets;
    }

    public void updateState() {
        Direction playerDirection = inputController.getDirection();
        Boolean playerShoot = inputController.getShoot();
        for (GameObject gameObject: gameObjectList) {
            if (gameObject instanceof Tank){
                if (gameObject != playerTank){
                    Direction botDirection = randomController.getDirection();
                    Boolean botShoot = randomController.getShoot();
                    produceCommands(botDirection, botShoot, (Tank) gameObject);
                }
                else {
                    produceCommands(playerDirection, playerShoot, (Tank) gameObject);
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
