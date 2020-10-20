package com.dametdamet.app.model;

import java.lang.reflect.Type;

public class Maze{
    public static int LENGTH = 20;
    public static int HEIGHT = 20;
    private Case[][] maze;

    public Maze(){
        maze = new Case[LENGTH][HEIGHT];
        generate();
    }

    /**
     * Génère le labyrinthe.
     */
    private void generate(){
        for (int i = 0; i < LENGTH ; i++){
            for (int j = 0 ; j < HEIGHT ; j++){
                Case newCase = new Case(TypeCase.EMPTY);
                maze[i][j] = newCase;
            }
        }
    }

    /**
     * Retourne le type de la case à la position donnée.
     * @param position
     * @return TypeCase
     */
    public TypeCase whatIsIn(Position position){
        int x = position.getX();
        int y = position.getY();

        boolean isWithin = ( x < LENGTH - 1 && x >= 0) && ( y < HEIGHT - 1 && y > 0);

        return isWithin ? maze[position.getX()][position.getY()].getType() : TypeCase.OUTOFBOUND;
    }

    /**
     * Retourne vrai si la position correspond à une case existante et n'est pas un mur.
     * @param position
     * @return
     */
    public boolean isOpen(Position position){
        TypeCase type = whatIsIn(position);
        return type != TypeCase.WALL && type != TypeCase.OUTOFBOUND;
    }
}
