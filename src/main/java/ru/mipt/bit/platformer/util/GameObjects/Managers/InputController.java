package ru.mipt.bit.platformer.util.GameObjects.Managers;

import com.badlogic.gdx.Gdx;

import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;

public class InputController implements ActionController {
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
    public void addMapping(int key, Direction direction) {
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
}
