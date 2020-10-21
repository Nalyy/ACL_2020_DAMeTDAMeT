package com.dametdamet.app.model.maze;

import com.dametdamet.app.model.Position;

public class Maze{
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;
    private Case[][] maze;

    /**
     * Initialise un Maze vide
     */
    public Maze(){
        maze = new Case[WIDTH][HEIGHT];
        generate();
    }

    /**
     * Génère un labyrinthe vide.
     */
    private void generate(){
        for (int x = 0; x < WIDTH; x++){
            for (int y = 0 ; y < HEIGHT; y++){
                maze[x][y] = new Case(TypeCase.EMPTY);
            }
        }
    }

    /**
     * Retourne le type de la case à la position donnée.
     * @param position position de la case que l'on cherche
     * @return la case correspondante
     */
    public Case whatIsIn(Position position){
        int x = position.getX();
        int y = position.getY();

        boolean isWithin = (x < WIDTH && x >= 0) && (y < HEIGHT && y >= 0);

        return isWithin ? maze[x][y] : new Case(TypeCase.OUTOFBOUND);
    }

    /**
     * Retourne vrai si la position correspond à une case qui n'est pas un mur et existante.
     * @param position postion de la case que l'on veut regarder
     * @return vrai si la position correspond à une case qui n'est pas un mur et existante.
     */
    public boolean isNotWall(Position position){
        return whatIsIn(position).getType() != TypeCase.WALL && isNotOutOfBound(position);
    }

    /**
     * Retourne vrai si la position correspond à une case qui est existante.
     * @param position postion de la case que l'on veut regarder
     * @return vrai si la position correspond à une case qui est existante.
     */
    public boolean isNotOutOfBound(Position position){
        return whatIsIn(position).getType() != TypeCase.OUTOFBOUND;
    }
}
