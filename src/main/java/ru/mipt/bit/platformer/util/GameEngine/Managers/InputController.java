package ru.mipt.bit.platformer.util.GameEngine.Managers;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.util.GameEngine.Direction;
import ru.mipt.bit.platformer.util.GameEngine.Level;
import ru.mipt.bit.platformer.util.GameEngine.Commands.Command;
import ru.mipt.bit.platformer.util.GameEngine.Commands.MovementCommand;
import ru.mipt.bit.platformer.util.GameEngine.Commands.ShootCommand;
import ru.mipt.bit.platformer.util.GameEngine.Commands.ToggleHealthBarCommand;
import ru.mipt.bit.platformer.util.Graphics.GraphicsController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.Input.Keys.*;

public class InputController implements ActionController {
    ArrayList<Command> commandList = new ArrayList<>();
    Long lastCallTime = System.currentTimeMillis();
    public InputController() {
        this.addMapping(UP, Direction.UP);
        this.addMapping(W, Direction.UP);
        this.addMapping(LEFT, Direction.LEFT);
        this.addMapping(A, Direction.LEFT);
        this.addMapping(DOWN, Direction.DOWN);
        this.addMapping(S, Direction.DOWN);
        this.addMapping(RIGHT, Direction.RIGHT);
        this.addMapping(D, Direction.RIGHT);
    }

    private final Map<Integer, Direction> keyToDirectionMap = new HashMap<>();
    private void addMapping(int key, Direction direction) {
        keyToDirectionMap.put(key, direction);
    }

    @Override
    public Direction getDirection() {
        for (Integer key : keyToDirectionMap.keySet()) {
            if (Gdx.input.isKeyPressed(key)) {
                return keyToDirectionMap.get(key);
            }
        }
        return null;
    }

    @Override
    public boolean getShoot() {
        if (Gdx.input.isKeyPressed(SPACE)) {
            return true;
        }
        return false;
    }

    public boolean livesToggle() {
        if (Gdx.input.isKeyPressed(L)) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastCallTime >= 200) {
                lastCallTime = currentTime;
                return true;
            }
        }
        return false;
    }

    public ArrayList<Command> getCommandList(Level level, GraphicsController graphicsController){
        commandList.clear();
        Direction direction = getDirection();
        boolean shoot = getShoot();
        boolean livesToggle = livesToggle();
        if (direction != null){
            commandList.add(new MovementCommand(level.getCollisionManager(), direction, level.getPlayerTank()));
        }
        if (shoot) {
            commandList.add(new ShootCommand(level, level.getPlayerTank()));
        }
        if (livesToggle) {
            commandList.add(new ToggleHealthBarCommand(graphicsController));
        }
        return commandList;
    }

}