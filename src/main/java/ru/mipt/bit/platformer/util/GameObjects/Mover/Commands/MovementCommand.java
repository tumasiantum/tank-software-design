package ru.mipt.bit.platformer.util.GameObjects.Mover.Commands;

import ru.mipt.bit.platformer.util.GameObjects.Managers.ActionController;
import ru.mipt.bit.platformer.util.GameObjects.Managers.CollisionManager;
import ru.mipt.bit.platformer.util.GameObjects.Managers.Direction;
import ru.mipt.bit.platformer.util.GameObjects.Managers.InputController;
import ru.mipt.bit.platformer.util.GameObjects.MovableObject;
import ru.mipt.bit.platformer.util.GameObjects.Mover.Command;
import ru.mipt.bit.platformer.util.GameObjects.Tank;

public class MovementCommand implements Command {

    private CollisionManager collisionManager;
    private Direction direction;
    private MovableObject movableObject;

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
