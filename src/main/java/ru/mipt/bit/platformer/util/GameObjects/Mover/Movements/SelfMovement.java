package ru.mipt.bit.platformer.util.GameObjects.Mover.Movements;

import ru.mipt.bit.platformer.util.GameObjects.Managers.Direction;
import ru.mipt.bit.platformer.util.GameObjects.Managers.CollisionManager;
import ru.mipt.bit.platformer.util.GameObjects.Mover.Command;
import ru.mipt.bit.platformer.util.GameObjects.Tank;

import java.util.Random;

public class SelfMovement implements Command {

    private Random random = new Random();
    private CollisionManager collisionManager;


    private Direction randomDirection(){
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

    private Tank tank;

    public SelfMovement(CollisionManager collisionManager, Tank tank) {
        this.collisionManager = collisionManager;
        this.tank = tank;
    }

    @Override
    public void move(float deltaTime) {
        this.tank.move(randomDirection(), collisionManager, deltaTime);
    }
}
