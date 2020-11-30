package com.dametdamet.app.model.entity.monster;

import com.dametdamet.app.engine.Command;
import com.dametdamet.app.model.Direction;
import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Maze;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public enum RandomMove implements MoveStrategy {
    INSTANCE;
    private Maze maze;

    @Override
    public void setGame(PacmanGame game) {
        maze = game.getMaze(); // juste besoin du labyrinthe pour cette stratégie
    }

    @Override
    public boolean hasMaze(){
        return maze!=null;
    }

    public void setMaze(Maze maze){ this.maze = maze; }

    @Override
    public Direction getNextDirection(Entity monster){
        Objects.requireNonNull(maze, "La stratégie appliquée au monstre n'a pas de labyrinthe associé.");

        // On vérifie si le monstre a le droit de se re-déplacer tout de suite.
        if (!((Monster)monster).isFinishedTimer()) return Direction.IDLE;

        // Si oui, on reset son timer pour le prochain déplacement et on lui donne sa direction.
        ((Monster)monster).resetTimer();

        Position position = monster.getPosition();
        int initialX = position.getX();
        int initialY = position.getY();
        TileType type;
        List<Direction> candidates = new ArrayList<>(4); // 4 = nombre de commandes possibles
        Random randomGenerator = new Random();

        /* On commence par regarder les commandes possibles à exécuter */
        // Tile de haut
        position.setY(initialY - 1);
        Tile tile = maze.whatIsIn(position);
        if (monster.canGoTo(tile)) candidates.add(Direction.UP);
        position.setY(initialY);  // remise à l'état initial de la position

        // Tile de bas
        position.setY(initialY + 1);
        tile = maze.whatIsIn(position);
        if (monster.canGoTo(tile)) candidates.add(Direction.DOWN);
        position.setY(initialY);

        // Tile de gauche
        position.setX(initialX - 1);
        tile = maze.whatIsIn(position);
        if (monster.canGoTo(tile)) candidates.add(Direction.LEFT); position.setX(initialX);

        // Tile de droite
        position.setX(initialX + 1);
        tile = maze.whatIsIn(position);
        if (monster.canGoTo(tile)) candidates.add(Direction.RIGHT);
        position.setX(initialX);

        int boundForRandom = candidates.size() ;
        return candidates.isEmpty() ? Direction.IDLE : candidates.get(randomGenerator.nextInt(boundForRandom));
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
