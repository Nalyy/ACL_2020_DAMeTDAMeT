package com.dametdamet.app.model.maze.gameplayTiles;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TileType;

public class BouncingshotEnabler extends Tile {

    private int NB_BOUNCE_TO_ADD = 1;

    public BouncingshotEnabler(){
        super(TileType.BOUNCINGSHOT_ENABLER);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        if (!isPressed() && entity.canTrigger(this)){
            entity.addProjectileBounce(NB_BOUNCE_TO_ADD);
            setPressed(true);
        }

    }
}
