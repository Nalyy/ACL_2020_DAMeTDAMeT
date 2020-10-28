package com.dametdamet.app.model.maze;

public enum TypeCase {
    EMPTY('0'), WALL('1'), TREASURE('2'), OUTOFBOUND('\0');

    private final char value;

    TypeCase(char value){
        this.value = value;
    }

    /**
     * Permet de récupérer le type de Case à partir d'un caractère
     * @param c le caractère donné
     * @return le type de Case en fonction de c
     */
    public static TypeCase getValueOf(char c){
        if (c == WALL.value) {
            return WALL;
        }else if (c == TREASURE.value){
            return TREASURE;
        }
        return EMPTY;
    }
}
