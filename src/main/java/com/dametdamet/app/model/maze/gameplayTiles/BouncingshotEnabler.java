package com.dametdamet.app.model.maze.gameplayTiles;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TileType;

public class BouncingshotEnabler extends Tile {

    public BouncingshotEnabler(){
        super(TileType.BOUNCINGSHOT_ENABLER);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        if (entity.canTrigger(this)){
        }

    }
}
