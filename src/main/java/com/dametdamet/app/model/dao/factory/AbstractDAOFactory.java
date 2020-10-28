package com.dametdamet.app.model.dao.factory;

import com.dametdamet.app.model.dao.dao.AbstractFileDAO;

public abstract class AbstractDAOFactory {

    public static final int TXT = 0;

    /**
     * Permet de récupérer la classe qui gère la lecture de fichier
     * @return Une classe qui gère la lecture de fichier
     */
    public abstract AbstractFileDAO getFileDAO();

    /**
     * Permet de récupérer la factory pour la DAO
     * @param value Valeur (constante) qui permet de récupérer la bonne factory
     * @return La factory qui correspond à value
     */
    public static AbstractDAOFactory getAbstractDAOFactory(int value){
        switch(value){
            case TXT:
            default:
                return new ConcreteFileDAOFactory();
        }
    }

}
