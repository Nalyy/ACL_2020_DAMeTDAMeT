package com.dametdamet.app.model.graphic.factory;

import com.dametdamet.app.model.TypeEntity;

import com.dametdamet.app.model.maze.TypeCase;

import java.awt.*;

public enum ColorFactory {

    INSTANCE;

    private final Color heroColor = Color.blue;
    private final Color monsterColor = Color.red;

    private final Color wallColor = Color.gray;
    private final Color emptyColor = Color.white;
    private final Color treasureColor = Color.yellow;

    /**
     * retourne une couleur selon le type d'entité
     * @param type type de l'entité à colorier
     * @return la couleur correspondante au type d'entité en paramètre
     */
    public Color getEntityColor(TypeEntity type){
        switch (type){
            case HERO:
                return heroColor;
            case MONSTER:
                return monsterColor;
            default:
                return Color.black;
        }
    }

    /**
     * retourne une couleur selon le type de case
     * @param type type de l'entité à colorier
     * @return la couleur correspondante au type de case en paramètre
     */
    public Color getCaseColor(TypeCase type){
        switch (type){
            case WALL:
                return wallColor;
            case EMPTY:
                return emptyColor;
            case TREASURE:
                return treasureColor;
            default:
                return Color.green;
        }
    }




}
