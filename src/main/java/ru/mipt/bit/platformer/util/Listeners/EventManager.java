package ru.mipt.bit.platformer.util.Listeners;

import ru.mipt.bit.platformer.util.GameEngine.GameObjects.GameObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {
    Map<Event, List<EventListener>> listeners = new HashMap<>();

    public EventManager(Event... operations) {
        for (Event operation : operations) {
            this.listeners.put(operation, new ArrayList<>());
        }
    }

    public void subscribe(Event eventType, EventListener listener) {
        List<EventListener> users = listeners.get(eventType);
        users.add(listener);
    }

    public void unsubscribe(Event eventType, EventListener listener) {
        List<EventListener> users = listeners.get(eventType);
        users.remove(listener);
    }

    public void notify(Event eventType, GameObject gameObject) {
        List<EventListener> users = listeners.get(eventType);
        for (EventListener listener : users) {
            listener.update(eventType, gameObject);
        }
    }
}
