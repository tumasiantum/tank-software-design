package ru.mipt.bit.platformer.util.Graphics.LevelGenerator;

import com.badlogic.gdx.math.GridPoint2;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelPositions {
    private Map<Type, List<GridPoint2>> results = new HashMap<>();

    private GridPoint2 playerStartCoordinates;

    public int width, height;

    public List<GridPoint2> addList(Type key, List<GridPoint2> list){
        if(this.results.containsKey(key)) return this.results.get(key);

        this.results.put(key, list);

        return this.results.get(key);
    }

    public void setPlayerStartCoordinates(GridPoint2 playerStartCoordinates) {
        this.playerStartCoordinates = playerStartCoordinates;
    }

    public Map<Type, List<GridPoint2>> getResults() {
        return results;
    }

    public GridPoint2 getPlayerStartCoordinates() {
        return playerStartCoordinates;
    }
}
