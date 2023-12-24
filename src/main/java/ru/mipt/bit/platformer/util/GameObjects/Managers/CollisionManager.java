package ru.mipt.bit.platformer.util.GameObjects.Managers;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.GameObjects.Level;

import java.util.HashSet;
import java.util.Set;

public class CollisionManager {
    private Set<GridPoint2> obstacleSet = new HashSet<>();

    private Level level;

    public CollisionManager(Level level) {
        this.level = level;
        level.getGameObjectList().forEach(gameObject -> this.obstacleSet.add(gameObject.getCoordinates()));
        addBorders();
    }

    private void addBorders() {
        for (int i = 0; i < this.level.width; i++) {
            obstacleSet.add(new GridPoint2(i, -1));
            obstacleSet.add(new GridPoint2(i, this.level.height));
        }
        for (int i = 0; i < this.level.height; i++) {
            obstacleSet.add(new GridPoint2(-1, i));
            obstacleSet.add(new GridPoint2(this.level.width, i));
        }
    }

    public boolean isFree(GridPoint2 checkCoordinates){
        if (obstacleSet.contains(checkCoordinates)){
            return false;
        }
        obstacleSet.add(checkCoordinates);
        return true;
    }

    public void endMovement(GridPoint2 removableCoordinates){
        obstacleSet.remove(removableCoordinates);
    }
}
