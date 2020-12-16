package com.dametdamet.app.model.entity;

import com.dametdamet.app.model.Direction;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.Timer;
import com.dametdamet.app.model.entity.attack.Projectile;
import com.dametdamet.app.model.entity.monster.MoveStrategy;
import com.dametdamet.app.model.entity.projectileStrategies.GeneratorProjectileStrategy;
import com.dametdamet.app.model.entity.projectileStrategies.MultiProjectileStrategy;
import com.dametdamet.app.model.entity.projectileStrategies.SingleProjectileStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class ProjectileStat {
    private int nbHP;
    private int nbBounces;
    private MoveStrategy moveStrategy;
    private GeneratorProjectileStrategy generatorStrategy;

    protected Timer projectileTimer;
    protected int projectileCooldown = 750;

    public ProjectileStat(int nbHP, int nbBounces, MoveStrategy moveStrategy, GeneratorProjectileStrategy generatorStrategy){
        this.nbHP = nbHP;
        this.nbBounces = nbBounces;
        this.moveStrategy = moveStrategy;
        this.generatorStrategy = generatorStrategy;

        Objects.requireNonNull(moveStrategy, "Il faut donner une stratégie de déplacement au projectile.");
        Objects.requireNonNull(generatorStrategy, "Il faut donner une stratégie de génération au projectile.");
        assert nbHP>0 : "Il faut que le projectile ait au moins 1 point de vie.";
        assert nbBounces >= 0 : "Il ne faut pas un nombre négatif de nombre de rebonds.";

        projectileTimer = new Timer();
    }

    public Collection<Projectile> generateProjectiles(Position position, Direction direction){
        Collection<Projectile> projectiles = new ArrayList<>();

        if (projectileTimer.isFinished()) {
            projectileTimer.top(projectileCooldown);
            projectiles.addAll(generatorStrategy.generateProjectiles(position, direction, nbHP, nbBounces, moveStrategy));
        }

        return projectiles;

    }

    public void setMultiProjectileStrategy(){
        generatorStrategy = MultiProjectileStrategy.INSTANCE;
    }

    public void reduceShootingCooldown(int toSubstract){
        assert(toSubstract >= 0): "On ne peut pas soustraire un nombre négatif";
        projectileCooldown -= toSubstract;

        if (projectileCooldown < 0) projectileCooldown = 0;
    }

    public void addProjectileHP(int toAdd) {
        nbHP += toAdd;
        if(nbHP <= 0){
            nbHP = 1;
        }
        if(nbHP >= 50){
            nbHP = 50;
        }
    }

    public void addProjectileBounce(int toAdd) {
        nbBounces += toAdd;
    }
}
