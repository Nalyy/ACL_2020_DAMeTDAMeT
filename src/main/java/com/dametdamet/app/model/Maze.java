package com.dametdamet.app.model;

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

    }

    /**
     * Retourne le type de la case à la position donnée.
     * @param position
     * @return TypeCase
     */
    public TypeCase whatIsIn(Position position){
        return maze[position.getX()][position.getY()].getType();
    }
}
