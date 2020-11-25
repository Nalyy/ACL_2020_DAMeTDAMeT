package com.dametdamet.app.model.entity.attack;

import com.dametdamet.app.model.Direction;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.entity.EntityType;
import com.dametdamet.app.model.entity.monster.MoveStrategy;

import java.util.Objects;

public class Projectile extends Entity {

    private final Direction direction;
    private MoveStrategy strategy;

    /**
     * Construit un projectile.
     * @param position la position initiale du projectile
     * @param direction la direction (fixe et non IDLE) du projectile
     * @param strategy la stratégie (non nulle) permettant au projectile de se mouvoir
     */
    public Projectile(Position position, Direction direction, MoveStrategy strategy) {
        super(position, EntityType.PROJECTILE);
        assert !direction.equals(Direction.IDLE) : "La direction du projectile est IDLE.";
        this.direction = direction;

        // Validité de la stratégie (@author Diana)
        Objects.requireNonNull(strategy, "La stratégie donnée à la construction du monstre est null.");
        assert strategy.hasMaze()  : "La stratégie donnée n'a pas de labyrinthe.";
        assert strategy.isInMaze(position) : "La position initiale est hors du labyrinthe.";

        this.strategy = strategy;
    }

    /**
     * Retourne la direction du projectile.
     */
    public Direction getDirection() { return this.direction; }

    /**
     * Retourne la direction du projectile ou Direction.IDLE s'il ne peut plus avancer.
     */
    public Direction getNextDirection() { return this.strategy.getNextDirection(this); }
}
