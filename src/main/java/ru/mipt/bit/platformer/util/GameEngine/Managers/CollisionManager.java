package ru.mipt.bit.platformer.util.GameEngine.Managers;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.GameEngine.*;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.Bullet;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.GameObject;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.LiveableObject;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.MovableObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CollisionManager {
    private Set<GridPoint2> obstacleSet = new HashSet<>();

    public CollisionManager(int width, int height) {
        addBorders(width, height);
    }

    private void addBorders(int width, int height) {
        for (int i = 0; i < width; i++) {
            obstacleSet.add(new GridPoint2(i, -1));
            obstacleSet.add(new GridPoint2(i, height));
        }
        for (int i = 0; i < height; i++) {
            obstacleSet.add(new GridPoint2(-1, i));
            obstacleSet.add(new GridPoint2(width, i));
        }
    }

    public boolean isFree(GridPoint2 checkCoordinates){
        return !obstacleSet.contains(checkCoordinates);
    }


    public void addObstacle(GridPoint2 addCoordinates){
        obstacleSet.add(addCoordinates);
    }
    public void removeObstacle(GridPoint2 removableCoordinates){
        obstacleSet.remove(removableCoordinates);
    }

    public void updateState(Level level){
        deleteDiedObjects(level);
    }

    private ArrayList<GameObject> getShootedObjects(Level level) {
        ArrayList<Bullet> deletedBullets = getDeletedBullets(level);
        ArrayList<GameObject> deletingObjects = new ArrayList<>();
        for (Bullet bullet: deletedBullets){
            level.removeObject(bullet);
            for (GameObject gameObject: level.getGameObjectList()){
                if (gameObject instanceof LiveableObject){
                    if (bullet.getCoordinates().equals(gameObject.getCoordinates())) {
                        ((LiveableObject) gameObject).damage(bullet.getDamage());
                        if (!(((LiveableObject) gameObject).isAlive())) {
                            deletingObjects.add(gameObject);
                        }
                    }
                }
            }
        }
        return deletingObjects;
    }

    private ArrayList<Bullet> getDeletedBullets(Level level) {
        ArrayList<Bullet> deletedBullets = new ArrayList<>();

        for (GameObject bullet: level.getGameObjectList()) {
            if (bullet instanceof Bullet){
                if (obstacleSet.contains(bullet.getCoordinates())){
                    deletedBullets.add((Bullet) bullet);
                }
            }
        }
        return deletedBullets;
    }

    private void deleteDiedObjects(Level level) {
        ArrayList<GameObject> deletingObjects = getShootedObjects(level);
        for (GameObject gameObject: deletingObjects){
            level.removeObject(gameObject);
            obstacleSet.remove(gameObject.getCoordinates());
            if (gameObject instanceof MovableObject){
                obstacleSet.remove(((MovableObject) gameObject).getDestinationCoordinates());
            }
        }
    }
}
