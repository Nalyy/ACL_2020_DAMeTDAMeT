package com.dametdamet.app.model.maze.gameplayTiles;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TileType;

public class MultishootEnabler extends Tile {

    public MultishootEnabler() {
        super(TileType.MULTISHOOT_ENABLER);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        if (entity.canTrigger(this) & !isPressed()){
            entity.setMultiProjectileStrategy();
        }
    }
}
