package ru.mipt.bit.platformer.util.GameObjects.Mover.Commands;

import ru.mipt.bit.platformer.util.GameObjects.Managers.ActionController;
import ru.mipt.bit.platformer.util.GameObjects.Managers.CollisionManager;
import ru.mipt.bit.platformer.util.GameObjects.Managers.InputController;
import ru.mipt.bit.platformer.util.GameObjects.Mover.Command;
import ru.mipt.bit.platformer.util.GameObjects.Tank;

public class ShootCommand implements Command {
    private CollisionManager collisionManager;
    private Tank tank;

    public ShootCommand(Tank tank) {
        this.tank = tank;
    }

    @Override
    public void execute() {
        this.tank.shoot();
    }
}