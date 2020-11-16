package com.dametdamet.app.model.maze.trapCases;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.entity.Hero;
import com.dametdamet.app.model.maze.TypeCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class SpawnerMonsterTest {

    SpawnerMonster spawnerMonster;
    PacmanGame game;


    @BeforeEach
    void setUp() {
        spawnerMonster = new SpawnerMonster(TypeCase.SPAWNER_MONSTERS);
    }

    @AfterEach
    void tearDown() {
        spawnerMonster = null;
        game = null;
    }


    @Test
    void testRight1(){

        String[] mazes = new String[1];
        mazes[0]= "maze_spawnerMonster_1.txt";
        game = new PacmanGame("helpFilePacman.txt", mazes);

        spawnerMonster.applyEffect(game,game.getHero());

        for (Entity entity:game) {
            System.out.println();
            assertEquals(new Position(1,1),entity.getPosition());
        }
    }

    @Test
    void testRight2(){

        String[] mazes = new String[1];
        mazes[0]= "maze_spawnerMonster_2.txt";
        game = new PacmanGame("helpFilePacman.txt", mazes);

        spawnerMonster.applyEffect(game,game.getHero());

        for (Entity entity:game) {
            System.out.println(entity.getPosition());
            assertEquals(new Position(0,0),entity.getPosition());
        }
    }

    @Test
    void testRightMazeFullWall(){

        String[] mazes = new String[1];
        mazes[0]= "maze_rempli.txt";
        game = new PacmanGame("helpFilePacman.txt", mazes);

        spawnerMonster.applyEffect(game,game.getHero());

        Iterator<Entity> ite = game.iterator();

        assertEquals(false,ite.hasNext());
    }

    @Test
    void testBoundaryGameIsNull(){
        assertThrows(NullPointerException.class,() -> spawnerMonster.applyEffect(game,new Hero(new Position(0,0),3)));
    }

    @Test
    void testBoundaryEntityIsNull(){

        String[] mazes = new String[1];
        mazes[0]= "maze_rempli.txt";
        game = new PacmanGame("helpFilePacman.txt", mazes);

        assertThrows(NullPointerException.class,() -> spawnerMonster.applyEffect(game,null));
    }

    @Test
    void testBoundaryGameAndEntityAreNull(){
        assertThrows(NullPointerException.class,() -> spawnerMonster.applyEffect(game,null));
    }
}