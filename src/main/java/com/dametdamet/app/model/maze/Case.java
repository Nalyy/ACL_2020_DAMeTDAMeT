package com.dametdamet.app.model.maze;

import java.util.Objects;
import java.util.Random;

public class Case {
    private final TypeCase type;
    private int numSprite;

    /**
     * Crée une case de type donné.
     * @param type type de la case initialisé
     * @param numSprite numéro du sprite actuel de la case
     */
    public Case(TypeCase type,int numSprite){

        this.numSprite = numSprite;
        this.type = type;
    }

    /**
     * Crée une case de type donné.
     * @param type type de la case initialisé
     */
    public Case(TypeCase type){
        this.type = type;
        this.numSprite = 0;
    }

    /**
     * Retourne le type de la case.
     * @return TypeCase
     */
    public TypeCase getType(){
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Case aCase = (Case) o;
        return type == aCase.type;
    }

    public int getNumSprite() {
        return numSprite;
    }

    public void setNumSprite(int numSprite){
        this.numSprite = numSprite;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
