package com.dametdamet.app.model.entity.monster;

import com.dametdamet.app.engine.Command;
import com.dametdamet.app.model.Direction;
import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.monster.Monster;
import com.dametdamet.app.model.maze.Maze;

public interface MoveStrategy {

    /**
     * Donne le jeu à la stratégie. Nécessaire si besoin de connaître la position du héros.
     * @param game
     */
    void setGame(PacmanGame game);

    /**
     * Retourne la prochaine commande que le monstre va faire.
     * @param monster le monstre qui va exécuter la commande
     * @return Command.IDLE, LEFT, RIGHT, UP, DOWN selon la prochaine direction du monstre.
     */
    Direction getNextDirection(Monster monster);

    /**
     * Vérifie si la stratégie a bien un labyrinthe où travailler.
     * @return vrai si la stratégie possède un labyrinthe.
     */
    boolean hasMaze();

    /**
     * Vérifie si la position donnée est bien dans le labyrinthe.
     * Sert à l'initialisation du monstre.
     *
     * @param position la position pour laquelle on demande
     * @return vrai si la position donnée est dans le labyrinthe.
     */
    boolean isInMaze(Position position);

}
