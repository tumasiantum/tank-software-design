package ru.mipt.bit.platformer.util.GameObjects.Mover;

import ru.mipt.bit.platformer.util.GameObjects.Tank;

import java.util.HashMap;

/** The Invoker class */
public class Mover {
    private Tank tank;
    private final HashMap<Tank, Command> commandMap = new HashMap<>();

    public void register(Tank tank, Command command) {
        commandMap.put(tank, command);
    }

    public void execute(Tank tank, float deltaTime) {
        Command command = commandMap.get(tank);
        if (command == null) {
            throw new IllegalStateException("no command registered for " + tank);
        }
        command.move(deltaTime);
    }
}
