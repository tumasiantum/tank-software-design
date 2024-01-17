package ru.mipt.bit.platformer.util.GameObjects.Managers;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.GameObjects.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
        for (GameObject gameObject: this.level.getGameObjectList()){
            if (!(gameObject instanceof Bullet)){
                obstacleSet.add(gameObject.getCoordinates());
            }
        }
        if (obstacleSet.contains(checkCoordinates)){
            return false;
        }
        obstacleSet.add(checkCoordinates);
        return true;
    }


    public void endMovement(GridPoint2 removableCoordinates){
        obstacleSet.remove(removableCoordinates);
    }

    public void updateState(){
        ArrayList<Bullet> deletedBullets = new ArrayList<>();
        for (Bullet bullet: level.getBulletsArray()){
            if (obstacleSet.contains(bullet.getCoordinates())){
                level.removeObject(bullet);
                deletedBullets.add(bullet);
            }
        }
//        return deletedBullets;
        ArrayList<GameObject> deletingObjects = new ArrayList<>();
        for (Bullet bullet: deletedBullets){
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
        for (GameObject gameObject: deletingObjects){
            level.removeObject(gameObject);
            endMovement(gameObject.getCoordinates());
            if (gameObject instanceof Tank){
                endMovement(((Tank) gameObject).getDestinationCoordinates());
            }
        }
    }
}
