package com.dametdamet.app.model.dao.factory;

import com.dametdamet.app.model.dao.dao.AbstractFileDAO;
import com.dametdamet.app.model.dao.dao.TxtDAO;

public class ConcreteFileDAOFactory extends AbstractDAOFactory{

    @Override
    public AbstractFileDAO getFileDAO() {
        return TxtDAO.INSTANCE;
    }
}
