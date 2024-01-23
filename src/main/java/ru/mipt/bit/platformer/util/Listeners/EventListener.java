package ru.mipt.bit.platformer.util.Listeners;

import ru.mipt.bit.platformer.util.GameEngine.GameObjects.GameObject;

public interface EventListener {
    void update(Event eventType, GameObject gameObject);
}
