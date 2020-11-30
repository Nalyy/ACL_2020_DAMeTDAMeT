package com.dametdamet.app.model.entity.monster;

import com.dametdamet.app.model.Direction;
import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AStarMonsterTest {

    PacmanGame game = new PacmanGame("", new String[]{"maze_astar.txt"});

    @Test
    public void testRight(){
        MoveStrategy strategy = AStarMove.INSTANCE;
        strategy.setGame(game);
        Monster monster = new Monster(new Position(0, 0),  strategy);
        Direction direction = monster.getNextDirection();
        Assertions.assertEquals(Direction.RIGHT, direction);
    }
}
