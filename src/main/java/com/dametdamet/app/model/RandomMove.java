package com.dametdamet.app.model;

public enum RandomMove implements MoveStrategy {
    INSTANCE;

    private Maze maze;

    // Le constructeur d'une enum ne prend pas d'arguments
    RandomMove(){
    }

    public void setMaze(Maze maze){
        this.maze = maze;
    }
}
