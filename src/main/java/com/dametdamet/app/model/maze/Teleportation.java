package com.dametdamet.app.model.maze;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;

public class Teleportation extends Case {

    public Teleportation() {
        super(TypeCase.TELEPORTATION);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        super.applyEffect(game, entity);
        game.teleport(entity);
    }
}
