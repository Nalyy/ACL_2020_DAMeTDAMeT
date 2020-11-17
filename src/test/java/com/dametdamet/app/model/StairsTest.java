package com.dametdamet.app.model;

import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.entity.Hero;
import com.dametdamet.app.model.entity.monster.Monster;
import com.dametdamet.app.model.entity.monster.RandomMove;
import com.dametdamet.app.model.maze.Maze;
import com.dametdamet.app.model.maze.normalTiles.Stairs;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StairsTest {

    private PacmanGame game;
    private Stairs stairs;

    private void loadGame() {
        String[] mazes = new String[2];
        mazes[0] = "stairs_maze/maze_1_stair.txt";
        mazes[1] = "stairs_maze/maze_2_stairs.txt";
        this.game = new PacmanGame("no help", mazes);
        this.stairs = (Stairs) game.getMaze().whatIsIn(new Position(2, 1));
    }

    /**
     * On change de niveau.
     */
    @Test
    public void testLevelChanges() {
        loadGame();
        Maze firstMaze = game.getMaze();

        stairs.applyEffect(game, game.getHero());
        Maze newMaze = game.getMaze();

        assertNotEquals(newMaze, firstMaze);
    }

    /**
     * Le score augmente de 1000 * le numéro du niveau.
     */
    @Test
    public void testScoreChanges() {
        String[] mazes = new String[3];
        mazes[0] = "stairs_maze/maze_1_stair.txt";
        mazes[1] = "stairs_maze/maze_1_stair.txt";
        mazes[2] = "stairs_maze/maze_1_stair.txt";
        this.game = new PacmanGame("", mazes);
        this.stairs = (Stairs) game.getMaze().whatIsIn(new Position(2, 1));

        int prevScore = game.getScore();
        stairs.applyEffect(game, game.getHero());
        assertEquals(prevScore + 1000, game.getScore());
        prevScore = game.getScore();
        stairs.applyEffect(game, game.getHero());
        assertEquals(prevScore + 2 * 1000, game.getScore());
    }

    /**
     * On gagne le jeu après le dernier labyrinthe.
     */
    @Test
    public void testGameEnds() {
        loadGame();
        stairs.applyEffect(game, game.getHero());
        stairs.applyEffect(game, game.getHero());
        assertTrue(game.isWon());
    }

    /**
     * Le héro est à la bonne position après changement de labyrinthe.
     */
    @Test
    public void testReInitPositionHero() {
        loadGame();
        stairs.applyEffect(game, game.getHero());
        Position posHero = game.getHero().getPosition();
        Position initPosHero = game.getMaze().getInitialPositionPlayer();
        assertEquals(initPosHero, posHero);
    }

    /**
     * Les monstres sont changés quand on change de labyrinthe.
     */
    @Test
    public void testMonstersChange(){
        String[] mazes = new String[2];
        mazes[0] = "stairs_maze/maze_1_stair.txt";
        mazes[1] = "maze_monstres_1.txt";
        this.game = new PacmanGame("no help", mazes);
        this.stairs = (Stairs) game.getMaze().whatIsIn(new Position(2, 1));

        int cptFirstMaze = 0;
        for (Entity entity : game) {
            cptFirstMaze ++;
        }
        stairs.applyEffect(game, game.getHero());
        int cptNewMaze = 0;
        for (Entity entity : game) {
            cptNewMaze ++;
        }

        assertNotEquals(cptNewMaze, cptFirstMaze);
    }

    /**
     * Le timer n'est pas réinitialisé.
     */
    @Test
    public void testNoTimerChange() {
        loadGame();
        int prevTime = game.getGameTimer();
        stairs.applyEffect(game, game.getHero());
        assertTrue(prevTime >= game.getGameTimer());
    }

    /**
     * Les points de vie du héro ne changent pas.
     */
    @Test
    public void testNoHPChange() {
        loadGame();
        Hero prevHero = (Hero) game.getHero();
        int prevPV = prevHero.getHP();
        stairs.applyEffect(game, prevHero);
        Hero newHero = (Hero) game.getHero();
        assertEquals(prevPV, newHero.getHP());
    }

    /**
     * La case ne s'active pas si l'entité marchant dessus est un monstre.
     */
    @Test
    public void testDeadIfMonster() {
        loadGame();
        Monster monster = new Monster(game.getMaze().getInitialPositionPlayer(), RandomMove.INSTANCE);
        Maze prevMaze = game.getMaze();
        assertEquals(prevMaze, game.getMaze());
        stairs.applyEffect(game, monster);

        assertEquals(prevMaze, game.getMaze());
    }

    /**
     * La case renvoie une exception si le jeu PacmanGame est null.
     */
    @Test
    public void testNullGame() {
        Hero hero = new Hero(new Position(1, 1), 3);
        assertThrows(NullPointerException.class, () -> stairs.applyEffect(null, hero));
    }

    /**
     * La classe renvoie une exception si le héro est null.
     */
    @Test
    public void testNullHero() {
        loadGame();
        assertThrows(NullPointerException.class, () -> stairs.applyEffect(game, null));
    }
}
