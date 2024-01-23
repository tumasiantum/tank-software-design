package ru.mipt.bit.platformer.util.GameEngine.Commands;

import ru.mipt.bit.platformer.util.GameEngine.Level;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.Tank;

public class ShootCommand implements Command {
    private final Tank tank;
    private final Level level;

    public ShootCommand(Level level, Tank tank) {
        this.tank = tank;
        this.level = level;
    }

    @Override
    public void execute() {
        this.tank.shoot(this.level);
    }
}