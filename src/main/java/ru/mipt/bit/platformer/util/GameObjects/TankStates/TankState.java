package ru.mipt.bit.platformer.util.GameObjects.TankStates;

import ru.mipt.bit.platformer.util.GameObjects.Tank;

public abstract class TankState {
    Tank tank;
    float initialHealth;

    public TankState(Tank tank) {
        this.tank = tank;
        initialHealth = 10f;
    }

    public abstract boolean getShootingAbility();
    public abstract float getHealth();
    public abstract void damage(float damage);
    public abstract float getMovementSpeed();

    public abstract long getRechargeTime();
}
