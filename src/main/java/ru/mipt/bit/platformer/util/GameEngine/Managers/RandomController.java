package ru.mipt.bit.platformer.util.GameEngine.Managers;

import ru.mipt.bit.platformer.util.GameEngine.Direction;

import java.util.Random;

public class RandomController implements ActionController {
    public RandomController() {}
    private final Random random = new Random();


    @Override
    public Direction getDirection() {
        int x = random.nextInt(12);
        Direction direction = null;
        if ( x == 0 ){
            direction = Direction.UP;
        } else if ( x == 1 ) {
            direction = Direction.RIGHT;
        } else if ( x == 2 ) {
            direction = Direction.DOWN;
        } else if ( x == 3 ) {
            direction = Direction.LEFT;
        }
        return direction;
    }

    @Override
    public boolean getShoot() {
        int x = random.nextInt(4);
        if (x == 1){
            return true;
        }
        return false;
    }
}
