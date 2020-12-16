package com.dametdamet.app.model.entity.projectileStrategies;

import com.dametdamet.app.model.Direction;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.attack.Projectile;
import com.dametdamet.app.model.entity.monster.MoveStrategy;

import java.util.ArrayList;
import java.util.Collection;

public enum MultiProjectileStrategy implements GeneratorProjectileStrategy {
    INSTANCE;

    @Override
    public Collection<Projectile> generateProjectiles(Position position, Direction direction, int nbHP, int nbBounces, MoveStrategy strategy) {
        Collection<Projectile> projectiles = new ArrayList<>(3);
        Projectile proj1 = new Projectile(position, direction, nbBounces, strategy);
        projectiles.add(proj1);

        Direction direction2, direction3;
        switch (direction) {
            case UP:
            case DOWN:
                direction2 = Direction.LEFT;
                direction3 = Direction.RIGHT;
                break;
            case RIGHT:
            case LEFT:
                direction2 = Direction.UP;
                direction3 = Direction.DOWN;
                break;
            default:
                direction2 = Direction.IDLE;
                direction3 = Direction.IDLE;
        }
        Projectile proj2 = new Projectile(new Position(position), direction2, nbBounces, strategy);
        Projectile proj3 = new Projectile(new Position(position), direction3, nbBounces, strategy);
        projectiles.add(proj2);
        projectiles.add(proj3);

        return projectiles;
    }
}
