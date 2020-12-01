package com.dametdamet.app.model.entity;

import com.dametdamet.app.model.Direction;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.attack.Projectile;
import com.dametdamet.app.model.entity.attack.ProjectileMove;
import com.dametdamet.app.model.maze.Maze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectileTest {

    private Projectile projDown;
    private Projectile projUp;
    private Projectile projRight;
    private Projectile projLeft;

    @BeforeEach
    void setUp() {
        Maze maze = new Maze();
        ProjectileMove.INSTANCE.setMaze(maze);
        projDown = new Projectile(new Position(0, 0), Direction.DOWN, ProjectileMove.INSTANCE);
        projUp = new Projectile(new Position(0, maze.getHeight()-1), Direction.UP, ProjectileMove.INSTANCE);
        projRight = new Projectile(new Position(0, 0), Direction.RIGHT, ProjectileMove.INSTANCE);
        projLeft = new Projectile(new Position(maze.getWidth()-1, 0), Direction.LEFT, ProjectileMove.INSTANCE);
    }

    @Test
    void rightGetNextDirectionDown(){
        assertEquals(Direction.DOWN, projDown.getNextDirection(), "");
    }

    @Test
    void rightGetNextDirectionUp(){
        assertEquals(Direction.UP, projUp.getNextDirection(), "");
    }

    @Test
    void rightGetNextDirectionRight(){
        assertEquals(Direction.RIGHT, projRight.getNextDirection(), "");
    }

    @Test
    void rightGetNextDirectionLeft(){
        assertEquals(Direction.LEFT, projLeft.getNextDirection(), "");
    }
}
