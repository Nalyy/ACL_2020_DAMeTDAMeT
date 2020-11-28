package com.dametdamet.app.model.entity.monster;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RunnerMonsterTest {

    // Tests getNextDirection

    // Tests getPositionTo

    /**
     * La direction du monstre ne peut pas être null.
     */
    @Test
    public void testNullDirection() {
        PacmanGame game = new PacmanGame("no help", new String[0]);
        Monster m = new Monster(new Position(1,1), RunnerMove.INSTANCE);
        m.setDirection(null);
        assertThrows(NullPointerException.class, m::getNextDirection);
    }

    /**
     * La position du monstre ne peut pas être nulle.
     */
    @Test
    public void testNullPosition() {
        PacmanGame game = new PacmanGame("no help", new String[0]);
        assertThrows(NullPointerException.class, ()->new Monster(null, RunnerMove.INSTANCE));
    }

    // Tests boundary
    /**
     * Le labyrinthe de la strategy ne peut pas être nulle.
     */
    @Test
    public void testNullMaze() {
        RunnerMove.INSTANCE.setMaze(null);
        assertThrows(AssertionError.class, ()->new Monster(new Position(1,1), RunnerMove.INSTANCE));
    }
}
