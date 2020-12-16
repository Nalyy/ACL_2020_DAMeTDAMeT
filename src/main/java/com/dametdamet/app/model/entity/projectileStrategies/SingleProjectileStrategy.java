package com.dametdamet.app.model.entity.projectileStrategies;

import com.dametdamet.app.model.Direction;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.attack.Projectile;
import com.dametdamet.app.model.entity.monster.MoveStrategy;

import java.util.ArrayList;
import java.util.Collection;

public enum SingleProjectileStrategy implements GeneratorProjectileStrategy {
    INSTANCE;

    @Override
    public Collection<Projectile> generateProjectiles(Position position, Direction direction, int nbHP, int nbBounces, MoveStrategy strategy) {
        // SingleProjectile : un seul projectile donc on initialise l'array list à 1
        Collection<Projectile> projectiles = new ArrayList<>(1);
        Projectile projectile = new Projectile(position, direction, nbHP, nbBounces, strategy);
        projectiles.add(projectile);
        return projectiles;
    }
}
