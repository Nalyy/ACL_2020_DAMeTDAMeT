package com.dametdamet.app.model;

public class Case {
    TypeCase type;

    /**
     * Crée une case de type donné.
     * @param type
     */
    Case(TypeCase type){
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
