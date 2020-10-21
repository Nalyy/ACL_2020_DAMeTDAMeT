package com.dametdamet.app.model.maze;

import com.dametdamet.app.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MazeTest {

    private Maze maze;

    @BeforeEach
    void setUp() {
        maze = new Maze(10, 10);
    }

    @Test
    void whatIsIn() {
        assertEquals(maze.whatIsIn(new Position(0, 0)), new Case(TypeCase.EMPTY));
        assertEquals(maze.whatIsIn(new Position(0, 1)), new Case(TypeCase.EMPTY));
        assertEquals(maze.whatIsIn(new Position(1, 0)), new Case(TypeCase.EMPTY));
        assertEquals(maze.whatIsIn(new Position(5, 5)), new Case(TypeCase.EMPTY));

        assertEquals(maze.whatIsIn(new Position(10, 10)), new Case(TypeCase.OUTOFBOUND));
        assertEquals(maze.whatIsIn(new Position(0, 10)), new Case(TypeCase.OUTOFBOUND));
        assertEquals(maze.whatIsIn(new Position(10, 0)), new Case(TypeCase.OUTOFBOUND));
        assertEquals(maze.whatIsIn(new Position(-1, -1)), new Case(TypeCase.OUTOFBOUND));
        assertEquals(maze.whatIsIn(new Position(0, -1)), new Case(TypeCase.OUTOFBOUND));
        assertEquals(maze.whatIsIn(new Position(-1, 0)), new Case(TypeCase.OUTOFBOUND));
    }

    @Test
    void isNotWall() {
        assert(maze.isNotWall(new Position(5, 5)));
        assert(maze.isNotWall(new Position(0, 0)));
        assert(maze.isNotWall(new Position(0, 1)));
        assert(maze.isNotWall(new Position(1, 0)));

        assert(!maze.isNotWall(new Position(10, 10)));
        assert(!maze.isNotWall(new Position(0, 10)));
        assert(!maze.isNotWall(new Position(10, 0)));
        assert(!maze.isNotWall(new Position(-1, -1)));
        assert(!maze.isNotWall(new Position(0, -1)));
        assert(!maze.isNotWall(new Position(-1, 0)));
    }

    @Test
    void isNotOutOfBound() {
        assert(maze.isNotOutOfBound(new Position(5, 5)));
        assert(maze.isNotOutOfBound(new Position(0, 0)));
        assert(maze.isNotOutOfBound(new Position(0, 1)));
        assert(maze.isNotOutOfBound(new Position(1, 0)));

        assert(!maze.isNotOutOfBound(new Position(10, 10)));
        assert(!maze.isNotOutOfBound(new Position(0, 10)));
        assert(!maze.isNotOutOfBound(new Position(10, 0)));
        assert(!maze.isNotOutOfBound(new Position(-1, -1)));
        assert(!maze.isNotOutOfBound(new Position(0, -1)));
        assert(!maze.isNotOutOfBound(new Position(-1, 0)));
    }
}