package com.dametdamet.app.model.maze;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.Entity;

public class Teleportation extends Case {

    private Position destination;

    public Teleportation(Position dest) {
        super(TypeCase.TELEPORTATION);
        this.destination = dest;
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        super.applyEffect(game, entity);
        entity.moveTo(destination);
    }
}