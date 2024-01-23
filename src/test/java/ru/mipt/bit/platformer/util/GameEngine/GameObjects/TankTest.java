package ru.mipt.bit.platformer.util.GameEngine.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.mipt.bit.platformer.util.GameEngine.Direction;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.Bullet;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.Tank;
import ru.mipt.bit.platformer.util.GameEngine.Level;
import ru.mipt.bit.platformer.util.GameEngine.Managers.CollisionManager;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.TankStates.LightTank;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.TankStates.TankState;
import org.powermock.reflect.Whitebox;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TankTest {

    private Tank tank;
    private Level level;
    private CollisionManager collisionManager;

    @BeforeEach
    void setUp() {
        // Создаем тестовый объект
        tank = new Tank(new GridPoint2(0, 0), Direction.UP);
        level = mock(Level.class);
        // Создаем заглушку (mock) для CollisionManager
        collisionManager = mock(CollisionManager.class);
    }

    @Test
    void getCoordinates_ReturnsCorrectCoordinates() {
        assertEquals(new GridPoint2(0, 0), tank.getCoordinates());
    }

    @Test
    void getDestinationCoordinates_ReturnsCorrectDestinationCoordinates() {
        assertEquals(new GridPoint2(0, 0), tank.getDestinationCoordinates());
    }

    @Test
    void getRotateProgress_ReturnsCorrectRotateProgress() {
        assertFalse(tank.getRotateProgress());
    }

    @Test
    void setRotateProgress_SetsCorrectRotateProgress() {
        tank.setRotateProgress(true);
        assertTrue(tank.getRotateProgress());
    }

    @Test
    void getMovementProgress_ReturnsCorrectMovementProgress() {
        assertEquals(1f, tank.getMovementProgress());
    }

    @Test
    void getDirection_ReturnsCorrectDirection() {
        assertEquals(Direction.UP, tank.getDirection());
    }

    @Test
    void move_CallsContinueProgressAndUpdatesCoordinates() {
        // Создаем заглушку для TankState
        TankState tankState = mock(LightTank.class);
        when(tankState.getMovementSpeed()).thenReturn(0.5f);
        tank.setState(tankState);
        Graphics graphics = mock(Graphics.class);
        Whitebox.setInternalState(Gdx.class, "graphics", graphics);
        // Вызываем метод move с mock CollisionManager
        tank.move(collisionManager);

        // Проверяем, что метод continueProgress был вызван и координаты обновлены
        verify(tankState, times(1)).getMovementSpeed();
        verify(collisionManager, times(0)).removeObstacle(any(GridPoint2.class));
    }

    @Test
    void startMovement_UpdatesDestinationCoordinatesIfFree() {
        // Подготавливаем коллизию
        when(collisionManager.isFree(any(GridPoint2.class))).thenReturn(true);

        // Вызываем метод startMovement
        tank.startMovement(Direction.RIGHT, collisionManager);

        // Проверяем, что координаты обновлены и коллизия добавлена
        assertEquals(new GridPoint2(1, 0), tank.getDestinationCoordinates());
        verify(collisionManager, times(1)).addObstacle(any(GridPoint2.class));
    }

    @Test
    void startMovement_DoesNotUpdateDestinationCoordinatesIfNotFree() {
        // Подготавливаем блокировку коллизии
        when(collisionManager.isFree(any(GridPoint2.class))).thenReturn(false);

        // Вызываем метод startMovement
        tank.startMovement(Direction.RIGHT, collisionManager);

        // Проверяем, что координаты не обновлены и коллизия не добавлена
        assertEquals(new GridPoint2(0, 0), tank.getDestinationCoordinates());
        verify(collisionManager, never()).addObstacle(any(GridPoint2.class));
    }

    @Test
    void rotate_SetsCorrectDirectionAndRotateProgress() {
        tank.rotate(Direction.DOWN);

        assertEquals(Direction.DOWN, tank.getDirection());
        assertTrue(tank.getRotateProgress());
    }

    @Test
    void setState_SetsCorrectTankState() throws NoSuchFieldException, IllegalAccessException {
        TankState newState = mock(TankState.class);

        tank.setState(newState);

        // Используем рефлексию для доступа к приватному полю state
        Field stateField = Tank.class.getDeclaredField("state");
        stateField.setAccessible(true);
        TankState actualState = (TankState) stateField.get(tank);

        assertSame(newState, actualState);
    }

    @Test
    void shoot_AddsBulletToLevelIfRechargeTimeElapsed() throws InterruptedException {
        TankState tankState = mock(LightTank.class);
        when(tankState.getRechargeTime()).thenReturn(500L);
        when(tankState.getShootingAbility()).thenReturn(true);
        tank.setState(tankState);
        Thread.sleep(1000);
        // Создаем объект ArgumentCaptor для класса Bullet
        ArgumentCaptor<Bullet> bulletCaptor = ArgumentCaptor.forClass(Bullet.class);


        tank.shoot(level);


        // Проверяем, что был вызван метод add объекта Level с аргументом типа Bullet
        verify(level, times(1)).addObject(bulletCaptor.capture());

        // Получаем захваченный объект Bullet
        Bullet capturedBullet = bulletCaptor.getValue();

        // Проверяем, что свойства Bullet соответствуют ожидаемым значениям
        assertEquals(tank.getDirection().apply(tank.getCoordinates()), capturedBullet.getCoordinates());
        assertEquals(tank.getDirection(), capturedBullet.getDirection());
    }


    @Test
    void shoot_DoesNotAddBulletToLevelIfRechargeTimeNotElapsed() {
        TankState tankState = mock(LightTank.class);
        when(tankState.getRechargeTime()).thenReturn(1000L);
        when(tankState.getShootingAbility()).thenReturn(true);
        tank.setState(tankState);

        tank.shoot(level);

        // Проверяем, что Bullet не добавлен в Level
        verify(level, never()).addObject(any(Bullet.class));
    }

    @Test
    void shoot_DoesNotAddBulletToLevelIfShootingAbilityFalse() {
        TankState tankState = mock(LightTank.class);
        when(tankState.getRechargeTime()).thenReturn(500L);
        when(tankState.getShootingAbility()).thenReturn(false);
        tank.setState(tankState);

        tank.shoot(level);

        // Проверяем, что Bullet не добавлен в Level
        verify(level, never()).addObject(any(Bullet.class));
    }

    @Test
    void damage_DelegatesToTankState() {
        TankState tankState = mock(LightTank.class);
        tank.setState(tankState);

        tank.damage(10f);

        verify(tankState, times(1)).damage(10f);
    }

    @Test
    void isAlive_ReturnsTrueIfHealthGreaterThanZero() {
        TankState tankState = mock(LightTank.class);
        when(tankState.getHealth()).thenReturn(50f);
        tank.setState(tankState);

        assertTrue(tank.isAlive());
    }

    @Test
    void isAlive_ReturnsFalseIfHealthZero() {
        TankState tankState = mock(LightTank.class);
        when(tankState.getHealth()).thenReturn(0f);
        tank.setState(tankState);

        assertFalse(tank.isAlive());
    }

    @Test
    void getHealth_DelegatesToTankState() {
        TankState tankState = mock(LightTank.class);
        when(tankState.getHealth()).thenReturn(75f);
        tank.setState(tankState);

        assertEquals(75f, tank.getHealth());
    }
}
