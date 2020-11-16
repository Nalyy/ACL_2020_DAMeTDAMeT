package com.dametdamet.app.model;

import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.entity.Hero;
import com.dametdamet.app.model.maze.Maze;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StairsTest {

    private PacmanGame game;

    private void loadGame() {
        String[] mazes = new String[2];
        mazes[0] = "stairs_maze/maze_1_stair.txt";
        mazes[1] = "stairs_maze/maze_2_stairs.txt";
        this.game = new PacmanGame("helpFilePacman.txt", mazes);
    }

    /**
     * On change de niveau.
     */
    @Test
    public void testLevelChanges() {
        loadGame();
        Maze firstMaze = game.getMaze();

        game.goToNextLevel();
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
        this.game = new PacmanGame("helpFilePacman.txt", mazes);

        int prevScore = game.getScore();
        game.goToNextLevel();
        assertEquals(prevScore + 1000, game.getScore());
        prevScore = game.getScore();
        game.goToNextLevel();
        assertEquals(prevScore + 2 * 1000, game.getScore());
    }

    /**
     * On gagne le jeu après le dernier labyrinthe.
     */
    @Test
    public void testGameEnds() {
        loadGame();
        game.goToNextLevel();
        game.goToNextLevel();
        assertTrue(game.isWon());
    }

    /**
     * Le héro est à la bonne position après changement de labyrinthe.
     */
    @Test
    public void testReInitPositionHero() {
        loadGame();
        game.goToNextLevel();
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
        mazes[0] = "maze_monstres_1.txt";
        mazes[1] = "maze_monstres_2.txt";
        this.game = new PacmanGame("helpFilePacman.txt", mazes);

        int cptFirstMaze = 0;
        for (Entity entity : game) {
            cptFirstMaze ++;
        }
        game.goToNextLevel();
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
        game.goToNextLevel();
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
        game.goToNextLevel();
        Hero newHero = (Hero) game.getHero();
        assertEquals(prevPV, newHero.getHP());
    }

    /**
     * Il n'y a pas d'erreur si le jeu n'a pas de labyrinthe.
     */
    @Test
    public void testNoMazeNoFail() {
        String[] mazes = new String[0];
        this.game = new PacmanGame("helpFilePacman.txt", mazes);
        game.goToNextLevel();
        assertTrue(game.isWon());
    }
}
