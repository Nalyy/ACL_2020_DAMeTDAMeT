package com.dametdamet.app.model.entity.monster;

import com.dametdamet.app.model.Direction;
import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.maze.Maze;

import java.util.Objects;

public enum RunnerMove implements MoveStrategy{
    INSTANCE;

    private Maze maze;

    @Override
    public void setGame(PacmanGame game) {
        maze = game.getMaze(); // pas besoin du game pour cette stratégie
    }

    @Override
    public Direction getNextDirection(Monster monster) {
        Objects.requireNonNull(maze, "La stratégie appliquée à l'ennemi n'a pas de labyrinthe associé.");

        return null;
    }

    @Override
    public boolean hasMaze() {
        return maze!=null;
    }

    @Override
    public boolean isInMaze(Position position) {
        int x = position.getX();
        int y = position.getY();
        int width = maze.getWidth();
        int height = maze.getHeight();

        boolean isWithinWitdh = (x >= 0) && (x < width);
        boolean isWithinHeight = (y >= 0) && (y < height);

        return isWithinWitdh && isWithinHeight;
    }
}
