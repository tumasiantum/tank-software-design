package ru.mipt.bit.platformer.util.GameEngine.Commands;

import ru.mipt.bit.platformer.util.GameEngine.Managers.CollisionManager;
import ru.mipt.bit.platformer.util.GameEngine.Direction;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.MovableObject;

public class MovementCommand implements Command {

    private final CollisionManager collisionManager;
    private final Direction direction;
    private final MovableObject movableObject;

    public MovementCommand(CollisionManager collisionManager, Direction direction, MovableObject movableObject) {
        this.collisionManager = collisionManager;
        this.direction = direction;
        this.movableObject = movableObject;
    }

    @Override
    public void execute() {
        this.movableObject.startMovement(direction, collisionManager);
    }
}
