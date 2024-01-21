package ru.mipt.bit.platformer.util.GameObjects.TankStates;

import ru.mipt.bit.platformer.util.GameObjects.Tank;

public class HeavyTank extends TankState {
    float health;
    private static final float MOVEMENT_SPEED = 1.2f;
    public HeavyTank(Tank tank, float health) {
        super(tank);
        this.health = health;
    }

    @Override
    public boolean getShootingAbility() {
        return false;
    }

    @Override
    public float getHealth() {
        return health/initialHealth;
    }

    @Override
    public void damage(float damage) {
        health -= damage;
    }

    @Override
    public float getMovementSpeed() {
        return MOVEMENT_SPEED;
    }

    @Override
    public long getRechargeTime() {
        return 0;
    }
}
