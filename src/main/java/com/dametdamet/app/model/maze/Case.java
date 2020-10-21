package com.dametdamet.app.model.maze;

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
}
