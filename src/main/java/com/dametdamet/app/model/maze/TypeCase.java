package com.dametdamet.app.model.maze;

public enum TypeCase {
    EMPTY('0'), WALL('1'), TREASURE('2'), OUTOFBOUND('\0');

    private char value;

    TypeCase(char value){
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    public static TypeCase getValueOf(char c){
        if (c == WALL.value) {
            return WALL;
        }else if (c == TREASURE.value){
            return TREASURE;
        }
        return EMPTY;
    }
}
