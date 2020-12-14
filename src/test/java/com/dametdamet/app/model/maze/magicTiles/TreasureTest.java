package com.dametdamet.app.model.maze.magicTiles;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.Hero;
import com.dametdamet.app.model.entity.monster.Monster;
import com.dametdamet.app.model.entity.monster.RandomMove;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TreasureTest {

    private PacmanGame game;
    private Treasure treasure;

    private void loadGame() {
        String[] mazes = new String[1];
        mazes[0] = "spawnerChest_maze/maze_1_bonusChest.txt";
        this.game = new PacmanGame("no help", mazes);
        SpawnerChest spawner = (SpawnerChest) game.getMaze().whatIsIn(new Position(4, 1));
        spawner.applyEffect(game, game.getHero());
        this.treasure = (Treasure) game.getMaze().whatIsIn(new Position(1, 3));
    }

    /**
     * La case n'est pas pressée après l'initialisation.
     */
    @Test
    public void testInitNotPressed() {
        loadGame();
        assertFalse(treasure.isPressed());
    }

    /**
     * La case est pressée après activation.
     */
    @Test
    public void testPressedAfterActivation() {
        loadGame();
        treasure.applyEffect(game, game.getHero());
        assertTrue(treasure.isPressed());
    }

    /**
     * Le score augmente de 2 000.
     */
    @Test
    public void testTreasure() {
        loadGame();

        int prevScore = game.getScore();
        treasure.applyEffect(game, game.getHero());

        assertEquals(prevScore + 2000, game.getScore());
    }

    /**
     * La case ne s'active pas si elle a déjà été pressée.
     */
    @Test
    public void testDeadIfPressed() {
        loadGame();
        treasure.applyEffect(game, game.getHero());

        int prevScore = game.getScore();
        treasure.applyEffect(game, game.getHero());

        assertEquals(prevScore, game.getScore());
    }

    /**
     * La case ne s'active pas si l'entité marchant dessus est un monstre.
     */
    @Test
    public void testDeadIfMonster() {
        loadGame();

        Monster monster = new Monster(game.getMaze().getInitialPositionPlayer(), RandomMove.INSTANCE);
        int prevScore = game.getScore();
        treasure.applyEffect(game, monster);

        assertEquals(prevScore, game.getScore());
        assertFalse(treasure.isPressed());
    }

    /**
     * La case renvoie une exception si le jeu PacmanGame est null.
     */
    @Test
    public void testNullGame() {
        Hero hero = new Hero(new Position(1, 1));
        assertThrows(NullPointerException.class, () -> treasure.applyEffect(null, hero));
    }

    /**
     * La classe renvoie une exception si le héro est null.
     */
    @Test
    public void testNullHero() {
        loadGame();
        assertThrows(NullPointerException.class, () -> treasure.applyEffect(game, null));
    }
}
