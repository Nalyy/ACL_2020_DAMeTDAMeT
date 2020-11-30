package com.dametdamet.app.model.dao.dao.maze;

import com.dametdamet.app.model.maze.Maze;

public interface AbstractMazeDAO {

    /**
     * Charge à partir d'un fichier un tableau à 2 dimensions de Cases
     * @param nomFichier nom du fichier à charger (présent dans le package ressources)
     * @return le tableau à deux dimensions pour le labyrinthe
     */
   Maze load(String nomFichier);
}
