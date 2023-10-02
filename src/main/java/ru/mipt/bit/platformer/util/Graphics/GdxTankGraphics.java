package ru.mipt.bit.platformer.util.Graphics;

import ru.mipt.bit.platformer.util.Direction;
import ru.mipt.bit.platformer.util.Tank;

public class GdxTankGraphics extends GraphicsObject {

    private Tank tank;

    public GdxTankGraphics(Direction direction, Tank tank) {
        super("images/tank_blue.png", direction);
        this.tank = tank;
    }

    public Tank getTank() {
        return tank;
    }

    public void moveTankPicture(Direction movingDirection){
        if (movingDirection != null & !tank.isMoving()) {
            this.setDirection(movingDirection);
        }
    }
}
