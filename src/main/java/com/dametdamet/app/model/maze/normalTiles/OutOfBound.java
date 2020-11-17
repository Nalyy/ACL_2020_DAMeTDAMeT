package com.dametdamet.app.model.maze.normalTiles;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TypeCase;

public class OutOfBound extends Tile {
    public OutOfBound() {
        super(TypeCase.OUTOFBOUND);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        // Do nothing.
    }
}
