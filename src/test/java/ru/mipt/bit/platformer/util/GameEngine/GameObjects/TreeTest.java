package ru.mipt.bit.platformer.util.GameEngine.GameObjects;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.util.GameEngine.Direction;
import ru.mipt.bit.platformer.util.GameEngine.GameObjects.Tree;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TreeTest {

    @Test
    void getCoordinates_ReturnsCorrectCoordinates() {
        GridPoint2 expectedCoordinates = new GridPoint2(3, 5);
        Tree tree = new Tree(expectedCoordinates);

        assertEquals(expectedCoordinates, tree.getCoordinates());
    }

    @Test
    void getDirection_ReturnsDirectionUp() {
        Tree tree = new Tree(new GridPoint2(1, 1));
        Assertions.assertEquals(Direction.UP, tree.getDirection());
    }

}
