package ru.mipt.bit.platformer.util.GameObjects.Managers;

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
        int x = random.nextInt(22);
        if (x < 3){
            return false;
        }
        return false;
    }
}
