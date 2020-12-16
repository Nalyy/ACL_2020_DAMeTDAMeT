package com.dametdamet.app.model.entity;

import com.dametdamet.app.model.Direction;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.attack.Projectile;
import com.dametdamet.app.model.entity.attack.ProjectileMove;
import com.dametdamet.app.model.entity.projectileStrategies.MultiProjectileStrategy;
import com.dametdamet.app.model.entity.projectileStrategies.SingleProjectileStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ProjectileStatTest {

    ProjectileStat projStat1;
    ProjectileStat projStat2;
    ProjectileStat projStat3;
    ProjectileStat projStat4;
    ProjectileStat projStat5;

    @BeforeEach
    void setUp(){
        projStat1 = new ProjectileStat(3, 5, ProjectileMove.INSTANCE, SingleProjectileStrategy.INSTANCE);
        projStat2 = new ProjectileStat(3, 5, ProjectileMove.INSTANCE, MultiProjectileStrategy.INSTANCE);
        projStat3 = new ProjectileStat(Integer.MAX_VALUE, 5, ProjectileMove.INSTANCE, SingleProjectileStrategy.INSTANCE);
        projStat4 = new ProjectileStat(3, Integer.MAX_VALUE, ProjectileMove.INSTANCE, SingleProjectileStrategy.INSTANCE);
        projStat5 = new ProjectileStat(3, 5, ProjectileMove.INSTANCE, SingleProjectileStrategy.INSTANCE);
    }

    @Test
    void rightProjectileStat(){
        projStat1.addProjectileHP(5);
        Collection<Projectile> projs = projStat1.generateProjectiles(new Position(0,0), Direction.RIGHT);
        assertEquals(new Projectile(new Position(0, 0), Direction.RIGHT, 5, ProjectileMove.INSTANCE), projs.iterator().next());
    }

    @Test
    void right2ProjectileStat(){
        projStat2.addProjectileHP(5);
        Collection<Projectile> projs = projStat2.generateProjectiles(new Position(0,0), Direction.RIGHT);
        assertTrue(projs.contains(new Projectile(new Position(0, 0), Direction.RIGHT, 5, ProjectileMove.INSTANCE)));
        assertTrue(projs.contains(new Projectile(new Position(0, 0), Direction.UP, 5, ProjectileMove.INSTANCE)));
        assertTrue(projs.contains(new Projectile(new Position(0, 0), Direction.DOWN, 5,ProjectileMove.INSTANCE)));
    }

    @Test
    void right3ProjectileStat(){
        projStat2.reduceShootingCooldown(5);
        Collection<Projectile> projs = projStat2.generateProjectiles(new Position(0,0), Direction.RIGHT);
        assertTrue(projs.contains(new Projectile(new Position(0, 0), Direction.RIGHT, 5, ProjectileMove.INSTANCE)));
        assertTrue(projs.contains(new Projectile(new Position(0, 0), Direction.UP, 5, ProjectileMove.INSTANCE)));
        assertTrue(projs.contains(new Projectile(new Position(0, 0), Direction.DOWN, 5,ProjectileMove.INSTANCE)));
    }

    @Test
    void BoundaryProjectileStat(){
        projStat3.addProjectileHP(Integer.MAX_VALUE);
        Collection<Projectile> projs = projStat2.generateProjectiles(new Position(0,0), Direction.RIGHT);
        assertTrue(projs.contains(new Projectile(new Position(0, 0), Direction.RIGHT, 5, ProjectileMove.INSTANCE)));
        assertTrue(projs.contains(new Projectile(new Position(0, 0), Direction.UP, 5, ProjectileMove.INSTANCE)));
        assertTrue(projs.contains(new Projectile(new Position(0, 0), Direction.DOWN, 5,ProjectileMove.INSTANCE)));
    }

    @Test
    void Boundary2ProjectileStat(){
        projStat3.addProjectileBounce(Integer.MAX_VALUE);
        Collection<Projectile> projs = projStat2.generateProjectiles(new Position(0,0), Direction.RIGHT);

    }

    @Test
    void Boundary3ProjectileStat(){
        Assertions.assertThrows(AssertionError.class, () ->
        { projStat3.reduceShootingCooldown(Integer.MIN_VALUE);});

    }
    @Test
    void Boundary4ProjectileStat(){
        Assertions.assertThrows(AssertionError.class, () ->
                { projStat3.reduceShootingCooldown(-1);});
    }
}