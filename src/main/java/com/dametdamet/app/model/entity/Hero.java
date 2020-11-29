package com.dametdamet.app.model.entity;

import com.dametdamet.app.model.Direction;
import com.dametdamet.app.model.Position;

public class Hero extends Entity {

    public Hero(Position position,int maxHp) {
        super(position, EntityType.HERO);
        setDirection(Direction.DOWN);
        hp = maxHp;
        this.maxHp = maxHp;
        recoveryTime = 3000;
    }
}
