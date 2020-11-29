package com.dametdamet.app.model.entity.monster;

import com.dametdamet.app.model.Direction;
import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RunnerMonsterTest {

    /**
     * Le monstre avance dans sa direction s'il le peut.
     */
    @Test
    public void testSameDirection() {
        PacmanGame game = new PacmanGame("no help", new String[0]);
        Monster m = new Monster(new Position(1,1), RunnerMove.INSTANCE);
        Direction direction = m.getDirection();
        assertEquals(direction, m.getNextDirection());
    }

    /**
     * Le monstre change de direction s'il ne peut pas avancer.
     */
    @Test
    public void testDirectionChanges() {
        PacmanGame game = new PacmanGame("no help", new String[0]);
        Monster m = new Monster(new Position(0,0), RunnerMove.INSTANCE);
        m.setDirection(Direction.UP);
        assertNotEquals(Direction.UP, m.getNextDirection());
    }

    /**
     * Le monstre ne peut pas être nul.
     */
    @Test
    public void testNullMonster() {
        assertThrows(NullPointerException.class, ()->RunnerMove.INSTANCE.getNextDirection(null));
    }

    /**
     * La direction du monstre ne peut pas être nulle.
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

    /**
     * Le labyrinthe de la strategy ne peut pas être nul.
     */
    @Test
    public void testNullMaze() {
        RunnerMove.INSTANCE.setMaze(null);
        assertThrows(AssertionError.class, ()->new Monster(new Position(1,1), RunnerMove.INSTANCE));
    }
}
