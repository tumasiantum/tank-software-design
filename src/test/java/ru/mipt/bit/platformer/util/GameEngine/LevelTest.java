package ru.mipt.bit.platformer.util.GameEngine;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.util.GameEngine.Commands.Command;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.*;
import ru.mipt.bit.platformer.util.GameEngine.Managers.CollisionManager;
import ru.mipt.bit.platformer.util.Listeners.EventManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class LevelTest {

    private Level level;
    private Tank playerTank;
    private CollisionManager collisionManager;
    private List<Command> commandList;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        playerTank = new Tank(new GridPoint2(0, 0), Direction.UP);
        level = new Level(10, 10, playerTank);
        level.events = mock(EventManager.class);
        Field collisionManagerField = Level.class.getDeclaredField("collisionManager");
        collisionManagerField.setAccessible(true);
        collisionManagerField.set(level, mock(CollisionManager.class));
        collisionManager = level.getCollisionManager();

//        commandList = mock(List.class);
//        Field commandListField = Level.class.getDeclaredField("commandList");
//        commandListField.setAccessible(true);
//        commandListField.set(level, commandList);

    }


    @Test
    void addRemoveObject_RemovesObjectFromGameObjectListAndNotify() {
        GameObject gameObject = new Tree(new GridPoint2(1, 1));
        level.addObject(gameObject);
        verify(collisionManager, times(1)).addObstacle(any());
        verify(level.events, times(1)).notify(any(),any());
        level.removeObject(gameObject);

        assertFalse(level.getGameObjectList().contains(gameObject));
        verify(level.events, times(2)).notify(any(),any());
    }

    @Test
    void addBullet_AddesBulletToGameObjectListAndNotify() {
        GameObject gameObject = new Bullet(new GridPoint2(1,1),Direction.DOWN);
        level.addObject(gameObject);
        verify(level.events, times(1)).notify(any(),any());
        verify(collisionManager, times(0)).addObstacle(any());
        assertTrue(level.getGameObjectList().contains(gameObject));

    }

    @Test
    void updateState_ShouldExecuteCommandsAndMoveMovableObjects() throws IllegalAccessException, NoSuchFieldException {
        Tank tank = mock(Tank.class);
        Tree tree = mock(Tree.class);
        Command command = mock(Command.class);
        ArrayList<Command> commandList = new ArrayList<>();
        Field commandListField = Level.class.getDeclaredField("commandList");
        commandListField.setAccessible(true);
        commandListField.set(level, commandList);

        // Создаем список команд
        ArrayList<Command> adCommandList = new ArrayList<>();
        adCommandList.add(command);

        // Настройка поведения моков
        level.addObject(tank);
        level.addObject(tree);

        // Вызываем метод updateState
        level.updateState(adCommandList);

        // Проверяем взаимодействие с моками
        verify(command, times(1)).execute();
        verify(tank, times(1)).move(collisionManager);
        verify(collisionManager, times(1)).updateState(level);
    }



}
