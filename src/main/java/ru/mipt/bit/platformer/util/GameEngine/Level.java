package ru.mipt.bit.platformer.util.GameEngine;

import ru.mipt.bit.platformer.util.GameEngine.GameObjects.Bullet;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.GameObject;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.MovableObject;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.Tank;
import ru.mipt.bit.platformer.util.GameEngine.Managers.CollisionManager;
import ru.mipt.bit.platformer.util.GameEngine.Managers.RandomController;
import ru.mipt.bit.platformer.util.GameEngine.Commands.Command;
import ru.mipt.bit.platformer.util.GameEngine.Commands.MovementCommand;
import ru.mipt.bit.platformer.util.GameEngine.Commands.ShootCommand;
import ru.mipt.bit.platformer.util.Listeners.Event;
import ru.mipt.bit.platformer.util.Listeners.EventManager;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private final List<GameObject> gameObjectList = new ArrayList<>();
    private final Tank playerTank;
    private final CollisionManager collisionManager;
    private final List<Command> commandList = new ArrayList<>();
    private final RandomController randomController = new RandomController();
    public EventManager events;


    public Level(Integer width, Integer height, Tank playerTank) {
        this.playerTank = playerTank;
        this.collisionManager = new CollisionManager(width, height);
        this.events = new EventManager(Event.ADD_GAME_OBJECT, Event.REMOVE_GAME_OBJECT);
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


    public void updateState(ArrayList<Command> additionalCommandList) {
        commandList.addAll(additionalCommandList);

        for (GameObject gameObject: gameObjectList) {
            if (gameObject instanceof Tank && gameObject != playerTank){
                produceCommands(randomController.getDirection(), randomController.getShoot(), (Tank) gameObject);
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
        collisionManager.updateState(this);
    }

    private void produceCommands(Direction direction, Boolean shoot, Tank tank) {
        if (direction != null){
            commandList.add(new MovementCommand(collisionManager, direction, tank));
        }
        if (shoot) {
            commandList.add(new ShootCommand(this, tank));
        }
    }

    public CollisionManager getCollisionManager() {
        return collisionManager;
    }

    public Tank getPlayerTank() {
        return playerTank;
    }

    public List<GameObject> getGameObjectList() {
        return gameObjectList;
    }
}
