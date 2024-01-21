package ru.mipt.bit.platformer.util.GameObjects.TankStates;

import ru.mipt.bit.platformer.util.GameObjects.Tank;

public class MiddleTank extends TankState {
    float health;
    private static final float MOVEMENT_SPEED = 0.8f;
    public MiddleTank(Tank tank, float health) {
        super(tank);
        this.health = health;
    }

    @Override
    public boolean getShootingAbility() {
        return true;
    }

    @Override
    public float getHealth() {
        return health/initialHealth;
    }

    @Override
    public void damage(float damage) {
        health -= damage;
        if (getHealth() < 0.15f){
            tank.setState(new HeavyTank(tank, health));
        }
    }

    @Override
    public float getMovementSpeed() {
        return MOVEMENT_SPEED;
    }

    @Override
    public long getRechargeTime() {
        return 300;
    }
}
