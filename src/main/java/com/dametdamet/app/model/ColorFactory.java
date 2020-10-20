package com.dametdamet.app.model;

import java.awt.*;

public enum ColorFactory {

    INSTANCE;

    private final Color heroColor = Color.blue;
    private final Color monsterColor = Color.red;

    private final Color wallColor = Color.gray;
    private final Color emptyColor = Color.white;
    private final Color treasureColor = Color.yellow;

    private ColorFactory(){

    }

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
