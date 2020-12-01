package com.dametdamet.app.model.entity.attack;

import com.dametdamet.app.model.Direction;
import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.entity.monster.MoveStrategy;
import com.dametdamet.app.model.maze.Maze;

import java.util.Objects;

public enum ProjectileMove implements MoveStrategy {

    INSTANCE;
    private Maze maze;

    @Override
    public void setMaze(Maze maze) { this.maze = maze; }

    @Override
    public void setGame(PacmanGame game) {
        maze = game.getMaze();
    }

    /**
     * Retourne la direction du projectile s'il peut aller sur la case suivante,
     * Direction.IDLE sinon.
     * @param entity le projectile
     * @return la prochaine direction du projectile
     */
    @Override
    public Direction getNextDirection(Entity entity) {
        Objects.requireNonNull(maze, "La stratégie appliquée au projectile n'a pas de labyrinthe associé.");

        Position posEntity = entity.getPosition();
        Position position = new Position(posEntity);
        Direction direction = entity.getDirection();

        switch (direction) {
            case UP:
                position.setY(position.getY() - 1);
                break;
            case DOWN:
                position.setY(position.getY() + 1);
                break;
            case LEFT:
                position.setX(position.getX() - 1);
                break;
            case RIGHT:
                position.setX(position.getX() + 1);
                break;
        }

        if (!entity.canGoTo(maze.whatIsIn(position))) {
            return Direction.IDLE;
        } else {
            return direction;
        }
    }

    @Override
    public boolean hasMaze() { return maze != null; }

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
