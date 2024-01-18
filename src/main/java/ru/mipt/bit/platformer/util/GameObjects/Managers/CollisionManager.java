package ru.mipt.bit.platformer.util.GameObjects.Managers;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.GameObjects.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CollisionManager {
    private Set<GridPoint2> obstacleSet = new HashSet<>();


    private Level level;

    public CollisionManager(Level level) {
        this.level = level;
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
        return !obstacleSet.contains(checkCoordinates);
    }


    public void addObstacle(GridPoint2 addCoordinates){
        obstacleSet.add(addCoordinates);
    }
    public void removeObstacle(GridPoint2 removableCoordinates){
        obstacleSet.remove(removableCoordinates);
    }

    public void updateState(){
        deleteDiedObjects(getShootedObjects(getDeletedBullets()));
    }

    private ArrayList<GameObject> getShootedObjects(ArrayList<Bullet> deletedBullets) {
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

    private ArrayList<Bullet> getDeletedBullets() {
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

    private void deleteDiedObjects(ArrayList<GameObject> deletingObjects) {
        for (GameObject gameObject: deletingObjects){
            level.removeObject(gameObject);
            obstacleSet.remove(gameObject.getCoordinates());
            if (gameObject instanceof MovableObject){
                obstacleSet.remove(((MovableObject) gameObject).getDestinationCoordinates());
            }
        }
    }
}
