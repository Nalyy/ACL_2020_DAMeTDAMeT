package com.dametdamet.app.model.entity;

import com.dametdamet.app.model.Direction;
import com.dametdamet.app.model.Position;

import java.util.Objects;

public class Hero extends Entity {
    private int MAX_HP = 3;

    public Hero(Position position){
        super(position, EntityType.HERO);
        setDirection(Direction.DOWN);

        this.hp = MAX_HP;
        this.maxHp = MAX_HP;
        recoveryTime = 3000;
    }

    public Hero(Position position, ProjectileStat projectileStat) {
        super(position, EntityType.HERO, projectileStat);

        Objects.requireNonNull(projectileStat, "La stratégie des projectiles ne doit pas être nulle.");
        setDirection(Direction.DOWN);

        this.hp = MAX_HP;
        this.maxHp = MAX_HP;
        recoveryTime = 3000;

    }

}
