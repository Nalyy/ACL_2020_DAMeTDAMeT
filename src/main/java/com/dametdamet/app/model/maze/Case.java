package com.dametdamet.app.model.maze;

import java.util.Objects;

public class Case {
    private final TypeCase type;

    /**
     * Crée une case de type donné.
     * @param type type de la case initialisé
     */
    public Case(TypeCase type){
        this.type = type;
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

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
