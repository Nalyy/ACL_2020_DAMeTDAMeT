package com.dametdamet.app.model.maze.normalTiles;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TileType;

public class Empty extends Tile {
    public Empty() {
        super(TileType.EMPTY);
    }
    public Empty(int numSprite) {
        super(TileType.EMPTY, numSprite);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        // Do nothing.
    }
}
