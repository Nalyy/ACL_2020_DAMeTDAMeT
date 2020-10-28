package com.dametdamet.app.model;

import com.dametdamet.app.engine.Command;
import com.dametdamet.app.model.maze.Maze;
import com.dametdamet.app.model.maze.TypeCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public enum RandomMove implements MoveStrategy {
    INSTANCE;
    private Maze maze;

    @Override
    public void setMaze(Maze maze){
        this.maze = maze;
    }

    @Override
    public boolean hasMaze(){
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

    @Override
    public Command getNextCommand(Monster monster){
        Objects.requireNonNull(maze, "La stratégie appliquée au monstre n'a pas de labyrinthe associé.");

        // On vérifie si le monstre a le droit de se re-déplacer tout de suite.
        if (!monster.isFinishedTimer()) return Command.IDLE;

        // Si oui, on reset son timer pour le prochain déplacement et on lui donne sa direction.
        monster.resetTimer();

        Position position = monster.getPosition();
        int initialX = position.getX();
        int initialY = position.getY();
        TypeCase type;
        List<Command> candidates = new ArrayList<>(4); // 4 = nombre de commandes possibles
        Random randomGenerator = new Random();

        /* On commence par regarder les commandes possibles à exécuter */
        // Case de haut
        position.setY(initialY - 1);
        if (maze.isNotWall(position)) candidates.add(Command.UP);
        position.setY(initialY);  // remise à l'état initial de la position

        // Case de bas
        position.setY(initialY + 1);
        if (maze.isNotWall(position)) candidates.add(Command.DOWN);
        position.setY(initialY);

        // Case de gauche
        position.setX(initialX - 1);
        if (maze.isNotWall(position)) candidates.add(Command.LEFT);
        position.setX(initialX);

        // Case de droite
        position.setX(initialX + 1);
        if (maze.isNotWall(position)) candidates.add(Command.RIGHT);
        position.setX(initialX);

        int boundForRandom = candidates.size() ;
        return candidates.isEmpty() ? Command.IDLE : candidates.get(randomGenerator.nextInt(boundForRandom));
    }

}
