package com.dametdamet.app.model.entity.projectileStrategies;

import com.dametdamet.app.model.Direction;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.attack.Projectile;
import com.dametdamet.app.model.entity.monster.MoveStrategy;

import java.util.Collection;

public interface GeneratorProjectileStrategy {

    Collection<Projectile> generateProjectiles(Position position,
                                               Direction direction,
                                               int nbHP,
                                               int nbBounces,
                                               MoveStrategy strategy);
}
