package com.dametdamet.app.model.dao.factory;

import com.dametdamet.app.model.dao.dao.AbstractFileDAO;

public abstract class AbstractDAOFactory {

    public static final int TXT = 0;

    public abstract AbstractFileDAO getFileDAO();

    public static AbstractDAOFactory getAbstractDAOFactory(int value){
        switch(value){
            case TXT:
            default:
                return new ConcreteFileDAOFactory();
        }
    }

}
