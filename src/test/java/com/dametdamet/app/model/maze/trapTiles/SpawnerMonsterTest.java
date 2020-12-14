package com.dametdamet.app.model.maze.trapTiles;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.entity.Hero;
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
        spawnerMonster = new SpawnerMonster();
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
        game = new PacmanGame("no help", mazes);

        spawnerMonster.applyEffect(game,game.getHero());
        int nbMonster = 0;
        int nbMonsterExpected = 1;

        Iterator<Entity> monstersIterator = game.getMonstersIterator();
        while (monstersIterator.hasNext()) {
            Entity entity = monstersIterator.next();
            nbMonster++;
            assertEquals(new Position(1,1),entity.getPosition());
        }
        assertEquals(nbMonsterExpected,nbMonster);
    }

    @Test
    void testRight2(){
        String[] mazes = new String[1];
        mazes[0]= "maze_spawnerMonster_2.txt";
        game = new PacmanGame("no help", mazes);

        int nbMonsterExpected = 10;

        for (int i = 0; i < nbMonsterExpected ; i ++){
            spawnerMonster.applyEffect(game,game.getHero());
            spawnerMonster = new SpawnerMonster();
        }

        int nbMonster = 0;

        Iterator<Entity> monstersIterator = game.getMonstersIterator();
        while (monstersIterator.hasNext()) {
            Entity entity = monstersIterator.next();
            nbMonster++;
            assertEquals(new Position(0,0),entity.getPosition());
        }

        assertEquals(nbMonsterExpected,nbMonster);
    }

    @Test
    void testRightMazeFullWall(){

        String[] mazes = new String[1];
        mazes[0]= "maze_rempli.txt";
        game = new PacmanGame("no help", mazes);

        spawnerMonster.applyEffect(game,game.getHero());

        Iterator<Entity> ite = game.getMonstersIterator();

        assertFalse(ite.hasNext());
    }

    @Test
    void testBoundaryGameIsNull(){
        assertThrows(NullPointerException.class,() -> spawnerMonster.applyEffect(game,new Hero(new Position(0,0))));
    }

    @Test
    void testBoundaryEntityIsNull(){

        String[] mazes = new String[1];
        mazes[0]= "maze_rempli.txt";
        game = new PacmanGame("no help", mazes);

        assertThrows(NullPointerException.class,() -> spawnerMonster.applyEffect(game,null));
    }

    @Test
    void testBoundaryGameAndEntityAreNull(){
        assertThrows(NullPointerException.class,() -> spawnerMonster.applyEffect(game,null));
    }
}