package com.dametdamet.app.model.entity.monster;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.EntityType;
import com.dametdamet.app.model.maze.Maze;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TileType;
import com.dametdamet.app.model.maze.magicTiles.Heal;
import com.dametdamet.app.model.maze.normalTiles.Empty;
import com.dametdamet.app.model.maze.normalTiles.OutOfBound;
import com.dametdamet.app.model.maze.normalTiles.Teleportation;
import com.dametdamet.app.model.maze.normalTiles.Wall;
import com.dametdamet.app.model.maze.trapTiles.Damage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


public class MonsterTest {

    private Monster monster;
    private Monster monster2;

    @BeforeEach
    void setUp(){
        RandomMove.INSTANCE.setMaze(new Maze());
        monster = new Monster(new Position(1, 1),RandomMove.INSTANCE, EntityType.GHOST);
        monster2 = new Monster(new Position(1, 1),RandomMove.INSTANCE, EntityType.MONSTER);
    }


    @Test
    void testGhostCanTrigger() {
        assertFalse(monster.canTrigger(new Teleportation(new Position(0,0))));
        assertFalse(monster.canTrigger(new Damage()));
        assertFalse(monster.canTrigger(new Heal()));
    }

    @Test
    void testMonsterCanTrigger() {
        assertTrue(monster2.canTrigger(new Teleportation(new Position(0,0))));
        assertFalse(monster2.canTrigger(new Damage()));
        assertFalse(monster2.canTrigger(new Heal()));
    }
    @Test
    void testGhostCanGo(){
        assertTrue(monster.canGoTo(new Wall(0)));
        assertTrue(monster.canGoTo(new Empty(0)));
        assertTrue(monster.canGoTo(new Teleportation(new Position(0,0))));
        assertFalse(monster.canGoTo(new OutOfBound()));
    }
    @Test
    void testMonsterCanGo(){
        assertFalse(monster2.canGoTo(new Wall(0)));
        assertTrue(monster2.canGoTo(new Empty(0)));
        assertTrue(monster2.canGoTo(new Teleportation(new Position(0,0))));
        assertFalse(monster2.canGoTo(new OutOfBound()));
    }
/*
    @Test
    void boundaryTestMonsterCanGo(){
        assertFalse(monster.canGoTo(null));
        assertFalse(monster2.canGoTo(null));
    }

    @Test
    void boundaryTestMonsterCanTrigger(){
        assertFalse(monster.canTrigger(null));
        assertFalse(monster2.canTrigger(null));
    }
*/
}
