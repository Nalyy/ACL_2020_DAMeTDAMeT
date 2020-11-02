package com.dametdamet.app.model.dao.dao;

import com.dametdamet.app.model.dao.factory.AbstractDAOFactory;

import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.maze.Maze;
import com.dametdamet.app.model.maze.TypeCase;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class TxtDAOTest {

    @Test
    void load() {
        AbstractDAOFactory ab = AbstractDAOFactory.getAbstractDAOFactory(AbstractDAOFactory.TXT);

        Maze maze = ab.getFileDAO().load("maze_vide.txt");
        /*
        Test avec un labyrinthe vide :
        Contenu :
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        */

        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getWidth(); y++) {
                assertEquals(maze.whatIsIn(new Position(x, y)).getType(), TypeCase.EMPTY);
            }
        }


        maze = ab.getFileDAO().load("maze_rempli.txt");

        /*
        Test avec un labyrinthe complètement rempli :
        Contenu :
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
         */

        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getWidth(); y++) {
                assertEquals(maze.whatIsIn(new Position(x, y)).getType(), TypeCase.WALL);
            }
        }
        maze.setInitialPositionPlayer(new Position(0, 0));

        assertEquals(new Position(0, 0), maze.getInitialPositionPlayer());

        maze = ab.getFileDAO().load("maze_rempli_2.txt");

        /*
        Test avec un labyrinthe complètement rempli :
        Contenu :
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        11111111111111X
         */

        maze.setInitialPositionPlayer(new Position(0, 0));

        assertEquals(new Position(maze.getWidth()-1, maze.getHeight()-1), maze.getInitialPositionPlayer());


        maze = ab.getFileDAO().load("maze_monstres.txt");

        /*
        Test avec un labyrinthe complètement rempli :
        Contenu :
        YYYYYYYYYYYYYYY
        YYYYYYYYYYYYYYY
        YYYYYYYYYYYYYYY
        YYYYYYYYYYYYYYY
        YYYYYYYYYYYYYYY
        YYYYYYYYYYYYYYY
        YYYYYYYYYYYYYYY
        YYYYYYYYYYYYYYY
        YYYYYYYYYYYYYYY
        YYYYYYYYYYYYYYY
        YYYYYYYYYYYYYYY
        YYYYYYYYYYYYYYY
        YYYYYYYYYYYYYYY
        YYYYYYYYYYYYYYY
        YYYYYYYYYYYYYYY
         */

        Iterator<Position> positions = maze.getIteratorMonsterPositions();

        for (int y = 0; y < maze.getWidth(); y++) {
            for (int x = 0; x < maze.getWidth(); x++) {
                assertEquals(positions.next(), new Position(x, y));
            }
        }


        maze = ab.getFileDAO().load("maze_monstres_1.txt");

        /*
        Test avec un labyrinthe complètement rempli :
        Contenu :
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        00000000000000Y
         */

        positions = maze.getIteratorMonsterPositions();

        assertEquals(positions.next(), new Position(maze.getWidth()-1, maze.getWidth()-1));

        maze = ab.getFileDAO().load("maze_monstres_2.txt");

        /*
        Test avec un labyrinthe complètement rempli :
        Contenu :
        Y00000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000Y00000000
        000000Y00000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        00000000000000Y
         */

        positions = maze.getIteratorMonsterPositions();

        assertEquals(positions.next(), new Position(0, 0));
        assertEquals(positions.next(), new Position(6, 6));
        assertEquals(positions.next(), new Position(6, 7));
        assertEquals(positions.next(), new Position(maze.getWidth()-1, maze.getWidth()-1));
    }
}