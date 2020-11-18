package com.dametdamet.app.model.maze.trapTiles;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.Hero;
import com.dametdamet.app.model.maze.Tile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DamageTest {

    Tile dmg;
    PacmanGame game;

    @BeforeEach
    void setUp() {
        dmg = new Damage();
    }

    @AfterEach
    void tearDown() {
        game = null;
        dmg = null;
    }

    @Test
    void testRightApplyEffect(){
        String[] mazes = new String[1];
        mazes[0]= "maze_vide.txt";

        game = new PacmanGame("no help", mazes);

        Hero hero = ((Hero)game.getHero());
        int heroLife = hero.getHP();

        dmg.applyEffect(game,hero);

        assertEquals(heroLife - 1,hero.getHP());

    }

    @Test
    void testRight2ApplyEffect(){
        String[] mazes = new String[1];
        mazes[0]= "maze_rempli.txt";

        game = new PacmanGame("no help", mazes);

        Hero hero = ((Hero)game.getHero());
        int heroLife = hero.getHP();

        dmg.applyEffect(game,hero);

        assertEquals(heroLife - 1,hero.getHP());
    }


    @Test
    void testBoundaryGameIsNull(){
        assertThrows(NullPointerException.class,() -> dmg.applyEffect(game, new Hero(new Position(0,0),3)));
    }

    @Test
    void testBoundaryEntityIsNull(){
        String[] mazes = new String[1];
        mazes[0]= "maze_rempli.txt";
        game = new PacmanGame("no help", mazes);

        assertThrows(NullPointerException.class,() -> dmg.applyEffect(game,null));
    }

    @Test
    void testBoundaryGameAndEntityAreNull(){
        assertThrows(NullPointerException.class,() -> dmg.applyEffect(game,null));
    }

}