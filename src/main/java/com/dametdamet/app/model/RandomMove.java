package com.dametdamet.app.model;

import com.dametdamet.app.engine.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum RandomMove implements MoveStrategy {
    INSTANCE;

    private Maze maze;

    // Le constructeur d'une enum ne prend pas d'arguments
    RandomMove(){
    }

    public void setMaze(Maze maze){
        this.maze = maze;
    }

    @Override
    public Command getNextCommand(Monster monster) {
        Position position = monster.getPosition();
        int initialX = position.getX();
        int initialY = position.getY();
        TypeCase type;
        List<Command> candidates = new ArrayList<>(4); // 4 = nombre de commandes possible
        Random randomGenerator = new Random();

        /* On commence par regarder les commandes possibles à exécuter */
        // Case de haut
        position.setY(initialY + 1);
        if (maze.isOpen(position)) candidates.add(Command.UP);
        position.setY(initialY);  // remise à l'état initial de la position

        // Case de bas
        position.setY(initialY - 1);
        if (maze.isOpen(position)) candidates.add(Command.DOWN);
        position.setY(initialY);

        // Case de gauche
        position.setX(initialX - 1);
        if (maze.isOpen(position)) candidates.add(Command.LEFT);
        position.setX(initialX);

        // Case de droite
        position.setX(initialX + 1);
        if (maze.isOpen(position)) candidates.add(Command.RIGHT);
        position.setX(initialX);

        // nbAléatoire : random.nextInt(max - min + 1) + min = 3 - 0 + 1 + 0 = 2
        return candidates.isEmpty() ? Command.IDLE : candidates.get(randomGenerator.nextInt(3));
    }
}
