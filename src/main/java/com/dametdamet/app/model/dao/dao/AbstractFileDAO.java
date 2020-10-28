package com.dametdamet.app.model.dao.dao;

import com.dametdamet.app.model.maze.Case;

public interface AbstractFileDAO {

    Case[][] load(String nomFichier);
}
