package com.dametdamet.app.model.maze.magicCase;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.Hero;
import com.dametdamet.app.model.entity.monster.Monster;
import com.dametdamet.app.model.entity.monster.RandomMove;
import com.dametdamet.app.model.maze.TypeCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SpawnerChestTest {

    private PacmanGame game;
    private SpawnerChest spawnerChest;

    private void loadGame(int numMaze) {
        String[] mazes = new String[1];
        Position posSpawner;
        if (numMaze == 0) {
            mazes[0] = "spawnerChest_maze/maze_no_bonusChest.txt";
            posSpawner = new Position(2, 1);
        } else if (numMaze == 1) {
            mazes[0] = "spawnerChest_maze/maze_1_bonusChest.txt";
            posSpawner = new Position(4, 1);
        } else {
            mazes[0] = "spawnerChest_maze/maze_2_bonusChests.txt";
            posSpawner = new Position(4, 1);
        }
        this.game = new PacmanGame("no help", mazes);
        this.spawnerChest = (SpawnerChest) game.getMaze().whatIsIn(posSpawner);
    }

    /**
     * La case n'est pas pressée après l'initialisation.
     */
    @Test
    public void testInitNotPressed() {
        loadGame(1);
        assertFalse(spawnerChest.isPressed());
    }

    /**
     * La case est pressée après activation.
     */
    @Test
    public void testPressedAfterActivation() {
        loadGame(1);
        spawnerChest.applyEffect(game, game.getHero());
        assertTrue(spawnerChest.isPressed());
    }

    /**
     * Un nouveau trésor apparaît dans le labyrinthe à l'activation.
     */
    @Test
    public void testNewChestSpawns() {
        loadGame(1);
        Position posChest = new Position(1, 3);
        assertEquals(TypeCase.EMPTY, game.getMaze().whatIsIn(posChest).getType());

        spawnerChest.applyEffect(game, game.getHero());
        assertEquals(TypeCase.BONUS, game.getMaze().whatIsIn(posChest).getType());
    }

    /**
     * Un trésor apparaît sur une case vide même sans position prédéfinie.
     */
    @Test
    public void testNoPosSpawn() {
        loadGame(0);
        assertEquals(TypeCase.EMPTY, game.getMaze().whatIsIn(game.getMaze().getInitialPositionPlayer()).getType());
        spawnerChest.applyEffect(game, game.getHero());

        assertEquals(TypeCase.BONUS, game.getMaze().whatIsIn(game.getMaze().getInitialPositionPlayer()).getType());
    }

    /**
     * Un deuxième coffre peut apparaître sur une case différente.
     */
    @Test
    public void testTwoChests() {
        loadGame(2);
        Position posFirstChest = new Position(1, 3);
        Position posSecondChest = new Position(4, 3);
        assertEquals(TypeCase.EMPTY, game.getMaze().whatIsIn(posFirstChest).getType());
        assertEquals(TypeCase.EMPTY, game.getMaze().whatIsIn(posSecondChest).getType());
        spawnerChest.applyEffect(game, game.getHero());
        assertTrue(TypeCase.BONUS.equals(game.getMaze().whatIsIn(posFirstChest).getType()) ^ TypeCase.BONUS.equals(game.getMaze().whatIsIn(posSecondChest).getType()));

        SpawnerChest secondSpawner = (SpawnerChest) game.getMaze().whatIsIn(new Position(4, 2));
        secondSpawner.applyEffect(game, game.getHero());

        assertEquals(TypeCase.BONUS, game.getMaze().whatIsIn(posFirstChest).getType());
        assertEquals(TypeCase.BONUS, game.getMaze().whatIsIn(posSecondChest).getType());
    }

    /**
     * La case ne s'active pas si elle a déjà été pressée.
     */
    @Test
    public void testDeadIfPressed() {
        loadGame(2);
        Position posFirstChest = new Position(1, 3);
        Position posSecondChest = new Position(4, 3);
        assertEquals(TypeCase.EMPTY, game.getMaze().whatIsIn(posFirstChest).getType());
        assertEquals(TypeCase.EMPTY, game.getMaze().whatIsIn(posSecondChest).getType());
        spawnerChest.applyEffect(game, game.getHero());
        assertTrue(TypeCase.BONUS.equals(game.getMaze().whatIsIn(posFirstChest).getType()) ^ TypeCase.BONUS.equals(game.getMaze().whatIsIn(posSecondChest).getType()));

        spawnerChest.applyEffect(game, game.getHero());
        assertTrue(TypeCase.BONUS.equals(game.getMaze().whatIsIn(posFirstChest).getType()) ^ TypeCase.BONUS.equals(game.getMaze().whatIsIn(posSecondChest).getType()));
    }

    /**
     * La case ne s'active pas si l'entité marchant dessus est un monstre.
     */
    @Test
    public void testDeadIfMonster() {
        loadGame(1);
        Position posChest = new Position(1, 3);
        assertEquals(TypeCase.EMPTY, game.getMaze().whatIsIn(posChest).getType());
        Monster monster = new Monster(game.getMaze().getInitialPositionPlayer(), RandomMove.INSTANCE);

        spawnerChest.applyEffect(game, monster);
        assertEquals(TypeCase.EMPTY, game.getMaze().whatIsIn(posChest).getType());
        assertFalse(spawnerChest.isPressed());
    }

    /**
     * La case renvoie une exception si le jeu PacmanGame est null.
     */
    @Test
    public void testNullGame() {
        Hero hero = new Hero(new Position(1, 1), 3);
        assertThrows(NullPointerException.class, () -> spawnerChest.applyEffect(null, hero));
    }

    /**
     * La classe renvoie une exception si le héro est null.
     */
    @Test
    public void testNullHero() {
        loadGame(1);
        assertThrows(NullPointerException.class, () -> spawnerChest.applyEffect(game, null));
    }

}
