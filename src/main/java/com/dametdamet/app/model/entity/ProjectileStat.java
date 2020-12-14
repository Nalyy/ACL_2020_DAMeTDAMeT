package com.dametdamet.app.model.entity;

import com.dametdamet.app.model.Direction;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.attack.Projectile;
import com.dametdamet.app.model.entity.monster.MoveStrategy;
import com.dametdamet.app.model.entity.projectileStrategies.GeneratorProjectileStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class ProjectileStat {
    private int nbHP;
    private int nbBounces;
    private MoveStrategy moveStrategy;
    private GeneratorProjectileStrategy generatorStrategy;

    public ProjectileStat(int nbHP, int nbBounces, MoveStrategy moveStrategy, GeneratorProjectileStrategy generatorStrategy){
        this.nbHP = nbHP;
        this.nbBounces = nbBounces;
        this.moveStrategy = moveStrategy;
        this.generatorStrategy = generatorStrategy;

        Objects.requireNonNull(moveStrategy, "Il faut donner une stratégie de déplacement au projectile.");
        Objects.requireNonNull(generatorStrategy, "Il faut donner une stratégie de génération au projectile.");
        assert nbHP>0 : "Il faut que le projectile ait au moins 1 point de vie.";
        assert nbBounces >= 0 : "Il ne faut pas un nombre négatif de nombre de rebonds.";
    }

    public Collection<Projectile> shoot(Position position, Direction direction){
        return generatorStrategy.generateProjectiles(position,direction, nbHP, nbBounces, moveStrategy);
    }
}
