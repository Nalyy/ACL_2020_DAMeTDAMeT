package com.dametdamet.app.model.entity;

import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.Timer;

public class Hero extends Entity {


    public Hero(Position position,int maxHp) {
        super(position, EntityType.HERO);
        hp = maxHp;
        this.maxHp = maxHp;
        recoveryTime = 3000;
    }
}
