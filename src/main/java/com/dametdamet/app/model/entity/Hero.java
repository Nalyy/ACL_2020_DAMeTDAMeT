package com.dametdamet.app.model.entity;

import com.dametdamet.app.model.Position;

public class Hero extends Entity {

    public Hero(Position position) {
        super(position, TypeEntity.HERO);
    }
}
