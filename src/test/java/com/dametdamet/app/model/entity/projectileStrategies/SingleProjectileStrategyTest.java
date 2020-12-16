package com.dametdamet.app.model.entity.projectileStrategies;

import com.dametdamet.app.model.Direction;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.attack.Projectile;
import com.dametdamet.app.model.entity.attack.ProjectileMove;
import com.dametdamet.app.model.entity.monster.Monster;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class SingleProjectileStrategyTest {

    private GeneratorProjectileStrategy strat = MultiProjectileStrategy.INSTANCE;

    @Test
    void RightgenerateProjectiles() {
        Collection<Projectile> proj = strat.generateProjectiles(new Position(0,0), Direction.RIGHT,1, 0, ProjectileMove.INSTANCE);
        assertEquals(proj.iterator().next(), new Projectile(new Position(0, 0), Direction.RIGHT, 1, 0, ProjectileMove.INSTANCE));
    }

    @Test
    void Right2generateProjectiles() {
        Collection<Projectile> proj = strat.generateProjectiles(new Position(0, 0), Direction.UP, 10, 50, ProjectileMove.INSTANCE);
        assertEquals(proj.iterator().next(), new Projectile(new Position(0, 0), Direction.UP, 10, 50, ProjectileMove.INSTANCE));
    }

    @Test
    void Right3generateProjectiles() {
        Assertions.assertThrows(AssertionError.class, () ->
        {Collection<Projectile> proj = strat.generateProjectiles(new Position(0,0), Direction.IDLE,10, 50, ProjectileMove.INSTANCE); }
        );
   }

    @Test
    void BoundarygenerateProjectiles() {
        Collection<Projectile> proj = strat.generateProjectiles(new Position(0, 0), Direction.UP, Integer.MAX_VALUE, 50, ProjectileMove.INSTANCE);
        assertEquals(proj.iterator().next(), new Projectile(new Position(0, 0), Direction.UP, 50, 50, ProjectileMove.INSTANCE));
    }

    @Test
    void Boundary2generateProjectiles() {
        Collection<Projectile> proj = strat.generateProjectiles(new Position(0,0), Direction.UP, Integer.MIN_VALUE, 50, ProjectileMove.INSTANCE);
        assertEquals(proj.iterator().next(), new Projectile(new Position(0, 0), Direction.UP, 1, 50, ProjectileMove.INSTANCE));
    }
    @Test
    void Boundary3generateProjectiles() {
        Collection<Projectile> proj = strat.generateProjectiles(new Position(0,0), Direction.UP, 5, Integer.MAX_VALUE, ProjectileMove.INSTANCE);
        assertEquals(proj.iterator().next(), new Projectile(new Position(0, 0), Direction.UP, 5, Integer.MAX_VALUE, ProjectileMove.INSTANCE));
    }

    @Test
    void Boundary4generateProjectiles() {
        Collection<Projectile> proj = strat.generateProjectiles(new Position(0,0), Direction.UP, 5, Integer.MIN_VALUE, ProjectileMove.INSTANCE);

        assertEquals(proj.iterator().next(), new Projectile(new Position(0, 0), Direction.UP, 5, Integer.MIN_VALUE, ProjectileMove.INSTANCE));
    }

    @Test
    void Boundary5generateProjectiles() {
        Assertions.assertThrows(NullPointerException.class, () ->
                { Collection<Projectile> proj = strat.generateProjectiles(new Position(0,0), Direction.UP, 5, 0, null); }
        );
    }
}