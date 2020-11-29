package com.dametdamet.app.model.entity.monster;

import com.dametdamet.app.model.Direction;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Maze;

public interface MoveStrategy {

    /**
     * Donne un labyrinthe à la stratégie.
     * @param maze le labyrinthe sur lequel se déroule le jeu
     */
    void setMaze(Maze maze);

    /**
     * Retourne la prochaine commande que le monstre va faire.
     * @param entity l'entité qui va exécuter la commande
     * @return Direction.IDLE, LEFT, RIGHT, UP, DOWN selon la prochaine direction du monstre.
     */
    Direction getNextDirection(Entity entity);

    /**
     * Vérifie si la stratégie a bien un labyrinthe où travailler.
     * @return vrai si la stratégie possède un labyrinthe.
     */
    boolean hasMaze();

    /**
     * Vérifie si la position donnée est bien dans le labyrinthe.
     *
     * @param position la position pour laquelle on demande
     * @return vrai si la position donnée est dans le labyrinthe.
     */
    boolean isInMaze(Position position);

}
