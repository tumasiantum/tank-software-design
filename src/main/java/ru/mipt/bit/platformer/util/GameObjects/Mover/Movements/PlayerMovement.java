package ru.mipt.bit.platformer.util.GameObjects.Mover.Movements;

import ru.mipt.bit.platformer.util.GameObjects.Managers.CollisionManager;
import ru.mipt.bit.platformer.util.GameObjects.Managers.InputController;
import ru.mipt.bit.platformer.util.GameObjects.Mover.Command;
import ru.mipt.bit.platformer.util.GameObjects.Tank;

public class PlayerMovement implements Command {

    private CollisionManager collisionManager;
    private InputController inputController;
    private Tank tank;

    public PlayerMovement(CollisionManager collisionManager, InputController inputController, Tank tank) {
        this.collisionManager = collisionManager;
        this.inputController = inputController;
        this.tank = tank;
    }

    @Override
    public void move(float deltaTime) {
        this.tank.move(inputController.getDirection(), collisionManager, deltaTime);
    }
}
