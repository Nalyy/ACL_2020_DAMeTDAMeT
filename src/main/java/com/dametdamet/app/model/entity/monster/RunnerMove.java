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

        Direction direction = monster.getDirection();
        Position positionToGo = getPositionTo(direction, monster.getPosition());

        /* Si le monstre ne peut pas aller sur cette position,
        ou si sa direction est indéfinie (IDLE),
        il choisit une case où aller aléatoirement */

        if (direction.equals(Direction.IDLE) || !monster.canGoTo(maze.whatIsIn(positionToGo))){
            direction = RandomMove.INSTANCE.getNextDirection(monster);
        }

        return direction;
    }

    private Position getPositionTo(Direction direction, Position fromPosition){
        Position positionToGo = null;
        switch (direction){
            case LEFT:
                positionToGo = fromPosition.getPositionToLeft();
                break;
            case RIGHT:
                positionToGo = fromPosition.getPositionToRight();
                break;
            case UP:
                positionToGo = fromPosition.getPositionToUp();
                break;
            case DOWN:
                positionToGo = fromPosition.getPositionToDown();
                break;
        }

        return positionToGo;
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
