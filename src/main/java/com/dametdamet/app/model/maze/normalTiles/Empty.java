package com.dametdamet.app.model.maze.normalTiles;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TypeCase;

public class Empty extends Tile {
    public Empty() {
        super(TypeCase.EMPTY);
    }
    public Empty(int numSprite) {
        super(TypeCase.EMPTY, numSprite);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        // Do nothing.
    }
}
