package ru.mipt.bit.platformer.util.GameEngine.GameObjects;

public interface LiveableObject {

    void damage(Float damage);
    Boolean isAlive();

    Float getHealth();
}
