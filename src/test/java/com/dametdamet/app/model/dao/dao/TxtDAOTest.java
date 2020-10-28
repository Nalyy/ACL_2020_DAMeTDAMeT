package com.dametdamet.app.model.dao.dao;

import com.dametdamet.app.model.dao.factory.AbstractDAOFactory;
import com.dametdamet.app.model.maze.Case;

import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.maze.TypeCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TxtDAOTest {

    @Test
    void load() {
        AbstractDAOFactory ab = AbstractDAOFactory.getAbstractDAOFactory(AbstractDAOFactory.TXT);

        Case[][] cases = ab.getFileDAO().load("maze_vide.txt");
        /*
        Test avec un labyrinthe vide :
        Contenu :
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        000000000000000
        */

        for (Case[] aCase : cases) {
            for (Case value : aCase) {
                assertEquals(value.getType(), TypeCase.EMPTY);
            }
        }

        cases = ab.getFileDAO().load("maze_rempli.txt");

        /*
        Test avec un labyrinthe complètement rempli :
        Contenu :
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
        111111111111111
         */
        for (Case[] aCase : cases) {
            for (Case value : aCase) {
                assertEquals(value.getType(), TypeCase.WALL);
            }
        }
    }
}