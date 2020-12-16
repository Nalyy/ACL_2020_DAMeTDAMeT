package com.dametdamet.app.model.maze.gameplayTiles;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TileType;

public class QuickshotEnabler extends Tile {

    private int COOLDOWN_RED = 100; // le nb de millisecondes à retirer au cooldown

    public QuickshotEnabler(){
        super(TileType.QUICKSHOT_ENABLER);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        if (!isPressed() && entity.canTrigger(this)){
            entity.reduceShootingCooldown(COOLDOWN_RED);
        }
    }
}
