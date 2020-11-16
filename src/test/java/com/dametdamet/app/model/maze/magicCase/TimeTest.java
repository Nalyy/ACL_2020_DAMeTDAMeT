package com.dametdamet.app.model.maze.magicCase;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.Hero;
import com.dametdamet.app.model.entity.monster.Monster;
import com.dametdamet.app.model.entity.monster.RandomMove;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TimeTest {

    private PacmanGame game;
    private Time time;

    private void loadGame() {
        String[] mazes = new String[1];
        mazes[0] = "maze_magicCase.txt";
        this.game = new PacmanGame("helpFilePacman.txt", mazes);
        this.time = (Time) game.getMaze().whatIsIn(new Position(4, 2));
    }

    /**
     * La case n'est pas pressée après l'initialisation.
     */
    @Test
    public void testInitNotPressed() {
        loadGame();
        assertFalse(time.isPressed());
    }

    /**
     * La case est pressée après activation.
     */
    @Test
    public void testPressedAfterActivation() {
        loadGame();
        time.applyEffect(game, game.getHero());
        assertTrue(time.isPressed());
    }

    /**
     * Le timer gagne cinq secondes.
     */
    @Test
    public void testTime() {
        loadGame();

        int prevTime = game.getGameTimer();
        time.applyEffect(game, game.getHero());

        assertEquals(prevTime + 5, game.getGameTimer());
    }

    /**
     * La case ne s'active pas si elle a été pressée.
     */
    @Test
    public void testDeadIfPressed() {
        loadGame();
        time.applyEffect(game, game.getHero());

        int prevTime = game.getGameTimer();
        time.applyEffect(game, game.getHero());

        assertEquals(prevTime, game.getGameTimer());
    }

    /**
     * La case ne s'active pas si l'entité marchant dessus est un monstre.
     */
    @Test
    public void testDeadIfMonster() {
        loadGame();

        Monster monster = new Monster(game.getMaze().getInitialPositionPlayer(), RandomMove.INSTANCE);
        int prevTime = game.getGameTimer();
        time.applyEffect(game, monster);

        assertEquals(prevTime, game.getGameTimer());
        assertFalse(time.isPressed());
    }

    /**
     * La case renvoie une exception si le jeu PacmanGame est null.
     */
    @Test
    public void testNullGame() {
        Hero hero = new Hero(new Position(1, 1), 3);
        assertThrows(NullPointerException.class, () -> time.applyEffect(null, hero));
    }

    /**
     * La classe renvoie une exception si le héro est null.
     */
    @Test
    public void testNullHero() {
        loadGame();
        assertThrows(NullPointerException.class, () -> time.applyEffect(game, null));
    }
}
